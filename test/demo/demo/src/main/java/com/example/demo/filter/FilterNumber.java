package com.example.demo.filter;

import com.example.demo.dto.BIVTDto;
import org.springframework.integration.annotation.Filter;
import org.springframework.stereotype.Component;

@Component
public class FilterNumber {
    @Filter(inputChannel = "personFilterChannel", outputChannel = "personOutboundChannel")
    public boolean oddOnly(BIVTDto BIVTDto) {
        return (BIVTDto.getRandomValue() & 1) == 1;
    }
}
