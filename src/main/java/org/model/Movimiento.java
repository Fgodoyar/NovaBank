package org.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Clase Movimiento
 */
public class Movimiento {
    /**
     * Atributos de la clase
     */
    private static Long contadorMovimiento = 1L;

    private Long id;
    private Long id_cuenta;
    private String tipo;
    private BigDecimal cantidad;
    private LocalDate fecha;

    /**
     * Constructor vacío
     */
    public Movimiento() {
        this.id = contadorMovimiento++;
    }

    /**
     * Constructor de la clase
     * @param id_cuenta
     * @param tipo
     * @param cantidad
     * @param fecha
     */
    public Movimiento(Long id_cuenta, String tipo, BigDecimal cantidad, LocalDate fecha) {
        this.id = contadorMovimiento++;
        this.id_cuenta = id_cuenta;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    /**
     * Obtiene el ID del movimiento.
     * @return ID del movimiento
     */
    public Long getId() {
        return id;
    }

    /**
     * Inserta el ID del movimiento.
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el ID de la cuenta.
     * @return ID de la cuenta
     */
    public Long getId_cuenta() {
        return id_cuenta;
    }

    /**
     * Inserta el ID de la cuenta.
     * @param id_cuenta
     */
    public void setId_cuenta(Long id_cuenta) {
        this.id_cuenta = id_cuenta;
    }

    /**
     * Obtiene el tipo de movimiento.
     * @return tipo de movimiento
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Inserta el tipo de movimiento
     * @param tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la cantidad del movimiento.
     * @return cantidad del movimiento
     */
    public BigDecimal getCantidad() {
        return cantidad;
    }

    /**
     * Inserta la cantidad del movimiento.
     * @param cantidad
     */
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene la fecha en la que se realizó el movimiento.
     * @return fecha de movimiento
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Inserta la fecha del movimiento.
     * @param fecha
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}