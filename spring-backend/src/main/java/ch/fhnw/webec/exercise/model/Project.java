package ch.fhnw.webec.exercise.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Project {
    @Id
    private String id;

    @OneToMany(mappedBy="project", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    @ManyToOne
    private User user;

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
