/*
processdemo.c

A simple demonstration of using some basic C Library system calls
for working with Linux processes.

Derived from "Linux Processes – Process IDs, fork, execv, wait, waitpid C Functions"
a blog post by Himanshu Arora on March 23, 2012
http://www.thegeekstuff.com/2012/03/c-process-control-functions/
*/

#include <unistd.h>
#include <sys/types.h>
#include <errno.h>
#include <stdio.h>
#include <sys/wait.h>
#include <stdlib.h>

int global; /* In BSS segement, will automatically be assigned 0 */

int main(int argc, char * argv[]) {
    pid_t pid;
    int status;
    int local = 0;
    
    printf("\nStarting %s\n", argv[0]);
    /* now create new process */
    pid = fork(); 
    
    if (pid < 0) {   /* fork() failed */
        fprintf(stderr, "%s: fork() failure", argv[0]);
        exit(1);
    }
    else if (pid == 0) { /* fork() returns 0 in the child process */
        printf("\nchild process\n");
        // Increment the local and global variables (only in child process)
        local++;
        global++;
        printf("my pid =  %d, parent pid = %d (no child)\n", getpid(), getppid());
        printf("In child, local = %d, global = %d\n", local, global);
        char * cmd = "whoami";  // see what happens if cmd = "badcommand"
        return execlp( cmd, cmd, NULL );
        // no need to call exit(), as the cmd executable will 
        // overlay the argv[0] executable in child at this point
    }
    else { /* fork() returns child's PID in the parent process */
        printf("\nparent process\n");
        printf("my PID =  %d, parent pid = %d, child pid = %d\n", getpid(), getppid(), pid);
        printf("parent will now wait for child to exit\n");
        wait(&status); /* wait for child to exit, and store child's exit status */
        printf("\nResuming parent, child exit code = %d\n", WEXITSTATUS(status));
        //The change in local and global variable in child process should not reflect here in parent process.
        printf("In parent, local = %d, global = %d\n", local, global);
        printf("Parent exiting\n\n");
        exit(0);  /* parent exits */
    }
    printf("\nThis line will never be reached.\n\n");
}