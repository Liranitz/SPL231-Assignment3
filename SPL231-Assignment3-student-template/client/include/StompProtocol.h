#pragma once

#include "../include/ConnectionHandler.h"
// TODO: implement the STOMP protocol
class StompProtocol
{
private:

public: 
  static std::string parse_to_frame(std::string input_string);
};
