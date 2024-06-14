package com.gifty.application.data.giftRegistry;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class GiftRegistryService {

    GiftRegistryRepository giftRegistryRepository;

    public void save(GiftRegistry giftRegistry){
        giftRegistryRepository.save(giftRegistry);
    }

    public List<GiftRegistry> getAllGiftRegistry(){
        return giftRegistryRepository.findAll();
    }

    public GiftRegistry getById(UUID id){
        return giftRegistryRepository.findAllById(Collections.singletonList(id)).iterator().next();
    }
}
