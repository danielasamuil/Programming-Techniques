package Model;

/**
 * The class defines the object of type product, having an ID,name,quantity and price
 */
public class product {
    private Integer idproduct;
    private String name;
    private Integer quantity;
    private Float price;

    public product() {
        this(null,null,null,null);
    }

    public product(Integer id, String name, Integer quantity, Float price) {
        this.idproduct = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(Integer idproduct) {
        this.idproduct = idproduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String toString(){
        return this.getIdproduct() + ", " + this.getName() +", " + this.getQuantity() + ", " + this.getPrice();
    }
}
