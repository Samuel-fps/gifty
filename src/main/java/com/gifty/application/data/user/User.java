package com.gifty.application.data.user;

import com.gifty.application.data.giftRegistry.GiftRegistry;
import com.gifty.application.data.person.Person;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name="user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    // GiftRegistry table
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GiftRegistry> GiftRegistries = new ArrayList<>();

    // Person table
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private List<Person> Persons = new ArrayList<>();

    public User(){}

    public User(String name, String lastname, String email, String password, Set<Role> roles) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        if (roles == null || roles.isEmpty()) {
            this.roles = new HashSet<>();
            this.roles.add(Role.USER);
        } else {
            this.roles = roles;
        }
    }

    public User(String name, String lastname, String email, String password) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.roles = new HashSet<>();
        this.roles.add(Role.USER);
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<GiftRegistry> getGiftRegistries() {
        return GiftRegistries;
    }

    public void setGiftRegistries(List<GiftRegistry> giftRegistries) {
        GiftRegistries = giftRegistries;
    }

    public List<Person> getPersons() {
        return Persons;
    }

    public void setPersons(List<Person> persons) {
        Persons = persons;
    }

    // UserDetail method override

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    private List<GrantedAuthority> getAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthorities(this.roles);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
