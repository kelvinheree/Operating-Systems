package cs131.pa1.filter.concurrent;

import java.util.LinkedList;
//import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import cs131.pa1.filter.Message;

public class ConcurrentREPL {

	static String currentWorkingDirectory;
	
	public static void main(String[] args){
		currentWorkingDirectory = System.getProperty("user.dir");
		Scanner s = new Scanner(System.in);
		System.out.print(Message.WELCOME);
		String command;
		//Thread holder
		List<Thread> processes = new LinkedList<Thread>();
		while(true) {
			//obtaining the command from the user
			System.out.print(Message.NEWCOMMAND);
			command = s.nextLine();
			if(command.equals("exit")) {
				break;
			} else if (command.equals("repl_jobs")){
				//shows backgrounded jobs
				//job counter
				int j = 1;
				//looks through the backgrounded threads and checks if they are still alive, then print them out
				for (Thread i: processes){
					if (i.isAlive()){
						System.out.println("	" + j + ". " + i.getName() + "&");
						j++;
					}
				}	
			}
			else if(!command.trim().equals("")) {
				//if it contains &, redo the command without & but in the background, so the main thread does not wait for it
				boolean background = false;
				if (command.substring(command.length()-1).equals("&")){
					background = true;
					command = command.substring(0,command.length()-1);
				}
				//building the filters list from the command
				List<ConcurrentFilter> filterlist = ConcurrentCommandBuilder.createFiltersFromCommand(command);
				//last thread for the background list or for the main thread to join
				Thread last = Thread.currentThread();
				if (filterlist != null){
					for (ConcurrentFilter x:filterlist){
						//starting threads
						x.start(command);
						//if it's the last filter, make last reference it
						if (x == filterlist.get(filterlist.size() - 1)){
							last = x.thread;
						}
					}
					//if true, add last to the list
					if (background){
						processes.add(last);
					} else {
						try {
							//joining with main thread
							if (Thread.currentThread().getId() == 1){
								last.join();		
							}
						} catch (InterruptedException e) {
						}
					}
				}
			}
		}
		s.close();
		System.out.print(Message.GOODBYE);
	}
}
