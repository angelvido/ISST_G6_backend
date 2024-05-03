package com.factorrh.hrmanagement.controller;

import com.factorrh.hrmanagement.entity.Schedule;
import com.factorrh.hrmanagement.repository.ControllerRepository;
import com.factorrh.hrmanagement.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/controller")
public class ControllerController {

    private final ScheduleService scheduleService;
    private final ControllerRepository controllerRepository;

    public ControllerController (ScheduleService scheduleService, ControllerRepository controllerRepository) {
        this.scheduleService = scheduleService;
        this.controllerRepository = controllerRepository;
    }

    //Query de este metodo: http://localhost:8080/api/controller/schedules?controllerId={id del controlador}
    @GetMapping("/schedules")
    public ResponseEntity<List<Schedule>> getSchedules(@RequestParam UUID controllerId) {
        if (controllerRepository.findById(controllerId).isPresent()) {
            return ResponseEntity.ok().body(scheduleService.getAllSchedules());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Query de este metodo: http://localhost:8080/api/controller/schedule/{id del horario}?controllerId={id del controlador}
    @PutMapping("/schedule/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(@PathVariable UUID scheduleId, @RequestParam UUID controllerId) {
        if (controllerRepository.findById(controllerId).isPresent()) {
            scheduleService.updateSchedule(scheduleId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}