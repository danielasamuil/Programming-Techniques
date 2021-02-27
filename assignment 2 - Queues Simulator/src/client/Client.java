package client;

public class Client {
    private int ID;
    private int arrival_time;
    private int service_time;

    public Client(int i, int a, int s){
        this.ID=i;
        this.arrival_time=a;
        this.service_time=s;
    }

    public int getID(){

        return this.ID;
    }

    public int getArrival_time(){

        return this.arrival_time;
    }

    public int getService_time(){

        return this.service_time;
    }

    public String toString() {
        return "(" + this.getID() + ", " + this.getArrival_time() + ", " + this.getService_time() + ")";
    }
}
