package org.service;

import org.model.Cuenta;
import org.model.Movimiento;
import org.repositorio.Repositorio;

import java.time.LocalDate;

/**
 * Clase GestorConsultas, encargada de gestionar las consultas
 * @author fgodoyar
 */
public class GestorConsultas {
    /**
     * Atributos de la clase
     */
    private Repositorio repositorio;
    private GestorCuenta gestorCuenta;

    /**
     * Constructor de la clase
     */
    public GestorConsultas(Repositorio repositorio, GestorCuenta gestorCuenta) {
        this.repositorio = repositorio;
        this.gestorCuenta = gestorCuenta;
    }

    /**
     * Consulta para ver el saldo de una cuenta.
     * En caso de que no exista el número, lanza un error por consola.
     * @param numeroCuenta
     */
    public void consultarSaldo(String numeroCuenta){
        Cuenta cuenta = gestorCuenta.buscarCuenta(numeroCuenta);
        if (cuenta == null){
            throw new IllegalArgumentException("ERROR: La cuenta no se ha encontrado.");
        }
        System.out.println("Saldo Actual: " + cuenta.getSaldo());
    }

    /**
     * Consulta para ver el historial de movimientos de una cuenta.
     * En caso de que no exista el número, lanza un error por consola.
     * @param numeroCuenta
     */
    public void historialMovimientos(String numeroCuenta){
        Cuenta cuenta = gestorCuenta.buscarCuenta(numeroCuenta);
        if (cuenta == null){
            throw new IllegalArgumentException("ERROR: No se ha encontrado la cuenta.");
        }
        for (Movimiento movimiento : repositorio.movimientos.values()){
            System.out.println("Historial de movimientos " + cuenta.getNumero_cuenta() + ":");
            System.out.printf("|%-20s |%-20s| |%-20s|\n", "Fecha", "Tipo", "Cantidad");
            System.out.println("|---------------------|---------------------|---------------------|");
            System.out.printf("|%-20s |%-20s| |%-20s|\n", movimiento.getFecha(), movimiento.getTipo(), movimiento.getCantidad());
        }
    }

    /**
     * Consulta que muestra los movimientos en intervalos de fechas.
     * En caso de que no existan las fechas, lanza un error por consola.
     * @param fechaInicio
     * @param fechaFin
     */
    public void movimientosPorFecha(LocalDate fechaInicio, LocalDate fechaFin){
        for (Movimiento movimiento : repositorio.movimientos.values()){
            if(!movimiento.getFecha().equals(fechaInicio) || !movimiento.getFecha().equals(fechaFin)){
                System.out.println("ERROR: Las fechas no están registradas en la base de datos.");
            }
            if(!fechaInicio.isAfter(fechaFin)){
                System.out.println("ERROR: La fecha inicial es superior a la fecha final.");
            }
            System.out.println("Historial de movimientos: ");
            System.out.printf("|%-20s |%-20s| |%-20s|\n", "Fecha", "Tipo", "Cantidad");
            System.out.println("|---------------------|---------------------|---------------------|");
            System.out.printf("|%-20s |%-20s| |%-20s|\n", movimiento.getFecha(), movimiento.getTipo(), movimiento.getCantidad());
        }
    }
}
