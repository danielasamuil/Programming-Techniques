package queue;

import client.Client;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private ArrayBlockingQueue<Client> tasks;
    private AtomicInteger waitingPeriod;
    private boolean OK;

    public Server(int nr_clients) {
        this.tasks = new ArrayBlockingQueue<Client>(nr_clients/2);
        this.waitingPeriod = new AtomicInteger(0);
        this.OK=true;
    }

    public void addTask(Client newTask) {
        tasks.add(newTask);
        waitingPeriod.set(waitingPeriod.get()+newTask.getService_time());
    }

    public void run() {
        try {
            while (OK) {
                if(tasks.size()!=0) {
                    Client c = tasks.poll();
                    waitingPeriod.set(0);
                    Thread.sleep(c.getService_time() * 1000);

                    if(tasks.size()==0)
                        OK=false;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Someone interrupted me!");
            return;
        }
    }

    public void setOK(boolean flag){
        this.OK=flag;
    }

    public AtomicInteger getWaitingPeriod()
    {
        return this.waitingPeriod;
    }

    public Client[] getTasks(){
        Client c[] = new Client[this.tasks.size()];
        this.tasks.toArray(c);
        return c;
    }

    public void removeClient(Client c){
        tasks.remove(c);
    }
}
