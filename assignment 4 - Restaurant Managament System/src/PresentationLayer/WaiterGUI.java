package PresentationLayer;

import BusinessLayer.BaseProduct;
import BusinessLayer.IRestaurantProcessing;
import BusinessLayer.MenuItem;
import BusinessLayer.Order;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class WaiterGUI {
    private JFrame view;
    private JTable table;
    private IRestaurantProcessing restaurantProcessing;
    private Vector selectedRow;
    private JButton addToOrder;
    private ArrayList<MenuItem> currentOrderItems;

    public WaiterGUI(IRestaurantProcessing processing) {
        this.restaurantProcessing=processing;
        this.currentOrderItems = new ArrayList<MenuItem>();
    }

    public void draw() {
        view = new JFrame();

        JLabel tablenr = new JLabel("Table");
        tablenr.setBounds(700, 100, 70, 40);
        final JTextField tabletxt = new JTextField("");
        tabletxt.setBounds(800, 100, 70, 40);

        final JTextArea textArea = new JTextArea();
        textArea.setBounds(700, 150, 400, 300);

        addToOrder = new JButton("Add to order");
        addToOrder.setBounds(70, 100, 150, 40);
        addToOrder.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                MenuItem menuItem = restaurantProcessing.findItembyName((String) selectedRow.get(0));
                textArea.setText(textArea.getText() + "\n" + menuItem.getName());
                currentOrderItems.add(menuItem);
            }
        });

        JTable table = createTable(restaurantProcessing.getMenu());

        JButton save = new JButton("Save & Give Bill");
        save.setBounds(250, 100, 200, 40);
        save.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    Integer tableNumber = Integer.parseInt(tabletxt.getText());
                    Order o = restaurantProcessing.createOrder(tableNumber, currentOrderItems);
                    restaurantProcessing.generateBill(new Order(o.getOrderID(), o.getDate(), o.getTable()));
                    textArea.setText("");
                    currentOrderItems.removeAll(currentOrderItems);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(view, "Invalid input");
                }
            }
        });
        view.add(tabletxt);
        view.add(tablenr);
        view.add(addToOrder);
        view.add(save);
        if(table!=null)
        view.add(table);
        view.add(textArea);
        view.setSize(950, 600);
        view.setLayout(null);
        view.setVisible(true);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JTable createTable(Collection<MenuItem> menu) {
        if (menu.size() > 0) {
            Object[] header = { "Name", "Price", "Type of product" };
            Object[][] data = new Object[menu.size() + 1][];
            data[0] = header;
            MenuItem[] array = new MenuItem[menu.size()];
            menu.toArray(array);
            for (int i = 0; i < array.length; i++) {
                Object[] menuRow = new Object[3];
                MenuItem item = array[i];
                menuRow[0] = item.getName();
                menuRow[1] = item.computePrice();
                if (item instanceof BaseProduct)
                    menuRow[2] = "base";
                else
                    menuRow[2] = "composite";

                data[i + 1] = menuRow;
            }
            final DefaultTableModel model = new DefaultTableModel(data, header);
            final JTable table = new JTable(model);
            table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

                public void valueChanged(ListSelectionEvent e) {
                    selectedRow = (Vector) model.getDataVector().get(table.getSelectedRow());
                }
            });
            table.setBounds(70, 150, 600, 300);
            return table;
        }
        return null;
    }
}