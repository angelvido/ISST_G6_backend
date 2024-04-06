package com.factorrh.hrmanagement.service;

import com.factorrh.hrmanagement.entity.Controller;
import com.factorrh.hrmanagement.entity.Employee;
import com.factorrh.hrmanagement.entity.HRManager;
import com.factorrh.hrmanagement.entity.Recruiter;
import com.factorrh.hrmanagement.exception.AuthenticationException;
import com.factorrh.hrmanagement.model.dto.LoginRequest;
import com.factorrh.hrmanagement.model.dto.LoginResponse;
import com.factorrh.hrmanagement.model.dto.RegisterRequest;
import com.factorrh.hrmanagement.repository.ControllerRepository;
import com.factorrh.hrmanagement.repository.EmployeeRepository;
import com.factorrh.hrmanagement.repository.HRManagerRepository;
import com.factorrh.hrmanagement.repository.RecruiterRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AuthService {
    private final EmployeeRepository employeeRepository;
    private final RecruiterRepository recruiterRepository;
    private final HRManagerRepository hrManagerRepository;
    private final ControllerRepository controllerRepository;

    public AuthService(EmployeeRepository employeeRepository, RecruiterRepository recruiterRepository, HRManagerRepository hrManagerRepository, ControllerRepository controllerRepository) {
        this.employeeRepository = employeeRepository;
        this.recruiterRepository = recruiterRepository;
        this.hrManagerRepository = hrManagerRepository;
        this.controllerRepository = controllerRepository;
    }

    @Transactional
    public void register(RegisterRequest request) {
        String username = request.username();
        String name = request.name();
        String lastname = request.lastname();
        LocalDate admission = LocalDate.now();
        String job = request.job();
        String password = request.password();       //TODO password debería de encriptarse
        Optional<Employee> existingEmployee = employeeRepository.findByUsername(username);
        if (existingEmployee.isPresent()) {
            throw new DuplicateKeyException(String.format("Employee with username '%s' already exists.", username));
        }
        Employee employee = Employee.builder()
                .username(username)
                .name(name)
                .lastname(lastname)
                .admission(admission)
                .job(job)
                .password(password)
                .build();
        Employee savedEmployee = employeeRepository.save(employee);

        if ("Recruiter".equals(job)) {
            Recruiter recruiter = new Recruiter();
            recruiter.setEmployee(savedEmployee);
            recruiterRepository.save(recruiter);
        }

        if ("HRManager".equals(job)) {
            HRManager hrManager = new HRManager();
            hrManager.setEmployee(savedEmployee);
            hrManagerRepository.save(hrManager);
        }

        if ("Controller".equals(job)) {
            Controller controller = new Controller();
            controller.setEmployee(savedEmployee);
            controllerRepository.save(controller);
        }
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        String username = request.username();
        String password = request.password();
        Optional<Employee> existingEmployee = employeeRepository.findByUsername(username);
        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();
            String storedPassword = employee.getPassword();

            if (Objects.equals(storedPassword, password)) {     //TODO Hay que cambiar para que la contraseña se decodifique y demas

                UUID storedEmployeeId = employee.getEmployeeID();
                Optional<Recruiter> existingRecruiter = recruiterRepository.findByEmployee_EmployeeID(storedEmployeeId);
                Optional<HRManager> existingHRManager = hrManagerRepository.findByEmployee_EmployeeID(storedEmployeeId);
                Optional<Controller> existingController = controllerRepository.findByEmployee_EmployeeID(storedEmployeeId);

                if (existingRecruiter.isPresent()) {
                    Recruiter recruiter = existingRecruiter.get();
                    UUID storedRecruiterId = recruiter.getRecruiterId();
                    return new LoginResponse(storedEmployeeId, storedRecruiterId, null, null);
                } else {
                    if (existingHRManager.isPresent()) {
                        HRManager hrManager = existingHRManager.get();
                        UUID storedHRManagerId = hrManager.getManagerId();
                        return new LoginResponse(storedEmployeeId, null, storedHRManagerId, null);
                    } else {
                        if (existingController.isPresent()) {
                            Controller controller = existingController.get();
                            UUID storedControllerId = controller.getControllerId();
                            return new LoginResponse(storedEmployeeId, null, null, storedControllerId);
                        }
                    }
                    return new LoginResponse(storedEmployeeId, null, null, null);
                }
            } else {
                throw new AuthenticationException("Username or password incorrect.");
            }
        } else {
            throw new DuplicateKeyException(String.format("Employee with username '%s' already exists.", username));
        }
    }
}
