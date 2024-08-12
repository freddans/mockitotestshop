package com.freddans.mockitotestshop.repositories;

import com.freddans.mockitotestshop.entities.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    Shop findByName(String name);
}
