#include <iostream>
#include <queue>

using namespace std;

int main(int argc, char* argv[]) {

    queue<string> queue;

    queue.push("1");
    queue.push("2");
    queue.push("3");

    cout << queue.front() << endl;
    queue.pop();
    cout << queue.front() << endl;
    queue.pop();
    cout << queue.front() << endl;
    queue.pop();

    return 0;
}

