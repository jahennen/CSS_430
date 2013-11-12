#include <sys/types.h> // msgget, fork
#include <sys/ipc.h>   // msgget
#include <sys/msg.h>   // msgget
#include <unistd.h>    // fork
#include <iostream>    // cin, cout
#include <stdio.h>     // perror

struct mymsgbuf {
  long mtype;
  char mtext[1];
};

using namespace std;

int main( int argc, char* argv[] ) {
  int msgid; // message queue id
  struct mymsgbuf message;
  int pid;

  message.mtype = 1;
  //                     key   flag
  if ( ( msgid = msgget( 200, 0777 | IPC_CREAT ) ) == -1 ) {
    perror( "msgget" );
  }

  if ( ( pid = fork( ) ) < 0 )     // fork a child
    perror( "fork" );
  else if ( pid > 0 ) {
    // parent
    for ( int i = 0; i < 10; i++ ) { // read from the shared message queue
      msgrcv( msgid, &message, 1, 1, 0 ); // wait for a message arrival
      cout << message.mtext[0] << endl;
    }
    cout << "parent terminated" << endl;
  } 
  else { // pid == 0
    // child
    sleep( 5 );                      // sleep for 5 sec
    for ( int i = 0; i < 10; i++ ) { // write to the sahred message queue
      message.mtext[0] = 'a' + i;
      msgsnd( msgid, &message, 1, 0 );
    }
    cout << "child terminated" << endl;
    exit( 1 );
  }
}
