package com.bank.msactives.models.documents;

import com.bank.msactives.models.utils.Audit;
import lombok.Data;

@Data
public class Credit extends Audit
{
    private String id;
    private Float creditMont;
}
