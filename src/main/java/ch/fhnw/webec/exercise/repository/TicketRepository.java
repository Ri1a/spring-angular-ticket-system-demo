package ch.fhnw.webec.exercise.repository;

import ch.fhnw.webec.exercise.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Query("""
        SELECT DISTINCT ticket.title FROM Ticket ticket
        WHERE lower(ticket.title) LIKE lower(concat('%', :search, '%'))
    """)
    List<Ticket> findByTitle(@Param("search") String search);

    @Query("""
        SELECT DISTINCT ticket.id FROM Ticket ticket
        WHERE ticket.id = :search
    """)
    Ticket findById(@Param("search") String search);

}
