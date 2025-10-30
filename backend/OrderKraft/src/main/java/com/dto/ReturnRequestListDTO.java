package com.dto;

import java.util.List;

public class ReturnRequestListDTO {
    private List<ReturnRequestDTO> requests;

    public List<ReturnRequestDTO> getRequests() {
        return requests;
    }

    public void setRequests(List<ReturnRequestDTO> requests) {
        this.requests = requests;
    }
}
