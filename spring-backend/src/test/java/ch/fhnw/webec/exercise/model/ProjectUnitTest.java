package ch.fhnw.webec.exercise.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectUnitTest {

    private Project project;
    private User user;
    private Ticket ticket1, ticket2;

    @BeforeEach
    public void setUp() {
        project = new Project();
        user = new User();
        ticket1 = new Ticket();
        ticket2 = new Ticket();
    }

    @Test
    public void testGetSetId() {
        String id = "testId";
        project.setId(id);
        assertEquals(id, project.getId());
    }

    @Test
    public void testUserAssociation() {
        project.setUser(user);
        assertEquals(user, project.getUser());
    }

    @Test
    public void testTicketAssociation() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket1);
        tickets.add(ticket2);

        project.setTickets(tickets);
        assertEquals(2, project.getTickets().size());
        assertTrue(project.getTickets().contains(ticket1));
        assertTrue(project.getTickets().contains(ticket2));
    }
}
