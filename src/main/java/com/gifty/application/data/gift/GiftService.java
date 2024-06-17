package com.gifty.application.data.gift;

import com.gifty.application.data.giftRegistry.GiftRegistry;
import com.gifty.application.data.giftRegistry.GiftRegistryRepository;
import com.gifty.application.data.giftRegistry.GiftRegistryService;
import com.gifty.application.data.person.Person;
import com.gifty.application.data.user.User;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class GiftService {

    private GiftRepository giftRepository;
    private GiftRegistryService giftRegistryService;

    public GiftService(GiftRepository giftRepository, GiftRegistryService giftRegistryService) {
        this.giftRepository = giftRepository;
        this.giftRegistryService = giftRegistryService;
    }

    public void save(Gift gift){ giftRepository.save(gift); }

    public Gift getGiftById(UUID id){
        Optional<Gift> giftOptional= giftRepository.findById(id);
        return giftOptional.orElse(null);
    }

    public void delete(Gift gift, GiftRegistry giftRegistry){
        giftRegistry.setTotalPrice(giftRegistry.getTotalPrice().subtract(gift.getPrice()));
        giftRegistryService.save(giftRegistry);
        giftRepository.delete(gift);
    }
}
