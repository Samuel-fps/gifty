package com.gifty.application.data.gift;

import com.gifty.application.data.person.Person;
import com.gifty.application.data.user.User;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GiftService {

    private GiftRepository giftRepository;

    public GiftService(GiftRepository giftRepository) {
        this.giftRepository = giftRepository;
    }

    public void save(Gift gift){ giftRepository.save(gift); }

}
