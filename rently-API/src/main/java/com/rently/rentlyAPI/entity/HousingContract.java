package com.rently.rentlyAPI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "housing_contract")
public class HousingContract extends AbstractEntity{
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company handlingCompany;
    @ManyToOne
    @JoinColumn(name = "occupant_id")
    private Occupant occupant;

    @ManyToOne
    @JoinColumn(name = "condo_id")
    private Condo condo;

    private String occupantType;
}