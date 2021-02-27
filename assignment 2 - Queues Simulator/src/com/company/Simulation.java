package com.company;

import client.Client;
import client.Random_Client_Generator;
import queue.Server;
import scheduler.Scheduler;
import scheduler.SelectionPolicy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Simulation implements Runnable {
    public int N;
    public int Q;
    public int t_simulation;
    public int t_min_arrival;
    public int t_max_arrival;
    public int t_min_service;
    public int t_max_service;
    public SelectionPolicy selection = SelectionPolicy.SHORTEST_QUEUE;
    private Scanner scanner;
    private Scheduler scheduler;
    private List<Client> generatedTasks = new ArrayList<Client>();
    private String outputFile;


    public Simulation(String in, String out) {
        ArrayList<String> dataFromFile = new ArrayList<String>();

        try {
            File file = new File(in);
            this.scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                dataFromFile.add(data);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file");
        }

        this.N = Integer.parseInt(dataFromFile.get(0));
        this.Q = Integer.parseInt(dataFromFile.get(1));
        this.t_simulation = Integer.parseInt(dataFromFile.get(2));
        String[] parts = dataFromFile.get(3).split(",");
        String part1 = parts[0];
        String part2 = parts[1];
        this.t_min_arrival = Integer.parseInt(part1);
        this.t_max_arrival = Integer.parseInt(part2);
        String[] parts1 = dataFromFile.get(4).split(",");
        String ppart1 = parts1[0];
        String ppart2 = parts1[1];
        this.t_min_service = Integer.parseInt(ppart1);
        this.t_max_service = Integer.parseInt(ppart2);

        this.scheduler = new Scheduler(Q, selection, N);

        Random_Client_Generator rcg = new Random_Client_Generator(this.N);
        rcg.addClients(this.t_min_arrival, this.t_max_arrival, this.t_min_service, this.t_max_service);
        for (Client c : rcg.getListOfClients())
            this.generatedTasks.add(c);

        this.outputFile=out;

    }

    public void createFile(){
        try {
            File newFile = new File(this.outputFile);
            if (newFile.createNewFile()) {
                System.out.println("File created: " + newFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public void writeToFile(String file,String toBeWritten,boolean newLine){
        try {
            if(newLine){
                BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
                output.append(toBeWritten);
                output.append('\n');
                output.close();
            }
            else {
                FileWriter myWriter = new FileWriter(file, true);
                myWriter.write(toBeWritten);
                myWriter.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public void run() {
        int currentTime = 0;
        createFile();
        while (currentTime < this.t_simulation && this.generatedTasks.size()!=0) {
            try {
                for (int i = 0; i < this.generatedTasks.size(); i++)
                    if (this.generatedTasks.get(i).getArrival_time() == currentTime) {
                        Client c = this.generatedTasks.get(i);
                        scheduler.dispatchTask(c);
                        this.generatedTasks.remove(c);
                    }
                writeToFile(this.outputFile,"Time " + currentTime,true);
                writeToFile(this.outputFile,"Waiting clients: ",false);
                for (Client c1 : this.generatedTasks){
                    writeToFile(this.outputFile,c1.toString() + " ",false);
                    System.out.print(c1 + " ");
                }
                writeToFile(this.outputFile,"",true);
                System.out.println();
                for (int i = 0; i < this.Q; i++) {
                    int k = i + 1;
                    writeToFile(this.outputFile,"Queue " + k + ": " ,false);
                    if (this.scheduler.getServers().get(i).getTasks().length==0)
                        writeToFile(this.outputFile,"closed",true);
                    else {
                        for (int j = 0; j < this.scheduler.getServers().get(i).getTasks().length; j++) {
                            writeToFile(this.outputFile,this.scheduler.getServers().get(i).getTasks()[j].toString(),false);
                            if (currentTime - this.scheduler.getServers().get(i).getTasks()[j].getArrival_time()== this.scheduler.getServers().get(i).getTasks()[j].getService_time())
                                this.scheduler.getServers().get(i).removeClient(this.scheduler.getServers().get(i).getTasks()[j]);
                            writeToFile(this.outputFile,"",true);
                        }
                    }
                }
                System.out.println();
                currentTime++;
                Thread t = new Thread();
                t.sleep(990);
            } catch (InterruptedException e) {
                System.out.println("Someone interrupted me!");
                return;
            }
        }

    }
}
