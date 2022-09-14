package com.emilianohg.views;

import com.emilianohg.controllers.DashboardController;

import javax.swing.*;
import java.awt.*;

public class DashboardView extends JFrame {

    final private DashboardController controller;

    public DashboardView(DashboardController controller) {
        super("Banco del cambio");
        this.controller = controller;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);

        JPanel panelInventario = new JPanel();
        JPanel panelDistribucion = new JPanel();
        //panelDistribucion.setLayout(new BoxLayout(panelDistribucion, BoxLayout.PAGE_AXIS));

        JLabel labelCantidad = new JLabel("Cantidad: ");
        JTextField textCantidad = new JTextField();
        JButton btnCambiar = new JButton("Cambiar");

        btnCambiar.addActionListener(actionEvent -> this.controller.retirar());

        JPanel panelFormCambiar = new JPanel();
        GridLayout panelFormCambiarLayout = new GridLayout(0,3);
        panelFormCambiar.setSize(100, 100);
        panelFormCambiar.setLayout(panelFormCambiarLayout);

        panelFormCambiar.add(labelCantidad);
        panelFormCambiar.add(textCantidad);
        panelFormCambiar.add(btnCambiar);

        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("BANCO DEL CAMBIO");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTitulo.add(titulo);

        JLabel subtitulo = new JLabel("CAJA #1");
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTitulo.add(subtitulo);

        panelDistribucion.add(new JLabel("Distribución de billetes"), BorderLayout.NORTH);

        JLabel tituloInventario = new JLabel("Inventario de billetes");
        tituloInventario.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInventario.add(tituloInventario, BorderLayout.NORTH);

        panelInventario.setLayout(new BoxLayout(panelInventario, BoxLayout.PAGE_AXIS));

        String[][] data = controller.getBilletesFromInventario().stream().map(billete -> new String[] {
            String.valueOf(billete.getDenominacion()),
            String.valueOf(billete.getExistencia()),
        }).toArray(String[][]::new);

        String[] columns = {"Denominación", "Cantidad"};

        JTable table = new JTable(data, columns);

        panelInventario.add(new JScrollPane(table));

        panelDistribucion.add(panelFormCambiar, BorderLayout.CENTER);
        panelDistribucion.add(new JScrollPane(), BorderLayout.SOUTH);


        add(panelTitulo, BorderLayout.PAGE_START);
        add(panelInventario, BorderLayout.LINE_END);
        add(panelDistribucion, BorderLayout.CENTER);

        setVisible(true);
    }
}
