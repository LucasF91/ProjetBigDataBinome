package com.inti.formation.data.elastic.model;

import lombok.Data;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "prix", type = "_doc")
@Data
public class Prix {
        @Id
        @Field(type = FieldType.Long)
        private long idPrice;
        
        @Field(type = FieldType.Float)
        private float montant;
        
        @Field(type = FieldType.Boolean)
        private boolean active;
        
        @Field(type = FieldType.Keyword)
        private String code;
        
        @Field(type = FieldType.Date)
        private Date date;
        
        @Field(type = FieldType.Date)
        private Date dateSup;
        
}
