package com.act.hospitalmanagementsystem.inventory.repository;

import com.act.hospitalmanagementsystem.inventory.entity.Location;
import com.act.hospitalmanagementsystem.inventory.enums.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {

    Optional<Location> findByLocationCode(String locationCode);

    List<Location> findByLocationType(LocationType locationType);

    List<Location> findByIsActiveTrue();

    List<Location> findByParentLocationId(UUID parentLocationId);
}
