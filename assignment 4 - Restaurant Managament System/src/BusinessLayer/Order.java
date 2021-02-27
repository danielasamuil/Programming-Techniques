package BusinessLayer;

import java.util.Date;

public class Order {
    private int orderID;
    private Date Date;
    private int Table;


    public Order(int id, Date d, int t) {
        this.orderID = id;
        this.Date = d;
        this.Table = t;
    }

    public int getOrderID() {
        return orderID;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        Date = date;
    }

    public int getTable() {
        return Table;
    }

    public void setTable(int table) {
        Table = table;
    }

    @Override
    public int hashCode() {
        String str = orderID + "_" + Date.toString() + "_" + Table;
        return str.hashCode();
    }

    @Override
    public boolean equals(Object order) {
        if(!(order instanceof Order))
            return false;

        return this.orderID == ((Order) order).getOrderID();
    }
}