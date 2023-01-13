#pragma once
#include <map>
#include "../include/ConnectionHandler.h"
#include <fstream>


//#include "../include/eventController.h"

class StompProtocol
{
private:
 

public: 
  //StompProtocol();
  static void send_messages_by_event(std::string path_message , ConnectionHandler& connectionHandler, EventController &EventCoontroller);
  static std::string parse_to_frame(std::string input_string , ConnectionHandler& connectionHandler, EventController &eventController);
  static void creatSummary(ofstream file, EventController &EventController);
};
