package ch.fhnw.webec.exercise.controller;

import ch.fhnw.webec.exercise.model.StatusEnum;
import ch.fhnw.webec.exercise.model.Ticket;
import ch.fhnw.webec.exercise.repository.TicketRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;

    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @GetMapping("/{id}")
    public Ticket showTicket(@PathVariable String id) {
        return ticketRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public Ticket addTicket(@Valid @RequestBody Ticket ticket) {
        ticket.setId(UUID.randomUUID().toString());
        ticket.setCreationDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return ticketRepository.save(ticket);
    }

    @PostMapping("/{id}/update")
    public Ticket updateTicket(@PathVariable String id, @Valid @RequestBody Ticket ticket) {
        ticket.setId(id);
        if (!ticketRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        ticket.setCreationDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return ticketRepository.save(ticket);
    }

    @PostMapping("/{id}/delete")
    public void deleteTicket(@PathVariable String id) {
        if (!ticketRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        ticketRepository.deleteById(id);
    }

    @PutMapping("/{id}/status")
    public Ticket updateTicketStatus(@PathVariable String id, @RequestBody StatusEnum status) {
        Ticket ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        ticket.setStatus(status);
        return ticketRepository.save(ticket);
    }
}
