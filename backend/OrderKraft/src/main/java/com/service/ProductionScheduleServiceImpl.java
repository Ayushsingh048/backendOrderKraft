package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.Production_ScheduleDTO;
import com.entity.ProductionSchedule;
import com.entity.User;
import com.repository.ProductionScheduleRepository;
import com.repository.UserRepository;

@Service
public class ProductionScheduleServiceImpl implements ProductionScheduleService {

    @Autowired
    private ProductionScheduleRepository scheduleRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public ProductionSchedule createSchedule(Production_ScheduleDTO dto) {
        // Convert java.util.Date to java.time.LocalDate
       // LocalDate startDate = new java.sql.Date(dto.getStart_date().getTime()).toLocalDate();
        //LocalDate endDate = new java.sql.Date(dto.getEnd_date().getTime()).toLocalDate();

        // Get the manager (User) entity by ID
        User manager = userRepo.findById(dto.getProduction_manager_id())
                .orElseThrow(() -> new RuntimeException("Manager not found with ID: " + dto.getProduction_manager_id()));

        // Map DTO to entity
        ProductionSchedule schedule = new ProductionSchedule();
        schedule.setStartDate(dto.getStart_date());
        schedule.setEndDate(dto.getEnd_date());
        schedule.setStatus(dto.getStatus());
        schedule.setUser(manager);

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
    public List<ProductionSchedule> getSchedulesByProductionManagerId(Long productionManagerId) {
        return scheduleRepo.findByUser_Id(productionManagerId);
    }

    @Override
    public List<ProductionSchedule> getSchedulesByStatus(String status) {
        return scheduleRepo.findByStatus(status);
    }
}
