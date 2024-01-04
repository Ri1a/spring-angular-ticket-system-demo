package ch.fhnw.webec.exercise.model;

import jakarta.persistence.*;

@Entity
@Table(name = "authority")
public class Authority {

    public Authority(String name) {
        this.name = name;
    }

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    public Authority() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
