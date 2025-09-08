package com.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.dto.InvoiceDTO;

public interface InvoiceService {
    InvoiceDTO generateForOrder(Long orderId);
    Optional<InvoiceDTO> getById(Long invoiceId);
    Optional<InvoiceDTO> getByOrderId(Long orderId);
    List<InvoiceDTO> getByInvoiceDate(LocalDate date);
    List<InvoiceDTO> getByDueDate(LocalDate date);
    List<InvoiceDTO> getAll();
    byte[] renderPdf(Long invoiceId);
}
