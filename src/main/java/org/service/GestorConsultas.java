package org.service;

import org.model.Cuenta;
import org.model.Movimiento;

import java.time.LocalDate;

/**
 * Clase GestorConsultas, encargada de gestionar las consultas
 * @author fgodoyar
 */
public class GestorConsultas {
    /**
     * Atributos de la clase
     */
    private GestorCuenta gestorCuenta = new GestorCuenta();
    private GestorOperaciones gestorOperaciones = new GestorOperaciones();

    /**
     * Constructor vacío
     */
    public GestorConsultas(){}

    /**
     * Consulta para ver el saldo de una cuenta.
     * En caso de que no exista el número, lanza un error por consola.
     * @param numeroCuenta
     */
    public void consultarSaldo(String numeroCuenta){
        for(Cuenta cuenta : gestorCuenta.getCuentas().values()){
            if (cuenta.getNumero_cuenta().equals(numeroCuenta)){
                System.out.println("Saldo Actual: " + cuenta.getSaldo());
            }else {
                System.out.println("ERROR: No se ha encontrado el número de cuenta en la base de datos.");
            }
        }
    }

    /**
     * Consulta para ver el historial de movimientos de una cuenta.
     * En caso de que no exista el número, lanza un error por consola.
     * @param numeroCuenta
     */
    public void historialMovimientos(String numeroCuenta){
        for(Cuenta cuenta : gestorCuenta.getCuentas().values()){
            if (cuenta.getNumero_cuenta().equals(numeroCuenta)){
                for (Movimiento movimiento : gestorOperaciones.getMovimientos().values()){
                    System.out.println("Historial de movimientos " + cuenta.getNumero_cuenta() + ":");
                    System.out.printf("|%-20s |%-20s| |%-20s|\n", "Fecha", "Tipo", "Cantidad");
                    System.out.println("|---------------------|---------------------|---------------------|");
                    System.out.printf("|%-20s |%-20s| |%-20s|\n", movimiento.getFecha(), movimiento.getTipo(), movimiento.getCantidad());
                }
            }else {
                System.out.println("ERROR: No se ha encontrado el número de cuenta en la base de datos.");
            }
        }
    }

    /**
     * Consulta que muestra los movimientos en intervalos de fechas.
     * En caso de que no existan las fechas, lanza un error por consola.
     * @param fechaInicio
     * @param fechaFin
     */
    public void movimientosPorFecha(LocalDate fechaInicio, LocalDate fechaFin){
        for (Movimiento movimiento : gestorOperaciones.getMovimientos().values()){
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
