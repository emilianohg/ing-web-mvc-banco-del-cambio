package com.emilianohg;

import com.emilianohg.controllers.DashboardController;
import com.emilianohg.models.DashboardModel;
import com.emilianohg.views.DashboardView;

public class Main {

    public static void main(String[] args) {

        DashboardModel dashboardModel = new DashboardModel();

        DashboardController dashboardController = new DashboardController(dashboardModel);

        DashboardView dashboardView = new DashboardView(dashboardController);
    }
}
