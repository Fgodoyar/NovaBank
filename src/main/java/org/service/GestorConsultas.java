package org.service;

import org.model.Cuenta;
import org.model.Movimiento;


public class GestorConsultas {
    private GestorCuenta gestorCuenta = new GestorCuenta();
    private GestorOperaciones gestorOperaciones = new GestorOperaciones();

    public GestorConsultas(){}

    public void consultarSaldo(String numeroCuenta){
        for(Cuenta cuenta : gestorCuenta.getCuentas().values()){
            if (cuenta.getNumero_cuenta().equals(numeroCuenta)){
                System.out.println("Saldo Actual: " + cuenta.getSaldo());
            }
        }
    }

    public void historialMovimientos(String numeroCuenta){
        for(Cuenta cuenta : gestorCuenta.getCuentas().values()){
            if (cuenta.getNumero_cuenta().equals(numeroCuenta)){
                for (Movimiento movimiento : gestorOperaciones.getMovimientos().values()){
                    System.out.println("Historial de movimientos: " + cuenta.getNumero_cuenta() + ":");
                    System.out.printf("|%-20s |%-20s| |%-20s|\n", "Fecha", "Tipo", "Cantidad");
                    System.out.println("|---------------------|---------------------|---------------------|");
                    System.out.printf("|%-20s |%-20s| |%-20s|\n", movimiento.getFecha(), movimiento.getTipo(), movimiento.getCantidad());
                }
            }
        }
    }
}
