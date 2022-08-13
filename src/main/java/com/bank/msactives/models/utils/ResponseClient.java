package com.bank.msactives.models.utils;

import com.bank.msactives.models.documents.Client;
import lombok.Data;

@Data
public class ResponseClient
{
    private Client data;

    private String message;

    private String status;

}
