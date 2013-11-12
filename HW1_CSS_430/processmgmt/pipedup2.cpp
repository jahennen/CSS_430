#include <iostream>    // cin, cout
#include <sys/types.h> // fork
#include <unistd.h>    // pipe, close, read, dup2, fork, execlp
#include <stdio.h>     // perror

#define MAXLINE 80

using namespace std;

int main( ) {
  int fd[2];           // fd[0]: pipe input, fd[1]: pipe output
  int pid;             // process id
  char line[MAXLINE];  // message from fd[0]
  int nread;           // #bytes read

  if ( pipe( fd ) < 0 )
    perror( "pipe error" );
  else if ( ( pid = fork( ) ) < 0 )
    perror ( "fork error" );
  else if ( pid > 0 ) {
    // parent
    close( fd[1] );
    while ( ( nread = read( fd[0], line, MAXLINE ) ) > 0 ) {
      for ( int i = 0; i < nread; i++ ) {
	if ( line[i] >= 'a' && line[i] <= 'z' )
	  line[i] += 'A' - 'a';   // conver a lower to a capital character
	cout << line[i];
      }
    }
  } else { // pid == 0
    // child
    close( fd[0] );
    dup2( fd[1], 1 );
    execlp( "ps", "ps", "-A", ( char *) 0 );
  }
}

