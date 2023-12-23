package ch.fhnw.webec.exercise.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;
import java.util.List;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String title;

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String description;
    private Date creationDate;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticket")
    private List<Comment> comments;

    @ManyToOne
    private User user;

    public String getTicketId() {
        return id;
    }

    public void setTicketId(String ticketId) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
