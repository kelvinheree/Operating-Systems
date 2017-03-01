package cs131.pa1.filter.sequential;

import java.io.File;
import java.io.PrintWriter;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

public class Redirection extends SequentialFilter{
	//writing file name
	protected String name;
	//constructor
	public Redirection(String name){
		this.name = name;
	}
	

	@Override
	public void process(){
		//null input
		if (input == null){
			System.out.print(Message.REQUIRES_INPUT.with_parameter("> " + name));
			SequentialREPL.abort = true;
		} else {
			//writing to file
			File file = new File(SequentialREPL.currentWorkingDirectory + Filter.FILE_SEPARATOR + name);
			try{
				PrintWriter writer = new PrintWriter(file);
				while (!input.isEmpty()){
					String line = input.poll();
					writer.println(line);
				}
				writer.close();
			} catch (Exception e){
				System.out.println("fked");
			}
		}
	}
	
	@Override
	protected String processLine(String line) {
		// TODO Auto-generated method stub
		return null;
	}

}
