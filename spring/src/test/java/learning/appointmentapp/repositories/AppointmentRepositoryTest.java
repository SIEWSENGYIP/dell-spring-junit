package learning.appointmentapp.repositories;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
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

/**
 * AppointmentRepositoryTest
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AppointmentRepositoryTest {
    @Autowired
    AppointmentRepository repo;

    @Autowired
    EmployeeRepository empRepo;

    @Test
    public void testFindAllEmpty() {
        // Given: there are no appointment in the DB
        repo.deleteAll();

        // When: appointmentRepository.findAll()
        List<Appointment> result = repo.findAll();

        // Then: get an empty array
        assertEquals(0, result.size());
    }

    
    @Test

    public void testFindAllNotEmpty() {
        // Given: there are appointments in the DB
        seedAppointment("John", "john@gmail.com", LocalDateTime.of(2019, Month.AUGUST, 16, 00, 00, 00));

        // When: appointmentRepository.findAll()
        List<Appointment> result = repo.findAll();

        // Then: get an array of the appointments
        assertEquals(1, result.size());
    }

    public Employee seedEmployee(String name, String email) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setEmail(email);
        empRepo.save(employee);

        return employee;
    }

    public void seedAppointment(String name, String email, LocalDateTime timeSlot) {
        Employee employee = seedEmployee(name, email);
        Appointment seeded = new Appointment();
        seeded.setTimeslot(timeSlot);
        seeded.setEmployee(employee);
        repo.save(seeded);
    }

    @Test
    public void testFindByTimeSlotBetweenEmpty() {
        // Given: there are no appointment in the DB
        repo.deleteAll();

        // When: appointmentRepository.findByTimeslotBetween()
        LocalDateTime startTime = LocalDateTime.of(2019, Month.AUGUST, 16, 8, 00, 00);
        LocalDateTime endTime = LocalDateTime.of(2019, Month.AUGUST, 16, 12, 00, 00);
        List<Appointment> result = repo.findByTimeslotBetween(startTime, endTime);

        // Then: get an empty array
        assertEquals(0, result.size());
    }

    @Test
    public void testFindByTimeSlotBetweenNotEmpty() {
        // Given: there are appointments in the DB
        seedAppointment("John", "john@gmail.com", LocalDateTime.of(2019, Month.AUGUST, 16, 9, 00, 00));
        seedAppointment("Jane", "jane@gmail.com", LocalDateTime.of(2019, Month.AUGUST, 16, 11, 00, 00));
        seedAppointment("James", "james@gmail.com", LocalDateTime.of(2019, Month.AUGUST, 16, 15, 00, 00));

        // When: appointmentRepository.findByTimeslotBetween()
        LocalDateTime startTime = LocalDateTime.of(2019, Month.AUGUST, 16, 8, 00, 00);
        LocalDateTime endTime = LocalDateTime.of(2019, Month.AUGUST, 16, 12, 00, 00);
        List<Appointment> result = repo.findByTimeslotBetween(startTime, endTime);

        // Then: get an array of the appointments
        assertEquals(2, result.size());
    }

    @Test
    public void testFindByEmployeeEmailContainsEmpty() {
        // Given: there are no appointment in the DB
        repo.deleteAll();

        // When: appointmentRepository.findByEmployeeEmailContains()
        String emailKeyword = "%hotmail%";
        List<Appointment> result = repo.findByEmployeeEmailContains(emailKeyword);

        // Then: get an empty array
        assertEquals(0, result.size());
    }

    @Test
    public void testFindByEmployeeEmailContainsNotEmpty() {
        // Given: there are appointments in the DB
        seedAppointment("John", "john@gmail.com", LocalDateTime.of(2019, Month.AUGUST, 11, 00, 00, 00));
        seedAppointment("Jane", "jane@gmail.com", LocalDateTime.of(2019, Month.AUGUST, 12, 00, 00, 00));
        seedAppointment("James", "james@hotmail.com", LocalDateTime.of(2019, Month.AUGUST, 16, 00, 00, 00));

        // When: appointmentRepository.findByEmployeeEmailContains()
        String emailKeyword = "%hotmail%";
        List<Appointment> result = repo.findByEmployeeEmailContains(emailKeyword);

        // Then: get an array of the appointments
        assertEquals(1, result.size());
    }

    
}