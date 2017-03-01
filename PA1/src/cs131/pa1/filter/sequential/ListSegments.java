package cs131.pa1.filter.sequential;

import java.io.File;

import cs131.pa1.filter.Message;

public class ListSegments extends SequentialFilter {

	@Override
	public void process(){
		//null input
		if (input != null){
			System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter("ls"));
			SequentialREPL.abort = true;
		} else {
			//list and add files to output
			File directory = new File(SequentialREPL.currentWorkingDirectory);
			File[] filesList = directory.listFiles();
			for (File file : filesList) {
				output.add(file.getName());	
			}
		}
	}


	@Override
	protected String processLine(String line) {
		return null;
	}

}
