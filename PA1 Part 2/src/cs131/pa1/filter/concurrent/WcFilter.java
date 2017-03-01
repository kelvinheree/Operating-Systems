package cs131.pa1.filter.concurrent;

public class WcFilter extends ConcurrentFilter {
	private int linecount;
	private int wordcount;
	private int charcount;
	
	public WcFilter() {
		super();
	}
	/*
	public void process() {
		if(isDone()) {
			System.out.println(isDone());
			output.add(processLine(null));
		} else {
			super.process();
		}
	}
	*/
	@Override
	public void process(){
		linecount = 0; charcount = 0; wordcount = 0;
		while (!isDone()){
			//if the incoming string is "THREADCOMPLETED", do not poll, let isDone do its job. Else, poll and process
				String line = input.peek();
				if (line != null){
					if (!line.equals("THREADCOMPLETED")){
						//pulls it out to prevent infinite loop
						input.poll();
						//counting
						linecount ++;
						charcount += line.length();
						String[] split = line.split(" ");
						wordcount += split.length;
					}
				}
		}
		String finalString = "" + linecount + " " + wordcount + " " + charcount;
		output.add(finalString);
		//completed thread
		output.add("THREADCOMPLETED");
	}
	
	
	public String processLine(String line) {
			return null;
	}

}
