#pragma once

#include "../include/ConnectionHandler.h"
// TODO: implement the STOMP protocol
class StompProtocol
{
private:
  StompProtocol::StompProtocol();
  int cur_subscribe;
  std::map<string, int> map_of_subscribes;

public: 
  std::string parse_to_frame(std::string input_string);
};
