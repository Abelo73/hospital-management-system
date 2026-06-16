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

    @Query("SELECT i FROM Item i WHERE i.itemCode = :itemCode AND i.deleted = false")
    Optional<Item> findByItemCode(@Param("itemCode") String itemCode);

    @Query("SELECT i FROM Item i WHERE i.itemType = :itemType AND i.deleted = false")
    Page<Item> findByItemType(@Param("itemType") ItemType itemType, Pageable pageable);

    @Query("SELECT i FROM Item i WHERE i.category = :category AND i.deleted = false")
    Page<Item> findByCategory(@Param("category") String category, Pageable pageable);

    @Query("SELECT i FROM Item i WHERE i.isActive = true AND i.deleted = false")
    Page<Item> findByIsActiveTrue(Pageable pageable);

    @Query("SELECT i FROM Item i WHERE i.deleted = false AND i.isActive = true AND (i.itemName LIKE %:query% OR i.itemCode LIKE %:query% OR i.description LIKE %:query%)")
    Page<Item> searchItems(@Param("query") String query, Pageable pageable);

    @Query("SELECT i FROM Item i WHERE i.deleted = false AND i.isActive = true AND i.reorderLevel > 0")
    Page<Item> findItemsWithReorderLevelSet(Pageable pageable);
}
