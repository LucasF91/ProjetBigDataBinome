package com.inti.formation.data.kafka.message;

import java.util.Date;

import lombok.Data;

@Data
public class Price {
	
	private long idPrice;
	private float montant;
	private boolean active;
    private String code;
    private Date date;
    private Date dateSup;
}
