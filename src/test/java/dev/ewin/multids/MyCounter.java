package dev.ewin.multids;

public class MyCounter {
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void increment() {
		int temp = count;
		count = temp + 1;
	}
	
	public synchronized void incrementSync() {
		int temp = count;
		count = temp + 1;
	}
	
	public synchronized void incrementWait() throws InterruptedException {
		int temp = count;
		wait(100);
		count = temp + 1;
	}
}
