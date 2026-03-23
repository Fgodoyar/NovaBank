package org.service;

import org.model.Cliente;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GestorCliente {
    private Map<Integer, Cliente> clientes;
    private static final String EMAIL_PATTERN = "\\^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public GestorCliente() {
        this.clientes = new HashMap<>();
    }

    public void insertarCliente(int id, Cliente cliente){
        clientes.put(id, cliente);
    }

    public void insertarCliente(String nombre, String apellidos, String dni, String email,
                                String telefono){
        if (verificarDNI(dni) && verificarEmail(email) && verificarTelefono(telefono)){
            return;
        }

        Cliente cliente = new Cliente();
        LocalDate fecha_creacion = LocalDate.now();
        cliente.setNombre(nombre);
        cliente.setApellidos(apellidos);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);
        cliente.setFecha_creacion(fecha_creacion);

        insertarCliente(cliente.getId(), cliente);
    }

    public boolean verificarTelefono(String telefono){
        for(Cliente cliente : clientes.values()){
            if(cliente.getTelefono().equals(telefono)){
                System.out.println("ERROR: Ya existe un cliente con el teléfono " + telefono + ".");
                return true;
            }
        }
        return  false;
    }

    public boolean verificarEmail(String email){
        for(Cliente cliente : clientes.values()){
            if(cliente.getEmail().equals(email)){
                System.out.println("ERROR: Ya existe un cliente con el Email " + email + ".");
                return true;
            }
        }
        return false;
    }

    public boolean confirmarEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public boolean verificarDNI(String dni) {
        for(Cliente cliente : clientes.values()){
            if(cliente.getDni().equals(dni)){
                System.out.println("ERROR: Ya existe un cliente con el DNI " + dni + ".");
                return true;
            }
        }
        return false;
    }

    public void buscarClienteID(int id){
        Cliente cliente = clientes.get(id);
        if (cliente != null){
            System.out.println("Cliente encontrado: ");
            System.out.println("ID: " + cliente.getId());
            System.out.println("Nombre: " + cliente.getNombre() + cliente.getApellidos());
            System.out.println("DNI: " + cliente.getDni());
            System.out.println("Email: " + cliente.getEmail());
            System.out.println("Teléfono: " + cliente.getTelefono());
        }else {
            System.out.println("ERROR: No se encontró ningún cliente con ID " + id + ".");
        }
    }

    public void buscarClienteDNI(String dni){
        Cliente cliente = clientes.get(dni);
        if(cliente != null){
            System.out.println("Cliente encontrado: ");
            System.out.println("ID: " + cliente.getId());
            System.out.println("Nombre: " + cliente.getNombre() + cliente.getApellidos());
            System.out.println("DNI: " + cliente.getDni());
            System.out.println("Email: " + cliente.getEmail());
            System.out.println("Teléfono: " + cliente.getTelefono());
        }else {
            System.out.println("ERROR: No se encontró ningún cliente con DNI " + dni + ".");
        }
    }

    public void listarClientes(){
        System.out.println("ID | Nombre | DNI | Email | Teléfono");
        System.out.println("-----|------------------|------------|-----------------------------------");
        clientes.forEach((id, cliente) -> {
            System.out.printf("|%2d |%-15s |%-10s |%-20s |%-10s|\n",
                    cliente.getId(), cliente.getNombre() + " " + cliente.getApellidos(),
                    cliente.getDni(), cliente.getEmail(), cliente.getTelefono());
        });
    }
}
