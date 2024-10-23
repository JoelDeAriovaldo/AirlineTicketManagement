/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author JoelDeAriovaldo
 */
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFGenerator {

    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

    public static byte[] generateTicketPDF(String customerName, String flightNumber, String origin, String destination, 
                                           String departureTime, String seatNumber, double totalPrice) {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            // Adicionar título do bilhete
            Paragraph title = new Paragraph("Bilhete de Passagem Aérea", TITLE_FONT);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" ")); // Espaçamento

            // Tabela com informações do bilhete
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            addTableCell(table, "Nome do Cliente:", customerName);
            addTableCell(table, "Número do Voo:", flightNumber);
            addTableCell(table, "Origem:", origin);
            addTableCell(table, "Destino:", destination);
            addTableCell(table, "Horário de Partida:", departureTime);
            addTableCell(table, "Assento:", seatNumber);
            addTableCell(table, "Preço Total:", String.format("R$ %.2f", totalPrice));

            document.add(table);

            // Rodapé
            document.add(new Paragraph(" "));
            Paragraph footer = new Paragraph("Obrigado por escolher nossa companhia aérea!", NORMAL_FONT);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return baos.toByteArray();
    }

    private static void addTableCell(PdfPTable table, String key, String value) {
        PdfPCell keyCell = new PdfPCell(new Phrase(key, NORMAL_FONT));
        keyCell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(keyCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, NORMAL_FONT));
        valueCell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(valueCell);
    }

    // Método para salvar o PDF em um arquivo (opcional)
    public static void savePDFToFile(String fileName, byte[] pdfBytes) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(pdfBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Exemplo de uso do PDFGenerator
        byte[] pdfBytes = generateTicketPDF("João Silva", "TP123", "Lisboa", "São Paulo", 
                                            "2024-12-01 14:00", "12A", 1200.50);
        savePDFToFile("BilheteDePassagem.pdf", pdfBytes);
    }
}
