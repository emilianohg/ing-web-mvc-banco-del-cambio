package com.emilianohg.views;

import com.emilianohg.models.Billete;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class BilleteTableModel extends DefaultTableModel {

    public BilleteTableModel() {
        super(new Object[][]{}, new String[]{"Denominación", "Cantidad"});
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        return false;
    }

    public void clear() {
        this.setRowCount(0);
    }

    public void addBilletes(List<Billete> billetes) {
        for (Billete billete : billetes) {
            this.addRow(billete);
        }
    }

    public void addRow(Billete billete) {
        super.addRow(new Object[] {
            String.format("$ %d", billete.getDenominacion()),
            billete.getExistencia()
        });
    }
}
