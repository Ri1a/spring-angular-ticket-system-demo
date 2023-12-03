package ch.fhnw.webec.exercise.service;

import ch.fhnw.webec.exercise.model.Status;
import ch.fhnw.webec.exercise.model.Ticket;
import ch.fhnw.webec.exercise.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> findTicketsByTitle(String title) {
        return ticketRepository.findByTitle(title);
    }

    public Ticket createTicket(String title, String description, Status status) {
        return ticketRepository.save(new Ticket(title, description, status));
    }

    public Ticket updateTicket(String ticketId, Ticket updatedTicket) {
        Ticket existingTicket = ticketRepository.findById(ticketId);
        existingTicket.setTitle(updatedTicket.getTitle());
        existingTicket.setDescription(updatedTicket.getDescription());
        existingTicket.setStatus(updatedTicket.getStatus());
        return ticketRepository.save(existingTicket);
    }

    public void deleteTicket(String ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId);
        ticketRepository.delete(ticket);
    }
}
