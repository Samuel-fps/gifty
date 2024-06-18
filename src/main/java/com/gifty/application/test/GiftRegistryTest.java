package com.gifty.application.test;

import com.gifty.application.data.gift.Gift;
import com.gifty.application.data.giftRegistry.GiftRegistry;
import com.gifty.application.data.giftRegistry.State;
import com.gifty.application.data.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class GiftRegistryTest {

    private GiftRegistry giftRegistry;
    private UUID id;
    private String name;
    private BigDecimal totalPrice;
    private State state;
    private User user;

    @BeforeEach
    public void setUp() {
        id = UUID.randomUUID();
        name = "Wedding Registry";
        totalPrice = new BigDecimal("1000.00");
        state = State.PENDIENTE;
        user = mock(User.class);
        giftRegistry = new GiftRegistry(id, name, totalPrice, state);
        giftRegistry.setUser(user);
    }

    @Test
    public void testGiftRegistryCreation() {
        assertNotNull(giftRegistry);
        assertEquals(id, giftRegistry.getId());
        assertEquals(name, giftRegistry.getName());
        assertEquals(totalPrice, giftRegistry.getTotalPrice());
        assertEquals(state, giftRegistry.getState());
        assertEquals(user, giftRegistry.getUser());
    }

    @Test
    public void testSetName() {
        String newName = "Birthday Registry";
        giftRegistry.setName(newName);
        assertEquals(newName, giftRegistry.getName());
    }

    @Test
    public void testSetTotalPrice() {
        BigDecimal newPrice = new BigDecimal("2000.00");
        giftRegistry.setTotalPrice(newPrice);
        assertEquals(newPrice, giftRegistry.getTotalPrice());
    }

    @Test
    public void testSetState() {
        State newState = State.CERRADA;
        giftRegistry.setState(newState);
        assertEquals(newState, giftRegistry.getState());
    }

    @Test
    public void testSetUser() {
        User newUser = mock(User.class);
        giftRegistry.setUser(newUser);
        assertEquals(newUser, giftRegistry.getUser());
    }

    @Test
    public void testSetGifts() {
        List<Gift> gifts = new ArrayList<>();
        Gift gift1 = mock(Gift.class);
        Gift gift2 = mock(Gift.class);
        gifts.add(gift1);
        gifts.add(gift2);
        giftRegistry.setGifts(gifts);
        assertEquals(gifts, giftRegistry.getGifts());
    }

    @Test
    public void testGetGifts() {
        List<Gift> gifts = new ArrayList<>();
        Gift gift1 = mock(Gift.class);
        Gift gift2 = mock(Gift.class);
        gifts.add(gift1);
        gifts.add(gift2);
        giftRegistry.setGifts(gifts);
        List<Gift> retrievedGifts = giftRegistry.getGifts();
        assertNotNull(retrievedGifts);
        assertEquals(2, retrievedGifts.size());
        assertTrue(retrievedGifts.contains(gift1));
        assertTrue(retrievedGifts.contains(gift2));
    }

    @Test
    public void testEqualsAndHashCode() {
        GiftRegistry sameGiftRegistry = new GiftRegistry(id, name, totalPrice, state);
        GiftRegistry differentGiftRegistry = new GiftRegistry(UUID.randomUUID(), name, totalPrice, state);

        assertEquals(giftRegistry, sameGiftRegistry);
        assertNotEquals(giftRegistry, differentGiftRegistry);
        assertEquals(giftRegistry.hashCode(), sameGiftRegistry.hashCode());
        assertNotEquals(giftRegistry.hashCode(), differentGiftRegistry.hashCode());
    }
}

