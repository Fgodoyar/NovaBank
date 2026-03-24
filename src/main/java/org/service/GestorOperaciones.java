package org.service;

import org.model.Cuenta;
import org.model.Movimiento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase GestorOperaciones, encargada de gestionar los métodos de operaciones.
 */
public class GestorOperaciones {
    private List<Movimiento> movimientos;
    private GestorCuenta gestorCuenta = new GestorCuenta();

    /**
     * Constructor vacío
     */
    public GestorOperaciones() {
        this.movimientos = new ArrayList<>();
    }

    /**
     * Método encargado de depositar dinero en una cuenta
     * Valida que el número este registrado y que la cantidad sea mayor que 0.
     * En caso contrario, imprime un error por pantalla.
     * @param numeroCuenta
     * @param cantidad
     */
    public void depositarDinero(String numeroCuenta, BigDecimal cantidad){
        for(Cuenta cuenta : gestorCuenta.getCuentas().values()){
            if (cuenta.getNumero_cuenta().equals(numeroCuenta) && cantidad.compareTo(BigDecimal.ZERO) > 0){
                BigDecimal nuevoSueldo = cuenta.getSaldo().add(cantidad);
                cuenta.setSaldo(nuevoSueldo);
                movimientos.add(new Movimiento(cuenta.getId(), "DEPÓSITO", cantidad.plus(), LocalDate.now()));
            }
        }
    }

    /**
     * Método encargado de retirar dinero de una cuenta.
     * Valida que el número este registrado, que la cantidad sea mayor que 0 y que la cantidad a retirar sea
     * siempre menor que el saldo de la cuenta.
     * @param numeroCuenta
     * @param cantidad
     */
    public void retirarDinero(String numeroCuenta, BigDecimal cantidad){
        for(Cuenta cuenta : gestorCuenta.getCuentas().values()){
            if(cuenta.getNumero_cuenta().equals(numeroCuenta) && cantidad.compareTo(BigDecimal.ZERO) > 0
            && cuenta.getSaldo().compareTo(cantidad) > 0){
                BigDecimal nuevoSueldo = cuenta.getSaldo().subtract(cantidad);
                cuenta.setSaldo(nuevoSueldo);
                movimientos.add(new Movimiento(cuenta.getId(), "DEPÓSITO", cantidad.negate(), LocalDate.now()));
            }
        }
    }

}
