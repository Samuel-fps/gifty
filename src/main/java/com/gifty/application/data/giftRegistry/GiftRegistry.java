package com.gifty.application.data.giftRegistry;

import com.gifty.application.data.gift.Gift;
import com.gifty.application.data.user.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "giftRegistry")
public class GiftRegistry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(precision = 19, scale = 2)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private State state;

    // user table
    @ManyToOne
    private User user;

    // gift table
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "giftRegistry_id")
    private List<Gift> gifts = new ArrayList<>();

    public GiftRegistry(){}

    public GiftRegistry(UUID id, String name, BigDecimal price, State state) {
        this.id = id;
        this.name = name;
        this.totalPrice = price != null ? price : BigDecimal.ZERO;
        this.state = Objects.requireNonNullElse(state, State.PENDIENTE);
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public List<Gift> getGifts() { return gifts; }

    public void setGifts(List<Gift> gifts) { this.gifts = gifts; }
}
