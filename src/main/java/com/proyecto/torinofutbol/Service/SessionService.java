package com.proyecto.torinofutbol.Service;

import org.springframework.stereotype.Service;

@Service
public class SessionService {
    private int cantidadReservas = 3;

    public int getCantidadReservas() {
        return cantidadReservas;
    }

    public void setCantidadReservas(int num) {
        this.cantidadReservas = num;
    }
}
