package com.bank.msactives.services;

import com.bank.msactives.models.utils.ResponseClient;
import reactor.core.publisher.Mono;

public interface ClientService {
    Mono<ResponseClient> findByCode(String id);
}
