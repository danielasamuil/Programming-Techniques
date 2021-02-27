package BusinessLayer;

import DataLayer.FilesWriter;
import DataLayer.RestaurantSerializator;

import java.io.*;
import java.util.*;

/**
 * This is the class that implements the main methods used in our application
 * @f menu: all the menu items currently in the restaurant
 * @f orders: current orders taken by the waiter and given to the chef to process
 * @f nextOrderId : the ID of the order that is to be taken next
 */
public class Restaurant extends Observable  implements IRestaurantProcessing {

    private HashSet<MenuItem> menu;
    private Map<Order, Collection<MenuItem>> orders;
    private int nextOrderId;

    /**
     * The constructor initializes the first order and also loads the restaurant info;
     * Because serialization is used, the data is kept even after the application is shut down, that's why we have to
     * load it, to make sure the state we were in when we close the application is still available
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public Restaurant() throws ClassNotFoundException, IOException {
        nextOrderId = 1;
        menu = loadRestaurantInfo();
        orders = new HashMap<Order, Collection<MenuItem>>();
    }

    /**
     *This method is equivalent to the deserialization process, loading all the information preserved in the
     * restaurant.ser file into the app, making sure to preserve the state of the restaurant items
     * @return it returns the menu items available
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private HashSet<MenuItem> loadRestaurantInfo() throws ClassNotFoundException, IOException {
        try {
            FileInputStream fileIn = new FileInputStream("restaurant.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            menu = (HashSet<MenuItem>) in.readObject();
            in.close();
            fileIn.close();
            return menu;
        } catch (FileNotFoundException e) {
            return new HashSet<MenuItem>();
        }
    }

    /**
     * This method is equivalent to the serialization process, saving all the information about the current menu items
     * into a file that can be later on loaded
     * @throws IOException
     */
    private void saveRestaurantInfo() throws IOException {
      try {
          FileOutputStream fileOut = new FileOutputStream("restaurant.ser", false);
          ObjectOutputStream out = new ObjectOutputStream(fileOut);
          out.writeObject(this.menu);
          out.close();
          fileOut.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
    }

    /**
     * I implemented a "well formed" method to always check if the restaurant information is not destroyed after performing certain
     * actions on its elements.
     * We should check if the elements contained in the orders correspond to the items that we have in stock and if not, that means that
     * the client ordered something off the menu which is not accepted. Also,all orders should have at least one menu item.
     * @return it returns if the state of the restaurant is a 'good' or 'bad' one
     */
    public boolean WellFormed(){
        boolean OK = true;

        for (Order order : orders.keySet()) {
            Collection<MenuItem> orderItems = orders.get(order);

            for (MenuItem item : orderItems)
                if (!menu.contains(item)) {
                    OK = false;
                    break;
                }

            if (orderItems.size() < 1) {
                OK = false;
                break;
            }
        }
        return OK;
    }

    /**
     * The function takes all the items in an order and computes the final price od the order
     * @param order represents the for which the price should be computed
     * @return it returns a float representing the total value of the order
     * Ussing assertion i will test a precondition(that the order has at least one element) & a postcondition (that the price computed is nonzero)
     */
    public float computeOrderPrice(Order order) {
        //precondition
        assert orders.get(order) != null : "Order does not exist";

        float totalPrice = 0f;
        for (MenuItem orderItem : orders.get(order))
            totalPrice += orderItem.computePrice();

        //postcondition
        assert totalPrice > 0 : "Failed to calculate the price";

        return totalPrice;
    }

    /**
     *
     * @return it returns the menu items
     */
    public HashSet<MenuItem> getMenu() {
        return menu;
    }

    /**
     *The method adds the item given as a parameter to the current restaurant menu; To test if the addition to the menu
     * was made accordingly, the postcondition is used, that's why se calculate the size of the menu before and after the addition of the new item
     * The information is serialized
     * @param item the menu item that will be added
     * @throws IOException
     * @throws RestaurantSerializator
     */
    public void createNewMenuItem(MenuItem item) throws IOException, RestaurantSerializator{
        int beforeAdding = menu.size();
        menu.add(item);
        int afterAdding = menu.size();

        assert afterAdding-beforeAdding == 1 : "Creating the item was not successful";

        if (!WellFormed())
            throw new RestaurantSerializator();

        saveRestaurantInfo();
    }

    /**
     * The method deletes the item given as a parameter from the menu of the restaurant. The same idea for testing is used
     * as in the adding method, calculating the number of menu elements before and after deletion
     * The information is serialized
     * @param item represents the menu item that will be deleted from the menu
     * @throws IOException
     * @throws RestaurantSerializator
     */
    public void deleteMenuItem(MenuItem item) throws IOException, RestaurantSerializator {
        assert menu.contains(item) : "Menu doesn't contain this item.";

        int beforeDeleting = menu.size();
        menu.remove(item);
        int afterDeleting = menu.size();

        assert beforeDeleting - afterDeleting == 1 : "Deleting the item was not successful";

        if (!WellFormed())
          throw new RestaurantSerializator();

        saveRestaurantInfo();
    }

    /**
     * This method changes the name of an menu item; Firstly, we should see if the item is on the menu, else it would be
     * incorrectly; it is is, remove it and change its' name before adding it again. For a postcondition, the size of the menu
     * is considered, because editing a menu item should not change the size of the already existing menu
     * @param item represents the item that should be edited
     * @param newName represents the new name given the product
     * @throws IOException
     * @throws RestaurantSerializator
     */
    public void editMenuItem(MenuItem item,String newName) throws IOException, RestaurantSerializator {
        boolean isOnTheMenu = false;

        for (MenuItem menuItems : menu)
            if (menuItems.getName().equals(item.getName())) {
                isOnTheMenu = true;
                break;
            }

        assert isOnTheMenu : "This item is not on the menu";

        int beforeEditing = menu.size();
        menu.remove(item);
        item.setName(newName);
        menu.add(item);

        assert beforeEditing == menu.size() : "The size of the menu changed after editing";

        if (!WellFormed())
            throw new RestaurantSerializator();

        saveRestaurantInfo();
    }

    /**
     * This method creates a new order. It uses preconditions to test if the correct information is given before the order is created.
     * Also, we should test if the items requested by the client are on the menu list; if they are, then a new Order object is created,
     * assigning it the table number, the ID and the current date. Then a postcondition should be met regarding the size of the HashSet that
     * contains the orders.
     * Also, because the waiter should communicate directly with the chef about the orders, the methods linked to the Observable class are used
     * @param table represents the table number
     * @param orderItems represents the menu items that the command is formed of
     * @return it returns an object of type Order containing the information regarding the placed command(the table number, date and order ID)
     * @throws RestaurantSerializator
     */
    public Order createOrder(int table, Collection<MenuItem> orderItems) throws RestaurantSerializator {
        assert table > 0: "Invalid order: not a correct table";
        assert orderItems.size() > 0 : "Invalid order: no items in the order";

        boolean itemsAvailable = true;

        for (MenuItem orderItem : orderItems)
            if (!menu.contains(orderItem)) {
                itemsAvailable = false;
                break;
            }

        assert itemsAvailable : "One of the items in the order is not in the menu";

        int beforeOrdering = orders.keySet().size();
        Order newOrder = new Order(nextOrderId, new Date(), table);
        nextOrderId++;
        orders.put(newOrder, orderItems);

        assert orders.keySet().size() == beforeOrdering + 1 : "The order was not added";

        if (!WellFormed())
            throw new RestaurantSerializator();

        setChanged();
        notifyObservers(orderItems);
        clearChanged();

        return newOrder;
    }

    /**
     * This method writes the bill in a .txt format, having the table number, order number, the date when the order was placed,
     * the items from the order and the total price of all these items
     * @param order represents the order for which the bill is generated
     * @throws IOException
     */
    public void generateBill(Order order) throws IOException {
        assert order != null && orders.get(order) != null && orders.get(order).size() > 0 : "Invalid Order";

        FilesWriter w= new FilesWriter(computeOrderPrice(order));

        String s= w.createFile(order);
        w.writeToFile(s,"Table number: " + order.getTable());
        w.writeToFile(s,"Order number: " + order.getOrderID());
        w.writeToFile(s,"Date: " + order.getDate());
        w.writeToFile(s,"");
        for (MenuItem orderItem : orders.get(order))
            w.writeToFile(s,orderItem.getName() + ": " + orderItem.computePrice() + " RON");
        w.writeToFile(s,"");
        w.writeToFile(s,"Total: " + computeOrderPrice(order) +" RON");
    }

    /**
     * This method searches for a specific menu item that has the specified name given as a parameter
     * @param name represents the name that the menu item should have
     * @return it returns the menu item with the specified name
     */
    public MenuItem findItembyName(String name){
        for(MenuItem item : getMenu())
            if(item.getName().equals(name))
                return item;

        return null;
    }
}
