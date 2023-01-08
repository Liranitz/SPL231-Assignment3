#include <vector>
#include <ConnectionHandler.h>
using namespace std;
using namespace boost;

class KeyBoard_imp {
public:
    //~Keyboardinput();
    KeyBoard_imp(ConnectionHandler &c_H);
    void input();
private:
    vector<string> result();
    vector<string> split(const string &stringToSplit, char delimiter);
    ConnectionHandler &c_h;
    string bookName(int start, int end, vector<string> all);
};