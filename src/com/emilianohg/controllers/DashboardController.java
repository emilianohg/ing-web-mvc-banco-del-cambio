package com.emilianohg.controllers;

import com.emilianohg.models.Billete;
import com.emilianohg.models.BilletesInsuficientesException;
import com.emilianohg.models.DashboardModel;
import com.emilianohg.views.DashboardView;

import java.util.List;

public class DashboardController {

    private DashboardModel model;
    private DashboardView view;

    public DashboardController(DashboardModel model, DashboardView view) {
        this.model = model;
        this.view = view;
        this.view.setController(this);
    }

    public List<Billete> getBilletesFromInventario() {
        List<Billete> billetes = this.model.getAll();
        this.view.setBilletesInventario(billetes);
        return billetes;
    }

    public void agregarBilletes() {
        this.model.agregarBilletes();
        this.getBilletesFromInventario();
    }

    public void retirar(int cantidad) {
        this.view.clearTextFieldCantidad();

        try {
            List<Billete> billetes = this.model.retirar(cantidad);
            this.view.setBilletesRetirados(billetes);
            this.getBilletesFromInventario();
        } catch (BilletesInsuficientesException e) {
            this.view.showMessageError(e.getMessage());
        }
    }

    public void toogleViewInventario() {
        this.view.setShowingInventario(!this.view.isShowingInventario());

        if (this.view.isShowingInventario()) {
            this.getBilletesFromInventario();
            return;
        }

        this.view.clearBilletesInventario();
    }
}
