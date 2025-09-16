package com.service;

import com.dto.ReturnRequestDTO;
import com.entity.ReturnRequest;
import java.util.List;

public interface ReturnRequestService {
    ReturnRequest createReturnRequest(ReturnRequestDTO dto);
    List<ReturnRequest> getByOrder(Long orderId);
    ReturnRequest updateStatus(Long id, String status);
}
