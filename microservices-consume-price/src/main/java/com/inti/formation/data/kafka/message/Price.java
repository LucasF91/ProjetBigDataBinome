package com.inti.formation.data.kafka.message;

import lombok.Data;

@Data
public class Price {
	
	private String montant;
	private String active;
    private String code;
    private java.util.Date date;
}
