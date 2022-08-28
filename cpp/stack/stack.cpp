#include <iostream>
#include <stack>

using namespace std;

int main(int argc, char* argv[]) {


    stack<string> stack;

    stack.push("1");
    stack.push("2");
    stack.push("3");

    cout << stack.top() << endl;
    stack.pop();
    cout << stack.top() << endl;
    stack.pop();
    cout << stack.top() << endl;
    stack.pop();

    return 0;
}

