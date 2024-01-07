package ch.fhnw.webec.exercise.controller;

import ch.fhnw.webec.exercise.model.Project;
import ch.fhnw.webec.exercise.model.StatusEnum;
import ch.fhnw.webec.exercise.model.Ticket;
import ch.fhnw.webec.exercise.repository.ProjectRepository;
import ch.fhnw.webec.exercise.repository.TicketRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final ProjectRepository projectRepository;

    public TicketController(TicketRepository ticketRepository, ProjectRepository projectRepository) {
        this.ticketRepository = ticketRepository;
        this.projectRepository = projectRepository;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public Ticket showTicket(@PathVariable String id) {
        return ticketRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/add")
    public Ticket addTicket(@Valid @RequestBody Ticket ticket) {
        if (ticket.getProject() != null && ticket.getProject().getId() != null) {
            Project project = projectRepository.findById(ticket.getProject().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found."));
            ticket.setProject(project);
        }
        ticket.setId(UUID.randomUUID().toString());
        ticket.setCreationDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        try {
            return ticketRepository.save(ticket);
        } catch (Exception e) {
            if(ticketRepository.existsByTitle(ticket.getTitle())) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Ticket with title " + ticket.getTitle() + " already exists.");
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating ticket.");
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/{id}/update")
    public Ticket updateTicket(@PathVariable String id, @Valid @RequestBody Ticket ticket) {
        ticket.setId(id);
        if (!ticketRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found.");
        }

        if (ticket.getProject() != null && ticket.getProject().getId() != null) {
            Project project = getProjectOrThrow(ticket.getProject().getId());
            ticket.setProject(project);
        }

        ticket.setCreationDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        try {
            return ticketRepository.save(ticket);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating ticket.");
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/{id}/delete")
    public void deleteTicket(@PathVariable String id) {
        if (!ticketRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found.");
        }
        try {
            ticketRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting ticket.");
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/{id}/status")
    public Ticket updateTicketStatus(@PathVariable String id, @RequestBody StatusEnum status) {
        Ticket ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found."));

        if (ticket.getProject() != null && ticket.getProject().getId() != null) {
            Project project = getProjectOrThrow(ticket.getProject().getId());
            ticket.setProject(project);
        }

        ticket.setStatus(status);
        try {
            return ticketRepository.save(ticket);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating ticket status.");
        }
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
        return new ResponseEntity<>(ex.getReason(), ex.getStatusCode());
    }

    private Project getProjectOrThrow(String projectId) {
        return projectRepository.findById(projectId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found."));
    }
}
