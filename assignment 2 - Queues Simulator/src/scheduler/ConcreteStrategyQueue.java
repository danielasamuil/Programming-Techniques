package scheduler;

import client.Client;
import queue.Server;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ConcreteStrategyQueue implements Strategy {
    public void addTaskk(List<Server> servers, Client c) {
        int OK = 1;
        for (Server s : servers) {
            if (OK == 1)
                if (s.getWaitingPeriod().get() == 0) {
                    s.addTask(c);
                    s.setOK(true);
                    OK = 0;
                }

        }
    }
}
