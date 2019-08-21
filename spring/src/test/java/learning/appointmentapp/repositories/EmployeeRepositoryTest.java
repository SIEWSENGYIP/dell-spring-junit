package learning.appointmentapp.repositories;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import learning.appointmentapp.entities.Employee;

/**
 * EmployeeRepositoryTest
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class EmployeeRepositoryTest {
    // findAll
    @Autowired
    EmployeeRepository repo;

    @Test
    public void testFindAllEmpty() {
        // Given: there are no employees in the DB
        repo.deleteAll();

        // When: employeeRepository.findAll()
        List<Employee> result = repo.findAll();

        // Then: get an empty array
        assertEquals(0, result.size());
    }

    
    @Test

    public void testFindAllNotEmpty() {
        // Given: there are employees in the DB
        seedEmployee();

        // When: employeeRepository.findAll()
        List<Employee> result = repo.findAll();

        // Then: get an array of the employees
        assertEquals(1, result.size());

        Employee retrievedEmployee = result.get(0);
        assertEquals("John", retrievedEmployee.getName());
        assertEquals("john@gmail.com", retrievedEmployee.getEmail());
    }

    public Employee seedEmployee() {
        Employee seeded = new Employee();
        seeded.setName("John");
        seeded.setEmail("john@gmail.com");
        repo.save(seeded);
        return seeded;
    }

    @Test
    public void testFindByIdEmpty() {
        // Given: there are no employees in the DB
        repo.deleteAll();

         // When: employeeRepository.findById();
         Long id = 1L;
         Employee result = repo.findById(id).orElse(null);

         // Then: get a null object
         assertEquals(null, result);

    }

    @Test
    public void testFindByIdNotEmpty() {
         // Given: there is employee with id in db
         Employee seeded = seedEmployee();

         // When: employeeRepository.findById();
         Employee result = repo.findById(seeded.getId()).orElse(null);

         // Then: get the employee by Id
         assertEquals(seeded.getId(), result.getId());
    }

    @Test
    public void testFindByEmailEmpty() {
        // Given: there are no employees in the DB
        repo.deleteAll();

         // When: employeeRepository.findByEmail();
         List<Employee> result = repo.findByEmail("john@gmail.com");

         // Then: get an empty array
         assertEquals(0, result.size());

    }

    @Test
    public void testFindByEmailNotEmpty() {
        // Given: there are employees in the DB
        seedEmployee();

         // When: employeeRepository.findByEmail();
         List<Employee> result = repo.findByEmail("john@gmail.com");

         // Then: get an array of the employees
         assertEquals(1, result.size());

    }

    @Test
    public void testFindByNameEmpty() {
        // Given: there are no employees in the DB
        repo.deleteAll();

         // When: employeeRepository.findByEmail();
         List<Employee> result = repo.findByName("John");

         // Then: get an empty array
         assertEquals(0, result.size());

    }

    @Test
    public void testFindByNameNotEmpty() {
        // Given: there are employees in the DB
        seedEmployee();

         // When: employeeRepository.findByEmail();
         List<Employee> result = repo.findByName("John");

         // Then: get an array of the employees
         assertEquals(1, result.size());

    }

    @Test
    public void testFindBySortingNotEmpty() {
        // Given: there are employees in the DB
        Employee seeded1 = new Employee();
        seeded1.setName("John");
        seeded1.setEmail("john@gmail.com");
        repo.save(seeded1);

        Employee seeded2 = new Employee();
        seeded2.setName("Jane");
        seeded2.setEmail("jane@gmail.com");
        repo.save(seeded2);

        Employee seeded3 = new Employee();
        seeded3.setName("James");
        seeded3.setEmail("james@gmail.com");
        repo.save(seeded3);

         // When: employeeRepository.findByEmail();
         //Sort sorting = new Sort(Sort.Direction.DESC, "name");
        //List<Employee> result = repo.findAll(sorting);
         List<Employee> result = repo.findByOrderByNameAsc();

         // Then: get an array of the employees
         Employee retriveEmployee1 = result.get(0);
         assertEquals("James", retriveEmployee1.getName());

         Employee retriveEmployee2 = result.get(1);
         assertEquals("Jane", retriveEmployee2.getName());

         Employee retriveEmployee3 = result.get(2);
         assertEquals("John", retriveEmployee3.getName());


    }
}