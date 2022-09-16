package com.emilianohg.controllers;

import com.emilianohg.models.Billete;
import com.emilianohg.models.BilletesInsuficientesException;
import com.emilianohg.models.DashboardModel;
import com.emilianohg.views.DashboardView;
import com.emilianohg.views.RetirarBilletesView;

import java.util.List;

public class DashboardController implements Controller {
    private DashboardModel model;
    private RetirarBilletesView view;

    public DashboardController(DashboardModel model, RetirarBilletesView view) {
        this.model = model;
        this.view = view;
        this.view.setController(this);
    }

    public List<Billete> getBilletesFromInventario() {
        return this.model.getAll();
    }

    public void agregarBilletes() {
        List<Billete> billetes = this.model.agregarBilletes();
    }

    public void retirar(int cantidad) {
        try {

            List<Billete> billetes = this.model.retirar(cantidad);
            this.view.setBilletesRetirados(billetes);

        } catch (BilletesInsuficientesException e) {
            this.view.showMessageError(e.getMessage());
        }
    }
}
