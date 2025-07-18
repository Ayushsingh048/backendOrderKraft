package com.repository;
import java.time.LocalDate;
import java.util.*;
import com.entity.Invoice;


import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository <Invoice, Long>  {

	 //Optional<Invoice> findByInvoiceId(long invoiceId);
	    List<Invoice> findByDueDate(LocalDate dueDate);
	    List<Invoice> findByInvoiceDate(LocalDate invoiceDate);

}

