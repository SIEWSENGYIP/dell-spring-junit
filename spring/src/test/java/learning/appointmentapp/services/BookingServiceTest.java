package learning.appointmentapp.services;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import learning.appointmentapp.entities.Appointment;
import learning.appointmentapp.entities.Employee;
import learning.appointmentapp.repositories.AppointmentRepository;
import learning.appointmentapp.repositories.EmployeeRepository;
import learning.appointmentapp.services.BookingService;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BookingServiceTest {

    @Autowired
    BookingService service;

    @Autowired
    AppointmentRepository appointmentRepo;

    @Autowired
    EmployeeRepository employeeRepo;

    @Test
    public void testCheckAppointment() {
        // Given
        // create an employee
        // create 2-3 appointments
        Employee employee = seedEmployee();
        seedAppointment(LocalDateTime.now().plusHours(1), employee);
        seedAppointment(LocalDateTime.now().plusHours(3), employee);
        seedAppointment(LocalDateTime.now().plusHours(5), employee);

        // When
        // run checkAppointment
        List<LocalDateTime> results = service.checkAppointment(employee);

        // Then
        // result should be an array of LocalDateTime
        assertEquals(3, results.size());
        
    }

    @Test
    public void testBookAppointment() {
        Employee employee = seedEmployee();
        seedAppointment(LocalDateTime.now().plusHours(2), employee);
        seedAppointment(LocalDateTime.now().plusHours(4), employee);

        Appointment result = service.bookAppointment(LocalDateTime.now().plusHours(6), employee);

        assertEquals(employee, result.getEmployee());

    }

    Employee seedEmployee() {
        Employee employee = new Employee();
        employee.setName("John");
        employee.setEmail("john@gmail.com");
        employeeRepo.save(employee);
        return employee;
    }

    Appointment seedAppointment(LocalDateTime timeslot, Employee employee) {
        Appointment appointment = new Appointment();
        appointment.setTimeslot(timeslot);
        appointment.setEmployee(employee);
        appointmentRepo.save(appointment);
        return appointment;
    }

}