package edu.Brandeis.cs131.Common.KelvinMei;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import edu.Brandeis.cs131.Common.Abstract.Client;
import edu.Brandeis.cs131.Common.Abstract.Server;
import edu.Brandeis.cs131.Common.Abstract.Log.Log;

public class MasterServer extends Server {

    private final Map<Integer, List<Client>> mapQueues = new HashMap<Integer, List<Client>>();
    private final Map<Integer, Server> mapServers = new HashMap<Integer, Server>();
    private final Map<Integer, Condition> mapCond = new HashMap<Integer, Condition>();
    //locks and condition
    private final ReentrantLock lock = new ReentrantLock();

    public MasterServer(String name, Collection<Server> servers, Log log) {
        super(name, log);
        Iterator<Server> iter = servers.iterator();
        while (iter.hasNext()) {
            this.addServer(iter.next());
        }
    }

    public void addServer(Server server) {
        int location = mapQueues.size();
        this.mapServers.put(location, server);
        this.mapQueues.put(location, new LinkedList<Client>());
        this.mapCond.put(location, lock.newCondition());
    }

	@Override
    public boolean connectInner(Client client) {
        //locking
    	lock.lock();
        try{
        	//getting server and queue for client
        	int location = getKey(client);
	        Server server = mapServers.get(location);
	        List<Client> queue = mapQueues.get(location);
	        Condition cond = mapCond.get(location);
	        //adds client to queue
	        queue.add(client);
	        //FIFO implementation
	        //waits until it's first in line
	        while (!queue.get(0).equals(client)){
	        	try{
		        	cond.await();
	        	} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	        }
	        //once it's first you connect and get out of the queue, if it cannot enter you will continue waiting
	        while (!server.connectInner(client)){
	        	try{
		        	cond.await();
	        	} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	        }
	        //remove client from the queue
	        queue.remove(client);
	        //Successfully entered
	        return true;
        } finally {
        	lock.unlock();
        }
    }

    @Override
    public void disconnectInner(Client client) {
    	lock.lock();
        try{
        	int location = getKey(client);
	        Server server = mapServers.get(location);
	        Condition cond = mapCond.get(location);
	        server.disconnectInner(client);
	        //signals the condition
	        cond.signalAll();
        } finally {
        	lock.unlock();
        }
    }

	//returns a number from 0- mapServers.size -1
    // MUST be used when calling get() on mapServers or mapQueues
    private int getKey(Client client) {
        return client.getSpeed() % mapServers.size();
    }
}
