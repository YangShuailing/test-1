package test.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

public class GeneratePDF {
    public static void main(String[] args) {
        try {
            OutputStream file = new FileOutputStream(new File("C:\\Users\\wajian\\Documents\\Test\\testPDF.pdf"));

            Document document = new Document();
            PdfWriter.getInstance(document, file);

            document.open();
            document.add(new Paragraph("Hello World, iText"));
            document.add(new Paragraph(new Date().toString()));

            document.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
