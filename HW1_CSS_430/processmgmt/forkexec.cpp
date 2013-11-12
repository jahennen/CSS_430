#include <iostream>     // cin, cout
#include <sys/types.h>  // fork, wait
#include <sys/wait.h>   // wait
#include <stdio.h>      // NULL
#include <unistd.h>     // fork, exit, execlp
#include <stdlib.h>     // exit

using namespace std;

int main( ) {
    string cmd;
    int pid;

    while ( true ) {
        cin >> cmd;
        if ( cmd == "exit" )
            break;
        if ( ( pid = fork( ) ) == 0 ) {
            // child
            if ( cmd == "?" ) {
                cout << "I'm a child" << endl;
                exit( 0 );
            } else {
                execlp( cmd.c_str( ), cmd.c_str( ), (char *)0 );
                cout << "I'm a child" << endl;
            }
        } else {
            // parent
            if ( pid == wait( NULL ) )
                cout << "My child process " << pid << " was terminated." << endl;
            else
                cerr << "Who is this process " << pid << " ?" << endl;
        }
    }
}
