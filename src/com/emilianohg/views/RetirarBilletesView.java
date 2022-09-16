package com.emilianohg.views;

import com.emilianohg.controllers.Controller;
import com.emilianohg.controllers.DashboardController;
import com.emilianohg.models.Billete;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class RetirarBilletesView extends JFrame {

    private DashboardController controller;

    private Font normalFont, boldFont;
    JTable tableBilletesRetirados, tableInventario;

    public RetirarBilletesView() {
        super("Banco del cambio");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);

        try {
            this.normalFont = Font.createFont(
                Font.TRUETYPE_FONT,
                new File("assets/Roboto/Roboto-Regular.ttf")
            ).deriveFont(16.0f);

            this.boldFont = Font.createFont(
                Font.TRUETYPE_FONT,
                new File("assets/Roboto/Roboto-Bold.ttf")
            ).deriveFont(16.0f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        Container contentPanel = this.getContentPane();

        GroupLayout groupLayout = new GroupLayout(contentPanel);

        contentPanel.setLayout(groupLayout);

        JLabel clickMe = new JLabel("Click Here");
        JButton button = new JButton("This Button");

        JPanel panelFormulario = this.makePanelFormulario();
        JPanel panelTitulo = this.makePanelTitulo();
        JPanel panelBilletesRetirados = this.makePanelRetirarBilletes();
        JPanel panelInventario = this.makePanelInventario();

        Component glue = Box.createVerticalGlue();

        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup()
                .addComponent(panelTitulo)
                .addGroup(
                    groupLayout.createSequentialGroup()
                        .addGap(40)
                        .addGroup(
                            groupLayout.createParallelGroup()
                                .addComponent(panelFormulario, GroupLayout.Alignment.CENTER, 500, 500, 500)
                                .addComponent(panelBilletesRetirados, GroupLayout.Alignment.CENTER, 500, 500, 500)
                        )
                        .addGap(20)
                        .addGroup(
                            groupLayout.createParallelGroup()
                                .addComponent(panelInventario, GroupLayout.Alignment.CENTER, 300, 300, 300)
                        )
                        .addGap(40)
                )
        );

        groupLayout.setVerticalGroup(
            groupLayout.createSequentialGroup()
                .addComponent(panelTitulo)
                .addGroup(
                    groupLayout.createParallelGroup()
                        .addGroup(
                            groupLayout.createSequentialGroup()
                                .addComponent(panelFormulario)
                                .addGap(10)
                                .addComponent(panelBilletesRetirados, 100, 100, 500)
                        )
                        .addGroup(
                            groupLayout.createSequentialGroup()
                                .addComponent(panelInventario)
                        )
                )
        );

    }

    JPanel makePanelTitulo() {
        JPanel panel = new JPanel();
        panel.setBorder(
            BorderFactory.createCompoundBorder(
                panel.getBorder(),
                BorderFactory.createEmptyBorder(10,10,10,10)
            )
        );

        BoxLayout layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
        panel.setLayout(layout);

        JLabel labelTitulo = new JLabel("Banco del cambio", JLabel.CENTER);
        labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelTitulo.setFont(this.boldFont.deriveFont(25.0f));

        JLabel labelSubtitulo = new JLabel("Caja #1 ", JLabel.CENTER);
        labelSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelSubtitulo.setFont(this.normalFont.deriveFont(20.0f));

        panel.add(Box.createHorizontalGlue());
        panel.add(labelTitulo);
        panel.add(Box.createHorizontalGlue());
        panel.add(labelSubtitulo);

        return panel;
    }

    JPanel makePanelFormulario() {
        JPanel panel = new JPanel();

        GroupLayout groupLayout = new GroupLayout(panel);

        JLabel labelCantidad = new JLabel("Cantidad: ");
        labelCantidad.setFont(this.boldFont);

        JTextField textFieldCantidad = new JFormattedTextField();
        textFieldCantidad.setColumns(20);
        textFieldCantidad.setFont(this.normalFont);

        textFieldCantidad.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (
                    c >= '0' && c <= '9'
                    || c == KeyEvent.VK_BACK_SPACE
                    || c == KeyEvent.VK_DELETE
                ) {
                    return;
                }
                e.consume();
            }
        });

        textFieldCantidad.setBorder(
            BorderFactory.createCompoundBorder(
                textFieldCantidad.getBorder(),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)
            )
        );

        JButton btnRetirar = new JButton("Retirar");
        btnRetirar.setFont(boldFont);

        btnRetirar.addActionListener(event -> {
            String textCantidad = textFieldCantidad.getText();

            if (textCantidad.isEmpty()) {
                showMessageError("La cantidad es un campo requerido", "Ingresa correctamente los datos");
                return;
            }

            int cantidad = Integer.parseInt(textCantidad);

            this.controller.retirar(cantidad);
        });

        groupLayout.setHorizontalGroup(
            groupLayout.createSequentialGroup()
                .addComponent(labelCantidad)
                .addGap(10)
                .addComponent(textFieldCantidad)
                .addGap(10)
                .addComponent(btnRetirar)
        );

        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelCantidad)
                .addComponent(textFieldCantidad)
                .addComponent(btnRetirar)
        );

        panel.setPreferredSize(new Dimension(500, 40));
        panel.setMaximumSize(panel.getPreferredSize());
        panel.setMinimumSize(panel.getPreferredSize());

        return panel;
    }

    JPanel makePanelRetirarBilletes() {
        String[] columns = {"Cant.", "Denominación"};
        String[][] exampleData = {{"10", "100 MXN"}, {"5", "20 MXN"}};


        JPanel panelBilletesRetirados = new JPanel();

        BoxLayout layout = new BoxLayout(panelBilletesRetirados, BoxLayout.PAGE_AXIS);
        panelBilletesRetirados.setLayout(layout);

        JLabel tituloBilletesRetirados = new JLabel("Billetes retirados", JLabel.CENTER);
        tituloBilletesRetirados.setFont(this.boldFont);
        tituloBilletesRetirados.setAlignmentX(Component.CENTER_ALIGNMENT);
        tituloBilletesRetirados.setBorder(
            BorderFactory.createCompoundBorder(
                tituloBilletesRetirados.getBorder(),
                BorderFactory.createEmptyBorder(10,10,10,10)
            )
        );

        this.tableBilletesRetirados = new JTable(exampleData, columns);

        this.tableBilletesRetirados.setPreferredScrollableViewportSize(new Dimension(
            (int) this.tableBilletesRetirados.getPreferredSize().getWidth() + 100,
            (int) this.tableBilletesRetirados.getPreferredSize().getHeight()
        ));

        this.tableBilletesRetirados.getTableHeader().setFont(this.boldFont.deriveFont(16.0f));
        this.tableBilletesRetirados.setFont(this.normalFont.deriveFont(16.0f));

        JScrollPane scrollPaneBilletesRetirados = new JScrollPane(this.tableBilletesRetirados);
        scrollPaneBilletesRetirados.setBorder(null);

        scrollPaneBilletesRetirados.setPreferredSize(new Dimension(500, 200));
        scrollPaneBilletesRetirados.setMinimumSize(scrollPaneBilletesRetirados.getPreferredSize());
        scrollPaneBilletesRetirados.setMaximumSize(scrollPaneBilletesRetirados.getPreferredSize());

        panelBilletesRetirados.add(tituloBilletesRetirados);
        panelBilletesRetirados.add(scrollPaneBilletesRetirados);

        return panelBilletesRetirados;
    }

    JPanel makePanelInventario() {

        String[] columns = {"Cant.", "Denominación"};
        String[][] exampleData = {};

        JPanel panelInventario = new JPanel();

        BoxLayout layout = new BoxLayout(panelInventario, BoxLayout.PAGE_AXIS);
        panelInventario.setLayout(layout);

        JLabel titulo = new JLabel("Inventario", JLabel.CENTER);
        titulo.setFont(this.boldFont);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(
            BorderFactory.createCompoundBorder(
                titulo.getBorder(),
                BorderFactory.createEmptyBorder(10,10,10,10)
            )
        );

        this.tableInventario = new JTable(exampleData, columns);
        this.tableInventario.setPreferredScrollableViewportSize(new Dimension(
                (int) this.tableInventario.getPreferredSize().getWidth() + 100,
                (int) this.tableInventario.getPreferredSize().getHeight()
        ));

        this.tableInventario.getTableHeader().setFont(this.boldFont.deriveFont(16.0f));
        this.tableInventario.setFont(this.normalFont.deriveFont(16.0f));

        JScrollPane scrollPaneBilletesInventarios = new JScrollPane(this.tableInventario);
        scrollPaneBilletesInventarios.setBorder(null);

        scrollPaneBilletesInventarios.setPreferredSize(new Dimension(500, 250));
        scrollPaneBilletesInventarios.setMinimumSize(scrollPaneBilletesInventarios.getPreferredSize());
        scrollPaneBilletesInventarios.setMaximumSize(scrollPaneBilletesInventarios.getPreferredSize());

        panelInventario.add(titulo);
        panelInventario.add(scrollPaneBilletesInventarios);

        return panelInventario;
    }

    public void showMessageError(String message) {
        showMessageError(message, "Ocurrio un error");
    }

    public void showMessageError(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void setBilletesRetirados(List<Billete> billetes) {
        // todo: arreglar esto
        DefaultTableModel model = (DefaultTableModel) this.tableBilletesRetirados.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
        }

        for (Billete billete : billetes) {
            String[] row = {
                String.valueOf(billete.getExistencia()),
                String.valueOf(billete.getDenominacion())
            };
            model.addRow(row);
        }

    }

    public void setController(DashboardController controller) {
        this.controller = controller;
    }

}
