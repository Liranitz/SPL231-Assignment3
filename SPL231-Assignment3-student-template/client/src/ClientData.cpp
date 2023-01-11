#include <string>
#include <iostream>
#include "ClientData.h"


ClientData::ClientData(string name, string passcode):name(name),passcode(passcode),actions_by_receipt(map<int,string>()),
                                                    topic_to_id_map(map<string,int>())
{
    subscribe_counter = 1;
    receipts_counter = 1;
    is_logged_in = false;
}



ClientData::~ClientData(){
    topic_to_id_map.clear();
    actions_by_receipt.clear();
}

string ClientData::get_name(){
    return name;
}
