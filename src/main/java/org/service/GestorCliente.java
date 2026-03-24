package org.service;

import org.model.Cliente;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase gestora de clientes, encargada de implementar los métodos de los clientes.
 * @author fgodoyar
 */
public class GestorCliente {
    /**
     * Map de clientes que guarda en memoria los clientes insertados.
     */
    private Map<Long, Cliente> clientes;

    /**
     * Constructor de la clase
     */
    public GestorCliente() {
        this.clientes = new HashMap<>();
    }

    /**
     * Método principal para insertar datos.
     * @param id
     * @param cliente
     */
    public void insertarCliente(long id, Cliente cliente){
        clientes.put(id, cliente);
    }

    /**
     * Método sobrecargado que incluye la creación de un cliente y por último llama al otro
     * método para guardarlo en el mapa
     * @param nombre
     * @param apellidos
     * @param dni
     * @param email
     * @param telefono
     */
    public void insertarCliente(String nombre, String apellidos, String dni, String email,
                                String telefono){
        if (verificarDNI(dni) || verificarEmail(email) || verificarTelefono(telefono) || !confirmarEmail(email)){
            System.out.println("No se puede crear el usuario.");
            return;
        }
        Cliente cliente = new Cliente();
        LocalDate fecha_creacion = LocalDate.now();
        cliente.setNombre(nombre);
        cliente.setApellidos(apellidos);
        cliente.setDni(dni);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);
        cliente.setFecha_creacion(fecha_creacion);

        insertarCliente(cliente.getId(), cliente);
        System.out.println("Cliente insertado correctamente.");
        System.out.println("ID generado del cliente: " + cliente.getId());
    }

    /**
     * Método que verifica que un teléfono sea único por cliente.
     * @param telefono
     * @return
     */
    public boolean verificarTelefono(String telefono){
        for(Cliente cliente : clientes.values()){
            if(cliente.getTelefono().equals(telefono)){
                return true;
            }
        }
        return false;
    }

    /**
     * Método que verifica que un email es único por cliente
     * @param email
     * @return
     */
    public boolean verificarEmail(String email){
        for(Cliente cliente : clientes.values()){
            if(cliente.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    /**
     * Método que comprueba que un email cumpla con el patrón requerido
     * @param email
     * @return
     */
    public boolean confirmarEmail(String email){
        if(email == null || email.isEmpty()){
            return false;
        }
        String email_pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        return email.matches(email_pattern);
    }

    /**
     * Método que verifica que un DNI sea único por cliente
     * @param dni
     * @return
     */
    public boolean verificarDNI(String dni) {
        for(Cliente cliente : clientes.values()){
            if(cliente.getDni().equals(dni)){
                return true;
            }
        }
        return false;
    }

    /**
     * Método que busca un cliente por id.
     * @param id
     */
    public void buscarClienteID(Long id){
        try{
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
        } catch (Exception e) {
            System.out.println("ERROR: Caracter no válido");;
        }
    }

    /**
     * Método que busca un cliente por dni.
     * @param dni
     */
    public void buscarClienteDNI(String dni){
        for(Cliente cliente : clientes.values()){
            if(cliente.getDni().equals(dni)){
                System.out.println("Cliente encontrado: ");
                System.out.println("ID: " + cliente.getId());
                System.out.println("Nombre: " + cliente.getNombre() + " " + cliente.getApellidos());
                System.out.println("DNI: " + cliente.getDni());
                System.out.println("Email: " + cliente.getEmail());
                System.out.println("Teléfono: " + cliente.getTelefono());
            }
        }
        System.out.println("ERROR: No se encontró ningún cliente con DNI " + dni + ".");
    }

    /**
     * Método para listar todos los clientes dentro de la memoria.
     */
    public void listarClientes(){
        System.out.printf("|%4s |%-15s |%-10s |%-20s |%-10s|\n", "ID", "Nombre", "DNI", "Email", "Teléfono");
        System.out.println("------|----------------|-----------|---------------------|----------|");
        clientes.forEach((id, cliente) -> {
            System.out.printf("|%2d |%-15s |%-10s |%-20s |%-10s|\n",
                    cliente.getId(), cliente.getNombre() + " " + cliente.getApellidos(),
                    cliente.getDni(), cliente.getEmail(), cliente.getTelefono());
        });
    }
}
