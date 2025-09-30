package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.Production_ScheduleDTO;
import com.entity.ProductionSchedule;
import com.repository.ProductionScheduleRepository;

@Service
public class ProductionScheduleServiceImpl implements ProductionScheduleService {

    @Autowired
    private ProductionScheduleRepository scheduleRepo;

    @Override
    public ProductionSchedule createSchedule(Production_ScheduleDTO dto) {
        // Map DTO to entity
        ProductionSchedule schedule = new ProductionSchedule();
        schedule.setStartDate(dto.getStart_date());
        schedule.setEndDate(dto.getEnd_date());
        schedule.setStatus(dto.getStatus());

        return scheduleRepo.save(schedule);
    }

    @Override
    public List<ProductionSchedule> getAllSchedules() {
        return scheduleRepo.findAll();
    }

    @Override
    public Optional<ProductionSchedule> getScheduleById(Long id) {
        return scheduleRepo.findById(id);
    }

    @Override
    public List<ProductionSchedule> getSchedulesByStatus(String status) {
        return scheduleRepo.findByStatus(status);
    }
}
