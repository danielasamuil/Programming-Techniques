package scheduler;

import client.Client;
import queue.Server;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private int maxNoServers;
    private Strategy strategy;

    public Scheduler(int maxNServers, SelectionPolicy p, int nr_clients)
    {
        this.servers = new ArrayList<Server>();
        this.maxNoServers=maxNServers;
        this.changeStrategy(p);
        for(int i=0;i<maxNServers;i++){
            this.servers.add(new Server(nr_clients));
            Thread t= new Thread(servers.get(i));
            t.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_QUEUE){
            this.strategy=new ConcreteStrategyQueue();
        }
    }

    public void dispatchTask(Client t){
        this.strategy.addTaskk(this.servers,t);
    }

    public List<Server> getServers(){
        return this.servers;
    }
}
