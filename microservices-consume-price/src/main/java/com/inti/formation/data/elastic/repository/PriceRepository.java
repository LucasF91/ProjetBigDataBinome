package com.inti.formation.data.elastic.repository;


import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

import com.inti.formation.data.elastic.model.PriceDevice;


@Repository
public interface PriceRepository extends ElasticsearchCrudRepository<PriceDevice, Long> {

}
