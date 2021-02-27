package com.company;

import client.Client;
import queue.Server;
import scheduler.Scheduler;
import scheduler.SelectionPolicy;

public class Main {

    public static void main(String[] args) {
       Simulation gen = new Simulation(args[0],args[1]);
       Thread t = new Thread(gen);
       t.start();

       // Client c1 = new Client(1,2,5);
       // Client c2= new Client(2,3,5);
       // Client c3= new Client(3,3,6);

        //Server s = new Server(7);
        //s.addTask(c1);
        //s.addTask(c2);
        //s.addTask(c3);
        //Thread t=new Thread(s);
        //t.start();

       //Scheduler s = new Scheduler(2,SelectionPolicy.SHORTEST_QUEUE,7);
       //s.dispatchTask(c1);
       //s.dispatchTask(c2);
       //s.dispatchTask(c3);

    }
}
