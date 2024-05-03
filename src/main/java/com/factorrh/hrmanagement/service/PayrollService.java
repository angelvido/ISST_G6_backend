package com.factorrh.hrmanagement.service;

import com.factorrh.hrmanagement.JobType;
import com.factorrh.hrmanagement.entity.Employee;
import com.factorrh.hrmanagement.entity.Payroll;
import com.factorrh.hrmanagement.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PayrollService {

    private final PayrollRepository payrollRepository;
    private final ScheduleService scheduleService;

    @Autowired
    public PayrollService(PayrollRepository payrollRepository, ScheduleService scheduleService) {
        this.payrollRepository = payrollRepository;
        this.scheduleService = scheduleService;
    }

    public void generatePayrolls(List<Employee> employees, LocalDate startDate, LocalDate endDate) {
        List<Payroll> payrolls = new ArrayList<>();

        for (Employee employee : employees) {
            // Obtener las horas trabajadas por el empleado desde startDate hasta endDate (suponiendo que hay un método para esto)
            BigDecimal hoursWorked = scheduleService.getHoursWorkedForEmployee(employee, startDate, endDate);

            String jobId = employee.getJob();

            BigDecimal hourlyRate;
            try {
                hourlyRate = JobType.valueOf(jobId).getHourlyRate();
            } catch (IllegalArgumentException e) {
                hourlyRate = JobType.Default.getHourlyRate();
            }

            // Calcular el monto total de la nómina
            BigDecimal totalAmount = hoursWorked.multiply(hourlyRate);

            // Crear una instancia de Payroll y establecer sus valores
            Payroll payroll = new Payroll();
            payroll.setEmployee(employee);
            payroll.setStartDate(startDate);
            payroll.setEndDate(endDate);
            payroll.setHours(hoursWorked);
            payroll.setAmount(totalAmount);

            // Agregar la nómina a la lista de nóminas
            payrolls.add(payroll);
        }
        payrollRepository.saveAll(payrolls);
    }

    public List<Payroll> getPayrollsByEmployeeId(UUID employeeId) {
        return payrollRepository.findByEmployee_EmployeeID(employeeId);
    }
}