package scheduler;

import client.Client;
import queue.Server;

import java.util.List;

public interface Strategy {
    public void addTaskk(List<Server> servers, Client t);
}
