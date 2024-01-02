package ch.fhnw.webec.exercise.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;
import java.util.Set;

import static org.springframework.test.util.AssertionErrors.assertEquals;

public class CommentUnitTest {
    private Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }

    private <T> List<ConstraintViolation<T>> getConstraintViolationsByPropertyPath(Set<ConstraintViolation<T>> constraintViolations, String propertyPath) {
        return constraintViolations.stream().filter(violation -> violation.getPropertyPath().toString().equals(propertyPath)).toList();
    }

    @Test
    void testTextValidation() {
        Validator validator = createValidator();
        Comment comment = new Comment();

        Set<ConstraintViolation<Comment>> constraintViolations = validator.validate(comment);
        List<ConstraintViolation<Comment>> textViolations = getConstraintViolationsByPropertyPath(constraintViolations, "text");

        assertEquals("Must not be null", 1, textViolations.size());
    }

    @Test
    void testTicketRelation() {
        Comment comment = new Comment();
        Ticket ticket = new Ticket();

        comment.setTicket(ticket);

        assertEquals("", ticket, comment.getTicket());
    }

    @Test
    void testUserRelation() {
        Comment comment = new Comment();
        User user = new User();

        comment.setUser(user);

        assertEquals("", user, comment.getUser());
    }
}
