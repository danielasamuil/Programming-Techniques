package Start;

import Presentation.PDF_generator;
import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;

/**
 * This is the main function
 *
 * @author Samuil Daniela Teodora
 *
 */

public class Main {
    public static void main(String[] args) throws FileNotFoundException, DocumentException {
        PDF_generator generate = new PDF_generator(args[0]);
        generate.generate_PDFs();
    }
}
