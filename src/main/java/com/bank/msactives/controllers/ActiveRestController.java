package com.bank.msactives.controllers;

import com.bank.msactives.handler.ResponseHandler;
import com.bank.msactives.models.documents.Active;
import com.bank.msactives.services.ActiveService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/active")
public class ActiveRestController
{
    @Autowired
    private ActiveService activeService;

    @GetMapping
    public Mono<ResponseHandler> findAll() {
        return activeService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseHandler> find(@PathVariable String id) {
        return activeService.find(id);
    }

    @PostMapping
    @CircuitBreaker(name="client", fallbackMethod = "fallBackClient")
    public Mono<ResponseHandler> create(@Valid @RequestBody  Active act) {
        return activeService.create(act);
    }

    @PutMapping("/{id}")
    public Mono<ResponseHandler> update(@PathVariable("id") String id,@Valid @RequestBody Active act) {
        return activeService.update(id, act);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseHandler> delete(@PathVariable("id") String id) {
        return activeService.delete(id);
    }

    public Mono<ResponseHandler> fallBackClient(RuntimeException runtimeException){
        return Mono.just(new ResponseHandler("Microservicio externo no responde", HttpStatus.BAD_REQUEST,runtimeException.getMessage()));
    }

    @GetMapping("/client/{id}")
    public Mono<ResponseHandler> activesByClient(@PathVariable String id) {
        return activeService.activesByClient(id);
    }
}
