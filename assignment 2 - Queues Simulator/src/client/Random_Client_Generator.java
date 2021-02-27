package client;

import client.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Random_Client_Generator {
private ArrayList<Client> listOfClients;
private int N;

public Random_Client_Generator(int n)
{
    this.listOfClients= new ArrayList<Client>();
    this.N=n;
}

public void addClients(int min_arrival,int max_arrival, int min_service, int max_service){
    Random rand = new Random();

    for(int i=1; i<=this.N; i++)
{
    int rand_arrival = rand.nextInt((max_arrival - min_arrival) + 1) + min_arrival;
    int rand_service = rand.nextInt((max_service - min_service) + 1) + min_service;
    Client c=new Client(i,rand_arrival,rand_service);
    listOfClients.add(c);
}
}

public ArrayList<Client> getListOfClients(){
    return this.listOfClients;
}

public String toString(){
    for(Client c: this.listOfClients)
        System.out.println(c);
    return "";
}
}
