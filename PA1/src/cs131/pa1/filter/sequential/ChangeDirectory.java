package cs131.pa1.filter.sequential;

import java.io.File;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

public class ChangeDirectory extends SequentialFilter{

	//directory arg
	protected String argument;
	
	//constructor
	public ChangeDirectory(String arg){
		super();
		this.argument = arg;
	}
	
	
	@Override
	public void process(){
		//null input case
		if (input != null){
			System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter("cd " + argument));	
			SequentialREPL.abort = true;
		} else {
			//modifying directory string
			switch (argument){
				case "~":
					SequentialREPL.currentWorkingDirectory = SequentialREPL.homeDirectory;
					break;
				case "..":
					SequentialREPL.currentWorkingDirectory = SequentialREPL.currentWorkingDirectory.substring(0, SequentialREPL.currentWorkingDirectory.lastIndexOf(Filter.FILE_SEPARATOR));
					break;
				case "":
					System.out.print(Message.REQUIRES_PARAMETER.with_parameter("cd"));
					break;
				case ".":
					//do nothing
					break;
				default:
					String newLocation = SequentialREPL.currentWorkingDirectory + Filter.FILE_SEPARATOR + argument;
					File directory = new File (newLocation);
					if (directory.exists()){
						SequentialREPL.currentWorkingDirectory += Filter.FILE_SEPARATOR + argument;
					} else {
						System.out.print(Message.DIRECTORY_NOT_FOUND.with_parameter("cd " + argument));
						SequentialREPL.abort = true;
					}
			}
		}
	}
	
	@Override
	protected String processLine(String line) {
		// TODO Auto-generated method stub
		return null;
	}

}
