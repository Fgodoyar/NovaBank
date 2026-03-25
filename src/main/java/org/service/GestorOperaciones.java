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
     * Valida que el número esté registrado y que la cantidad sea mayor que 0.
     * Por último, guarda el movimiento.
     * En caso contrario, imprime un error por pantalla.
     * @param numeroCuenta
     * @param cantidad
     */
    public void depositarDinero(String numeroCuenta, BigDecimal cantidad){
        for(Cuenta cuenta : repositorio.cuentas.values()){
            if (!cuenta.getNumero_cuenta().equals(numeroCuenta)){
                System.out.println("ERROR: No se ha encontrado el número de cuenta en la base de datos.");
            }
            if(!(cantidad.compareTo(BigDecimal.ZERO) > 0)){
                System.out.println("ERROR: La cantidad debe ser superior a 0 para poder depositar.");
            }
            BigDecimal nuevoSueldo = cuenta.getSaldo().add(cantidad);
            cuenta.setSaldo(nuevoSueldo);
            Movimiento deposito = new Movimiento(cuenta.getId(), "DEPÓSITO", cantidad.plus(), LocalDate.now());
            repositorio.movimientos.put(deposito.getId(), deposito);
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
        for(Cuenta cuenta : repositorio.cuentas.values()){
            if(!cuenta.getNumero_cuenta().equals(numeroCuenta)){
                System.out.println("ERROR: No se ha encontrado el número de cuenta en la base de datos.");
            }
            if(!(cantidad.compareTo(BigDecimal.ZERO) > 0)){
                System.out.println("ERROR: La cantidad debe ser superior a 0 para poder retirar.");
            }
            if(!(cuenta.getSaldo().compareTo(cantidad) > 0)){
                System.out.println("ERROR: Saldo insuficiente.");
            }
            BigDecimal nuevoSueldo = cuenta.getSaldo().subtract(cantidad);
            cuenta.setSaldo(nuevoSueldo);
            Movimiento retirar = new Movimiento(cuenta.getId(), "RETIRO", cantidad.negate(), LocalDate.now());
            repositorio.movimientos.put(retirar.getId(), retirar);
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
        for(Cuenta cuenta : repositorio.cuentas.values()){
            if(numeroCuentaOrigen.equals(numeroCuentaDestino)){
                System.out.println("ERROR: No se puede transferir dinero sobre la misma cuenta.");
            }
            if(!cuenta.getNumero_cuenta().equals(numeroCuentaOrigen) || !cuenta.getNumero_cuenta().equals(numeroCuentaDestino)){
                System.out.println("ERROR: No se ha encontrado el número de cuenta en la base de datos.");
            }
            if (!(cantidad.compareTo(BigDecimal.ZERO) > 0)){
                System.out.println("ERROR: La cantidad debe ser mayor que 0.");
            }
            if(!(cuenta.getSaldo().compareTo(cantidad) > 0)){
                System.out.println("ERROR: Saldo insuficiente.");
            }
            Cuenta cuentaOrigen = gestorCuenta.getCuentas().get(numeroCuentaOrigen);
            Cuenta cuentaDestino = gestorCuenta.getCuentas().get(numeroCuentaDestino);

            retirarDinero(cuentaOrigen.getNumero_cuenta(), cantidad);
            depositarDinero(cuentaDestino.getNumero_cuenta(), cantidad);
            Movimiento transferencia_saliente = new Movimiento(cuentaOrigen.getId(), "TRANSFERENCIA_SALIENTE", cantidad.negate(), LocalDate.now());
            repositorio.movimientos.put(transferencia_saliente.getId(), transferencia_saliente);
            Movimiento transferencia_entrante = new Movimiento(cuentaDestino.getId(), "TRANSFERENCIA_ENTRANTE", cantidad.plus(), LocalDate.now());
            repositorio.movimientos.put(transferencia_entrante.getId(), transferencia_entrante);

            System.out.println("Transferencia realizada correctamente.");
            System.out.println("Cuenta Origen: " + cuentaOrigen.getNumero_cuenta() + " -> " + cantidad.negate());
            System.out.println("Cuenta Destino: " + cuentaDestino.getNumero_cuenta() + " -> " + cantidad.plus());
        }
    }
}