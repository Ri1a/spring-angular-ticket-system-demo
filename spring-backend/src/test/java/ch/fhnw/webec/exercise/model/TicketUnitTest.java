package ch.fhnw.webec.exercise.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;
import java.util.Set;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
class TicketUnitTest {

    private Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }

    private <T> List<ConstraintViolation<T>> getConstraintViolationsByPropertyPath(Set<ConstraintViolation<T>> constraintViolations, String propertyPath) {
        return constraintViolations.stream().filter(violation -> violation.getPropertyPath().toString().equals(propertyPath)).toList();
    }

    @Test
    void testTitleValidation() {
        Validator validator = createValidator();
        Ticket ticket = new Ticket();

        Set<ConstraintViolation<Ticket>> constraintViolations = validator.validate(ticket);
        List<ConstraintViolation<Ticket>> titleViolations = getConstraintViolationsByPropertyPath(constraintViolations, "title");

        assertEquals("Must not be null", 1, titleViolations.size());
    }

    @Test
    void testDescriptionValidation() {
        Validator validator = createValidator();
        Ticket ticket = new Ticket();

        Set<ConstraintViolation<Ticket>> constraintViolations = validator.validate(ticket);
        List<ConstraintViolation<Ticket>> descriptionViolations = getConstraintViolationsByPropertyPath(constraintViolations, "description");

        assertEquals("Must not be null", 1, descriptionViolations.size());
    }

    @Test
    void testProjectTicketsRelation() {
        Project project = new Project();
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();

        project.setTickets(List.of(ticket1, ticket2));

        assertEquals("User should have 2 projects",2, project.getTickets().size());
    }
}
