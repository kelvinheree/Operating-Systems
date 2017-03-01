package cs131.pa1.filter.sequential;


import java.util.LinkedList;
import java.util.List;

import cs131.pa1.filter.Message;


public class SequentialCommandBuilder {
	
	public static List<SequentialFilter> createFiltersFromCommand(String command){
		//adds a pipe before redirections to enable the split for redirection
		if (command.contains(">")){
			String a = command.substring(0, command.indexOf(">"));
			String b = command.substring(command.indexOf(">"));
			command = a + "|" + b;
			//System.out.println(command);
		}
		//creates list
		List<SequentialFilter> list = new LinkedList<SequentialFilter>();
		//generates list
		while (command.trim().length() > 0){
			list.add(0,determineFinalFilter(command));
			command = adjustCommandToRemoveFinalFilter(command);
			//abort beforehand to avoid link
			if (SequentialREPL.abort){
				return list;
			}
		}
		linkFilters(list);
		return list;
		
	}
	
	private static SequentialFilter determineFinalFilter(String command){
		//gets final filter
		String[] split = command.split("\\|");
		return constructFilterFromSubCommand(split[split.length - 1].trim());
	}	

	private static String adjustCommandToRemoveFinalFilter(String command){
		//removes the final filter e.g. part after last pipe
		String[] split = command.split("\\|");
		String ans = "";
		for (int i = 0; i < split.length - 1; i++){
			ans += split[i] + "|";
		}
		if (split.length == 1){
			return "";
		}
		return ans.substring(0,ans.length() - 1);
	}
	
	private static SequentialFilter constructFilterFromSubCommand(String subCommand){
		//returns filters based on commands
		String[] newCommand = subCommand.split(" ");
		switch (newCommand[0]){
		case "ls": 
			return new ListSegments();
		case "pwd":
			return new PrintWorkingDirectory();
		case "cd":
			if (newCommand.length > 1){
				return new ChangeDirectory(newCommand[1]);
			} else {
				return new ChangeDirectory("");
			}
		case "head":
			if (newCommand.length == 3){
				return new Head(newCommand[1], newCommand[2],subCommand);
			} else if (newCommand.length == 2){
				try {
					Integer.parseInt(newCommand[1].substring(1));
					System.out.print(Message.REQUIRES_PARAMETER.with_parameter(subCommand));
					SequentialREPL.abort = true;
					break;
				} catch (Exception e){
					return new Head(newCommand[1],subCommand);
				}
			} else {
				System.out.print(Message.REQUIRES_PARAMETER.with_parameter(subCommand));
				SequentialREPL.abort = true;
				break;
			}
		case "grep":
			if (newCommand.length == 2){
				return new GREP(newCommand[1]);
			} else {
				System.out.print(Message.REQUIRES_PARAMETER.with_parameter(subCommand));
				SequentialREPL.abort = true;
				break;
			}
		case "wc":
			return new WordCount();
		case ">":
			if (newCommand.length == 2){
				return new Redirection(newCommand[1]);
			} else {
				System.out.print(Message.REQUIRES_PARAMETER.with_parameter(subCommand));
				SequentialREPL.abort = true;
				break;
			}
		default:
			System.out.print(Message.COMMAND_NOT_FOUND.with_parameter(subCommand));
			SequentialREPL.abort = true;
			break;
		}
		return null;
	}

	private static void linkFilters(List<SequentialFilter> filters){	
		//linking filters together
		if (filters.size() != 0){
			for (int i = 0; i < filters.size() - 1; i++){
				filters.get(i).setNextFilter(filters.get(i+1));
			}
			SequentialFilter last = filters.get(filters.size() - 1);
			last.output = new LinkedList<String>();
		}
	}
}
