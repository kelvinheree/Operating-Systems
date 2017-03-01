package edu.Brandeis.cs131.Common.KelvinMei;

import edu.Brandeis.cs131.Common.Abstract.Industry;

public class SharedClient extends MyClient {
	
	//constructor
	public SharedClient(String label, Industry industry){
		super(label,industry);
	}
	
	 @Override
	public String toString() {
		 return String.format("%s SHARED %s", super.getIndustry(), super.getName());
	}
	
}
