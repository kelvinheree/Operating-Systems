package cs131.pa1.filter.concurrent;

public class PrintFilter extends ConcurrentFilter {
	public PrintFilter() {
		super();
	}
	
	public void process() {
		//if the incoming string is "THREADCOMPLETED", do not poll, let isDone do its job. Else, poll and process
		while(!isDone()) {
			String line = input.peek();
			if (line != null){
				if (!line.equals("THREADCOMPLETED")){
					processLine(input.poll());
				}
			}
		}
	}
	
	public String processLine(String line) {
		System.out.println(line);
		return null;
	}

}
