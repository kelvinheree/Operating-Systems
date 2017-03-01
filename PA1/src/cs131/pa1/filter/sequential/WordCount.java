package cs131.pa1.filter.sequential;

import cs131.pa1.filter.Message;

public class WordCount extends SequentialFilter {

	protected int lines;
	protected int chars;
	protected int words;
	
	@Override
	public void process(){
		//null input
		if (input == null){
			System.out.print(Message.REQUIRES_INPUT.with_parameter("wc"));
			SequentialREPL.abort = true;
		} else {
			//basic counting with scanner
			lines = 0; chars = 0; words = 0;
			while (!input.isEmpty()){
				String line = input.poll();
				lines ++;
				chars += line.length();
				String[] split = line.split(" ");
				words += split.length;
			}
			String finalString = "" + lines + " " + words + " " + chars;
			output.add(finalString);
		}
	}
	
	@Override
	protected String processLine(String line) {
		return null;
	}

}
