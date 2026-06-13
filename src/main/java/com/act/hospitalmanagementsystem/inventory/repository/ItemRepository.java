package com.act.hospitalmanagementsystem.inventory.repository;

import com.act.hospitalmanagementsystem.inventory.entity.Item;
import com.act.hospitalmanagementsystem.inventory.enums.ItemType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {

    Optional<Item> findByItemCode(String itemCode);

    Page<Item> findByItemType(ItemType itemType, Pageable pageable);

    Page<Item> findByCategory(String category, Pageable pageable);

    Page<Item> findByIsActiveTrue(Pageable pageable);

    @Query("SELECT i FROM Item i WHERE i.deleted = false AND i.isActive = true AND (i.itemName LIKE %:query% OR i.itemCode LIKE %:query% OR i.description LIKE %:query%)")
    Page<Item> searchItems(@Param("query") String query, Pageable pageable);

    @Query("SELECT i FROM Item i WHERE i.deleted = false AND i.isActive = true AND i.reorderLevel > 0")
    Page<Item> findItemsWithReorderLevelSet(Pageable pageable);
}
