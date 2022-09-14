package com.emilianohg.controllers;

import com.emilianohg.models.Billete;
import com.emilianohg.models.DashboardModel;

import java.util.List;

public class DashboardController {
    private DashboardModel model;

    public DashboardController(DashboardModel model) {
        this.model = model;
    }

    public List<Billete> getBilletesFromInventario() {
        return this.model.getAll();
    }

    public void agregarBilletes() {
        List<Billete> billetes = this.model.agregarBilletes();
    }

    public void retirar() {
        int cantidad = 1699;
        this.model.retirar(cantidad);
    }
}
