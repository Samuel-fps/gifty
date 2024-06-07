package com.gifty.application.data.gift;

import com.gifty.application.data.giftRegistry.GiftRegistry;
import com.gifty.application.data.person.Person;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name="gift")
public class Gift {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String url;

    @Column(precision = 19, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private State state;

    // Person table
    @ManyToOne
    private Person person;

    public Gift(){}

    public Gift(String name, String url, BigDecimal price, State state, GiftRegistry giftRegistry, Person person) {
        this.name = name;
        this.url = url;
        this.price = price;
        this.state = state;
        this.person = person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
