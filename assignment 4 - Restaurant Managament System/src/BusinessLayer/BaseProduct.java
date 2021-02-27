package BusinessLayer;

public class BaseProduct implements MenuItem {

    private static final long serialVersionUID = 1L;

    private String name;
    private float price;

    public BaseProduct() {
        this.name=null;
        this.price=0f;
    }

    public BaseProduct(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float computePrice() {
        return price;
    }



    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BaseProduct))
            return false;

        BaseProduct other = (BaseProduct) obj;
        return this.getName().equals(other.getName());
    }

    public String toString(){
        return this.name + " " + this.price;
    }
}