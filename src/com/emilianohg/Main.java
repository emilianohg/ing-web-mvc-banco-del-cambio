package com.emilianohg;

import com.emilianohg.controllers.DashboardController;
import com.emilianohg.models.DashboardModel;
import com.emilianohg.views.DashboardView;
import com.emilianohg.views.RetirarBilletesView;

public class Main {

    public static void main(String[] args) {

        DashboardModel dashboardModel = new DashboardModel();

        RetirarBilletesView retirarBilletesView = new RetirarBilletesView();

        DashboardController dashboardController = new DashboardController(dashboardModel, retirarBilletesView);

        retirarBilletesView.setVisible(true);
    }
}
