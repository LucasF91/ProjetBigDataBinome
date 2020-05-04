package com.inti.formation.data.kafka.message;

import lombok.Data;

@Data
public class Price {
	
	private long idPrice;
	private float montant;
	private boolean active;
    private String code;
    private java.util.Date date;
}
