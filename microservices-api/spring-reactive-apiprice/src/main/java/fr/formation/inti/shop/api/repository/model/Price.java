package fr.formation.inti.shop.api.repository.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.io.Serializable;
import java.util.Date;


@Data
@Document(collection = "price")
@CompoundIndexes({
        @CompoundIndex(name = "price", def = "{ idPrice: 1 }", unique = false)
        // unique = false acceptation des doublons true non
        // unique = true rejet  des doublons
})

public class Price implements Serializable {
	/**
     * customer identifer
     */
    @Id
    private long idPrice;
    @Indexed(unique = false)
    private float montant;
    private boolean active;
    private String code;
    private Date date;

}