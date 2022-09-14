package com.emilianohg.environment;

import com.emilianohg.utils.Couple;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Environment {
    Scanner scanner;
    String path;
    List<Couple<String, String>> variables;

    public Environment() {
        this.path = "src/.env";
        this.variables = new ArrayList<>();
        try {
            this.scanner = new Scanner(new File(this.path));
            while(this.scanner.hasNextLine()) {
                String statement = this.scanner.nextLine();
                String variable = statement.split("=")[0];
                String value = statement.split("=")[1];
                this.variables.add(new Couple<>(variable, value));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public String getValue(String variable) {
        Optional<Couple<String, String>> _variable = this.variables.stream()
                .filter(var -> var.getKey().equals(variable))
                .findFirst();
        return _variable.isPresent() ? _variable.get().getValue() : "";
    }
}
