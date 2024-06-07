package com.gifty.application.user;


import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User u) {
        String encodedPassword = passwordEncoder.encode(u.getPassword());
        User user = new User(u.getName(), u.getLastname(), u.getEmail(), encodedPassword);
        userRepository.save(user);
    }

    public boolean validateCredentials(String email, String password){
        User user = userRepository.findByEmail(email);
        Notification.show("Entrando", 3000, Notification.Position.MIDDLE);

        if (user != null && passwordEncoder.matches(password, user.getPassword())){
            // Autenticación exitosa, guardar información de usuario en la sesión
            VaadinSession session = VaadinSession.getCurrent();
            WrappedSession wrappedSession = session.getSession();

            // Guardar información relevante del usuario en la sesión
            wrappedSession.setAttribute("user", user);
            return true;
        }

    return false;
    }

}
