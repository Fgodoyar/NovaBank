package org.repositorio;

import org.model.Cliente;
import org.model.Cuenta;
import org.model.Movimiento;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase Repositorio en la que guardaremos los datos
 */
public class Repositorio {
    /**
     * Atributos de la clase.
     */
    public final Map<Long, Cliente> clientes = new HashMap<>();
    public final Map<String, Cuenta> cuentas = new HashMap<>();
    public final Map<Long, Movimiento> movimientos = new HashMap<>();

    /**
     * Método que guarda un cliente a través del objeto cliente.
     * @param cliente
     * @return cliente
     */
    public Cliente guardarCliente(Cliente cliente){
        clientes.put(cliente.getId(), cliente);

        return cliente;
    }

    /**
     * Método para guardar una cuenta a través del objeto cuenta
     * @param cuenta
     * @return cuenta
     */
    public Cuenta guardarCuenta(Cuenta cuenta){
        cuentas.put(cuenta.getNumero_cuenta(), cuenta);

        return cuenta;
    }


}
