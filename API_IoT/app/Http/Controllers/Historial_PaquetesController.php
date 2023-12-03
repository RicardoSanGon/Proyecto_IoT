<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\Models\Historial_Paquete;
use App\Models\Paquete;
use Illuminate\Http\Request;
use Tymon\JWTAuth\Facades\JWTAuth;

class Historial_PaquetesController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\JsonResponse
     */
    public function index(Request  $request,$id)
    {
        $userId=$this->getIDbyHeader($request->header('Authorization'));
        $historical=Historial_Paquete::where('paquete_id',$id)
                    ->where('user_id',$userId)->get();
        return response()->json(['data'=>$historical],200);
    }

    public function getIDbyHeader($header)
    {
        $token=substr($header,7);
        $payload=JWTAuth::parseToken($token);
        return $payload->getPayload()['sub'];
    }
}