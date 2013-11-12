
public class SyncQueue {
	private QueueNode[] queue;
	
	public SyncQueue() {
		this(10);
	}
	public SyncQueue(int condMax) {
		queue = new QueueNode[condMax];
	}
	public enqueueAndSleep(int condition) {
		queue.
	}
	public dequeueAndWakeup(int condition) {
		
	}
	
	private class QueueNode {
		int cond;
		QueueNode(int tid) {
			cond = tid;
		}
	}
}
