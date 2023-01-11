#pragma once
#include <map>
#include "../include/ConnectionHandler.h"

class StompProtocol
{
private:

public: 
  StompProtocol();
  static std::string parse_to_frame(std::string input_string , ConnectionHandler& connectionHandler);
};
