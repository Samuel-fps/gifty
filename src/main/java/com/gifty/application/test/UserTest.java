package com.gifty.application.test;

import com.gifty.application.data.giftRegistry.GiftRegistry;
import com.gifty.application.data.person.Person;
import com.gifty.application.data.user.Role;
import com.gifty.application.data.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class UserTest {

    private User user;
    private UUID id;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private Set<Role> roles;

    @BeforeEach
    public void setUp() {
        id = UUID.randomUUID();
        name = "John";
        lastname = "Doe";
        email = "john.doe@example.com";
        password = "password";
        roles = new HashSet<>(Collections.singletonList(Role.USER));
        user = new User(name, lastname, email, password, roles);
        user.setId(id);
    }

    @Test
    public void testUserCreation() {
        assertNotNull(user);
        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(lastname, user.getLastname());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(roles, user.getRoles());
    }

    @Test
    public void testSetName() {
        String newName = "Jane";
        user.setName(newName);
        assertEquals(newName, user.getName());
    }

    @Test
    public void testSetLastname() {
        String newLastname = "Smith";
        user.setLastname(newLastname);
        assertEquals(newLastname, user.getLastname());
    }

    @Test
    public void testSetEmail() {
        String newEmail = "jane.smith@example.com";
        user.setEmail(newEmail);
        assertEquals(newEmail, user.getEmail());
    }

    @Test
    public void testSetPassword() {
        String newPassword = "newpassword";
        user.setPassword(newPassword);
        assertEquals(newPassword, user.getPassword());
    }

    @Test
    public void testSetRoles() {
        Set<Role> newRoles = new HashSet<>(Collections.singletonList(Role.ADMIN));
        user.setRoles(newRoles);
        assertEquals(newRoles, user.getRoles());
    }

    @Test
    public void testSetGiftRegistries() {
        List<GiftRegistry> giftRegistries = new ArrayList<>();
        GiftRegistry giftRegistry = mock(GiftRegistry.class);
        giftRegistries.add(giftRegistry);
        user.setGiftRegistries(giftRegistries);
        assertEquals(giftRegistries, user.getGiftRegistries());
    }

    @Test
    public void testSetPersons() {
        List<Person> persons = new ArrayList<>();
        Person person = mock(Person.class);
        persons.add(person);
        user.setPersons(persons);
        assertEquals(persons, user.getPersons());
    }

    @Test
    public void testGetAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        assertEquals(authorities, user.getAuthorities());
    }

    @Test
    public void testUserDetailsMethods() {
        assertEquals(email, user.getUsername());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
    }
}
