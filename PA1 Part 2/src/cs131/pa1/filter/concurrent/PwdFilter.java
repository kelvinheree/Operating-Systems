package cs131.pa1.filter.concurrent;

public class PwdFilter extends ConcurrentFilter {
	public PwdFilter() {
		super();
	}
	
	public void process() {
		output.add(processLine(""));
		//completed thread
		output.add("THREADCOMPLETED");
	}
	
	public String processLine(String line) {
		return ConcurrentREPL.currentWorkingDirectory;
	}


}
