package com.emilianohg.models;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Vector;

public class DashboardModel {

    BilletesRepository billetesRepository = new BilletesRepository();

    public List<Billete> getAll() {
        return billetesRepository.getAll();
    }

    public List<Billete> agregarBilletes() {
        List<Billete> billetesInventario = this.getAll();
        List<Billete> billetesNuevos = new Vector<>();

        Random random = new Random();

        int billetesPorAgregar = random.nextInt(10) + 10; // [10 - 20]

        for (int i = 0; i < billetesPorAgregar; i++) {
            int posicionBillete = random.nextInt(billetesInventario.size());

            Billete billete = billetesInventario.get(posicionBillete);

            Optional<Billete> billeteNuevo = billetesNuevos
                .stream()
                .filter(_billete -> billete.getDenominacion() == _billete.getDenominacion())
                .findFirst();

            if (billeteNuevo.isPresent()) {
                billeteNuevo.get().agregarExistencia(1);
            } else {
                billetesNuevos.add(new Billete(billete.getDenominacion()));
            }
        }

        return billetesRepository.agregar(billetesNuevos);
    }

    public List<Billete> retirar(int cantidad) throws BilletesInsuficientesException {
        Optional<List<Billete>> billetesRetirados = Database.transaction(() -> {

            int cantidadActual = cantidad;

            List<Billete> billetesInventario = billetesRepository.getAll();
            List<Billete> billetesPorRetirar = new Vector<>();

            for (Billete billete : billetesInventario) {
                int denominacion = billete.getDenominacion();
                int existencia = billete.getExistencia();
                int maximoPorDenominacion = cantidadActual / denominacion;

                // Me quedo con la existencia o con el maximo que ocupo por denominación
                // Selecciono el menor de los dos
                int cantidadPorDenomiacion = Math.min(maximoPorDenominacion, existencia);

                if (cantidadPorDenomiacion > 0) {
                    cantidadActual -= denominacion * cantidadPorDenomiacion;
                    billetesPorRetirar.add(new Billete(denominacion, cantidadPorDenomiacion));
                }
            }

            Integer totalRetirado = billetesPorRetirar
                    .stream()
                    .map(_billete -> _billete.getDenominacion() * _billete.getExistencia())
                    .reduce(0, Integer::sum);

            if (totalRetirado != cantidad) {
                return null;
            }

            billetesRepository.retirar(billetesPorRetirar);

            return billetesPorRetirar;
        });

        if (!billetesRetirados.isPresent()) {
            throw new BilletesInsuficientesException();
        }

        return billetesRetirados.get();
    }

}
