package com.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.BOMDTO;
import com.dto.BOMMaterialDTO;
import com.entity.BOM;
import com.repository.BOMRepository;
import com.service.BOMService;

@Service
@Transactional
public class BOMServiceImpl implements BOMService {

    @Autowired
    private BOMRepository bomRepository;

    @Override
    public BOMDTO createBOM(BOMDTO bomDTO) {
        BOM bom = new BOM();
        bom.setbomName(bomDTO.getBomName());
        bom.setRemark(bomDTO.getRemark());

        BOM savedBOM = bomRepository.save(bom);
        bomDTO.setBomId(savedBOM.getBom_id());
        return bomDTO;
    }

    @Override
    public BOMDTO getBOMById(Long id) {
        BOM bom = bomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BOM not found"));

        List<BOMMaterialDTO> materials = bom.getMaterials()
                .stream()
                .map(m -> new BOMMaterialDTO(
                        m.getMaterialId(),
                        bom.getBom_id(),
                        m.getRawmaterial() != null ? m.getRawmaterial().getInventory_rawmaterial_id() : null,
                        m.getRawmaterial() != null ? m.getRawmaterial().getName() : null,  // ✅ include name
                        m.getQntperunit()))
                .collect(Collectors.toList());

        return new BOMDTO(
                bom.getBom_id(),
                bom.getbomName(),
                bom.getRemark(),
                materials);
    }


    @Override
    public List<BOMDTO> getAllBOMs() {
        return bomRepository.findAll().stream()
                .map(b -> new BOMDTO(b.getBom_id(), b.getbomName(), b.getRemark(), null))
                .collect(Collectors.toList());
    }

    @Override
    public BOMDTO updateBOM(Long id, BOMDTO bomDTO) {
        BOM bom = bomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BOM not found"));
        bom.setbomName(bomDTO.getBomName());
        bom.setRemark(bomDTO.getRemark());
        bomRepository.save(bom);
        return bomDTO;
    }

    @Override
    public void deleteBOM(Long id) {
        bomRepository.deleteById(id);
    }
}
