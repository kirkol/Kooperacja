package PDF;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

class PDF_MyFooter extends PdfPageEventHelper {
    Font ffont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
 
    
    public void onEndPage(PdfWriter writer, Document document) {
    	int i=writer.getPageNumber();
        PdfContentByte cb = writer.getDirectContent();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        Phrase header = new Phrase("Utworzono: "+dateFormat.format(date), ffont);
        Phrase podpis = new Phrase("Dokument wystawiony przez:", ffont);
        Phrase podpis2 = new Phrase("Podpis:                                    Data:", ffont);
        Phrase footer = new Phrase(String.format("Page %s", i), ffont);
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                header,
                document.right()-20,
                document.top() + 10, 0);
        ColumnText.showTextAligned(cb, Element.ALIGN_LEFT,
                podpis,
                (document.left()) / 2 + document.leftMargin(),
                document.bottom() + 5, 0);
        ColumnText.showTextAligned(cb, Element.ALIGN_LEFT,
                podpis2,
                (document.left()) / 2 + document.leftMargin(),
                document.bottom() - 5, 0);
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                footer,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.bottom() - 15, 0);
    
    }

}