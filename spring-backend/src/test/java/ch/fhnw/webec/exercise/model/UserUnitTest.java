package ch.fhnw.webec.exercise.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;
import java.util.Set;

import static org.springframework.test.util.AssertionErrors.assertEquals;

public class UserUnitTest {
    private Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();

        return localValidatorFactoryBean;
    }

    private <T> List<ConstraintViolation<T>> getConstraintViolationsByPropertyPath(Set<ConstraintViolation<T>> constraintViolations, String propertyPath) {
        return constraintViolations.stream().filter(violation -> violation.getPropertyPath().toString().equals(propertyPath)).toList();
    }
    @Test
    public void testUsernameValidation() {
        var validator = createValidator();
        var user = new User();
        var constraintViolations = validator.validate(user);
        var nameViolations = this.getConstraintViolationsByPropertyPath(constraintViolations, "username");

        assertEquals("Must not be null", 1, nameViolations.size());
    }

    @Test
    void testPasswordMinLength() {
        Validator validator = createValidator();
        User user = new User();
        user.setUsername("user1");
        user.setPassword("1111");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        List<ConstraintViolation<User>> usernameViolations = getConstraintViolationsByPropertyPath(constraintViolations, "password");

        assertEquals("Must be at least 6 characters long", 1, usernameViolations.size());
    }

    @Test
    public void testPasswordValidation() {
        var validator = createValidator();
        var user = new User();
        var constraintViolations = validator.validate(user);
        var passwordViolations = this.getConstraintViolationsByPropertyPath(constraintViolations, "password");

        assertEquals("Must not be null", 1, passwordViolations.size());
    }

    @Test
    void testUserProjectsRelation() {
        User user = new User();
        Project project1 = new Project();
        Project project2 = new Project();

        user.setProjects(List.of(project1, project2));

        assertEquals("User should have 2 projects",2, user.getProjects().size());
    }

}

