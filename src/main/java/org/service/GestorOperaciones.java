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
     * Valida que el número esté registrado y que la cantidad sea mayor que 0.
     * Por último, guarda el movimiento.
     * En caso contrario, imprime un error por pantalla.
     * @param numeroCuenta
     * @param cantidad
     */
    public void depositarDinero(String numeroCuenta, BigDecimal cantidad){
        for(Cuenta cuenta : gestorCuenta.getCuentas().values()){
            if (cuenta.getNumero_cuenta().equals(numeroCuenta) || cantidad.compareTo(BigDecimal.ZERO) > 0){
                BigDecimal nuevoSueldo = cuenta.getSaldo().add(cantidad);
                cuenta.setSaldo(nuevoSueldo);
                movimientos.add(new Movimiento(cuenta.getId(), "DEPÓSITO", cantidad.plus(), LocalDate.now()));
            }
        }
    }

    /**
     * Método encargado de retirar dinero de una cuenta.
     * Valida que el número esté registrado, que la cantidad sea mayor que 0 y que la cantidad a retirar sea
     * siempre menor que el saldo de la cuenta.
     * Por último, guarda el movimiento.
     * En caso contrario, imprime un error por pantalla.
     *
     * @param numeroCuenta
     * @param cantidad
     */
    public void retirarDinero(String numeroCuenta, BigDecimal cantidad){
        for(Cuenta cuenta : gestorCuenta.getCuentas().values()){
            if(cuenta.getNumero_cuenta().equals(numeroCuenta) || cantidad.compareTo(BigDecimal.ZERO) > 0
            || cuenta.getSaldo().compareTo(cantidad) > 0){
                BigDecimal nuevoSueldo = cuenta.getSaldo().subtract(cantidad);
                cuenta.setSaldo(nuevoSueldo);
                movimientos.add(new Movimiento(cuenta.getId(), "RETIRO", cantidad.negate(), LocalDate.now()));
            }
        }
    }

    /**
     * Método transacción que retira dinero de una cuenta para ingresarla a otra mientras no sea la misma.
     * Valida que el número esté registrado, que la cantidad sea mayor que 0 y que la cantidad a retirar sea
     * siempre menor que el saldo de la cuenta.
     * Recorre el mapa cuentas y obtiene la Cuenta a través del número de cuenta.
     * La cuenta origen utiliza el método retirar y la cuenta destino deposita la cantidad.
     * Por último, guarda los movimientos y muestra el resultado.
     * En caso contrario, imprime un error por pantalla.
     *
     * @param numeroCuentaOrigen
     * @param numeroCuentaDestino
     * @param cantidad
     */
    public void transaccion(String numeroCuentaOrigen, String numeroCuentaDestino, BigDecimal cantidad){
        for(Cuenta cuenta : gestorCuenta.getCuentas().values()){
            if(!numeroCuentaOrigen.equals(numeroCuentaDestino) || cuenta.getNumero_cuenta().equals(numeroCuentaOrigen) ||
            cuenta.getNumero_cuenta().equals(numeroCuentaDestino) || cantidad.compareTo(BigDecimal.ZERO) > 0 || 
            cuenta.getSaldo().compareTo(cantidad) > 0){
                Cuenta cuentaOrigen = gestorCuenta.getCuentas().get(numeroCuentaOrigen);
                Cuenta cuentaDestino = gestorCuenta.getCuentas().get(numeroCuentaDestino);

                retirarDinero(cuentaOrigen.getNumero_cuenta(), cantidad);
                depositarDinero(cuentaDestino.getNumero_cuenta(), cantidad);

                movimientos.add(new Movimiento(cuentaOrigen.getId(), "TRANSFERENCIA_SALIENTE", cantidad.negate(), LocalDate.now()));
                movimientos.add(new Movimiento(cuentaDestino.getId(), "TRANSFERENCIA_ENTRANTE", cantidad.plus(), LocalDate.now()));

                System.out.println("Transferencia realizada correctamente.");
                System.out.println("Cuenta Origen: " + cuentaOrigen.getNumero_cuenta() + " -> " + cantidad.negate());
                System.out.println("Cuenta Destino: " + cuentaDestino.getNumero_cuenta() + " -> " + cantidad.plus());
            }
        }
    }
}
