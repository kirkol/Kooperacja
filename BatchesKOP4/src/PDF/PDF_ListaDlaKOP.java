package PDF;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.ImageIcon;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import WB.AddBatch;
import WB.Login;

import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.io.IOException;

public class PDF_ListaDlaKOP {
	
	public static Font ffont = FontFactory.getFont("times", BaseFont.CP1250, BaseFont.EMBEDDED, 10); 
	public static Font ffont2 = FontFactory.getFont("times", BaseFont.CP1250, BaseFont.EMBEDDED, 12);
	//public static Font ffont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
	//public static Font ffont2 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	// private static Connection conn;
	
	private static void addHeader(PdfPTable t){
		
		String komorki[] = new String[] {"NrSerii", "Artykul", "Nazwa", "Ilosc", "Material", "Opis technologii", "Komentarz"};
		for(int i = 0; i<7; i++){
			PdfPCell cell = new PdfPCell(new Phrase(komorki[i], ffont));
			cell.setFixedHeight(30f);
			cell.setBackgroundColor(BaseColor.ORANGE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setBorderWidth(0.2f);
			cell.setBorderWidthTop(1f);
			cell.setBorderWidthBottom(1f);
			cell.setBorderWidthLeft(0.2f);
			cell.setBorderWidthRight(0.2f);
			cell.setNoWrap(false);
			t.addCell(cell);
		}
	}
	
	private static void addCell (PdfPTable t, String a){
		
		PdfPCell cell = new PdfPCell(new Phrase(a, ffont));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setMinimumHeight(25f);
		cell.setBorderWidth(0.2f);
		cell.setNoWrap(false);
		// if(a.equals("coœ ciekawego")) cell.setBackgroundColor(BaseColor.YELLOW);
		t.addCell(cell);
	}

	public static void createPDF(Connection conn) throws SQLException{
		
		Document PDFkooperacja = new Document(PageSize.A4.rotate());
		// SimpleDateFormat Wpliku = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat doNazwy = new SimpleDateFormat("yyyy.MM.dd");
		SimpleDateFormat doNazwy2 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat godz = new SimpleDateFormat("HH;mm");
		 Calendar date = Calendar.getInstance();
		 
	try	 
	{ 
		String path = PDF.Parameters.getPathtoFolder()+"/";
		String lista = doNazwy.format(date.getTime())+" - "+godz.format(date.getTime())+" "+"Lista czesci do kooperacji";
		File plik = new File(path+lista+doNazwy);
		if (plik.exists() && plik.isDirectory())
				lista = godz.format(date.getTime())+" "+lista;
		PdfWriter writer = PdfWriter.getInstance(PDFkooperacja, new FileOutputStream(path+lista+".pdf"));
		PDFkooperacja.open();
		writer.setPageEvent(new PDF_MyFooter());
		
		Image img = Image.getInstance(PDF_ListaDlaKOP.class.getClassLoader().getResource("FATlogo_mini.png"));
		img.scalePercent(50);
		img.setAbsolutePosition(735f, 470f);
		PDFkooperacja.add(img);
		
		String nazwa = AddBatch.getNazwaOdbiorcy();
		String adresOdbiorcy = AddBatch.getAdresOdbiorcy();
		
		Paragraph adres = new Paragraph( "Fabryka Automatów Tokarskich we Wroc³awiu S.A.           NAZWA I ADRES ODBIORCY:", ffont2);
		Paragraph adres2 = new Paragraph("53-234 Wroc³aw, ul. Grabiszyñska 281                                  "+nazwa, ffont2);
		Paragraph adres3 = new Paragraph("fax +48 71/360-91-21                                                              "+adresOdbiorcy, ffont2);
		Paragraph adres4 = new Paragraph("tel. +48 71/360-91-00", ffont2);
		
		//Paragraph title = new Paragraph("Wydanie materia³u na zewn¹trz: WZ/"+doNazwy2.format(date.getTime())+"1", ffont2);
		Paragraph title = new Paragraph("Wydanie materia³u na zewn¹trz: WZ/"+AddBatch.getNrWZ(), ffont2);
		
		adres.setAlignment(Element.ALIGN_LEFT);
		adres.setFont(ffont2);
		PDFkooperacja.add(adres);
		
		adres2.setAlignment(Element.ALIGN_LEFT);
		adres2.setFont(ffont2);
		PDFkooperacja.add(adres2);
		
		adres3.setAlignment(Element.ALIGN_LEFT);
		adres3.setFont(ffont2);
		PDFkooperacja.add(adres3);
		
		adres4.setAlignment(Element.ALIGN_LEFT);
		adres4.setFont(ffont2);
		PDFkooperacja.add(adres4);
		
		title.setSpacingAfter(10f);
		title.setAlignment(Element.ALIGN_LEFT);
		title.setFont(ffont2);
		PDFkooperacja.add(title);
		
		PdfPTable table = new PdfPTable(7);
		float widths[] = new float[] {5, 10, 10, 10, 10, 18, 7};
		addHeader(table);
		
		Statement takeBatchesKOP = conn.createStatement();
		ResultSet tabelaKOP;
		
		if(Login.getAdmin()){
			tabelaKOP = takeBatchesKOP.executeQuery("SELECT * FROM BatchesKOP");
		}else{
			tabelaKOP = takeBatchesKOP.executeQuery("SELECT * FROM BatchesKOP2");
		}
		
		while (tabelaKOP.next()){
			for (int i=2; i<=8; i++){
			addCell (table, tabelaKOP.getString(i));	
			}
			
		}
		takeBatchesKOP.close();
		table.setWidthPercentage(100);
		table.setWidths(widths);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.setHeaderRows(1);
		if(table.size()==0 ){
			Paragraph a = new Paragraph("Document is empty", ffont);
			PDFkooperacja.add(a);
		}
		else{
			PDFkooperacja.add(table);
		}
		
//		Paragraph podpisy = new Paragraph("Dokument wystawiony przez:", ffont2);
//		Paragraph podpisy2 = new Paragraph("Podpis:                                    Data:", ffont2);
//		
//		podpisy.setAlignment(Element.ALIGN_LEFT);
//		podpisy.setFont(ffont2);
//		PDFkooperacja.add(podpisy);
//		
//		podpisy2.setAlignment(Element.ALIGN_LEFT);
//		podpisy2.setFont(ffont2);
//		PDFkooperacja.add(podpisy2);
		
		PDFkooperacja.close();
		 
		 
	}
	catch(Exception e){
		e.printStackTrace();	 

		}
	
	}

}
