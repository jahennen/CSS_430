/* 	CSS 430 Operating Systems
		Programming Assignment 3
		Author: Jay Hennen
		11/12/2013
*/


import java.util.*;


public class Test3 extends Thread{
	private int numPairs;
    private long submissionTime;
    private long elapsedTime;
	
	public Test3( String[] args ) {
		numPairs = Integer.parseInt(args[0]);
		submissionTime = System.currentTimeMillis();
	}
	
	public void run() {
		Map<Integer,String> m = new HashMap<Integer,String>();
		for (int i = 0; i < numPairs; i++) {
			String[] td1 = SysLib.stringToArgs("TestThread3 0 1000000");
			String[] td2 = SysLib.stringToArgs("TestThread3 1 200");
			m.put(SysLib.exec(td1),"comp");
			m.put(SysLib.exec(td2),"disk");
		}
		for (int i = 0; i < numPairs*2; i++) {
			String type = m.remove(SysLib.join());
			SysLib.cout(type +" finished in " + 
			(System.currentTimeMillis() - submissionTime) + "ms\n");
		}
		elapsedTime = System.currentTimeMillis() - submissionTime;
		SysLib.cout("elapsed time = " + elapsedTime + "ms\n");
		SysLib.exit();
	}
}
