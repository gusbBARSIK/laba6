package com.example.demo.component;

import com.example.demo.dto.BIVTDto;
import com.example.demo.gateway.BIVTGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;

@Component
public class CommandLineComponent implements CommandLineRunner {
    private final BIVTGateway BIVTGateway;

    @Autowired
    public CommandLineComponent(BIVTGateway BIVTGateway) {
        this.BIVTGateway = BIVTGateway;
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 10; i++) {
            BIVTDto dto = new BIVTDto();
            dto.setFullName("Атмакин Кирилл Михайлович");
            dto.setBirthday(LocalDate.of(2002, Month.FEBRUARY, 10));
            dto.setRandomValue((int) (Math.random() * 99 + 1));
            BIVTGateway.send(dto);
        }
    }
}
