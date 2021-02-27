package Model;

/**
 * The class defines the object of type order, having an ID,product name,quantity,ID of the product,ID of the client and client name
 */
public class order {
    private Integer idorder;
    private String product_name;
    private Integer quantity_ordered;
    private Integer id_product;
    private Integer id_client;
    private String client_name;

    public order() {
        this(null,null,null,null,null,null);
    }

    public order(Integer id, String pname, Integer quantity, Integer productId, Integer customerId, String s) {
        this.idorder = id;
        this.product_name=pname;
        this.quantity_ordered = quantity;
        this.id_product = productId;
        this.id_client = customerId;
        this.client_name=s;
    }

    public Integer getIdorder() {
        return idorder;
    }

    public void setIdorder(Integer idorder) {
        this.idorder = idorder;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Integer getQuantity_ordered() {
        return quantity_ordered;
    }

    public void setQuantity_ordered(Integer quantity_ordered) {
        this.quantity_ordered = quantity_ordered;
    }

    public Integer getId_product() {
        return id_product;
    }

    public void setId_product(Integer id_product) {
        this.id_product = id_product;
    }

    public Integer getId_client() {
        return id_client;
    }

    public void setId_client(Integer id_client) {
        this.id_client = id_client;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String toString(){
        return this.getIdorder() + ", " + this.getProduct_name() + ", " + this.getQuantity_ordered() + ", " +  this.getId_product() + ", " + this.getId_client() + ", "  + this.getClient_name() ;
    }
}
