#pragma once
#include <map>
#include "../include/ConnectionHandler.h"
#include <fstream>


#include "../include/EventController.h"

class StompProtocol
{
private:
 

public: 
  //StompProtocol();
  static void send_messages_by_event(std::string path_message , ConnectionHandler& connectionHandler);
  static std::string parse_to_frame(std::string input_string , ConnectionHandler& connectionHandler, EventController &eventController);

};
