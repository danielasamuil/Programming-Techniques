package dataAccessLayer;

import Model.order;

/**
 * The class implements the methods from the AbstractQueries class, only specifically for the objects of type order
 */
public class OrderQueries extends AbstractQueries<order> {

    public void insert(order c) {
        super.insert(c);
    }

    public void delete(order c, String s) {
        super.delete(c,s);
    }
}