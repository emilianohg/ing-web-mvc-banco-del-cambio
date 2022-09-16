package com.emilianohg.models;

public class BilletesInsuficientesException extends Exception {
    BilletesInsuficientesException() {
        super("No contamos con los billetes necesarios para su retiro");
    }
}
