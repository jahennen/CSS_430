/* 	CSS 430 Operating Systems
		Programming Assignment 3
		Author: Jay Hennen
		11/12/2013
*/

import java.util.*;

public class SyncQueue {
	private QueueNode[] queue;
	
	// Constructors
	public SyncQueue() {
		this(10);
	}
	public SyncQueue(int condMax) {
		queue = new QueueNode[condMax];
		for (int i=0; i < queue.length; i++)
			queue[i] = new QueueNode();
	}
	
	// Sleeps a thread on the condition
	public int enqueueAndSleep(int condition) {
		return queue[condition].sleep();
	}
	
	// Wakes a thread on the condition once, and passes the child tid
	public void dequeueAndWakeup( int condition, int tid ) {
		queue[condition].wakeup(tid);
	}
	
	public void dequeueAndWakeup(int condition) {
		dequeueAndWakeup(condition,0);
	}
	
	// QueueNode and private queue helper methods
	public class QueueNode {
		private Vector<Integer> tids;
		
		QueueNode() {
			tids = new Vector<Integer>();
		}
		
		// Sleeps a thread on this object if no child has exited and given it's tid
		// When a child gives it's tid, the thread is notified, and then the child's tid
		// is returned.
		public synchronized int sleep() {
			if (tids.size() == 0) {
				try {
					wait();
				} catch (InterruptedException e) {}
			}
			return tids.remove(0);
		}
		
		// Wakes a thread waiting on this object, after adding the child tid to the list
		public synchronized void wakeup(int tid) {
			tids.add(tid);
			notify();
		}
	}
}