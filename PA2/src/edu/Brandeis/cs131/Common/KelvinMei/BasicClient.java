package edu.Brandeis.cs131.Common.KelvinMei;

import edu.Brandeis.cs131.Common.Abstract.Industry;

public class BasicClient extends MyClient {
	
	//constructor
	public BasicClient(String label, Industry industry){
		super(label,industry);
	}
	
    @Override
    public String toString() {
        return String.format("%s BASIC %s", super.getIndustry(), super.getName());
    }

	
}
