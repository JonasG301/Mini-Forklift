#include "simpletools.h"
#include "ping.h"
#include "abdrive360.h"
#include "stdio.h"
#include "servo360.h"
#include "wifi.h"
#include "stdio.h"


// SYSTEM CONFIG

#define DEBUG 1
#define DEFAULT_SPEED 10

#define THRESHOLD 1 // how much offset (distance to wall vs target distance) is ok in cm

typedef enum { false, true } bool; // definition of boolean


fdserial* wifi_connection;

// Interfaces

void drive_forward();
void drive_backwards();
void turn_right();
void turn_left();
void left_hard();
void right_hard();
void stop();
void turn_90deg();
void communicate_thread();
void fire();
void go_up();
void go_down();
                 // Include simple tools

volatile char d; // outgoing character buffer
  char string[20] = "";              // String feld aufteilen - siehe andere datei
  char* ptr = NULL;              //in funktion packen
  int i = 1;
  int x = 0;
  int y= 0;
  int raufrunter = 0;
  const char trenner[1] = "/";

void communicate_thread() {
  char ry,ry1,rx,rx1,rrauf,rrauf1, tx; // rx: received data, tx: data to transmit
  
  while(1) {
    pause(50);
     
    string[0] = fdserial_rxChar(wifi_connection);
    if(string[0] != '\0'){
        while(string[i-1] != '\0'){
          string[i] = fdserial_rxChar(wifi_connection);
          ++i;  
         }
       string[i] = '\0';
       i =   1;
     }
     
     

        ptr = strtok(string,trenner);
        x = atoi(ptr);  //ptr
           
        
        ptr = strtok(NULL,trenner);
        y = atoi(ptr);  // ptr
      
    
        ptr = strtok(NULL,trenner);
        raufrunter = atoi(ptr);  // ptr
            
        *ptr = NULL;  
        
        /*
        if(x == 99){
         high(26);
         pause(100);
         low(26);
        }
        
        if(y == 99){
         high(27);
         pause(100);
         low(27);
        }
        
        if(raufrunter==99){
          high(26);
          high(27);
          pause(100);
          low(26);
          low(27);  
        }
        */

        if(x == 10000){
            servo360_goto(16,36); //servo fährt hoch
            x=0;
        }
         if(x == 10010){
            drive_goto(36,-36); //servo fährt hoch
            x=0;
        }
         if(x == -10010){
            drive_goto(-36,36); //servo fährt hoch
            x=0;
        }
        if(x == 10005){
           drive_goto(25,25); //fährt auf knopf 2 5cm vorwärts
           x=0;
        }
        if(x == -10005){
           drive_goto(-25,-25); //fährt uaf knopf 3 5cm rückwärts
           x=0;
        }
        if(x >= 10 || y>= 10){
            drive_speed((x+y),(x-y));
              
        } else if(x <= -10 || y<= -10){ 
            drive_speed((x+y),(x-y));
        } else{
            drive_speed(0,0);  
        }
        
        
      if(raufrunter <= -7){
          servo360_speed(16,raufrunter);
        } else if(raufrunter >= 7){
          servo360_speed(16,raufrunter);
        } else{
          servo360_speed(16,0);  
        }
        raufrunter = 0; // nicht getestete neuerrung
        
        
    }  
   
}
/*

void drive_forward() {
  high(26);
  pause(100);
  low(26);
  
  //drive_goto(DEFAULT_SPEED, DEFAULT_SPEED);
}

void drive_backwards() {
   high(27);
  pause(100);
  low(27);
}

void turn_left() { // 10 degrees
  high(26);
  high(27);
  pause(100);
  low(26);
  low(27);
  //drive_goto(25/8, -26/8);
}

void turn_right() { // 10 degrees
    high(26);
    high(27);
    pause(5000);
    low(26);
    low(27);
 // drive_goto(-26/7, 25/7);
}

void left_hard() { // 90 degrees
 //drive_goto((DEFAULT_SPEED*2), DEFAULT_SPEED*2);
}

void right_hard() { // 90 degrees
  //drive_goto((DEFAULT_SPEED*(-2)), (DEFAULT_SPEED*(-2)));
}

void stop() {
  drive_speed(0, 0);
}

void go_up(){
   high(26);
   pause(1000);
   low(26);
   
   
  //  servo360_speed(16,50);
  //    pause(2000);
  //  servo360_stop(16);
}

void go_down(){
 servo360_speed(16,-50);
 pause(1000);
 servo360_stop(16);
}

void fire(){
  servo360_speed(17,25);
  pause(8500);
  servo360_stop(17);
  high(26);
  servo360_speed(17,25);
  pause(7000);
  servo360_stop(17);
  high(27);
}

void blink_thread(){
  while(1){  
    high(27);
    pause(1000);
    low(27);
    pause(1000);
  }
}  
  */



  

int main() {
  wifi_connection = fdserial_open(1, 0, 0, 115200); //MAIN TRANSFER

  set_output(7, 1);

 // bus = i2c_newbus(PING_LASER_SDA_PIN, PING_LASER_SCL_PIN, 0);
 // vl53_init(bus);

  cog_run(&communicate_thread, 200); // start WIFI communication
  //cog_run(blink_thread,100);
  //cog_run(&ping_thread, 100); // enable async pinging
  //cog_run(&counter_thread, 100); // start timer
  //cog_run(&roam_thread, 100); // start roaming/autopilot thread
  input(5);
  //input(6);
  servo360_connect(16,5);
  //servo360_connect(17,6);

  while (1) {pause(100);} // do nothing in main thread
}