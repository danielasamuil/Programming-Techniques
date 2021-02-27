package businessLayer;

import Model.order;
import dataAccessLayer.OrderQueries;

/**
 * This class implements the insert from the dataAccessLayer, being a link between the exterior and the interior of the application
 */
public class OrderBL {
    private OrderQueries orderDAO;
    private QuantityValidator validator;

    public OrderBL() {
        this.orderDAO = new OrderQueries();
        this.validator = new QuantityValidator();
    }

    /**
     * Insertion of an order
     *
     * @param o the order to be inserted
     */
    public void insert(order o) {
       validator.validate(o);
       orderDAO.insert(o);
    }
}
