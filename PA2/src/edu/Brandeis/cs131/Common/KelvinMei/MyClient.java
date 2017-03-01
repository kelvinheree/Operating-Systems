package edu.Brandeis.cs131.Common.KelvinMei;

import edu.Brandeis.cs131.Common.Abstract.Client;
import edu.Brandeis.cs131.Common.Abstract.Industry;

public abstract class MyClient extends Client {

    public MyClient() {
        super("NOT IMPLEMENTED", Industry.TECHNOLOGY, 0, 0);
    }
    
    public MyClient(String label, Industry industry){
    	//calls the parent to create a Client with the label and industry given with a speed of 0-9 and request level of 3
    	super(label,industry,((int)Math.random() * 9),3);
    }
}
