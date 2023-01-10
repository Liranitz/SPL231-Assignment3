#pragma once
#include <map>
#include "../include/ConnectionHandler.h"

class StompProtocol
{
private:
  int cur_subscribe;
  std::map<std::string, int> map_of_subscribes;

public: 
  StompProtocol();
  std::string parse_to_frame(std::string input_string);
};
