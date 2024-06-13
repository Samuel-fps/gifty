package com.gifty.application.data.giftRegistry;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftRegistryService {

    GiftRegistryRepository giftRegistryRepository;

    public void save(GiftRegistry giftRegistry){
        giftRegistryRepository.save(giftRegistry);
    }

    public List<GiftRegistry> getAllGiftRegistry(){
        return giftRegistryRepository.findAll();
    }
}
