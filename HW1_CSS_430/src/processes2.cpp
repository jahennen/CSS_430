#include <unistd.h>
#include <fcntl.h>
#include <iostream>
#include <sys/wait.h>
#include <cstdio>

using namespace std;

int main(int argc, char* argv[]) {
	int pipefds1[2];
	int pipefds2[2];
	int rc;
	if (argc < 2)
		cerr << "not enough arguments" << endl;
	if ((rc = pipe(pipefds1)) < 0)
		cerr << "error creating pipefds1" << endl;
	if (!(rc = pipe(pipefds2)) < 0)
		cerr << "error creating pipefds2" << endl;
	pid_t pid = fork();
	if ((pid == 0)) {	//child
		pid = fork();
		if ((pid == 0)) { //grandchild
			pid = fork();
			if ((pid == 0)) { //greatgrandchild
				close(pipefds1[0]);							//close pipe1 read side
				dup2(pipefds1[1],1);						//pipe 1 write ->> cout
				execlp("/bin/ps", "ps", "-A", NULL);
			} else {
				wait(NULL);
				close(pipefds1[1]);							//close pipe1 write side
				dup2(pipefds1[0],0);						//pipe1 read ->> cin
				close(pipefds2[0]);							//close pipe2 read
				dup2(pipefds2[1],1);						//pipe2 write ->> cout
				execlp("/bin/grep", "grep", argv[1], NULL);
			}
		} else {
			close(pipefds1[0]);								//close pipe1 fds so children
			close(pipefds1[1]);								//can terminate
			wait(NULL);
			close(pipefds2[1]);								//close pipe2 write
			dup2(pipefds2[0],0);							//pipe2 read ->> cin
			execlp("/bin/wc", "wc", "-l", NULL);
		}
	} else {
		for (int i = pipefds1[0]; i <= pipefds2[1]; i++) {	// close all open fds so
			close(i);										// children can terminate
		}
		wait(NULL);
	}
	return 0;
}
