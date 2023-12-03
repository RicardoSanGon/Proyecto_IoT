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
        Schema::create('historial_paquetes', function (Blueprint $table) {
            $table->id();
            $table->double('datos_sensor_ph');
            $table->double('datos_sensor_turbidez');
            $table->double('datos_sensor_temperatura');
            $table->double('datos_sensor_conductividad');
            $table->double('datos_sensor_nivel_agua');
            $table->foreignId('user_id')->references('id')->on('users');
            $table->foreignId('paquete_id')->references('id')->on('paquetes');
            $table->timestamp('fecha_hora');
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
