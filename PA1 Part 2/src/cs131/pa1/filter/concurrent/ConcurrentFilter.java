package cs131.pa1.filter.concurrent;
import java.util.concurrent.LinkedBlockingQueue;

import cs131.pa1.filter.Filter;


public abstract class ConcurrentFilter extends Filter implements Runnable{
	
	protected LinkedBlockingQueue<String> input;
	protected LinkedBlockingQueue<String> output;
	protected Thread thread;
	
	@Override
	public void setPrevFilter(Filter prevFilter) {
		prevFilter.setNextFilter(this);
	}
	
	@Override
	public void setNextFilter(Filter nextFilter) {
		if (nextFilter instanceof ConcurrentFilter){
			ConcurrentFilter sequentialNext = (ConcurrentFilter) nextFilter;
			this.next = sequentialNext;
			sequentialNext.prev = this;
			if (this.output == null){
				this.output = new LinkedBlockingQueue<String>();
			}
			sequentialNext.input = this.output;
		} else {
			throw new RuntimeException("Should not attempt to link dissimilar filter types.");
		}
	}
	
	public void process(){
		//if the incoming string is "THREADCOMPLETED", do not poll, let isDone do its job. Else, poll and process
		while (!isDone()){
			String line = input.peek();
			if (line != null ){
				if (!line.equals("THREADCOMPLETED")){
					String processedLine = processLine(input.poll());
					if (processedLine != null){
						output.add(processedLine);
					}
				}
			}
		}
		//completed thread
		output.add("THREADCOMPLETED");
	}
	
	@Override
	public boolean isDone(){
		//if the previous thread tells this one it's completed, return true, otherwise false for any other case
		String l = input.peek();
		if (l == null){
			return false;
		} else {
			if (l.equals("THREADCOMPLETED")){
				return true;
			}
			return false;
		}
	}
	
	//processes when run is called
	public void run() {
		process();
	}
	
	//makes a thread with itself as the Runnable argument
	//then starts the thread so run is called
	 public void start (String name) {
	      if (thread == null) {
	         thread = new Thread (this, name);
	         thread.start();
	      }
	   }
	
	protected abstract String processLine(String line);
	
}
