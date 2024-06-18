package com.gifty.application.test;

import com.gifty.application.data.gift.Gift;
import com.gifty.application.data.gift.State;
import com.gifty.application.data.person.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class GiftTest {

    private Gift gift;
    private UUID id;
    private String name;
    private String url;
    private BigDecimal price;
    private State state;
    private Person person;

    @BeforeEach
    public void setUp() {
        id = UUID.randomUUID();
        name = "Gift Name";
        url = "http://example.com";
        price = new BigDecimal("100.00");
        state = State.POR_COMPRAR;
        person = mock(Person.class);
        gift = new Gift(name, url, price, state, person);
        gift.setId(id);
    }

    @Test
    public void testGiftCreation() {
        assertNotNull(gift);
        assertEquals(name, gift.getName());
        assertEquals(url, gift.getUrl());
        assertEquals(price, gift.getPrice());
        assertEquals(state, gift.getState());
        assertEquals(person, gift.getPerson());
    }

    @Test
    public void testSetName() {
        String newName = "New Gift Name";
        gift.setName(newName);
        assertEquals(newName, gift.getName());
    }

    @Test
    public void testSetUrl() {
        String newUrl = "http://newexample.com";
        gift.setUrl(newUrl);
        assertEquals(newUrl, gift.getUrl());
    }

    @Test
    public void testSetPrice() {
        BigDecimal newPrice = new BigDecimal("200.00");
        gift.setPrice(newPrice);
        assertEquals(newPrice, gift.getPrice());
    }

    @Test
    public void testSetState() {
        State newState = State.RECIBIDO;
        gift.setState(newState);
        assertEquals(newState, gift.getState());
    }

    @Test
    public void testSetPerson() {
        Person newPerson = mock(Person.class);
        gift.setPerson(newPerson);
        assertEquals(newPerson, gift.getPerson());
    }

    @Test
    public void testEqualsAndHashCode() {
        Gift sameGift = new Gift(name, url, price, state, person);
        sameGift.setId(id);
        Gift differentGift = new Gift(name, url, price, state, person);
        differentGift.setId(UUID.randomUUID());

        assertEquals(gift, sameGift);
        assertNotEquals(gift, differentGift);
        assertEquals(gift.hashCode(), sameGift.hashCode());
        assertNotEquals(gift.hashCode(), differentGift.hashCode());
    }
}
