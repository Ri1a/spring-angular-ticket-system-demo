package ch.fhnw.webec.exercise.controller;

import ch.fhnw.webec.exercise.model.StatusEnum;
import ch.fhnw.webec.exercise.model.Tickets;
import ch.fhnw.webec.exercise.repository.TicketRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class TicketController {

    private final TicketRepository ticketRepository;

    TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    // list all tickets
    @RequestMapping(value = "/api/tickets", method = RequestMethod.GET, produces = "application/json")
    public List<Tickets> listAll() {
        return ticketRepository.findAll();
    }

    // save ticket
    @PostMapping(value = "/api/add")
    public Tickets saveTicket(@RequestBody Tickets ticket){
        Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        ticket.setCreationDate(date);
        //ticket.setTicketId(UUID.randomUUID().toString());
        return ticketRepository.save(ticket);
    }

    // update ticket status
    @PostMapping(value = "/api/updateTicketStatus/{ticketId}/{status}")
    public Tickets updateTicketStatus(@PathVariable int id, @PathVariable StatusEnum status){
        List<Tickets> list = ticketRepository.findAll();
        for(Tickets ticket: list) {
            if(ticket.getTicketId() == id) {
                ticket.setStatus(status);
                ticketRepository.save(ticket);
            }
        }
        return null;
    }

    // update ticket
    @PostMapping(value = "/api/updateTicket")
    public Tickets updateTicket(@RequestBody Tickets ticket){
        Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        ticket.setCreationDate(date);
        return ticketRepository.save(ticket);
    }

    // delete ticket
    @PostMapping(value = "/api/delete/{ticketId}")
    public void deleteTicket(@PathVariable("ticketId") String ticketId){
        ticketRepository.deleteById(ticketId);
    }
}
