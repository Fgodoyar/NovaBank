package org.model;

import java.time.LocalDate;

/**
 * Clase Cliente
 * @author fgodoyar
 */
public class Cliente {
    /**
     * Atributos de la clase.
     */
    private static Long contadorClientes = 1000L;

    private Long id;
    private String nombre;
    private String apellidos;
    private String dni;
    private String email;
    private String telefono;
    private LocalDate fecha_creacion;

    /**
     * Constructor vacío para implementaciones.
     */
    public Cliente(){
        this.id = ++contadorClientes;
    }

    /**
     * Constructor de la clase.
     * @param nombre
     * @param apellidos
     * @param dni
     * @param email
     * @param telefono
     * @param fecha_creacion
     */
    public Cliente(String nombre, String apellidos, String dni, String email, String telefono, LocalDate fecha_creacion) {
        this.id = ++contadorClientes;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.fecha_creacion = fecha_creacion;
    }

    //GETTERS Y SETTERS

    /**
     * Obtiene el id del ciente
     * @return id del cliente
     */
    public Long getId() {
        return id;
    }

    /**
     * Inserta un id
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del cliente
     * @return nombre del cliente
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Inserta el nombre
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los apellidos del cliente
     * @return los apellidos del cliente
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Inserta los apellidos del cliente
     * @param apellidos
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el DNI del cliente
     * @return DNI del cliente
     */
    public String getDni() {
        return dni;
    }

    /**
     * Inserta el DNI del cliente
     * @param dni
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Obtiene el email del cliente
     * @return email del cliente
     */
    public String getEmail() {
        return email;
    }

    /**
     * Inserta el email del cliente
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el teléfono del cliente
     * @return teléfono del cliente
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Inserta el teléfono del cliente
     * @param telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la fecha de creación del cliente
     * @return decha de creación del cliente
     */
    public LocalDate getFecha_creacion() {
        return fecha_creacion;
    }

    /**
     * Inserta la fecha de creación del cliente
     * @param fecha_creacion
     */
    public void setFecha_creacion(LocalDate fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }
}
