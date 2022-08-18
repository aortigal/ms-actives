package com.bank.msactives.services;

import com.bank.msactives.handler.ResponseHandler;
import com.bank.msactives.models.documents.Active;
import reactor.core.publisher.Mono;

public interface ActiveService {
    Mono<ResponseHandler> findAll();

    Mono<ResponseHandler> find(String id);

    Mono<ResponseHandler> create(Active act);

    Mono<ResponseHandler> update(String id, Active act);

    Mono<ResponseHandler> delete(String id);
}
