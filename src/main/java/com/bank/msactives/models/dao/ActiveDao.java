package com.bank.msactives.models.dao;

import com.bank.msactives.models.documents.Active;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ActiveDao extends ReactiveMongoRepository<Active, String> {
}
