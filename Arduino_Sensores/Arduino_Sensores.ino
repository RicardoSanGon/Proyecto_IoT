
#include <AdafruitIO_WiFi.h>
#include <esp_log.h>

// Configuración de la red WiFi
#define SSID "Megacable_2.4G_3864"
#define PASS "aGdd6Ey3"

// Configuración de la API de Adafruit
#define IO_NAME "ricardo_sanchz"
#define IO_KEY "aio_SBcS73PeGdgkiVtkIIvcxZgBZtBA"
const char* feedGroup = "proyecto-iot";
AdafruitIO_WiFi io(IO_NAME, IO_KEY, SSID, PASS);

// Pines de los sensores
const int pHpin = A0;
const int turbiditypin = T1;
const int temppin = T2;
const int conductancypin = A3;
const int waterlevelpin = A4;

AdafruitIO_Group *grupo;
AdafruitIO_Feed *TemperaturaFeed;
AdafruitIO_Feed *ConductividadFeed = io.feed("conductividad");
AdafruitIO_Feed *waterlevelFeed = io.feed("nivel_agua");
AdafruitIO_Feed *PhFeed = io.feed("ph");
AdafruitIO_Feed *TurbidezFeed = io.feed("turbidez");


void setup() {
  Serial.begin(115200);
  // Inicialización de los sensores
  //pinMode(pHpin, INPUT);
  //pinMode(turbiditypin, INPUT);
  //pinMode(temppin, INPUT);
  //pinMode(conductancypin, INPUT);
  //pinMode(waterlevelpin, INPUT);
    io.connect();
    grupo = io.group("Proyecto_IoT");
    randomSeed(analogRead(0));
    while(io.status() < AIO_CONNECTED) {
    Serial.print(".");
    delay(500);
  }

  // we are connected
  Serial.println();
  Serial.println(io.statusText());

}

void loop() {
  // Lectura de los valores de los sensores
  //float pHValue = analogRead(pHpin);
  //float turbidityValue = analogRead(turbiditypin);
  //float tempValue = analogRead(temppin);
  //float conductivityValue = analogRead(conductancypin);
  //float waterLevelValue = analogRead(waterlevelpin);
  int numeroAleatorio = random(101);
  grupo->set("temperatura",numeroAleatorio);
  grupo->save();
  Serial.println("Enviado:"+String(numeroAleatorio));

  delay(10000); // Esperar 1 minuto antes de volver a enviar los datos
}