package PresentationLayer;

import BusinessLayer.BaseProduct;
import BusinessLayer.CompositeProduct;
import BusinessLayer.IRestaurantProcessing;
import BusinessLayer.MenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class AdministratorGUI {
    private IRestaurantProcessing restaurantProcessing;
    private JFrame view;
    private JTable table;
    private JButton addBaseItem;
    private JButton addComposedItem;
    private JButton editItem;
    private JButton deleteItem;
    private Vector selectedRow;

    public AdministratorGUI(IRestaurantProcessing processing){
        this.restaurantProcessing=processing;
    }

    public void draw() {
        view = new JFrame();

        addBaseItem = new JButton("New Base Item");
        addBaseItem.setBounds(70, 100, 150, 40);
        addBaseItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                final JFrame newBaseProductView = new JFrame();
                newBaseProductView.setSize(400, 400);
                newBaseProductView.setLayout(null);
                newBaseProductView.setVisible(true);

                JButton save = new JButton("Save product");
                save.setBounds(70, 30, 150, 40);

                JLabel name = new JLabel("Name");
                name.setBounds(70, 130, 70, 40);
                final JTextField nametxt = new JTextField("");
                nametxt.setBounds(150, 130, 150, 40);

                JLabel priceLabel = new JLabel("Price");
                priceLabel.setBounds(70, 200, 70, 40);
                final JTextField priceTxt = new JTextField("");
                priceTxt.setBounds(150, 200, 150, 40);

                save.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        try {
                            restaurantProcessing.createNewMenuItem(new BaseProduct(nametxt.getText(), Float.parseFloat(priceTxt.getText())));
                            newBaseProductView.dispatchEvent(new WindowEvent(newBaseProductView, WindowEvent.WINDOW_CLOSING));
                            redrawTable();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(newBaseProductView, "Invalid input");
                        }
                    }
                });
                newBaseProductView.add(save);
                newBaseProductView.add(name);
                newBaseProductView.add(nametxt);
                newBaseProductView.add(priceLabel);
                newBaseProductView.add(priceTxt);
            }
        });

        addComposedItem = new JButton("New Composed Item");
        addComposedItem.setBounds(300, 100, 150, 40);
        addComposedItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                final JFrame newCompositeProductView = new JFrame();
                newCompositeProductView.setSize(400, 400);
                newCompositeProductView.setLayout(null);
                newCompositeProductView.setVisible(true);

                JButton save = new JButton("Save product");
                save.setBounds(90, 30, 150, 40);

                JLabel name = new JLabel("Name");
                name.setBounds(70, 130, 70, 40);
                final JTextField nametxt = new JTextField("");
                nametxt.setBounds(150, 130, 150, 40);
                newCompositeProductView.add(name);
                newCompositeProductView.add(nametxt);

                JLabel ingredient = new JLabel("Ingredients");
                ingredient.setBounds(70, 170, 170, 40);
                newCompositeProductView.add(ingredient);

                int idx = 0;
                final List<JTextField> ingredients = new ArrayList<JTextField>();

                //add up to 3 incredients
                for (int i=0; i<3; i++) {

                    final JTextField ingredienttxt = new JTextField("Name ingredient " + i);
                    ingredienttxt.setText("name");
                    ingredienttxt.setBounds(70, 200 + idx * 50, 100, 40);
                    ingredients.add(ingredienttxt);

                    final JTextField pricetxt = new JTextField("price");
                    pricetxt.setText("price");
                    pricetxt.setBounds(200, 200 + idx * 50, 50, 40);
                    ingredients.add(pricetxt);

                    idx++;
                    newCompositeProductView.add(ingredienttxt);
                    newCompositeProductView.add(pricetxt);
                }
                /*ArrayList<MenuItem> menuItemss=new ArrayList<MenuItem>();
                for(MenuItem m: restaurantProcessing.getMenu())
                    if(m instanceof BaseProduct)
                    menuItemss.add(m);

                MenuItem[] ingredients = new MenuItem[menuItemss.size()];
                for(int i=0;i<menuItemss.size();i++) {
                    ingredients[i] = menuItemss.get(i);
                }

                JComboBox<BaseProduct> addBasicProduct = new JComboBox(ingredients);
                addBasicProduct.setEditable(false);
                newCompositeProductView.add(addBasicProduct);

                ArrayList<BaseProduct> baseComponents = new ArrayList<BaseProduct>();*/

                save.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        try {
                            /*JButton addElement = new JButton("Add product");
                            addElement.setBounds(200, 30, 150, 40);
                            addElement.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent actionEvent) {
                                    BaseProduct product = (BaseProduct) addBasicProduct.getSelectedItem();
                                    baseComponents.add(product);
                                }
                            });*/
                            ArrayList<BaseProduct> baseComponents = new ArrayList<BaseProduct>();
                           for (int i = 0; i < ingredients.size() - 1; i = i + 2) {
                                String name = ingredients.get(i).getText();
                                float price = Float.parseFloat(ingredients.get(i + 1).getText());
                                BaseProduct b = new BaseProduct(name,price);
                                if(restaurantProcessing.getMenu().contains(b))
                                baseComponents.add(b);
                                else
                                    System.out.println("Product is not in the menu");
                            }
                            restaurantProcessing.createNewMenuItem(new CompositeProduct(nametxt.getText(), baseComponents));
                            newCompositeProductView.dispatchEvent(new WindowEvent(newCompositeProductView, WindowEvent.WINDOW_CLOSING));
                            redrawTable();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(newCompositeProductView, "Invalid input");
                        }
                    }
                });
                newCompositeProductView.add(save);
            }
        });

        view.add(addComposedItem);

        editItem = new JButton("Edit Menu Item");
        editItem.setBounds(500, 100, 150, 40);
        editItem.setEnabled(false);
        editItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                MenuItem menuItem = restaurantProcessing.findItembyName((String) selectedRow.get(0));

                if (menuItem instanceof CompositeProduct) {
                    final JFrame editView = new JFrame();
                    editView.setSize(400, 400);
                    editView.setLayout(null);
                    editView.setVisible(true);

                    JButton save = new JButton("Save");
                    save.setBounds(70, 30, 70, 40);

                    CompositeProduct product = (CompositeProduct) menuItem;
                    JLabel name = new JLabel("Name");
                    name.setBounds(70, 130, 70, 40);
                    final JTextField nametxt = new JTextField("");
                    nametxt.setText(menuItem.getName());
                    nametxt.setBounds(150, 130, 150, 40);
                    editView.add(name);
                    editView.add(nametxt);

                    JLabel ingredient = new JLabel("Ingredients");
                    ingredient.setBounds(1300, 130, 70, 40);
                    editView.add(ingredient);

                    int idx = 0;
                    final List<JTextField> ingredients = new ArrayList<JTextField>();
                    for (MenuItem cpItem : product.getItems()) {
                        final JTextField ingredienttxt = new JTextField(product.getName() + "Name");
                        ingredienttxt.setText(cpItem.getName());
                        ingredienttxt.setBounds(70, 200 + idx * 50, 150, 40);
                        ingredients.add(ingredienttxt);

                        final JTextField pricetxt = new JTextField(product.getName() + "Price");
                        pricetxt.setText(Float.toString(cpItem.computePrice()));
                        pricetxt.setBounds(250, 200 + idx * 50, 70, 40);
                        ingredients.add(pricetxt);

                        idx++;
                        editView.add(ingredienttxt);
                        editView.add(pricetxt);
                    }
                    String oldName=nametxt.getText();
                    save.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            try {
                                ArrayList<BaseProduct> baseComponents = new ArrayList<BaseProduct>();
                                for (int i = 0; i < ingredients.size() - 1; i = i + 2) {
                                    String name = ingredients.get(i).getText();
                                    float price = Float.parseFloat(ingredients.get(i + 1).getText());
                                    BaseProduct b = new BaseProduct(name,price);
                                    baseComponents.add(b);
                                }
                                restaurantProcessing.editMenuItem(new CompositeProduct(oldName, baseComponents),nametxt.getText());
                                editView.dispatchEvent(new WindowEvent(editView, WindowEvent.WINDOW_CLOSING));
                                redrawTable();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(editView, "Invalid input");
                            }
                        }
                    });
                    editView.add(save);
                }
            }
        });


        deleteItem = new JButton("Delete Menu Item");
        deleteItem.setBounds(700, 100, 150, 40);
        deleteItem.setEnabled(false);
        deleteItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                MenuItem menuItem = restaurantProcessing.findItembyName((String) selectedRow.get(0));
                try {
                    if(menuItem instanceof BaseProduct)
                    restaurantProcessing.deleteMenuItem(new BaseProduct((String) selectedRow.get(0), (Float) selectedRow.get(1)));
                    else
                        if(menuItem instanceof CompositeProduct)
                            restaurantProcessing.deleteMenuItem(new CompositeProduct((String) selectedRow.get(0),(ArrayList<BaseProduct>) selectedRow.get(1)));
                    redrawTable();
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(view, "Invalid input");
                }
            }
        });

        view.setSize(950, 600);
        view.setLayout(null);
        view.setVisible(true);
        view.add(addBaseItem);
        view.add(addComposedItem);
        view.add(editItem);
        view.add(deleteItem);
        redrawTable();
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JTable createTable(Collection<MenuItem> menu) {
        if (menu.size() > 0) {
            Object[] header = { "Name", "Price", "Base Product" };
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
                    menuRow[2] = true;
                 else
                    menuRow[2] = false;

                data[i + 1] = menuRow;
            }
            final DefaultTableModel model = new DefaultTableModel(data, header);
            JTable table = new JTable(model);
            table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

                public void valueChanged(ListSelectionEvent e) {
                    selectedRow = (Vector) model.getDataVector().get(e.getFirstIndex());
                    // enable edit and delete actions
                    editItem.setEnabled(true);
                    deleteItem.setEnabled(true);
                }
            });
            table.setBounds(70, 150, 600, 300);
            return table;
        }
        return null;
    }

    public void redrawTable() {
        if (view.isAncestorOf(table))
            view.remove(table);

        table = createTable(restaurantProcessing.getMenu());
        if(table!=null)
        view.add(table);
        SwingUtilities.updateComponentTreeUI(view);
    }
}