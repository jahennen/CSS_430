/* 	CSS 430 Operating Systems
		Programming Assignment 3
		Author: Jay Hennen
		11/12/2013
*/


import java.util.*;


public class TestThread3 extends Thread {
	private int type;
	private byte[]b;
	private Random r;
	private int reps;
	final int diskSize = 1000;

	// type 1 is disk-intensive thread, else comp-intensive thread
	public TestThread3(String[] args) {
		type = Integer.parseInt(args[0]);
		reps = Integer.parseInt(args[1]);
		b = new byte[Disk.blockSize];
		r = new Random();
	}
	
	public void run() {
		if (type == 1) { 	// Disk
			DiskThread();
		} else {			// Comp			
			CompThread();
		}
		SysLib.exit();
	}
	
	// Randomly reads or writes to a random block number on disk
	private void DiskThread() {
		for(int i = 0; i < reps; i++) {
			int blk = r.nextInt(diskSize);
			int opt = r.nextInt(1);
			if (opt == 0)
				SysLib.rawread(blk, b);
			else
				SysLib.rawwrite(blk, b);

		}
	}
	
	// Repeatedly computes a new byte array based on an initial byte array.
	private void CompThread() {
		r.nextBytes(b);
		byte[] a = new byte[Disk.blockSize];
		for(int i = 0; i < reps; i++) {
			a[0] = b[0];
			for (int j = 1; j < a.length; j++) {
				if ((int)b[j-1] != 0)
					a[j] = (byte) ((int)b[j] % (int)b[j-1]);
				else
					a[j] = b[j];
			}
			b = Arrays.copyOf(a, b.length);
		}
	}
}
