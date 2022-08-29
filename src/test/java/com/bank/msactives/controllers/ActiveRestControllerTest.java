package com.bank.msactives.controllers;

import com.bank.msactives.handler.ResponseHandler;
import com.bank.msactives.models.documents.Active;
import com.bank.msactives.models.documents.Credit;
import com.bank.msactives.models.enums.ActiveType;
import com.bank.msactives.services.ActiveService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ActiveRestController.class)
public class ActiveRestControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ActiveService activeService;

    @Test
    void findAllTest() {

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(null);

        when(activeService.findAll()).thenReturn(Mono.just(responseHandler));

        webClient
                .get().uri("/api/active")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ResponseHandler.class);

    }

    @Test
    void findByIdTest() {
        Active active = new Active();
        active.setId("62edbc767ba3a05551fb10d6");
        active.setClientId("Test");
        active.setActiveType(ActiveType.COMPANY_CREDIT);
        active.setCredits(null);

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(active);

        Mockito
                .when(activeService.find("62edbc767ba3a05551fb10d6"))
                .thenReturn(Mono.just(responseHandler));

        webClient.get().uri("/api/active/{id}", "62edbc767ba3a05551fb10d6")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResponseHandler.class);

        Mockito.verify(activeService, times(1)).find("62edbc767ba3a05551fb10d6");
    }

    @Test
    void createTest() {

        Credit credit = new Credit();
        credit.setId("62edbc767ba3a05551fb10d1");
        credit.setCreditAmount(Float.parseFloat("100.00"));

        List<Credit> creditList = new ArrayList<>();
        creditList.add(credit);

        Active active = new Active();
        active.setId("62edbc767ba3a05551fb10d6");
        active.setClientId("Test");
        active.setActiveType(ActiveType.COMPANY_CREDIT);
        active.setCredits(creditList);

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(active);

        Mockito
                .when(activeService.create(active)).thenReturn(Mono.just(responseHandler));

        webClient
                .post()
                .uri("/api/active")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(active))
                .exchange()
                .expectStatus().isOk();

        Mockito
                .verify(activeService, times(1)).create(active);
    }

    @Test
    void uodateTest() {

        Credit credit = new Credit();
        credit.setId("62edbc767ba3a05551fb10d1");
        credit.setCreditAmount(Float.parseFloat("100.00"));

        List<Credit> creditList = new ArrayList<>();
        creditList.add(credit);

        Active active = new Active();
        active.setId("62edbc767ba3a05551fb10d6");
        active.setClientId("Test");
        active.setActiveType(ActiveType.COMPANY_CREDIT);
        active.setCredits(creditList);

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(active);

        Mockito
                .when(activeService.update("62edbc767ba3a05551fb10d6",active)).thenReturn(Mono.just(responseHandler));

        webClient
                .put()
                .uri("/api/active/{id}", "62edbc767ba3a05551fb10d6")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(active))
                .exchange()
                .expectStatus().isOk();

        Mockito
                .verify(activeService, times(1)).update("62edbc767ba3a05551fb10d6",active);
    }

    @Test
    void deleteTest() {

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(null);

        Mockito
                .when(activeService.delete("62edbc767ba3a05551fb10d6"))
                .thenReturn(Mono.just(responseHandler));

        webClient.delete().uri("/api/active/{id}", "62edbc767ba3a05551fb10d6")
                .exchange()
                .expectStatus().isOk();
    }
}
