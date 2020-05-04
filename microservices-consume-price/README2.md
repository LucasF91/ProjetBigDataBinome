# MICROSERVICE CONSUME PRICE ( spring 5 ) 

CREER UN MAPPING PRICE

localhost:9200/price

{
"mappings":{
    "properties": {
      "idPrice":    { "type": "long" },  
      "montant":  { "type": "float"  },
      "active":  { "type": "boolean"  },
      "code":  { "type": "keyword"  },
      "date":   { "type": "date"  }      }}
}
