package com.bank.msactives.services;

import com.bank.msactives.handler.ResponseHandler;
import com.bank.msactives.models.dao.ActiveDao;
import com.bank.msactives.models.documents.Active;
import com.bank.msactives.models.documents.Credit;
import com.bank.msactives.models.enums.ActiveType;
import com.bank.msactives.services.impl.ActiveServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class ActiveServiceTest {

    @InjectMocks
    private ActiveServiceImpl activeService;

    @Mock
    private ActiveDao dao;

    @Test
    void findAllTest() {

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

        Mockito.when(dao.findAll()).thenReturn(Flux.just(active));

        Mono<ResponseHandler> responseHandlerMono = activeService.findAll();

        StepVerifier.create(responseHandlerMono)
                .expectNextMatches(response -> response.getData() !=null)
                .verifyComplete();
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

        //Mockito.when(dao.save(active)).thenReturn(Mono.just(active));
        Mockito.when(dao.save(active)).thenReturn(Mono.just(active));
    }

    @Test
    void findTest() {

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

        Mockito.when(dao.findById("62edbc767ba3a05551fb10d6"))
                .thenReturn(Mono.just(active));

        Mono<ResponseHandler> responseHandlerMono = activeService.find("62edbc767ba3a05551fb10d6");

        StepVerifier.create(responseHandlerMono)
                .expectNextMatches(response -> response.getData() !=null)
                .verifyComplete();
    }

    @Test
    void updateTest() {

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

        Mockito.when(dao.existsById("62edbc767ba3a05551fb10d6"))
                .thenReturn(Mono.just(true));

        Mockito.when(dao.save(active))
                .thenReturn(Mono.just(active));

        Mono<ResponseHandler> responseHandlerMono = activeService
                .update("62edbc767ba3a05551fb10d6", active);

        StepVerifier.create(responseHandlerMono)
                .expectNextMatches(response -> response.getData() !=null)
                .verifyComplete();
    }

    @Test
    void updateFoundTest() {

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

        Mockito.when(dao.existsById("62edbc767ba3a05551fb10d6"))
                .thenReturn(Mono.just(false));

        Mono<ResponseHandler> responseHandlerMono = activeService
                .update("62edbc767ba3a05551fb10d6", active);

        StepVerifier.create(responseHandlerMono)
                .expectNextMatches(response -> response.getMessage().equals("Not found"))
                .verifyComplete();
    }

    @Test
    void deleteTest() {

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(null);

        Mockito.when(dao.existsById("62edbc767ba3a05551fb10d6"))
                .thenReturn(Mono.just(true));

        Mockito.when(dao.deleteById("62edbc767ba3a05551fb10d6")).thenReturn(Mono.empty());

        Mono<ResponseHandler> responseHandlerMono = activeService
                .delete("62edbc767ba3a05551fb10d6");

        StepVerifier.create(responseHandlerMono)
                .expectNextMatches(response -> response.getMessage().equals("Done"))
                .verifyComplete();
    }

    @Test
    void deleteFoundTest() {

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(null);

        Mockito.when(dao.existsById("62edbc767ba3a05551fb10d6"))
                .thenReturn(Mono.just(false));

        Mono<ResponseHandler> responseHandlerMono = activeService
                .delete("62edbc767ba3a05551fb10d6");

        StepVerifier.create(responseHandlerMono)
                .expectNextMatches(response -> response.getMessage().equals("Not found"))
                .verifyComplete();
    }
}
