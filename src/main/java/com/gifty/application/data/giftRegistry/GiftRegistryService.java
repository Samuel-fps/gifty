package com.gifty.application.data.giftRegistry;

import com.gifty.application.data.gift.Gift;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GiftRegistryService {

    GiftRegistryRepository giftRegistryRepository;

    public GiftRegistryService(GiftRegistryRepository giftRegistryRepository) {
        this.giftRegistryRepository = giftRegistryRepository;
    }

    public void save(GiftRegistry giftRegistry){
        giftRegistryRepository.save(giftRegistry);
    }

    public List<GiftRegistry> getAllGiftRegistry(){
        return giftRegistryRepository.findAll();
    }

    public GiftRegistry getGiftRegistryById(UUID id){
        Optional<GiftRegistry> giftOptional= giftRegistryRepository.findById(id);
        return giftOptional.orElse(null);
    }

    public void addGift(GiftRegistry giftRegistry, Gift gift){
        giftRegistry.getGifts().add(gift);
        giftRegistry.setTotalPrice(giftRegistry.getTotalPrice().add(gift.getPrice()));
        giftRegistryRepository.save(giftRegistry);
    }
}
