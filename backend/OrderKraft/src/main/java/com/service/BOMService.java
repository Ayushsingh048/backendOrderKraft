package com.service;

import java.util.List;
import com.dto.BOMDTO;

public interface BOMService {
    BOMDTO createBOM(BOMDTO bomDTO);
    BOMDTO getBOMById(Long id);
    List<BOMDTO> getAllBOMs();
    BOMDTO updateBOM(Long id, BOMDTO bomDTO);
    void deleteBOM(Long id);
}
