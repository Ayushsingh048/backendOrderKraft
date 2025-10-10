package com.service;

import com.dto.ReturnRequestDTO;
import com.entity.ReturnRequest;
import java.util.List;

public interface ReturnRequestService {
    ReturnRequest createReturnRequest(ReturnRequestDTO dto);
    List<ReturnRequest> getByOrder(Long orderId);
    List<ReturnRequest> getBySupplier(Long supplierId); // ✅ Add this
    List<ReturnRequest> getAll();
    ReturnRequest updateStatus(Long id, String status);
}
