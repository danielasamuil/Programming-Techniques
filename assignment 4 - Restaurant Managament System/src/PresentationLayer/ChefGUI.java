package PresentationLayer;

import BusinessLayer.MenuItem;

import java.util.*;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ChefGUI implements Observer {
    private JFrame view;
    private JTextArea textArea;
    private List<MenuItem> ordersToPrepare;

    public ChefGUI() {
        ordersToPrepare = new ArrayList<MenuItem>();
    }

    public void draw() {
        view = new JFrame();

        textArea = new JTextArea();
        textArea.setBounds(200, 150, 400, 300);

        StringBuilder sp = new StringBuilder();

        for (MenuItem menuItem : ordersToPrepare) {
            sp.append(menuItem.getName());
            sp.append("\n");
        }
        textArea.setText(sp.toString());
        view.add(textArea);
        view.setSize(950, 600);
        view.setLayout(null);
        view.setVisible(true);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void update(Observable waiter, Object orders) {
        ordersToPrepare.addAll((Collection<MenuItem>) orders);
        StringBuilder sp = new StringBuilder();

        for (MenuItem menuItem : ordersToPrepare) {
            sp.append(menuItem.getName());
            sp.append("\n");
        }

        textArea.setText(sp.toString());
    }
}