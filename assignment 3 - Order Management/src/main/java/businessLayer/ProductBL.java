package businessLayer;

import Model.product;
import dataAccessLayer.ProductQueries;

/**
 * This class implements the insert,delete and findbyName from the dataAccessLayer, being a link between the exterior and the interior of the application
 */
public class ProductBL {
    private ProductQueries productDAO;

    public ProductBL() {
        this.productDAO=new ProductQueries();
    }

    /**
     * Insertion of a product
     *
     * @param p product to be inserted
     */
    public void insert(product p) {
        productDAO.insert(p);
    }

    /**
     * Deletion of a product
     *
     * @param p product to be inserted
     * @param s the name of the product to be deleted
     */
    public void delete(product p, String s) {
        productDAO.delete(p,s);

    }

    /**
     * Searching for a product by its name
     *
     * @param s the name of the product that is searched for
     * @return it returns the found product
     */
    public product findbyName(String s){ return productDAO.findProductByName(s);}

    public void update(product p) {productDAO.update(p);}
}
