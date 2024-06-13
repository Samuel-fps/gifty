package com.gifty.application.data.giftRegistry;

import com.gifty.application.data.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GiftRegistryRepository extends JpaRepository<GiftRegistry, UUID> {
    List<GiftRegistry> findByUser(User user);
}
