package Presentation;
import Model.client;
import Model.order;
import Model.product;
import businessLayer.ClientBL;
import businessLayer.OrderBL;
import businessLayer.ProductBL;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
/**
 * This is the class where the 'main' functionality is displayed: the work with the tables, the generating of the bill, the generating of the PDFs
 */
public class PDF_generator {
    private InputParser input;
    private ClientBL clientBusinessLayer;
    private ProductBL ProductBusinessLayer;
    private OrderBL orderBusinessLayer;
    private ArrayList<product> productsInStock;
    private ArrayList<product> toBeRemoved;
    private ArrayList<product> toBeAdded;
    private Integer idClient;
    private Integer idProduct;
    private Integer idClientDel;
    private Integer idProductDel;
    private Integer idOrder;
    private ArrayList<client> list_of_clients;
    private ArrayList<client> remove_from_clients;
    private ArrayList<product> remove_from_products;
    private ArrayList<order> list_of_orders;

    public PDF_generator(String s){
        this.input= new InputParser(s);
        this.clientBusinessLayer = new ClientBL();
        this.ProductBusinessLayer = new ProductBL();
        this.orderBusinessLayer = new OrderBL();
        this.productsInStock = new ArrayList<product>();
        this.toBeRemoved = new ArrayList<product>();
        this.toBeAdded =  new ArrayList<product>();
        this.idClient=1;
        this.idProduct=1;
        this.idClientDel=1;
        this.idProductDel=1;
        this.idOrder=1;
        this.list_of_clients = new ArrayList<client>();
        this.remove_from_clients= new ArrayList<client>();
        this.remove_from_products= new ArrayList<product>();
        this.list_of_orders = new ArrayList<order>();
    }
    /**
     * The method that combines all of the following methods;
     * It first separates the input and the goes through it deciding what to do next based on the command found
     *
     * @throws FileNotFoundException in case the input file is not found
     * @throws DocumentException in case we cannot create an appropriate PDF
     */
    public void generate_PDFs() throws FileNotFoundException, DocumentException {
        input.separateInput();
        for(int i=0; i<input.getNumber_of_lines(); i++) {
               insert_client(i);
               insert_product(i);
               delete_client(i);
               report_clients(i);
               delete_product(i);
               report_products(i);
               insert_order(i);
               report_order(i);
               update_stock();
        }
        for(product p2 : this.productsInStock)
            ProductBusinessLayer.insert(p2);
    }
    /**
     * The method inserts a client into the specific table from the database, using the class ClientBL;
     * To do that, it divides the information about that client such that it is separated in the fields we need: name & address
     *
     * @param i used from the iteration mentioned above, to take the specific command
     */
    public void insert_client(int i){
        if(input.getCommands().get(i).equals("Insert client"))
        {
            client c = new client();
            c.setIdclient(this.idClient);
            this.idClient++;
            ArrayList<String> details_client= input.getDetails_insert_client();
            String clientInsert = details_client.get(0);
            String parts[] = clientInsert.split(", ");
            c.setName(parts[0]);
            c.setAddress(parts[1]);
            list_of_clients.add(c);
            input.getDetails_insert_client().remove(0);
            System.out.println("insert client  " + c);
            clientBusinessLayer.insert(c);
        }
    }
    /**
     * The method has the same functionality as the one for client insertion;
     * I also has something more: if an object with the same name as one already inserted is added, the quantity should be updated,
     * so we need to keep in mind the object to be deleted(with the old quantity) and the object to be added(with new quantity)
     *
     * @param i used from the iteration mentioned above, to take the specific command
     */
    public void insert_product(int i) {
        if (input.getCommands().get(i).equals("Insert product")) {
            product p = new product();
            p.setIdproduct(this.idProduct);
            this.idProduct++;
            ArrayList<String> details_product = input.getDetails_insert_product();
            String productInsert = details_product.get(0);
            String parts[] = productInsert.split(", ");
            p.setName(parts[0]);
            p.setQuantity(Integer.parseInt(parts[1]));
            p.setPrice(Float.parseFloat(parts[2]));
            input.getDetails_insert_product().remove(0);
            System.out.println("insert product  " + p);
            if (productsInStock.isEmpty())
                productsInStock.add(p);
            for (product p1 : this.productsInStock)
                if (p1.getName().equals(p.getName())) {
                    product pnew = new product();
                    pnew.setIdproduct(p1.getIdproduct());
                    pnew.setName(p1.getName());
                    pnew.setPrice(p1.getPrice());
                    pnew.setQuantity(p1.getQuantity() + p.getQuantity());
                    toBeAdded.add(pnew);
                    toBeRemoved.add(p1);
                }
            productsInStock.add(p);
        }
    }
    /**
     * This is the method that updates the stock based on what needs to be deleted(old) and what needs to replace it(new)
     */
    public void update_stock() {
        for (product remove1 : this.toBeRemoved)
            productsInStock.remove(remove1);
        for (product add1 : this.toBeAdded)
            for (product pr : productsInStock)
                if (pr.getName().equals(add1.getName()))
                    pr.setQuantity(add1.getQuantity());
    }
    /**
     * This method deletes the client from the database, separating the details just like in the methods above and then using ClientBL for deletion
     *
     * @param i used from the iteration mentioned above, to take the specific command
     */
    public void delete_client(int i){
        if(input.getCommands().get(i).equals("Delete client")){
            client c = new client();
            c.setIdclient(this.idClientDel);
            this.idClientDel++;
            ArrayList<String> details_client= input.getDetails_delete_client();
            String clientInsert = details_client.get(0);
            String parts[] = clientInsert.split(", ");
            c.setName(parts[0]);
            c.setAddress(parts[1]);
            input.getDetails_delete_client().remove(0);
            for(client c1: list_of_clients){
                if(c1.getName().equals(c.getName()))
                    remove_from_clients.add(c1);
            }
            for(client c2: remove_from_clients)
                list_of_clients.remove(c2);
            System.out.println("delete client  " + c);
            clientBusinessLayer.delete(c,c.getName());
        }
    }
    /**
     * The method has the same functionality as the one above, only this time a product is deleted and not a client
     *
     * @param i used from the iteration mentioned above, to take the specific command
     */
    public void delete_product(int i){
        if(input.getCommands().get(i).equals("Delete product")){
            product p = new product();
            p.setIdproduct(this.idProductDel);
            this.idProductDel++;
            ArrayList<String> details_product= input.getDetails_delete_product();
            String product = details_product.get(0);
            p.setName(product);
            input.getDetails_delete_product().remove(0);
            for(product p1: this.productsInStock)
                if(p1.getName().equals(p.getName())) {
                    p.setQuantity(p1.getQuantity());
                    p.setPrice(p1.getPrice());
                }
            for(product p1: productsInStock){
                if(p1.getName().equals(p.getName()))
                    remove_from_products.add(p1);
            }
            for(product p2: remove_from_products)
                productsInStock.remove(p2);
            System.out.println("delete product  " + p);
            ProductBusinessLayer.delete(p,p.getName());
        }
    }
    /**
     * This is the method that creates the PDF used as output when Report command is called
     *
     * @param i used from the iteration mentioned above, to take the specific command
     */
    public void report_clients(int i){
        if(input.getCommands().get(i).equals("Report client")) {
          try {
              System.out.println("report client");
              Document document = new Document();
              PdfWriter.getInstance(document, new FileOutputStream("step " + i + " report_client.pdf"));
              document.open();
              Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
              Chunk chunk = new Chunk("Client", font);
              document.add(chunk);
              PdfPTable table = new PdfPTable(3);
              addRowsClient(table);
              document.add(table);
              document.close();
          }
            catch (FileNotFoundException e){
            System.out.println("The output file was not found");
            }
            catch (DocumentException e){
                System.out.println("Error: document exception");
             }
        }
    }
    /**
     * The method is the same as the one for the client report, only this time it creates a report for the products
     *
     * @param i used from the iteration mentioned above, to take the specific command
     */
    public void report_products(int i) {
        if (input.getCommands().get(i).equals("Report product")) {
            try {
                System.out.println("report product");
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream("step " + i + " report_product.pdf"));
                document.open();
                Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
                Chunk chunk = new Chunk("Product", font);
                document.add(chunk);
                PdfPTable table = new PdfPTable(4);
                addRowsProduct(table);
                document.add(table);
                document.close();
            } catch (FileNotFoundException e) {
                System.out.println("The output file was not found");
            } catch (DocumentException e) {
                System.out.println("Error: document exception");
            }
        }
    }
    /**
     * The method creates new rows into a given table;
     * It is used in the method report_client above
     *
     * @param table defines the table that the rows will be added to
     */
    private void addRowsClient(PdfPTable table) {
        table.addCell("ID");
        table.addCell("NAME");
        table.addCell("ADDRESS");
        for(int i=0;i<list_of_clients.size();i++) {
            String id="" + list_of_clients.get(i).getIdclient();
            table.addCell(id);
            table.addCell(list_of_clients.get(i).getName());
            table.addCell(list_of_clients.get(i).getAddress());
        }
    }
    /**
     * The method creates new rows into a given table;
     * It is used in the method report_product above
     *
     * @param table defines the table that the rows will be added to
     */
    private void addRowsProduct(PdfPTable table) {
        table.addCell("ID");
        table.addCell("NAME");
        table.addCell("QUANTITY");
        table.addCell("PRICE");
        for(int i=0;i<productsInStock.size();i++) {
            String id="" + productsInStock.get(i).getIdproduct();
            String q="" + productsInStock.get(i).getQuantity();
            String price="" + productsInStock.get(i).getPrice();
            table.addCell(id);
            table.addCell(productsInStock.get(i).getName());
            table.addCell(q);
            table.addCell(price);
        }
    }
    /**
     * The method should insert a new order into the database (unfortunately it is not working properly right now);
     * Because the details given into the command line read from the input do not contain the id of the client and the product
     * that the order refers to, we need to find them based on their name in the other already existing tables(findByName);
     * Also, for every order, there should be a PDF created as the bill
     *
     * @param i used from the iteration mentioned above, to take the specific command
     */
    public void insert_order(int i){
        if(input.getCommands().get(i).equals("Order"))
        {
            order o = new order();
            o.setIdorder(this.idOrder);
            this.idOrder++;
            ArrayList<String> details_order= input.getDetails_order();
            String orderInsert = details_order.get(0);
            String parts[] = orderInsert.split(", ");
            o.setClient_name(parts[0]);
            o.setProduct_name(parts[1]);
            o.setQuantity_ordered(Integer.parseInt(parts[2]));
            float totalprice=0;
            client c=clientBusinessLayer.findbyName(o.getClient_name());
            if(c!=null)
            o.setId_client(c.getIdclient());
            product p=ProductBusinessLayer.findbyName(o.getProduct_name());
            if(p!=null){
            o.setId_product(p.getIdproduct());
            if(p.getQuantity()>o.getQuantity_ordered()) {
                p.setQuantity(p.getQuantity()-o.getQuantity_ordered());
                ProductBusinessLayer.update(p);
                for(product pr: productsInStock)
                    if(pr.getName().equals(p.getName()))
                        pr.setQuantity(p.getQuantity());
                totalprice=o.getQuantity_ordered()*p.getPrice();
                list_of_orders.add(o);
            }
            else
                System.out.println("out of stock for this order");
            }
            input.getDetails_order().remove(0);

            System.out.println("insert order  " + o);
            orderBusinessLayer.insert(o);
            try {
                System.out.println("order:bill");
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream("step " + i + " order_bill.pdf"));
                document.open();
                Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
                Chunk chunk1 = new Chunk(o.getClient_name() + " ordered " + o.getQuantity_ordered() + " of " + o.getProduct_name() +"; total price: " + totalprice, font);
                document.add(chunk1);
                document.close();
            } catch (FileNotFoundException e) {
                System.out.println("The output file was not found");
            } catch (DocumentException e) {
                System.out.println("Error: document exception");
            }
        }
    }
    /**
     * The method returns the PDF report of the order table in the database, just like the other reports already shown
     *
     * @param i used from the iteration mentioned above, to take the specific command
     */
    public void report_order(int i) {
        if (input.getCommands().get(i).equals("Report order")) {
            try {
                System.out.println("report order");
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream("step " + i + " report_order.pdf"));
                document.open();
                Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
                Chunk chunk = new Chunk("Order", font);
                document.add(chunk);
                PdfPTable table = new PdfPTable(6);
                addRowsOrder(table);
                document.add(table);
                document.close();
            } catch (FileNotFoundException e) {
                System.out.println("The output file was not found");
            } catch (DocumentException e) {
                System.out.println("Error: document exception");
            }
        }
    }
    /**
     * The method adds new rows in the table from the order PDF
     *
     * @param table defined the table that the rows will be added to
     */
    private void addRowsOrder(PdfPTable table) {
        table.addCell("ID");
        table.addCell("PRODUCT NAME");
        table.addCell("QUANTITY");
        table.addCell("ID PRODUCT");
        table.addCell("ID CLIENT");
        table.addCell("CLIENT NAME");
        for(int i=0;i<list_of_orders.size();i++) {
            String id="" + list_of_orders.get(i).getIdorder();
            String q="" +list_of_orders.get(i).getQuantity_ordered();
            String idP="" +list_of_orders.get(i).getId_product();
            String idC="" +list_of_orders.get(i).getId_client();
            table.addCell(id);
            table.addCell(list_of_orders.get(i).getProduct_name());
            table.addCell(q);
            table.addCell(idP);
            table.addCell(idC);
            table.addCell(list_of_orders.get(i).getClient_name());
        }
    }
}
