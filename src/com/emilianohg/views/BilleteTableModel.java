package com.emilianohg.views;

import com.emilianohg.models.Billete;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.List;
import java.util.Vector;

public class BilleteTableModel implements TableModel {

    private List<Billete> billetes = new Vector<>();

    @Override
    public int getRowCount() {
        return billetes.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int i) {
        switch (i) {
            case 0: return "Denominacion";
            case 1: return "Cantidad";
            case 2: return "Fecha";
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int i) {
        return null;
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        return false;
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        Billete billete = billetes.get(fila);
        switch (columna) {
            case 0: return billete.getDenominacion();
            case 1: return billete.getExistencia();
            case 2: return billete.getFecha();
        }
        return null;
    }

    @Override
    public void setValueAt(Object newBillete, int fila, int columna) {

    }

    @Override
    public void addTableModelListener(TableModelListener tableModelListener) {
        billetes.add((Billete) tableModelListener);
    }

    @Override
    public void removeTableModelListener(TableModelListener tableModelListener) {
        billetes.remove((Billete) tableModelListener);
    }
}
