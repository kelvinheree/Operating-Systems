package cs131.pa1.filter.sequential;


import cs131.pa1.filter.Message;

public class GREP extends SequentialFilter{
	//search argument
	protected String search;
	//constructor
	public GREP(String search){
		super();
		this.search = search;
	}
	
	@Override
	public void process(){
		//null input 
		if (input == null){
			System.out.print(Message.REQUIRES_INPUT.with_parameter("grep " + search));
			SequentialREPL.abort = true;
		} else {
			//stringmatching
			while (!input.isEmpty()){
				String line = input.poll();
				String processedLine = processLine(line);
				if (processedLine != null){
					output.add(processedLine);
				}
			}
		}
	}
	
	@Override
	protected String processLine(String line) {
		if (line.contains(search)){
			return line;
		}
		return null;
	}
	
}
