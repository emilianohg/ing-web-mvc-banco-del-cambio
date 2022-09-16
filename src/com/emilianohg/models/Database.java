/*
    Materia: TEMAS SELECTOS DE BASE DE DATOS
    Maestro: GARCIA GERARDO CLEMENTE
    Equipo:
        HERNÁNDEZ GUERRERO EMILIANO
        AGUILAR PADILLA LUIS GERARDO
        AMARILLAS PACHECO EFRAIN
 */

package com.emilianohg.models;

import com.emilianohg.environment.Environment;

import java.sql.*;
import java.util.Optional;

interface Callback<T> {
    T call();
}

public class Database {

    private static Database instance;
    Environment env;

    private Connection connection;
    private final String SERVER;
    private final String PORT;
    private final String DATABASE_NAME;
    private final String USER;
    private final String PASSWORD;

    private Database() {
        this.env = new Environment();

        this.SERVER = env.getValue("SERVER");
        this.PORT = env.getValue("PORT");
        this.USER = env.getValue("USER");
        this.PASSWORD = env.getValue("PASSWORD");
        this.DATABASE_NAME = env.getValue("DATABASE_NAME");

        try {
            connection = DriverManager.getConnection(this.getConnectionUrl());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private String getConnectionUrl() {
        return String.format(
                "jdbc:sqlserver://%s:%s;databaseName=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=true;",
                this.SERVER,
                this.PORT,
                this.DATABASE_NAME,
                this.USER,
                this.PASSWORD
        );
    }

    public Connection getConnection() {
        return connection;
    }

    public static <T> Optional<T> transaction(Callback<T> callback) {
        Database db = Database.getInstance();

        T result = null;

        try {
            db.getConnection().setAutoCommit(false);

            result = callback.call();

            db.getConnection().setAutoCommit(true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                db.getConnection().rollback();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        if (result == null) {
            return Optional.empty();
        }

        return Optional.of(result);
    }

}
