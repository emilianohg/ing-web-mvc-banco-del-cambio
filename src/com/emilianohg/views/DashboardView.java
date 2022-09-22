package com.emilianohg.views;

import com.emilianohg.controllers.DashboardController;
import com.emilianohg.models.Billete;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DashboardView extends JFrame {

    private DashboardController controller;

    private Font normalFont, boldFont;
    private final BilleteTableModel billetesRetiradosTableModel, billetesInventariosTableModel;
    private boolean isShowingInventario = true;
    private JTextField textFieldCantidad;

    public DashboardView() {
        super("Banco del cambio");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 460);
        setResizable(false);
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

        billetesRetiradosTableModel = new BilleteTableModel();
        billetesInventariosTableModel = new BilleteTableModel();

        Container contentPanel = this.getContentPane();
        GroupLayout groupLayout = new GroupLayout(contentPanel);
        contentPanel.setLayout(groupLayout);

        JPanel panelFormulario = this.makePanelFormulario();
        JPanel panelTitulo = this.makePanelTitulo();
        JPanel panelBilletesRetirados = this.makePanelRetirarBilletes();
        JPanel panelInventario = this.makePanelInventario();

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

    public void showMessageError(String message) {
        showMessageError(message, "Ocurrio un error");
    }

    public void showMessageError(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void setBilletesRetirados(List<Billete> billetes) {
        this.billetesRetiradosTableModel.clear();
        this.billetesRetiradosTableModel.addBilletes(billetes);
    }

    public void setBilletesInventario(List<Billete> billetes) {
        if (!this.isShowingInventario) {
            return;
        }
        this.clearBilletesInventario();
        this.billetesInventariosTableModel.addBilletes(billetes);
    }

    public void clearBilletesInventario() {
        this.billetesInventariosTableModel.clear();
    }

    public boolean isShowingInventario() {
        return isShowingInventario;
    }

    public void setShowingInventario(boolean showingInventario) {
        isShowingInventario = showingInventario;
    }

    public void setController(DashboardController controller) {
        this.controller = controller;
        this.initalControllerActions();
    }

    public void clearTextFieldCantidad() {
        this.textFieldCantidad.setText(null);
    }

    private void initalControllerActions() {
        this.controller.getBilletesFromInventario();
    }

    private JPanel makePanelTitulo() {
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

    private JPanel makePanelFormulario() {
        JPanel panel = new JPanel();

        GroupLayout groupLayout = new GroupLayout(panel);

        JLabel labelCantidad = new JLabel("Cantidad: ");
        labelCantidad.setFont(this.boldFont);

        this.textFieldCantidad = new JFormattedTextField();
        this.textFieldCantidad.setColumns(20);
        this.textFieldCantidad.setFont(this.normalFont);

        this.textFieldCantidad.addKeyListener(new KeyAdapter() {
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

        this.textFieldCantidad.setBorder(
            BorderFactory.createCompoundBorder(
                    this.textFieldCantidad.getBorder(),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)
            )
        );

        JButton btnRetirar = new JButton("Retirar");
        btnRetirar.setFont(boldFont);

        btnRetirar.addActionListener(event -> {
            String textCantidad = this.textFieldCantidad.getText();

            if (textCantidad.isEmpty()) {
                showMessageError("La cantidad es un campo requerido", "Ingresa correctamente los datos");
                return;
            }

            int cantidad = Integer.parseInt(textCantidad);

            if (cantidad <= 0) {
                showMessageError("La cantidad debe ser mayor a 0", "Ingresa correctamente los datos");
            }

            this.controller.retirar(cantidad);
        });

        groupLayout.setHorizontalGroup(
            groupLayout.createSequentialGroup()
                .addComponent(labelCantidad)
                .addGap(10)
                .addComponent(this.textFieldCantidad)
                .addGap(10)
                .addComponent(btnRetirar)
        );

        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelCantidad)
                .addComponent(this.textFieldCantidad)
                .addComponent(btnRetirar)
        );

        panel.setPreferredSize(new Dimension(500, 40));
        panel.setMaximumSize(panel.getPreferredSize());
        panel.setMinimumSize(panel.getPreferredSize());

        return panel;
    }

    private JPanel makePanelRetirarBilletes() {
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

        JTable tableBilletesRetirados = new JTable(billetesRetiradosTableModel);

        tableBilletesRetirados.setPreferredScrollableViewportSize(new Dimension(
            (int) tableBilletesRetirados.getPreferredSize().getWidth() + 100,
            (int) tableBilletesRetirados.getPreferredSize().getHeight()
        ));

        tableBilletesRetirados.getTableHeader().setFont(this.boldFont.deriveFont(16.0f));
        tableBilletesRetirados.setFont(this.normalFont.deriveFont(16.0f));

        JScrollPane scrollPaneBilletesRetirados = new JScrollPane(tableBilletesRetirados);
        scrollPaneBilletesRetirados.setBorder(null);

        scrollPaneBilletesRetirados.setPreferredSize(new Dimension(500, 200));
        scrollPaneBilletesRetirados.setMinimumSize(scrollPaneBilletesRetirados.getPreferredSize());
        scrollPaneBilletesRetirados.setMaximumSize(scrollPaneBilletesRetirados.getPreferredSize());

        panelBilletesRetirados.add(tituloBilletesRetirados);
        panelBilletesRetirados.add(scrollPaneBilletesRetirados);

        return panelBilletesRetirados;
    }

    private JPanel makePanelInventario() {

        JPanel panelInventario = new JPanel();

        BoxLayout layout = new BoxLayout(panelInventario, BoxLayout.PAGE_AXIS);
        panelInventario.setLayout(layout);

        JLabel titulo = new JLabel("Billetes en Inventario", JLabel.CENTER);
        titulo.setFont(this.boldFont);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(
            BorderFactory.createCompoundBorder(
                titulo.getBorder(),
                BorderFactory.createEmptyBorder(10,10,10,10)
            )
        );

        JTable tableInventario = new JTable(this.billetesInventariosTableModel);
        tableInventario.setPreferredScrollableViewportSize(new Dimension(
                (int) tableInventario.getPreferredSize().getWidth() + 100,
                (int) tableInventario.getPreferredSize().getHeight()
        ));

        tableInventario.getTableHeader().setFont(this.boldFont.deriveFont(16.0f));
        tableInventario.setFont(this.normalFont.deriveFont(16.0f));

        JScrollPane scrollPaneBilletesInventarios = new JScrollPane(tableInventario);
        scrollPaneBilletesInventarios.setBorder(null);

        scrollPaneBilletesInventarios.setPreferredSize(new Dimension(300, 220));
        scrollPaneBilletesInventarios.setMinimumSize(scrollPaneBilletesInventarios.getPreferredSize());
        scrollPaneBilletesInventarios.setMaximumSize(scrollPaneBilletesInventarios.getPreferredSize());

        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(1, 2, 3, 3));

        panelButtons.setBorder(
            BorderFactory.createCompoundBorder(
                panelButtons.getBorder(),
                BorderFactory.createEmptyBorder(5,0,0,0)
            )
        );

        JButton btnOcultarMostrarInventario = new JButton("Ocultar/Mostrar");
        btnOcultarMostrarInventario.addActionListener(action -> this.controller.toogleViewInventario());
        btnOcultarMostrarInventario.setFont(this.boldFont.deriveFont(14.0f));

        JButton btnAgregarBilletes = new JButton("Agregar billetes");
        btnAgregarBilletes.addActionListener(action -> this.controller.agregarBilletes());
        btnAgregarBilletes.setFont(this.boldFont.deriveFont(14.0f));

        panelButtons.add(btnOcultarMostrarInventario);
        panelButtons.add(btnAgregarBilletes);

        panelButtons.setSize(new Dimension(300, 60));
        panelButtons.setMinimumSize(panelButtons.getPreferredSize());
        panelButtons.setMaximumSize(panelButtons.getPreferredSize());

        panelInventario.add(titulo);
        panelInventario.add(scrollPaneBilletesInventarios);
        panelInventario.add(panelButtons);

        return panelInventario;
    }

}
