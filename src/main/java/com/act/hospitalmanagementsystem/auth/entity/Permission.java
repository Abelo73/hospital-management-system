package com.act.hospitalmanagementsystem.auth.entity;

import com.act.hospitalmanagementsystem.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "permissions")
public class Permission extends BaseEntity {

    @Column(name = "name", unique = true, nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();
}
