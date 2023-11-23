#include "DHT.h"
int DHTPIN[]={12};
#define DHTTYPE DHT11

DHT dht(DHTPIN[0], DHTTYPE);

String Sensor[]={"01"};
int triggerPin[]={7};
int Usize=sizeof(triggerPin)/sizeof(triggerPin[0]);
int echoPin[]={8};
int cm = 0;
const int distancia_limite=10000;

long readUltrasonicDistance(int trg, int ech)
{
  pinMode(trg, OUTPUT); 
  digitalWrite(trg, LOW);
  delayMicroseconds(2);
  digitalWrite(trg, HIGH);
  delayMicroseconds(10);
  digitalWrite(trg, LOW);
  pinMode(ech, INPUT);
  return pulseIn(ech, HIGH);
}

void temperatura_humedad(String id)
{
  float temperatura = dht.readTemperature();
  float humedad = dht.readHumidity();
  Serial.println("TP"+id+":"+String(temperatura));
  delay(500);
  Serial.println("HE"+id+":"+String(humedad));
}

void setup()
{
  Serial.begin(9600);
  dht.begin();
}

void loop()
{
  
  cm = 0.01723 * readUltrasonicDistance(triggerPin[0],echoPin[0]);
  Serial.println("US"+Sensor[0]+":"+String(cm));
  temperatura_humedad(Sensor[0]);
  delay(500);
}