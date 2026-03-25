package org.service;

import org.model.Cuenta;
import org.model.Movimiento;
import org.repositorio.Repositorio;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Clase GestorOperaciones, encargada de gestionar los métodos de operaciones.
 */
public class GestorOperaciones {
    private Repositorio repositorio;
    private GestorCuenta gestorCuenta;

    /**
     * Constructor de la clase.
     */
    public GestorOperaciones(Repositorio repositorio, GestorCuenta gestorCuenta) {
        this.repositorio = repositorio;
        this.gestorCuenta = gestorCuenta;
    }


    /**
     * Método encargado de depositar dinero en una cuenta
     * Valida que el número esté registrado.
     * Por último, guarda el movimiento.
     * @param numeroCuenta
     * @param cantidad
     */
    public void depositarDinero(String numeroCuenta, BigDecimal cantidad){
        Cuenta cuenta = gestorCuenta.buscarCuenta(numeroCuenta);
        if (cuenta == null){
            throw new IllegalArgumentException("No se ha encontrado la cuenta.");
        }
        BigDecimal nuevoSueldo = cuenta.getSaldo().add(cantidad);
        cuenta.setSaldo(nuevoSueldo);
        Movimiento deposito = new Movimiento(cuenta.getId(), "DEPÓSITO", cantidad.plus(), LocalDate.now());
        repositorio.guardarMovimiento(deposito);
    }

    /**
     * Método encargado de retirar dinero de una cuenta.
     * Valida que el número esté registrado y que la cantidad a retirar sea
     * siempre menor que el saldo de la cuenta.
     * Por último, guarda el movimiento.
     *
     * @param numeroCuenta
     * @param cantidad
     */
    public void retirarDinero(String numeroCuenta, BigDecimal cantidad){
        Cuenta cuenta = gestorCuenta.buscarCuenta(numeroCuenta);
        if(cuenta == null){
            throw new IllegalArgumentException("No se ha encontrado ninguna cuenta.");
        }
        if(cuenta.getSaldo().compareTo(cantidad) < 0){
            throw new IllegalArgumentException("Saldo insuficiente.");
        }

        BigDecimal nuevoSueldo = cuenta.getSaldo().subtract(cantidad);
        cuenta.setSaldo(nuevoSueldo);
        Movimiento retirar = new Movimiento(cuenta.getId(), "RETIRO", cantidad.negate(), LocalDate.now());
        repositorio.guardarMovimiento(retirar);
    }

    /**
     * Método transacción que retira dinero de una cuenta para ingresarla a otra mientras no sea la misma.
     * Valida que el número esté registrado, que la cantidad sea mayor que 0 y que la cantidad a retirar sea
     * siempre menor que el saldo de la cuenta.
     * Recorre el mapa cuentas y obtiene la Cuenta a través del número de cuenta.
     * La cuenta origen utiliza el método retirar y la cuenta destino deposita la cantidad.
     * Por último, guarda los movimientos y muestra el resultado.
     *
     * @param numeroCuentaOrigen
     * @param numeroCuentaDestino
     * @param cantidad
     */
    public void transaccion(String numeroCuentaOrigen, String numeroCuentaDestino, BigDecimal cantidad){
        Cuenta cuentaOrigen = gestorCuenta.buscarCuenta(numeroCuentaOrigen);
        Cuenta cuentaDestinatario = gestorCuenta.buscarCuenta(numeroCuentaDestino);

        if(numeroCuentaOrigen.equals(numeroCuentaDestino)){
            throw new IllegalArgumentException("ERROR: No se puede transferir dinero sobre la misma cuenta.");
        }
        if(cuentaOrigen == null || cuentaDestinatario == null){
            throw new IllegalArgumentException("No se ha encontrado ninguna cuenta.");
        }

        if(!(cuentaOrigen.getSaldo().compareTo(cantidad) > 0)){
            throw new IllegalArgumentException("Saldo insuficiente.");
        }

        cuentaOrigen.setSaldo(cuentaOrigen.getSaldo().subtract(cantidad));
        try {
            cuentaDestinatario.setSaldo(cuentaDestinatario.getSaldo().add(cantidad));
            Movimiento transferencia_saliente = new Movimiento(cuentaOrigen.getId(), "TRANSFERENCIA_SALIENTE", cantidad.negate(), LocalDate.now());
            repositorio.movimientos.put(transferencia_saliente.getId(), transferencia_saliente);
            Movimiento transferencia_entrante = new Movimiento(cuentaDestinatario.getId(), "TRANSFERENCIA_ENTRANTE", cantidad.plus(), LocalDate.now());
            repositorio.movimientos.put(transferencia_entrante.getId(), transferencia_entrante);
            System.out.println("Transferencia realizada correctamente.");
            System.out.println("Cuenta Origen: " + cuentaOrigen.getNumero_cuenta() + " -> " + cantidad.negate());
            System.out.println("Cuenta Destino: " + cuentaDestinatario.getNumero_cuenta() + " -> " + "+" + cantidad);

        }catch (Exception e){
            cuentaOrigen.setSaldo(cuentaOrigen.getSaldo().add(cantidad));
            System.out.println("ERROR: No se pudo realizar la transacción.");
        }

    }
}