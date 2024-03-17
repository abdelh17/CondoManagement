package com.rently.rentlyAPI.entity.User;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "occupant_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Occupant extends User {
	
	// number of year of residence
	@Column(nullable = false, columnDefinition = "int default 0")
	private int residentSinceYears;

}
