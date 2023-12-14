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

        protected $aio_key="aio_IvxY42djLlmC59j3zATdrbwqvhH0";
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

        $pack=Paquete::find($id);
        if(!$pack)
            return response()->json(['msg'=>'No se encontro el paquete'],404);
        if($pack->status===0)
            return response()->json(['msg'=>'El paquete esta desactivado'],400);
        $sensor=$this->getDatos();
        $pack->datos_sensor_conductividad=$sensor[0];
        $pack->datos_sensor_nivel_agua=$sensor[1];
        $pack->datos_sensor_ph=$sensor[2];
        $pack->datos_sensor_temperatura=$sensor[3];
        $pack->datos_sensor_turbidez=$sensor[4];
        $pack->fecha_hora=$sensor[5];
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

    public function setLed(Request $request)
    {
        $response=Http::withHeaders([
            'X-AIO-Key'=>'aio_qeBY509JfOkTIfM2iAjGY5PztKKr'
        ])->post("https://io.adafruit.com/api/v2/Ang3L/feeds/proyecto-iot.led/data",
            ['value'=>$request->estado]);
        if($response->successful())
            return response()->json(['msg'=>'Led actualizado con exito'],$response->status());
        else
            return response()->json(['msg'=>'Error al actualizar el led'],$response->status());
    }
    //Linea para subir cambios

    public function getDatos(){
        $response=null;
        $data=[
            'proyecto-iot.conductivdad',
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
            'fecha'
        ];
        foreach ($data as $index=>$item){
            $response=Http::get("https://io.adafruit.com/api/v2/{$this->aio_username}/feeds/{$item}/data/last",
                ['X-AIO-Key'=>$this->aio_key]);
            $sensors[$index]=$response->json('value');
        }
        $sensors[5]=$response->json('updated_at');
        $sensors[5]=Carbon::parse($sensors[5])->setTimezone('UTC');
        $sensors[5]->setTimezone('America/Monterrey');
        $sensors[5]=$sensors[5]->format('d/m/Y h:i:s A');
        return $sensors;
    }
}
