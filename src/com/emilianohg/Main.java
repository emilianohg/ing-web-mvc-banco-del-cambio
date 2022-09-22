package com.emilianohg;

import com.emilianohg.controllers.DashboardController;
import com.emilianohg.models.DashboardModel;
import com.emilianohg.views.DashboardView;

public class Main {

    public static void main(String[] args) {

        DashboardModel dashboardModel = new DashboardModel();

        DashboardView dashboardView = new DashboardView();

        new DashboardController(dashboardModel, dashboardView);

        dashboardView.setVisible(true);
    }
}
