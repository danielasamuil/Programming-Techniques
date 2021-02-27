package Presentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The class that will separate the input from the file
 */
public class InputParser {
    private Scanner scanner;
    private Integer number_of_lines;
    private ArrayList<String> commands;
    private ArrayList<String> details_insert_client;
    private ArrayList<String> dataFromFile;
    private ArrayList<String> details_delete_client;
    private ArrayList<String> details_insert_product;
    private ArrayList<String> details_delete_product;
    private ArrayList<String> details_order;

    public InputParser(String in) {
        this.dataFromFile = new ArrayList<String>();
        this.number_of_lines = 0;
        this.commands = new ArrayList<String>();
        this.details_insert_client = new ArrayList<String>();
        this.details_delete_client= new ArrayList<String>();
        this.details_insert_product = new ArrayList<String>();
        this.details_delete_product= new ArrayList<String>();
        this.details_order = new ArrayList<String>();

        try {
            File file = new File(in);
            this.scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                dataFromFile.add(data);
                number_of_lines++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file");
        }
    }

    /**
     * The method will divide the input and select the methods out of it;
     * The methods are always the first string on a line before the ":" character
     *
     * @param s is a string that needs to be divided
     */
    public void divideCommands(String s) {
            if (s.contains(":")) {
                String parts[] = s.split(":");
                commands.add(parts[0]);
            } else
                commands.add(s);
    }

    /**
     * The method constructs lists of the details about clients,products,orders that are inserted,removed etc.
     * It uses the division method, taking this time the part after the character ":"
     *
     * @param s is the line string that,like in the method before
     */
    public void getData(String s){
        if(s.contains("Insert client: ")) {
            String parts[] = s.split(": ");
            details_insert_client.add(parts[1]);
        }
        if(s.contains("Delete client: ")){
            String parts[] = s.split(": ");
            details_delete_client.add(parts[1]);
        }
        if(s.contains("Insert product: ")) {
            String parts[] = s.split(": ");
            details_insert_product.add(parts[1]);
        }
        if(s.contains("Delete product: ")){
            String parts[] = s.split(": ");
            details_delete_product.add(parts[1]);
        }
        if(s.contains("Order: ")){
            String parts[] = s.split(": ");
            details_order.add(parts[1]);
        }
    }

    /**
     * Combining the two methods shown before
     */
    public void separateInput(){
        for(String s: this.dataFromFile){
            divideCommands(s);
            getData(s);
        }
    }

    public Integer getNumber_of_lines(){
        return this.number_of_lines;
    }

    public ArrayList<String> getCommands(){
        return this.commands;
    }

    public ArrayList<String> getDetails_insert_client(){
        return this.details_insert_client;
    }

    public ArrayList<String> getDetails_insert_product(){
        return this.details_insert_product;
    }

    public ArrayList<String> getDetails_delete_client(){
        return this.details_delete_client;
    }

    public ArrayList<String> getDetails_delete_product(){
        return this.details_delete_product;
    }

    public ArrayList<String> getDetails_order(){
        return this.details_order;
    }
}
