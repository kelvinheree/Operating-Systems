package edu.Brandeis.cs131.Common.KelvinMei;

import java.util.ArrayList;

import edu.Brandeis.cs131.Common.Abstract.Client;
import edu.Brandeis.cs131.Common.Abstract.Server;

public class BasicServer extends Server {
	//ArrayList to hold the current clients
	ArrayList<Client> clientList;
	
	public BasicServer(String name) {
		super(name);
		clientList = new ArrayList<Client>();
	}

	//synchronized to make sure only one thread calls this method for the Server at a time
	@Override
	public synchronized boolean connectInner(Client client) {
		//if servicing no clients, add client
		if (clientList.size() == 0){
			clientList.add(client);
			return true;
		} else if (clientList.size() == 1){
			Client current = clientList.get(0);
			//if there's 1 client and it's Basic, do not allow access
			if (current instanceof BasicClient){
				return false;
			} else {
				//if there's 1 client and it's Shared, but the client to add is basic, do not allow access
				if (client instanceof BasicClient){
					return false;
				}{
					//if industry of the two SharedClients are the same, do not allow access, else allow access and add client to clientList
					if (current.getIndustry() == client.getIndustry()){
						return false;
					} else {
						clientList.add(client);
						return true;
					}
				}
			}
		} else {
			//case when it's servicing 2 clients already
			return false;
		}
	}
	

	//synchronized to make sure only one thread calls this method for the Server at a time
	@Override
	public synchronized void disconnectInner(Client client) {
		//removing client from arraylist
		if (clientList.indexOf(client) != -1){
			clientList.remove(client);
		}
	}
}
