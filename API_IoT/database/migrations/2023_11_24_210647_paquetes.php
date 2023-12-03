<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('paquetes', function (Blueprint $table) {
            $table->id();
            $table->string('nombre_paquete');
            $table->string('lugar_paquete');
            $table->double('datos_sensor_ph')->default(0);
            $table->double('datos_sensor_turbidez')->default(0);
            $table->double('datos_sensor_temperatura')->default(0);
            $table->double('datos_sensor_conductividad')->default(0);
            $table->double('datos_sensor_nivel_agua')->default(0);
            $table->foreignId('user_id')->references('id')->on('users');
            $table->string('fecha_hora')->default(now());
            $table->boolean('status')->default(true);
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        //
    }
};
