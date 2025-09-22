package com.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SupplierDecisionDTO {

    @NotNull
    private Boolean accept;

    @Size(max = 4000)
    private String comment;

    public Boolean getAccept() { return accept; }
    public void setAccept(Boolean accept) { this.accept = accept; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
