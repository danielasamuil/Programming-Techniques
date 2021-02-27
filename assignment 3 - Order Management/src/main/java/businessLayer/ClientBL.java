package businessLayer;

import Model.client;
import dataAccessLayer.ClientQueries;

/**
 * This class implements the insert,delete and findbyName from the dataAccessLayer, being a link between the exterior and the interior of the application
 */
public class ClientBL {
    private ClientQueries clientDAO;

    public ClientBL() {
        clientDAO = new ClientQueries();
    }

    /**
     * Inserts a client
     *
     * @param c the client to be inserted
     */
    public void insert(client c) {
        clientDAO.insert(c);
    }

    /**
     * Deletion of a client
     *
     * @param c the client to be deleted
     * @param s the name of the client to be deleted
     */
    public void delete(client c,String s) {
        clientDAO.delete(c,s);
    }

    /**
     * Searching for a client by its name
     *
     * @param s the name of the client we are searching for
     * @return it returns the found client
     */
    public client findbyName(String s){
        return clientDAO.findByName(s);
    }
}
