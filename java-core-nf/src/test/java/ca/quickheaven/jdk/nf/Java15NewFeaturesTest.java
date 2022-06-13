package ca.quickheaven.jdk.nf;

import org.junit.Test;

import java.util.Date;

/**
 * {@link <a href="https://www.baeldung.com/java-15-new">New Features in Java 15</a>}
 */
public class Java15NewFeaturesTest {

    /*
     * Records (JEP 384)
     * records do have some restrictions. Among other things, they are always final, they cannot be declared abstract,
     * and they can't use native methods.
     */
    public record PersonJep384(String name, int age) {

        public PersonJep384 {
            if (age < 0) {
                throw new IllegalArgumentException("Age cannot be negative.");
            }
        }
    }

    /*
     * Sealed Classes (JEP 360)
     * the goal of sealed classes is to allow individual classes to declare which types may be used as sub-types.
     * This also applies to interfaces and determining which types can implement them.
     *
     * Any class that extends a sealed class must itself be declared sealed, non-sealed, or final.
     */
    public abstract sealed class Person permits Employee, Manager { // The only classes that can extend it are Employee and Manager
    }

    public final class Employee extends Person {

        private Long employeeId;

        private Date hireDate;

        private Integer yearsOfService;

        public Long getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(Long employeeId) {
            this.employeeId = employeeId;
        }

        public Date getHireDate() {
            return hireDate;
        }

        public void setHireDate(Date hireDate) {
            this.hireDate = hireDate;
        }

        public Integer getYearsOfService() {
            return yearsOfService;
        }

        public void setYearsOfService(Integer yearsOfService) {
            this.yearsOfService = yearsOfService;
        }
    }

    public non-sealed class Manager extends Person {

        private Long supervisorId;

        public Long getSupervisorId() {
            return supervisorId;
        }

        public void setSupervisorId(Long supervisorId) {
            this.supervisorId = supervisorId;
        }
    }

    @Test
    public void testSealedClassesJep360() {
        Person person = new Employee();
        if (person instanceof Employee) {
            ((Employee) person).getEmployeeId();
        } else if (person instanceof Manager) {
            ((Manager) person).getSupervisorId();
        }
    }

    @Test
    public void testPatternMatchingTypeJep375() {
        Person person = new Employee();
        if (person instanceof Employee) {
            Employee employee = (Employee) person;
            Date hireDate = employee.getHireDate();
        }

        // pattern matching feature simplifies by introducing a new binding variable:
        if (person instanceof Employee employee) {
            Date hireDate = employee.getHireDate();

            employee.setYearsOfService(10);
        }

        // JVM automatically casts the variable for us and assigns the result to the new binding variable
        // We can also combine the new binding variable with conditional statements:
        if (person instanceof Employee employee && employee.getYearsOfService() > 5) {
            //...
        }

    }

}
