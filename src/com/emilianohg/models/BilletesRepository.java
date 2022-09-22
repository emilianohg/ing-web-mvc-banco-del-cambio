package com.emilianohg.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class BilletesRepository {

    Database db = Database.getInstance();

    public List<Billete> getAll() {
        return getAll(false);
    }

    public List<Billete> getAll(boolean updLock) {
        String sql = String.format(
        "SELECT * FROM Billetes %s ORDER BY denominacion DESC",
            updLock ? "WITH (UPDLOCK)" : ""
        );

        List<Billete> billetes = new Vector<>();

        try {
            Statement stmt = db.getConnection().createStatement();
            ResultSet result = stmt.executeQuery(sql);

            while (result.next()) {
                int denominacion = result.getInt("denominacion");
                int existencia = result.getInt("existencia");
                String fecha = result.getString("fecha");

                billetes.add(new Billete(denominacion, existencia, fecha));
            }

            stmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return billetes;
    }

    public List<Billete> agregar(List<Billete> billetes) {

        try {
            Statement stmt = db.getConnection().createStatement();

            for (Billete billete : billetes) {
                String sqlAgregarExistencia = String.format(
                    "UPDATE Billetes SET existencia = existencia + %s, fecha = '%s' WHERE denominacion = %s",
                    billete.getExistencia(),
                    billete.getFecha(),
                    billete.getDenominacion()
                );
                stmt.execute(sqlAgregarExistencia);
            }

            stmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return this.getAll();
    }

    public List<Billete> retirar(List<Billete> billetes) {

        try {
            Statement stmt = db.getConnection().createStatement();

            for (Billete billete : billetes) {
                String sqlReducirExistencia = String.format(
                        "UPDATE Billetes SET existencia = existencia - %s, fecha = '%s' WHERE denominacion = %s",
                        billete.getExistencia(),
                        billete.getFecha(),
                        billete.getDenominacion()
                );
                stmt.execute(sqlReducirExistencia);
            }

            stmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return this.getAll();
    }

}
