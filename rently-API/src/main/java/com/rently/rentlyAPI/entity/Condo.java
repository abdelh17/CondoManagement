package com.rently.rentlyAPI.entity;

import com.rently.rentlyAPI.entity.enums.CondoStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "condo")
public class Condo extends AbstractEntity {

    private String name;

    private String address;

    private String condoNumber;

    private String condoType;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255) default 'AVAILABLE'")
    private CondoStatus status;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private Building building;

    // condo can be associated to 0 or 1 parking
    @OneToOne(mappedBy = "condo")
    private Parking parking;

    // condo can be associated to 0 or 1 locker
    @OneToOne(mappedBy = "condo")
    private Locker locker;


}
