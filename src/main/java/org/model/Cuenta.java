package org.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Clase Cuenta
 * @author fgodoyar
 */
public class Cuenta {
    /**
     * Atributos de la clase
     */
    private static long contadorCuentas = 1L;

    private long id;
    private String numero_cuenta;
    private String titular;
    private long cliente_id;
    private BigDecimal saldo;
    private LocalDate fecha_creacion;

    /**
     * Constructor vacío de Cuenta.
     */
    public Cuenta(){
        this.id = contadorCuentas++;
    }

    /**
     * Constructor de la clase
     * @param id
     * @param numero_cuenta
     * @param titular
     * @param cliente_id
     * @param saldo
     * @param fecha_creacion
     */
    public Cuenta(long id, String numero_cuenta, String titular, long cliente_id, BigDecimal saldo, LocalDate fecha_creacion) {
        this.id = contadorCuentas++;
        this.numero_cuenta = numero_cuenta;
        this.titular = titular;
        this.cliente_id = cliente_id;
        this.saldo = BigDecimal.ZERO;
        this.fecha_creacion = fecha_creacion;
    }

    /**
     * Obtiene el ID de la cuenta.
     * @return id de la cuenta
     */
    public long getId() {
        return id;
    }

    /**
     * Inserta el ID de una cuenta.
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Obtiene el número de la cuenta.
     * @return número de cuenta
     */
    public String getNumero_cuenta() {
        return numero_cuenta;
    }

    /**
     * Inserta un número de cuenta.
     * @param numero_cuenta
     */
    public void setNumero_cuenta(String numero_cuenta) {
        this.numero_cuenta = numero_cuenta;
    }

    /**
     * Obtiene el titular de la cuenta.
     * @return titular de la cuenta
     */
    public String getTitular() {
        return titular;
    }

    /**
     * Inserta el titular de la cuenta.
     * @param titular
     */
    public void setTitular(String titular) {
        this.titular = titular;
    }

    /**
     * Obtiene el ID del cliente.
     * @return
     */
    public long getCliente_id() {
        return cliente_id;
    }

    /**
     * Inserta el ID del cliente.
     * @param cliente_id
     */
    public void setCliente_id(long cliente_id) {
        this.cliente_id = cliente_id;
    }

    /**
     * Obtener el saldo de la cuenta.
     * @return saldo de la cuenta
     */
    public BigDecimal getSaldo() {
        return saldo;
    }

    /**
     * Insertar el saldo de la cuenta.
     * @param saldo
     */
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    /**
     * Obtiene la fecha de creación de la cuenta.
     * @return fecha de creación de la cuenta
     */
    public LocalDate getFecha_creacion() {
        return fecha_creacion;
    }

    /**
     * Inserta la fecha de creación de la cuenta.
     * @param fecha_creacion
     */
    public void setFecha_creacion(LocalDate fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }
}
