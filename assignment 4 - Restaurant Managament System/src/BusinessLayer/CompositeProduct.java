package BusinessLayer;
import java.util.ArrayList;
import java.util.Collection;

public class CompositeProduct implements MenuItem {

    private static final long serialVersionUID = 1L;

    private String name;
    private ArrayList<BaseProduct> items;

    public CompositeProduct() {
        this.name=null;
        this.items =null;
    }

    public CompositeProduct(String name, ArrayList<BaseProduct> list) {
        this.name = name;
        this.items = list;
    }

    public void add(BaseProduct item) {
        items.add(item);
    }

    public void remove(BaseProduct item) {
        items.remove(item);
    }

    public Collection<BaseProduct> getItems() {
        return items;
    }

    public float computePrice() {
        float price = 0f;
        for(MenuItem item : items) {
            price += item.computePrice();
        }
        return price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItems(ArrayList<BaseProduct> items) {
        this.items = items;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof CompositeProduct))
            return false;

        CompositeProduct other = (CompositeProduct) obj;
        return this.getName().equals(other.getName());
    }
}