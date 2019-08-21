package learning.appointmentapp.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learning.appointmentapp.entities.Appointment;
import learning.appointmentapp.entities.Employee;
import learning.appointmentapp.repositories.AppointmentRepository;
import learning.appointmentapp.repositories.EmployeeRepository;

/**
 * BookingService
 */
@Service
public class BookingService {
    @Autowired
    AppointmentRepository appointmentRepo;

    @Autowired
    EmployeeRepository employeeRepo;

    public List<LocalDateTime> checkAppointment(Employee employee) {
        List<Appointment> result = appointmentRepo.findByEmployee(employee);

        List<LocalDateTime> timeslot = new ArrayList<LocalDateTime>();
        for (int i = 0; i < result.size(); i++) {
            timeslot.add(result.get(i).getTimeslot());
        }

        return timeslot;
        
    }

    public Appointment bookAppointment(LocalDateTime timeslot, Employee employee){
        LocalDateTime startTime = timeslot.minusHours(2);
        LocalDateTime endTime = timeslot.plusHours(2);

        List<Appointment> results = appointmentRepo.findByTimeslotBetween(startTime, endTime);

        if (results.size() > 0) {
            return null;
        }

        Appointment appointment = new Appointment();
        appointment.setTimeslot(timeslot);
        appointment.setEmployee(employee);  
        appointmentRepo.save(appointment);

        return appointment;

    }
    
}