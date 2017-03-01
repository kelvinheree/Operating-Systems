package cs131.pa1.filter.sequential;

import java.io.File;
import java.util.Scanner;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

public class Head extends SequentialFilter{
	
	protected String lines;
	protected String file;
	protected String command;
	
	//constructor if given file
	public Head(String file, String command){
		super();
		this.lines = "-10";
		this.file = file;
		this.command = command;
	}
	//constructor if given file and lines
	public Head(String lines, String file, String command){
		super();
		this.lines = lines;
		this.file = file;
		this.command = command;
	}
	//converting lines as a string to int or returning error
	private int convert(String lines){
		if (!lines.substring(0,1).equals("-")){
			System.out.print(Message.INVALID_PARAMETER.with_parameter(command));
			SequentialREPL.abort = true;
			return 0;
		}
		try{
			int newLines = Integer.parseInt(lines.substring(1));
			return newLines;
		} catch (Exception e){
			System.out.print(Message.INVALID_PARAMETER.with_parameter(command));
			SequentialREPL.abort = true;
			return 0;
		}
	}
	
	
	@Override
	public void process(){
		//null input
		if (input != null){
			System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter(command));
			SequentialREPL.abort = true;
		}else {
			try{
				//reading and adding
				File directory = new File(SequentialREPL.currentWorkingDirectory + Filter.FILE_SEPARATOR + file);
				Scanner x = new Scanner(directory);
				int newLines = convert(lines);
				while (x.hasNextLine() && newLines > 0){
					String y = x.nextLine();
					output.add(y);
					newLines --;
				}
				x.close();
			} catch (Exception e){
				//file not found
				System.out.print(Message.FILE_NOT_FOUND.with_parameter(command));
				SequentialREPL.abort = true;
			}
		}
	}
	
	@Override
	protected String processLine(String line) {
		// TODO Auto-generated method stub
		return null;
	}

}
