#include <map>
#include <string>
#include <vector>
using namespace std;

class ClientData {
public :
    ClientData(string username, string password);
    string get_name();
    //consider if volatile?
    bool is_logged_in;
    int subscribe_counter;
    ~ClientData();
    int receipts_counter;
    map<string, int> topic_to_id_map;
    map<int , string> actions_by_receipt;
private:
    string name;
    string passcode;



    //map<string, vector<Book>> inventory;
    //vector<Book> lentBooks;
    //vector<pair<int, action *>> receiptAct;
    
    //vector<string> topics?    
};