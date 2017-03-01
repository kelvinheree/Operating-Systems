package cs131.pa1.filter.sequential;


import cs131.pa1.filter.Message;

public class PrintWorkingDirectory extends SequentialFilter{
	
	@Override
	//adds workingdirectory from REPL to output
	public void process(){
		if (input != null){
			System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter("pwd"));
			SequentialREPL.abort = true;
		} else {
			output.add(SequentialREPL.currentWorkingDirectory);
		}
	}

	
	
	@Override
	protected String processLine(String line) {
		return null;
	}

}
