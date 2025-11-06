package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.entity.ProductionTask;

public interface ProductionTaskRepository extends JpaRepository<ProductionTask, Long> {

    List<ProductionTask> findByProductionSchedule_Id(Long scheduleId);

    Optional<ProductionTask> findByName(String name);

    List<ProductionTask> findByStatus(String status);

    long countByProductionSchedule_Id(Long scheduleId);

    // ✅ ADD THIS (needed by service)
    long countByProductionSchedule_IdAndStatus(Long scheduleId, String status);
}




//package com.repository;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import com.entity.ProductionTask;
//
//public interface ProductionTaskRepository extends JpaRepository<ProductionTask, Long> {
//
//    // Find by production schedule ID
//	List<ProductionTask> findByProductionSchedule_Id(Long scheduleId);
//
//
//    // Find by name
//    Optional<ProductionTask> findByName(String name);
//
//    // Find by status
//    List<ProductionTask> findByStatus(String status);
//    
//    // total tasks for a schedule
//    long countByProductionSchedule_Id(Long scheduleId);
//    // OR if your entity has scheduleId field, use below instead:
//    // long countByScheduleId(Long scheduleId);
//
////    // completed tasks based on task status (assuming status = "COMPLETED")
////    long countByProductionSchedule_IdAndStatus(Long scheduleId, String status);
////    // OR if your entity has scheduleId field:
////    // long countByScheduleIdAndStatus(Long scheduleId, String status);
////
////    // if you use boolean completed instead of status string:
////    long countByProductionSchedule_IdAndCompletedTrue(Long scheduleId);
//
//}
