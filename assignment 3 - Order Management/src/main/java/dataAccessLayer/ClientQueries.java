package dataAccessLayer;

import Model.client;

/**
 * The class implements the methods from the AbstractQueries class, only specifically for the objects of type client
 */
public class ClientQueries extends AbstractQueries<client> {

    public client findByName(String s) {
        return super.findByName(s,"name");
    }

    public void insert(client c){
        super.insert(c);
    }

    public void delete(client c,String s){
        super.delete(c,s);
    }
}