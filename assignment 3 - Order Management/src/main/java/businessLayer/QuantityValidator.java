package businessLayer;

import Model.order;
import Model.product;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import dataAccessLayer.ProductQueries;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class QuantityValidator {
    /**
     * The method verifies if the quantity specified in the order given as a parameter in not greater than the stock;
     * If it is, a bill in PDF format will be generated, with the message "OUT OF STOCK"
     *
     * @param o it represents the order that we need to validate
     */
    public void validate(order o) {

        ProductQueries pr = new ProductQueries();
        product p = pr.findProductByName(o.getProduct_name());
        if(p!=null)
        if (o.getQuantity_ordered() >p.getQuantity()) {
            int i=1;
            try {
                System.out.println("report order");
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream("step " + i + " report_order.pdf"));
                document.open();
                Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
                Chunk chunk = new Chunk("OUT OF STOCK", font);
                document.add(chunk);
                document.close();
                i++;
            } catch (FileNotFoundException e) {
                System.out.println("The output file was not found");
            } catch (DocumentException e) {
                System.out.println("Error: document exception");
            }
        }

    }

}
