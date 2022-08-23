package com.bank.msactives.services.impl;

import com.bank.msactives.handler.ResponseHandler;
import com.bank.msactives.models.dao.ActiveDao;
import com.bank.msactives.models.documents.Active;
import com.bank.msactives.services.ActiveService;
import com.bank.msactives.services.ClientService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ActiveServiceImpl implements ActiveService {
    @Autowired
    private ActiveDao dao;

    @Autowired
    private ClientService clientService;
    private static final Logger log = LoggerFactory.getLogger(ActiveServiceImpl.class);

    @Override
    public Mono<ResponseHandler> findAll() {
        log.info("[INI] findAll Active");
        return dao.findAll()
                .doOnNext(active -> log.info(active.toString()))
                .collectList()
                .map(actives -> new ResponseHandler("Done", HttpStatus.OK, actives))
                .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)))
                .doFinally(fin -> log.info("[END] findAll Active"));
    }

    @Override
    public Mono<ResponseHandler> find(String id) {
        log.info("[INI] find Active");
        return dao.findById(id)
                .doOnNext(active -> log.info(active.toString()))
                .map(active -> new ResponseHandler("Done", HttpStatus.OK, active))
                .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)))
                .doFinally(fin -> log.info("[END] find Active"));
    }

    @Override
    public Mono<ResponseHandler> create(Active act) {
        log.info("[INI] create Active");
        return clientService.findByCode(act.getClientId())
                .doOnNext(transaction -> log.info(transaction.toString()))
                .flatMap(responseClient -> {
                    if(responseClient.getData() == null){
                        return Mono.just(new ResponseHandler("Does not have client", HttpStatus.BAD_REQUEST, null));
                    }

                    if(responseClient.getData().getType().equals("PERSONAL")){
                        if(act.getCredits().size()>1){
                            return Mono.just(new ResponseHandler(
                                    "Only one credit per person is allowed", HttpStatus.BAD_REQUEST, null));
                        }
                    }

                    act.getCredits().forEach(credit -> credit.setId(new ObjectId().toString()));
                    act.setDateRegister(LocalDateTime.now());
                    return dao.save(act)
                            .doOnNext(active -> log.info(active.toString()))
                            .map(active -> new ResponseHandler("Done", HttpStatus.OK, active)                )
                            .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)))
                            ;

                })
                .switchIfEmpty(Mono.just(new ResponseHandler("Client No Content", HttpStatus.BAD_REQUEST, null)))
                .doFinally(fin -> log.info("[END] create Active"));
    }

    @Override
    public Mono<ResponseHandler> update(String id, Active act) {
        log.info("[INI] update Active");

        return dao.existsById(id).flatMap(check -> {
            if (check){
                act.setDateUpdate(LocalDateTime.now());
                return dao.save(act)
                        .doOnNext(active -> log.info(active.toString()))
                        .map(active -> new ResponseHandler("Done", HttpStatus.OK, active)                )
                        .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)));
            }
            else
                return Mono.just(new ResponseHandler("Not found", HttpStatus.NOT_FOUND, null));

        }).doFinally(fin -> log.info("[END] update Active"));
    }

    @Override
    public Mono<ResponseHandler> delete(String id) {
        log.info("[INI] delete Active");

        return dao.existsById(id).flatMap(check -> {
            if (check)
                return dao.deleteById(id).then(Mono.just(new ResponseHandler("Done", HttpStatus.OK, null)));
            else
                return Mono.just(new ResponseHandler("Not found", HttpStatus.NOT_FOUND, null));
        }).doFinally(fin -> log.info("[END] update Active"));

    }

    @Override
    public Mono<ResponseHandler> activesByClient(String id) {
        log.info("[INI] activesByClient Active");
        return dao.findAll()
                .filter(active ->
                        active.getClientId().equals(id)
                )
                .collectList()
                .doOnNext(active -> log.info(active.toString()))
                .map(actives -> new ResponseHandler("Done", HttpStatus.OK, actives))
                .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)))
                .switchIfEmpty(Mono.just(new ResponseHandler("No Content", HttpStatus.BAD_REQUEST, null)))
                .doFinally(fin -> log.info("[END] activesByClient Active"));
    }
}
