package BusinessLayer;

import DataLayer.RestaurantSerializator;

import java.io.IOException;
import java.util.Collection;

/**
 * This is the interface that will be implemented by the Restaurant class. The methods below are specific to the
 * administrator as well as so the the waiter. For example, an administrator could create a new menu item (base or composite),
 * edit an already existing one or delete one. For creating and deleting a menu item, a MenuItem parameter will be needed,
 * whereas for editing a menu item we also need the String which will be the new name of the product.
 * The waiter can create a new order, needing the table number that the order was taken from and a Collection of
 * menuItems that were ordered. In order to also generate the bill, it will need the total price that will be computed
 * by the computeOrderPrice() function.
 *Also,for editing or deleting a menu item, we will need to identify it by its name, hence the findItembyName(String name).
 *The getMenu() returns all the menu items currently in the restaurant menu.
 */
public interface IRestaurantProcessing {
    Collection<MenuItem> getMenu();

    void createNewMenuItem(MenuItem newMenuItem) throws IOException, RestaurantSerializator;

    void deleteMenuItem(MenuItem menuItem) throws IOException, RestaurantSerializator;

    void editMenuItem(MenuItem menuItem, String name) throws IOException, RestaurantSerializator;

    Order createOrder(int table, Collection<MenuItem> orderItems) throws RestaurantSerializator;

    float computeOrderPrice(Order order);

    void generateBill(Order order) throws IOException;

    MenuItem findItembyName(String name);
}