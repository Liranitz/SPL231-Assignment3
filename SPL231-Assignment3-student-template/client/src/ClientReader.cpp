#include <iostream>
#include <vector>
#include <iterator>
#include <ClientReader.h>
#include <boost/algorithm/string.hpp>
using namespace std;

ClientReader::ClientReader(ConnectionHandler &c_h) : c_h(c_h){}

void ClientReader::read() {
    while(1){ // figure out if need to be otherway
        string cur_output = "";
        c_h.getLine(cur_output);
        vector<string> result;
        boost::split(result, cur_output, boost::is_any_of("\n"));
        string typeMessage = result[0]; //check if not null or some error
        if (typeMessage == "RECEIPT"){
            //succeeded to log in sub or unsub
            // gets receipt , save it?

        }
        else{
            // do nothing
        }

        if(0) // if got a receipt , clear vec , else dont
        {
            result.clear();
        }
    }
}
