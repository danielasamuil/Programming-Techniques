package dataAccessLayer;

import Model.product;

/**
 * The class implements the methods from the AbstractQueries class, only specifically for the objects of type product
 */
public class ProductQueries extends AbstractQueries<product> {

    public product findProductByName(String s) {
        return super.findByName(s, "name");
    }

    public void insert(product product) {
        super.insert(product);
    }

    public void delete(product p, String s) {
        super.delete(p,s);
    }

    public void update(product p) {super.update(p);}
}
