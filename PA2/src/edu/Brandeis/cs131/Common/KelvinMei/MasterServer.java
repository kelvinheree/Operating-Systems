package edu.Brandeis.cs131.Common.KelvinMei;

import java.util.HashMap;
import java.util.LinkedList;

import edu.Brandeis.cs131.Common.Abstract.Client;
import edu.Brandeis.cs131.Common.Abstract.Log.Log;
import edu.Brandeis.cs131.Common.Abstract.Server;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MasterServer extends Server {

    private final Map<Integer, List<Client>> mapQueues = new HashMap<Integer, List<Client>>();
    private final Map<Integer, Server> mapServers = new HashMap<Integer, Server>();

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
    }

    @Override
    public boolean connectInner(Client client) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void disconnectInner(Client client) {
        // TODO Auto-generated method stub
    }

	//returns a number from 0- mapServers.size -1
    // MUST be used when calling get() on mapServers or mapQueues
    private int getKey(Client client) {
        return client.getSpeed() % mapServers.size();
    }
}
