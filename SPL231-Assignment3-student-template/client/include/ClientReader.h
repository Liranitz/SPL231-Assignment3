#include <vector>
#include <ConnectionHandler.h>
using namespace std;
using namespace boost;

class ClientReader {
public:
    //~Keyboardinput();
    ClientReader(ConnectionHandler &c_h);
    void read(ConnectionHandler &c_h);
private:
    ConnectionHandler &c_h;
    string bookName(int start, int end, vector<string> all);
};