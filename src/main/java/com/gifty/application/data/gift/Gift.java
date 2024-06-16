package com.gifty.application.data.gift;

import com.gifty.application.data.giftRegistry.GiftRegistry;
import com.gifty.application.data.person.Person;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="gift")
public class Gift {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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

    public Gift(String name, String url, BigDecimal price, State state, Person person) {
        this.name = name;
        this.url = url;
        this.price = price;
        this.state = state;
        this.person = person;
    }

    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gift gift = (Gift) o;
        return Objects.equals(id, gift.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
