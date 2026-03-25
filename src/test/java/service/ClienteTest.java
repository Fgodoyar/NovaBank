package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.model.Cliente;
import org.repositorio.Repositorio;
import org.service.GestorCliente;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteTest {

    @Spy
    private Repositorio repositorio;

    @InjectMocks
    private GestorCliente gestorCliente;

    private String nombre;
    private String apellidos;
    private String dni;
    private String email;
    private String telefono;

    /**
     * Cargamos los datos
     */
    @BeforeEach
    void setUp() {
        nombre = "Pepe";
        apellidos = "Ruiz";
        dni = "12345678A";
        email = "pepe@email.com";
        telefono = "687569033";
    }

    /**
     * Crear cliente correctamente
     */
    @Test
    void insertarClienteCorrecto() {

        gestorCliente.insertarCliente(nombre, apellidos, dni, email, telefono);

        verify(repositorio, times(1)).guardarCliente(any(Cliente.class));
        assertEquals(1, repositorio.clientes.size());
    }

    /**
     * Dejar campos vacíos
     */
    @Test
    void insertarClienteCamposVacios() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> gestorCliente.insertarCliente("", apellidos, dni, email, telefono)
        );

        assertEquals("ERROR: Por favor, rellene todos los campos.", exception.getMessage());
    }

    /**
     * Utilizar un DNI duplicado
     */
    @Test
    void insertarClienteDniDuplicado() {

        gestorCliente.insertarCliente(nombre, apellidos, dni, email, telefono);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> gestorCliente.insertarCliente("Juan", "Lopez", dni, "otro@email.com", "612345678")
        );

        assertEquals("ERROR: El DNI ingresado está en uso.", exception.getMessage());
    }

    /**
     * Usar un email inválido
     */
    @Test
    void insertarClienteEmailInvalido() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> gestorCliente.insertarCliente(nombre, apellidos, dni, "email_invalido", telefono)
        );

        assertEquals("ERROR: El email proporcionado es inválido.", exception.getMessage());
    }

    /**
     * Insertar un teléfono inválido
     */
    @Test
    void insertarClienteTelefonoInvalido() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> gestorCliente.insertarCliente(nombre, apellidos, dni, email, "123")
        );

        assertEquals("ERROR: El teléfono proporcionado es inválido.", exception.getMessage());
    }

    /**
     * Buscar cliente por ID
     */
    @Test
    void buscarClientePorId() {

        gestorCliente.insertarCliente(nombre, apellidos, dni, email, telefono);

        Cliente clienteGuardado = repositorio.clientes.values().iterator().next();
        Cliente encontrado = gestorCliente.buscarClienteID(clienteGuardado.getId());

        assertNotNull(encontrado);
        assertEquals(dni, encontrado.getDni());
    }
}
