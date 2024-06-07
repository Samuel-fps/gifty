package com.gifty.application.data.person;

import com.gifty.application.data.gift.Gift;
import com.gifty.application.data.user.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    // Gift table
    @OneToMany(mappedBy = "person")
    private List<Gift> gifts = new ArrayList<>();

    public Person(){}

    public Person(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
/*
    public void setGifts(List<Gift> gifts) {
        this.gifts = gifts;
    }

    public List<Gift> getGifts() {
        return gifts;
    }*/
}
