package com.service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.entity.Invoice;
import com.entity.Order;
import com.entity.OrderItem;
import com.repository.OrderItemRepository;
import com.repository.OrderRepository;

// OpenPDF
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class PdfGeneratorService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    public PdfGeneratorService(OrderItemRepository orderItemRepository, OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
    }

    public byte[] generateInvoicePdf(Invoice invoice) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document doc = new Document(PageSize.A4);
            PdfWriter.getInstance(doc, out);
            doc.open();

            Order order = invoice.getOrder();
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            Paragraph title = new Paragraph("INVOICE #" + invoice.getInvoiceId(),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
            title.setSpacingAfter(10);
            doc.add(title);

            doc.add(new Paragraph("Order ID: " + (order != null ? order.getOrderId() : "-")));
            doc.add(new Paragraph("Invoice Date: " + df.format(invoice.getInvoiceDate())));
            if (invoice.getDueDate() != null) {
                doc.add(new Paragraph("Due Date: " + df.format(invoice.getDueDate())));
            }
            doc.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);

            table.addCell(headerCell("Item"));
            table.addCell(headerCell("Qty"));
            table.addCell(headerCell("Unit Price"));
            table.addCell(headerCell("Subtotal"));

            BigDecimal total = BigDecimal.ZERO;
            for (OrderItem it : orderItemRepository.findByOrder_OrderId(order.getOrderId())) {
                table.addCell(new Phrase(it.getName()));
                table.addCell(new Phrase(String.valueOf(it.getQuantity())));
                table.addCell(new Phrase(it.getUnitPrice().toPlainString()));
                BigDecimal line = it.getUnitPrice().multiply(new BigDecimal(it.getQuantity()));
                table.addCell(new Phrase(line.toPlainString()));
                total = total.add(line);
            }
            doc.add(table);

            Paragraph sum = new Paragraph("Total: " + invoice.getTotalAmount().toPlainString(),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
            sum.setSpacingBefore(10);
            doc.add(sum);

            doc.close();
            return out.toByteArray();
        } catch (DocumentException e) {
            throw new RuntimeException("Failed to generate invoice PDF", e);
        }
    }

    private PdfPCell headerCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
        return cell;
    }
}
