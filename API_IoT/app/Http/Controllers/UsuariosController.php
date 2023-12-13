<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\Mail\VericarEmail;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Mail;
use Illuminate\Support\Facades\URL;
use Illuminate\Support\Facades\Validator;
use Tymon\JWTAuth\Exceptions\JWTException;
use Tymon\JWTAuth\Facades\JWTAuth;

class UsuariosController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\JsonResponse
     */
    public function index(Request $request)
    {
        $header=$request->header('Authorization');
        $idUser=$this->getIDbyHeader($header);
        $user=User::find($idUser);
        if (!$user)
            return response()->json(['msg'=>'No se encontro el usuario'],404);
        return response()->json(['data'=>$user],200);
    }

    public function store(Request $request)
    {
        $validator=Validator::make($request->all(),[
            'nombre'=>'required|string|max:255',
            'email'=>'required|string|email|max:255|unique:users',
            'password'=>'required|string|min:6|confirmed'
        ]);
        if ($validator->fails())
            return response()->json(['msg'=>'Error al validar los campos','error'=>$validator->errors()],400);
        $user=new User();
        $user->nombre=$request->nombre;
        $user->email=$request->email;
        $user->password=bcrypt($request->password);
        try {
            $user->save();
            $token=JWTAuth::fromUser($user);
            $url=URL::temporarySignedRoute('verificar',now()->addHours(1),['token'=>$token]);
            Mail::to($request->email)->send(new VericarEmail($request->nombre,$url));
        }catch (\Exception $e){
            $user->delete();
            return response()->json(['msg'=>'Error al crear el usuario','error'=>$e->getMessage()],500);
        }

        return response()->json(['msg'=>'Usuario creado correctamente','data'=>$user],201);
    }


    public function update(Request $request)
    {
        $header=$request->header('Authorization');
        $validator=Validator::make($request->all(),[
            'nombre'=>'required|string|max:255',
            'password'=>'required|string|min:6'
        ]);
        if ($validator->fails())
            return response()->json(['msg'=>'Error al validar los campos','error'=>$validator->errors()],400);
        $user=User::find($this->getIDbyHeader($header));
        if (!$user)
            return response()->json(['msg'=>'No se encontro el usuario'],404);
        $user->nombre=$request->nombre;
        $user->password=bcrypt($request->password);
        try {
            $user->save();
        }catch (\Exception $e){
            return response()->json(['msg'=>'Error al actualizar el usuario','error'=>$e->getMessage()],500);
        }
        return response()->json(['msg'=>'Usuario actualizado correctamente','data'=>$user],200);
    }

    public function destroy(Request $request)
    {
        $header=$request->header('Authorization');
        $user=User::find($this->getIDbyHeader($header));
        if (!$user)
            return response()->json(['msg'=>'No se encontro el usuario'],404);
        try {
            $user->softDelete();
            $user->status=false;
        }catch (\Exception $e){
            return response()->json(['msg'=>'Error al eliminar el usuario','error'=>$e->getMessage()],500);
        }
        return response()->json(['msg'=>'Usuario eliminado correctamente'],202);
    }

    public function Login(Request $request)
    {
        $credentials=$request->only('email','password');
        try {
            if (!$token=JWTAuth::attempt($credentials))
                return response()->json(['msg'=>'Credenciales incorrectas'],401);
        }catch (JWTException $e){
            return response()->json(['msg'=>'No se pudo crear el token'],500);
        }
        $user=User::where('email',$request->email)->first();
        if (!$user) {
            return response()->json(['msg' => 'No se encontro el usuario'], 404);
        }
        if (!$user['status'])
            return response()->json(['msg'=>'El usuario no esta activo'],401);
        return response()->json(['msg'=>'Login Successfully','data'=>$user,'token'=>$token],200);
    }

    public function LogOut(Request $request)
    {
        $user=User::where('email',$request->email)->first();
        if (!$user) {
            return response()->json(['msg' => 'No se encontro el usuario'], 404);
        }
        $userpassword=$user->password;
        if(!$userpassword==bcrypt($request->password))
        {
            return response()->json(['msg'=>'Password not valid'],403);
        }
        $token = JWTAuth::parseToken();
        try
        {
            $token->invalidate();
            return response()->json(["msg"=>"LogOut Successfully"],200);
        }
        catch (JWTException $e)
        {
            return response()->json(["msg"=>"LogOut failed"],500);
        }
    }

    /**
     * @throws JWTException
     */
    public function getIDbyHeader($auth){
        $token = substr($auth, 7);
        $payload = JWTAuth::parseToken($token);
        return $payload->getPayload()['sub'];
    }

    public function getIDbyToken($token){
        $payload = JWTAuth::parseToken($token);
        return $payload->getPayload()['sub'];
    }

    public function verification_email($token)
    {
        $url='http://127.0.0.1:8000/api/resendemail/'.$token;
        try{
            JWTAuth::parseToken($token)->authenticate();
        }catch(JWTException $e){
            return response()->view('ErrorEmail',['reenviar_email'=>$url]);
        }
        $user=User::find($this->getIDbyToken($token));
        if(!$user)
            return response()->view('ErrorEmail',['reenviar_email'=>$url]);
        $user->status=true;
        $user->save();
        return response()->view('AcceptedEmail');

    }

    public function ReenviarEmail($token)
    {
        $user=User::find($this->getIDbyToken($token));
        if(!$user)
            return response()->view('ErrorEmail');
        $url=URL::temporarySignedRoute('verificar',now()->addHours(1),['token'=>$token]);
        Mail::to($user->email)->send(new VericarEmail($user->nombre,$url));
        return response()->view('ReSendEmail');
    }

}
