package org.service;

import org.model.Cliente;
import org.model.Cuenta;
import org.repositorio.Repositorio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase GestorCuenta, encargada de gestionar los métodos de cuenta
 */
public class GestorCuenta {
    /**
     * Atributos de la clase.
     */
    private Map<String, Cuenta> cuentas;
    private Repositorio repositorio;
    private GestorCliente gestorClientes;
    private static int contador = 0;

    /**
     * Constructor de la clase que usaremos para trasladar los datos de la memoria.
     */
    public GestorCuenta(Repositorio repositorio, GestorCliente gestorClientes) {
        this.repositorio = repositorio;
        this.gestorClientes = gestorClientes;
    }

    /**
     * Obtiene el mapa de las cuentas.
     * @return mapa de cuentas
     */
    public Map<String, Cuenta> getCuentas() {
        return cuentas;
    }

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
     * Método que crea una cuenta bancaria a través deñ id del cliente.
     * En caso de que el cliente no exista en el mapa clientes, saltará un error por consola.
     * @param id_titular
     */
    public void crearCuenta(Long id_titular){
        Cliente cliente = gestorClientes.buscarClienteID(id_titular);
        if(cliente == null){
            throw new IllegalArgumentException("ERROR: No hay clientes registrados");
        }
        Cuenta cuenta = new Cuenta();
        String iban = generarIBAN();
        LocalDate fecha_creacion = LocalDate.now();
        cuenta.setNumero_cuenta(iban);
        cuenta.setCliente_id(cliente.getId());
        cuenta.setTitular(cliente.getNombre() + " " + cliente.getApellidos());
        cuenta.setSaldo(BigDecimal.ZERO);
        cuenta.setFecha_creacion(fecha_creacion);

        repositorio.guardarCuenta(cuenta);
        System.out.println("Cuenta creada correctamente.");
        System.out.println("Número de cuenta: " + cuenta.getNumero_cuenta());

    }

    /**
     * Método que devuelve todas las cuentas de un usuario.
     * Si el ID está registrado, procede a buscar y mostrar las cuentas del cliente.
     * En caso contrario, mostrará un error por consola.
     * @param id_titular
     */
    public void listarCuentas(Long id_titular){
        for(Cuenta cuenta : repositorio.cuentas.values()){
            if (cuenta.getCliente_id().equals(id_titular)){
                System.out.println("Cuentas del cliente: " + cuenta.getTitular() + ":");
                System.out.printf("|%-22s |%-10s|\n", "Número de cuenta", "Saldo");
                System.out.println("|-----------------------|----------|");
                System.out.printf("|%-22s |%-10s|\n", cuenta.getNumero_cuenta(), cuenta.getSaldo());
            }
        }
    }

    public Cuenta buscarCuenta(String numeroCuenta){
        Cuenta cuenta = repositorio.cuentas.get(numeroCuenta);
        return cuenta;
    }

    /**
     * Método que devuelve la información de una cuenta.
     * Si el número de cuenta existe, procede a mostrar los datos.
     * En caso contrario, mostrará un error por consola.
     * @param numeroCuenta
     */
    public void informacionCuenta(String numeroCuenta){
        for(Cuenta cuenta : repositorio.cuentas.values()){
            if (cuenta.getNumero_cuenta().equals(numeroCuenta)){
                System.out.println("Número de cuenta: " + cuenta.getNumero_cuenta());
                System.out.println("Titular: " + cuenta.getTitular());
                System.out.println("Saldo: " + cuenta.getSaldo());
                System.out.println("Fecha de creación: " + cuenta.getFecha_creacion());
            }
        }
    }
}