package ch.fhnw.webec.exercise.repository;

import ch.fhnw.webec.exercise.model.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Tickets, String> {
}
