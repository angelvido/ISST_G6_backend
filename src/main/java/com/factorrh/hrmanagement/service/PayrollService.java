package com.factorrh.hrmanagement.service;

import com.factorrh.hrmanagement.utils.IRPFCalculator;
import com.factorrh.hrmanagement.utils.JobTypeEnum;
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
            BigDecimal hoursWorked = scheduleService.getHoursWorkedForEmployee(employee, startDate, endDate);
            String jobId = employee.getJob();
            BigDecimal hourlyRate;
            BigDecimal irpfRetentionPercentage;
            try {
                hourlyRate = JobTypeEnum.valueOf(jobId).getHourlyRate();
                irpfRetentionPercentage = IRPFCalculator.calcularRetencionMedia(JobTypeEnum.valueOf(jobId));
            } catch (IllegalArgumentException e) {
                hourlyRate = JobTypeEnum.Default.getHourlyRate();
                irpfRetentionPercentage = IRPFCalculator.calcularRetencionMedia(JobTypeEnum.Default);
            }

            BigDecimal totalGrossSalary = hoursWorked.multiply(hourlyRate);
            BigDecimal irpfRetention = totalGrossSalary.multiply(irpfRetentionPercentage);
            BigDecimal totalNetSalary = totalGrossSalary.subtract(irpfRetention);

            Payroll payroll = new Payroll();
            payroll.setEmployee(employee);
            payroll.setStartDate(startDate);
            payroll.setEndDate(endDate);
            payroll.setHours(hoursWorked);
            payroll.setGrossSalary(totalGrossSalary);
            payroll.setIrpfRetentionPercentage(irpfRetentionPercentage);
            payroll.setIrpfRetention(irpfRetention);
            payroll.setNetSalary(totalNetSalary);
            payrolls.add(payroll);
        }
        payrollRepository.saveAll(payrolls);
    }

    public List<Payroll> getPayrollsByEmployeeId(UUID employeeId) {
        return payrollRepository.findByEmployee_EmployeeID(employeeId);
    }
}