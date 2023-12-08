package ch.fhnw.webec.exercise.controller;

import ch.fhnw.webec.exercise.model.Status;
import ch.fhnw.webec.exercise.model.Ticket;
import ch.fhnw.webec.exercise.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/searchByTitle")
    public List<Ticket> searchTicketsByTitle(@RequestParam String title) {
        return ticketService.findTicketsByTitle(title);
    }

    @GetMapping("/createTicket")
    public Ticket createTicket(@RequestParam String title, String description, Status status) {
        return ticketService.createTicket(title, description, status);
    }

    @GetMapping("/editTicket")
    public Ticket editTicket(@RequestParam String id, String title, String description, Status status) {
        return ticketService.updateTicket(id, new Ticket(title, description, status));
    }

}
