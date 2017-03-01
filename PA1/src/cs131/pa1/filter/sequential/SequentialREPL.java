package cs131.pa1.filter.sequential;
import java.util.*;

import cs131.pa1.filter.Message;

import java.io.*;


public class SequentialREPL {

	static String currentWorkingDirectory;
	static String homeDirectory;
	static boolean abort;
	
	public static void main(String[] args){
		Scanner input = new Scanner (System.in);
		System.out.print(Message.WELCOME.toString());
		System.out.print(Message.NEWCOMMAND.toString());
		File directory = new File("");
		currentWorkingDirectory = directory.getAbsolutePath();
		homeDirectory = directory.getAbsolutePath();
		abort = false;
		while (true){
			outer:
			if (input.hasNextLine()){
				String line = input.nextLine();
				//exit command
				if (line.equals("exit")){
					System.out.print(Message.GOODBYE.toString());
					break;
				} else {
					//creating filters
					List<SequentialFilter> list = SequentialCommandBuilder.createFiltersFromCommand(line);
					//error aborts
					if (abort){
						abort = false;
						break outer;
					}
					//processing
					ListIterator<SequentialFilter> iterator = list.listIterator();
					while (iterator.hasNext()){
						SequentialFilter activity = iterator.next();
						if (activity != null){
							activity.process();		
						}
					}
					//error aborts
					if (abort){
						abort = false;
						break outer;
					}
					Queue<String> op = list.get(list.size() - 1).output;
					//cannot have output cases
					if (op != null){
						String[] x = line.split("\\|");
						for (int j = 0; j < x.length - 1; j++){
							if (x[j].contains(">")){
								System.out.print(Message.CANNOT_HAVE_OUTPUT.with_parameter(x[j].substring(x[j].indexOf(">"))));
								SequentialREPL.abort = true;
							}
							if (x[j].contains("cd")){
								System.out.print(Message.CANNOT_HAVE_OUTPUT.with_parameter(x[j]));
								SequentialREPL.abort = true;
							}
						}
					}
					//error aborts
					if (abort){
						abort = false;
						break outer;
					}
					//printing output
					for (String a: op){
						System.out.println(a);
					}
					
					

				}
			}
			System.out.print(Message.NEWCOMMAND.toString());
		}
		input.close();
	}

}
