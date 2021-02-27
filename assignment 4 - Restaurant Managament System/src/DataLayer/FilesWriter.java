package DataLayer;

import BusinessLayer.Order;

import java.io.*;

public class FilesWriter{

    float orderPrice;

    public FilesWriter(float price){
        this.orderPrice=price;
    }

    public String createFile(Order o)throws IOException{
        try {
            File newFile = new File("bill_for_table_" + o.getTable() + ".txt");
            if (newFile.createNewFile()) {
                System.out.println("File created: " + newFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
        return "bill_for_table_" + o.getTable() + ".txt";
    }

    public void writeToFile(String file,String toBeWritten){
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
            output.append(toBeWritten);
            output.append('\n');
            output.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

}

