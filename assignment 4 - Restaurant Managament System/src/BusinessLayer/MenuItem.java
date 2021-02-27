package BusinessLayer;

import java.io.Serializable;

public interface MenuItem extends Serializable {
    float computePrice();
    String getName();
    void setName(String s);
}