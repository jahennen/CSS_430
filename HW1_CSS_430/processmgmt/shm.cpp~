#include <sys/ipc.h>   // shmget
#include <sys/shm.h>   // shmget, shmat
#include <sys/types.h> // fork, shmat, wait
#include <sys/wait.h>  // wait
#include <unistd.h>    // fork, sleep, exit
#include <iostream>    // cin, cout
#include <stdlib.h>    // rand
#include <stdio.h>     // NULL

#define SIZE 100

using namespace std;

int main( int argc, char* argv[] ) {
  int shmId;     // shared memory id
  void *shmAddr; // shared memory address
  int pid;
 
  //                    key  size  flag
  if ( ( shmId = shmget( 75, SIZE, 0777 | IPC_CREAT ) ) == -1 ) {
    perror( "shmget" );       //    id  addr  flag
  } else if ( ( shmAddr = shmat( shmId, NULL, 0 ) ) == NULL ) {
    perror( "shmat" );
  }

  for ( int i = 0; i < SIZE/4; i++ )    // initialize shared memory
    ( ( int * )shmAddr )[i] = 0;

  if ( ( pid = fork( ) ) < 0 ) {        // fork a child
    perror( "fork" );
  } else if ( pid > 0 ) {
    // parent
    if ( argc == 2 && argv[1][0] == 'w' )      // sync with the child if needed
      wait( NULL );
    for ( int i = 0; i < SIZE/4; i++ )         // read shared memory
      cout << ( ( int * )shmAddr )[i] << endl;
    cout << "parent terminated" << endl;
  } else { // pid == 0
    // child
    sleep( 5 );                                // sleep for 5 sec
    for ( int i = 0; i < SIZE/4; i++ )         // write shared memmory
      ( ( int * )shmAddr )[i] = rand( );
    cout << "child terminated" << endl;
    exit( 1 );
  }
}
