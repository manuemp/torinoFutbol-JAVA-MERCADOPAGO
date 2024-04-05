package com.proyecto.torinofutbol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Array;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TorinoFutbolApplication {

    public static void main(String[] args) {

        SpringApplication.run(TorinoFutbolApplication.class, args);

        LocalTime horaLocal = LocalTime.now(ZoneId.of("America/Argentina/Buenos_Aires"));
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate hoy = LocalDate.now(ZoneId.of("America/Argentina/Buenos_Aires"));
        System.out.println(hoy.format(formato));

    }

}
