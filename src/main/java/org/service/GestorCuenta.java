package org.service;

import org.model.Cuenta;

import java.util.Map;

/**
 * Clase GestorCuenta, encargada de gestionar los métodos de cuenta
 */
public class GestorCuenta {
    /**
     * Atributos de la clase.
     */
    private Map<String, Cuenta> cuentas;
    private static int contador = 0;

    /**
     * Constructor vacío
     */
    public GestorCuenta() {}

    /**
     * Método que se encarga de generar un IBAN
     * La variable número se desplaza 12 ceros hacia la izquierda y con el contador se incrementa
     * @return el número de cuenta automátizado.
     */
    public String generarIBAN(){
        String numero = String.format("%012d", contador);
        contador++;

        String iban = "ES91210000" + numero;

        return iban;
    }

    /**
     * Método que devuelve todas las cuentas de un usuario.
     * Si el ID está registrado, procede a buscar y mostrar las cuentas del cliente.
     * En caso contrario, mostrará un error por consola.
     * @param id_titular
     */
    public void listarCuentas(int id_titular){
        for(Cuenta cuenta : cuentas.values()){
            if (cuenta.getCliente_id() == id_titular){
                System.out.println("Cuentas del cliente: " + cuenta.getTitular() + ":");
                System.out.printf("|%-22s |%-10s|\n", "Número de cuenta", "Saldo");
                System.out.println("|--------------------|-----------|");
                System.out.printf("|%-22s |%-10s|\n", cuenta.getNumero_cuenta(), cuenta.getSaldo());
            }else {
                System.out.println("ERROR: El ID no está registrado en la base de datos.");
            }
        }
    }

    /**
     * Método que devuelve la información de una cuenta.
     * Si el número de cuenta existe, procede a mostrar los datos.
     * En caso contrario, mostrará un error por consola.
     * @param numeroCuenta
     */
    public void informacionCuenta(String numeroCuenta){
        for(Cuenta cuenta : cuentas.values()){
            if (cuenta.getNumero_cuenta().equals(numeroCuenta)){
                System.out.println("Número de cuenta: " + cuenta.getNumero_cuenta());
                System.out.println("Titular: " + cuenta.getTitular());
                System.out.println("Saldo: " + cuenta.getSaldo());
                System.out.println("Fecha de creación: " + cuenta.getFecha_creacion());
            }else {
                System.out.println("El número no está registrado en la base de datos.");
            }
        }
    }
}
