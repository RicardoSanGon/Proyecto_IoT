<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\Models\Paquete;
use Carbon\Carbon;
use Illuminate\Support\Facades\Validator;
use http\Env;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;
use Tymon\JWTAuth\Facades\JWTAuth;

class PaquetesController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\JsonResponse
     */

        protected $aio_key="aio_SBcS73PeGdgkiVtkIIvcxZgBZtBA";
        protected $aio_username="ricardo_sanchz";
    public function index(Request $request)
    {
        $userId=$this->getIdByHeader($request->header('Authorization'));
        $packs=Paquete::all();
        $user_packs=Paquete::where('user_id',$userId)->get();
        if(!$packs)
            return response()->json(['msg'=>'No se encontraron paquetes'],404);
        return response()->json(['data'=>$user_packs],200);
    }


    public function store(Request $request)
    {
        $userId=$this->getIdByHeader($request->header('Authorization'));
        $validation=Validator::make($request->all(),[
            'nombre'=>'required|string|max:255',
            'lugar'=>'required|string|max:255'
        ]);
        if ($validation->fails())
            return response()->json(['msg'=>'Error al validar los campos','error'=>$validation->errors()],400);
        $pack=new Paquete();
        $pack->nombre_paquete=$request->nombre;
        $pack->lugar_paquete=$request->lugar;
        $pack->user_id=$userId;
        $pack->fecha_hora=now();
        $pack->save();
        return response()->json(['msg'=>'Paquete creado con exito','data'=>$pack],201);
    }



    public function update($id)
    {
        $sensor=$this->getDatos();
        $pack=Paquete::find($id);
        if(!$pack)
            return response()->json(['msg'=>'No se encontro el paquete'],404);
        if($pack->status===0)
            return response()->json(['msg'=>'El paquete esta desactivado'],400);
        $pack->datos_sensor_ph=$sensor['ph'];
        $pack->datos_sensor_turbidez=$sensor['turbidez'];
        $pack->datos_sensor_temperatura=$sensor['temperatura'];
        $pack->datos_sensor_conductividad=$sensor['conductividad'];
        $pack->datos_sensor_nivel_agua=$sensor['nivel-agua'];
        $pack->fecha_hora=$sensor['fecha'];
        $pack->save();
        return response()->json(['msg'=>'Datos actualizados con exito','data'=>$pack],202);
    }

    public function destroy($id)
    {
        $pack=Paquete::find($id);
        if(!$pack)
            return response()->json(['msg'=>'No se encontro el paquete'],404);
        $pack->status=0;
        return response()->json(['msg'=>'Paquete desactivado con exito'],200);
    }

    public function getIdByHeader($header){
        $token=JWTAuth::parseToken($header);
        return $token->getPayload()->get('sub');
    }

    public function getDatos(){
        $response=null;
        $data=[
            'proyecto-iot.conductividad',
            'proyecto-iot.nivel-agua',
            'proyecto-iot.ph',
            'proyecto-iot.temperatura',
            'proyecto-iot.turbidez'
        ];

        $sensors=[
            'conductividad',
            'nivel-agua',
            'ph',
            'temperatura',
            'turbidez',
        ];
        foreach ($data as $index=>$item){
            $response=Http::get("https://io.adafruit.com/api/v2/{$this->aio_username}/feeds/{$item}/data/last",
                ['X-AIO-Key'=>$this->aio_key]);
            $sensors[$index]=$response->json('value');
        }
        $sensors['fecha']=$response->json('updated_at');
        $sensors['fecha']=Carbon::parse($sensors['fecha'])->setTimezone('UTC');
        $sensors['fecha']->setTimezone('America/Monterrey');
        $sensors['fecha']=$sensors['fecha']->format('d/m/Y h:i:s A');
        return $sensors;
    }
}
