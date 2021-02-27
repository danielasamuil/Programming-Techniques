package com.company;

import BusinessLayer.IRestaurantProcessing;
import BusinessLayer.Restaurant;
import PresentationLayer.AdministratorGUI;
import PresentationLayer.ChefGUI;
import PresentationLayer.WaiterGUI;
import java.io.IOException;

public class Main{

    private static IRestaurantProcessing restaurantProcessing;
    private static ChefGUI chefGUI;
    private static AdministratorGUI administratorGUI;
    private static WaiterGUI waiterGUI;

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        restaurantProcessing = new Restaurant();
        chefGUI = new ChefGUI();
        ((Restaurant) restaurantProcessing).addObserver(chefGUI);
        administratorGUI = new AdministratorGUI(restaurantProcessing);
        waiterGUI = new WaiterGUI(restaurantProcessing);

        chefGUI.draw();
        waiterGUI.draw();
        administratorGUI.draw();
    }
}