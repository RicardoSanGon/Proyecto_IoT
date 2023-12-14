#include <AdafruitIO_WiFi.h>
#include <OneWire.h>
#include <DallasTemperature.h>

// Configuración de la red WiFi
#define SSID "MSI 0193"
#define PASS "12345678"

// Configuración de la API de Adafruit
#define IO_NAME "Ang3L"
#define IO_KEY "aio_qeBY509JfOkTIfM2iAjGY5PztKKr"
const char *feedGroup = "proyecto-iot";
AdafruitIO_WiFi io(IO_NAME, IO_KEY, SSID, PASS);

int pinLed = 27; // LED

void handleLed(AdafruitIO_Data *data) {
  int ledStatus = data->toInt();

  if (ledStatus == 0) {
    digitalWrite(pinLed, LOW); // Apaga el LED
    Serial.println("LED apagado");
  } else if (ledStatus == 1) {
    digitalWrite(pinLed, HIGH); // Enciende el LED
    Serial.println("LED encendido");
  }
}

// Pines de los sensores
const int pHpin = 34;
const int turbiditypin = 35;
const int tdsPin = 32;
const int oneWireBus = 33;

// Pines del sensor ultrasónico y LEDs
#define TRIGGER_PIN 25
#define ECHO_PIN 26
const int greenLEDPin = 12; // LED verde
const int yellowLEDPin = 13; // LED amarillo
const int redLEDPin = 14; // LED rojo

AdafruitIO_Group *grupo;
AdafruitIO_Feed *PhFeed;
AdafruitIO_Feed *TurbidezFeed;
AdafruitIO_Feed *TDSFeed;
AdafruitIO_Feed *TemperaturaFeed;
AdafruitIO_Feed *UltrasonicoFeed;

OneWire oneWire(oneWireBus);
DallasTemperature sensors(&oneWire);

void setup()
{
  Serial.begin(115200);

  // InicializaciAR de los sensores
  pinMode(turbiditypin, INPUT);
  pinMode(tdsPin, INPUT);
  sensors.begin();

  // LEDS ULTRASONICO
  pinMode(TRIGGER_PIN, OUTPUT);
  pinMode(ECHO_PIN, INPUT);
  pinMode(greenLEDPin, OUTPUT);
  pinMode(yellowLEDPin, OUTPUT);
  pinMode(redLEDPin, OUTPUT);

  digitalWrite(greenLEDPin, LOW);
  digitalWrite(yellowLEDPin, LOW);
  digitalWrite(redLEDPin, LOW);

  pinMode(pinLed, OUTPUT);
  io.connect();
  AdafruitIO_Feed *ledFeed = io.feed("proyecto-iot.led");
  ledFeed->onMessage(handleLed);

  grupo = io.group(feedGroup);

  while (io.status() < AIO_CONNECTED)
  {
    Serial.print(".");
    delay(500);
  }

  // Estamos conectados
  Serial.println();
  Serial.println(io.statusText());
}

void loop()
{
  io.run();
  // Lectura de pH
  float pH = analogRead(pHpin);
  float pHValue = mapearPH(pH);

  // Medir la turbidez
  float turbidityValue = medirTurbidez();

  // Lectura del sensor de TDS
  int tdsValue = analogRead(tdsPin);
  float tdsValueMapped = mapearTDS(tdsValue);

  float temperaturaC = obtenerTemperatura();

  // Distancia 
  float ultrasonicoValue = obtenerDistanciaUltrasonico();

  if (ultrasonicoValue >= 0 && ultrasonicoValue <= 5)
  {
    // Verde
    digitalWrite(greenLEDPin, HIGH);
    digitalWrite(yellowLEDPin, LOW);
    digitalWrite(redLEDPin, LOW);
  }
  else if (ultrasonicoValue >= 6 && ultrasonicoValue <= 18)
  {
    // Amarillo
    digitalWrite(greenLEDPin, LOW);
    digitalWrite(yellowLEDPin, HIGH);
    digitalWrite(redLEDPin, LOW);
  }
  else if (ultrasonicoValue >= 19)
  {
    // Rojo
    digitalWrite(greenLEDPin, LOW);
    digitalWrite(yellowLEDPin, LOW);
    digitalWrite(redLEDPin, HIGH);
  }
  else
  {
    digitalWrite(greenLEDPin, LOW);
    digitalWrite(yellowLEDPin, LOW);
    digitalWrite(redLEDPin, LOW);
  }

  // Envío de los valores a través de la API de AdafruitIO
  grupo->set("ph", pHValue);
  grupo->set("turbidez", turbidityValue);
  grupo->set("conductivdad", tdsValueMapped); 
  grupo->set("temperatura", temperaturaC);
  grupo->set("nivel_agua", ultrasonicoValue);
  grupo->save();

  Serial.println("Enviado");
  Serial.println("PH01 " + String(pHValue));
  Serial.println("TU02 " + String(turbidityValue));
  Serial.println("TD03 " + String(tdsValueMapped));
  Serial.println("TS04 " + String(temperaturaC));
  Serial.println("US05 " + String(ultrasonicoValue));

  delay(30000);
}

float mapearPH(int valor)
{
  float pHMin = 0.0;
  float pHMax = 14.0;
  float valorMin = 0;   
  float valorMax = 4095; 

  float pH = map(valor, valorMin, valorMax, pHMin, pHMax);
  return pH;
}

float mapearTDS(int lectura)
{
  const float valorMinimo = 0.0;    
  const float valorMaximo = 1000.0; 
  const int lecturaMinima = 0;     
  const int lecturaMaxima = 4095;   

  float valorTDS = map(lectura, lecturaMinima, lecturaMaxima, valorMinimo, valorMaximo);
  return valorTDS;
}

float obtenerTemperatura()
{
  sensors.requestTemperatures();
  return sensors.getTempCByIndex(0);
}

float obtenerDistanciaUltrasonico()
{
  digitalWrite(TRIGGER_PIN, HIGH);
  delayMicroseconds(10);
  digitalWrite(TRIGGER_PIN, LOW);

  unsigned long duration = pulseIn(ECHO_PIN, HIGH);

  float distance = (duration / 2.0) * 0.0343;

  return distance;
}

float medirTurbidez()
{
  int lecturaTurbidez = analogRead(turbiditypin);

  float nivelTurbidez = convertirTurbidez(lecturaTurbidez);

  return nivelTurbidez;
}

float convertirTurbidez(int lecturaAnaloga)
{
  const int valorMinimo = 0;      
  const int valorMaximo = 1023;   
  const float turbidezMinima = 0.0; 
  const float turbidezMaxima = 100.0; 

  float valorTurbidez = map(lecturaAnaloga, valorMinimo, valorMaximo, turbidezMinima, turbidezMaxima);
  return valorTurbidez;
}