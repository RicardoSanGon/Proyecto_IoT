<?php

use App\Http\Controllers\Historial_PaquetesController;
use App\Http\Controllers\PaquetesController;
use App\Http\Controllers\UsuariosController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

Route::post('/registry/user',[UsuariosController::class,'store']);


Route::get('/vericacionemail/{token}',[UsuariosController::class,'verification_email'])
    ->name('verificar')
    ->middleware('signed');

Route::get('/resendemail/{token}',[UsuariosController::class,'ReenviarEmail']);
Route::post('/login/user',[UsuariosController::class,'Login']);
Route::post('/logout/user',[UsuariosController::class,'LogOut']);

Route::middleware(['status.verify','token.verify'])->group(function (){
    Route::get('/info/pack/sensor',[PaquetesController::class,'index']);
    Route::post('/create/pack',[PaquetesController::class,'store']);
    Route::put('/update/pack/{id}',[PaquetesController::class,'update']);
    Route::get('/info/pack/historial/{id}',[Historial_PaquetesController::class,'index']);
    Route::post('/led/update',[PaquetesController::class,'setLed']);
});
