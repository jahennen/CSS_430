/* 	CSS 430 Operating Systems
		Programming Assignment 1 part 2
		Author: Jay Hennen
		10/12/2013
*/

import java.util.Set;
import java.util.HashSet;

public class Shell extends Thread{
	private int cmdCount = 1;
	
	public Shell() {}
	public Shell(String[] args) {}
	
	public void run() {
		repl();
		SysLib.exit();
	}
	
	public void repl() {
		while(true) {
			StringBuffer input = new StringBuffer();
			SysLib.cout("shell[" + cmdCount + "]% ");
			SysLib.cin(input);
			String cmds = new String(input);
			if(cmds.compareTo("exit")== 0)
				break;
			if (!cmds.isEmpty()) {
				cmdCount++;
				for(String cmd : cmds.split(";"))
					runConc(cmd);
			}
		}
	}
	
	 // Runs a cmd String concurrently. If there are multiple commands to run concurrently
	 // this method will split the string and execute each command concurrently
	public void runConc(String cmd) {
		Set<Integer> tids = new HashSet<Integer>(); // Set of outstanding tids
		int tid = 0;
		for (String c: cmd.split("&")) {
			String[] args = SysLib.stringToArgs(c);
			if (args.length == 0) 		// in case of empty command string
				continue;
			SysLib.cout(args[0] + "\n");
			tid = SysLib.exec(args); 	// Actual execution here
			if (tid > 0) 				// If successful, add tid to outstanding set
				tids.add(tid);
		}
		while(!tids.isEmpty()) { // While there are still threads running, join and
			tid = SysLib.join();
			if (tids.contains(tid)) {
				tids.remove(tid); // remove outstanding tids from set 
			}
		}
	}
}
