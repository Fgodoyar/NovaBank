package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.model.Cliente;
import org.model.Cuenta;
import org.repositorio.Repositorio;
import org.service.GestorCliente;
import org.service.GestorCuenta;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CuentaTest {

    private Repositorio repositorio;

    @Mock
    private GestorCliente gestorCliente;

    @InjectMocks
    private GestorCuenta gestorCuenta;

    private Cliente cliente;

    /**
     * Cargar datos antes de las pruebas
     */
    @BeforeEach
    void setUp() {
        repositorio = new Repositorio();
        gestorCuenta = new GestorCuenta(repositorio, gestorCliente);

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Pepe");
        cliente.setApellidos("Ruiz");
    }

    /**
     * Comprobar que se genera el IBAN correctamnete
     */
    @Test
    void generarIBANCorrecto() {
        String iban1 = gestorCuenta.generarIBAN();
        String iban2 = gestorCuenta.generarIBAN();

        assertNotNull(iban1);
        assertNotEquals(iban1, iban2);
        assertTrue(iban1.startsWith("ES91210000"));
    }

    /**
     * Verificar que se puede crear una cuenta correctamente.
     */
    @Test
    void crearCuentaCorrecto() {
        when(gestorCliente.buscarClienteID(1L)).thenReturn(cliente);

        gestorCuenta.crearCuenta(1L);

        assertEquals(1, repositorio.cuentas.size());

        Cuenta cuenta = repositorio.cuentas.values().iterator().next();
        assertEquals(cliente.getId(), cuenta.getCliente_id());
        assertEquals(BigDecimal.ZERO, cuenta.getSaldo());

        verify(gestorCliente, times(1)).buscarClienteID(1L);
    }

    /**
     * Verificar que no puedes crear una cuenta si no existe el cliente
     */
    @Test
    void crearCuentaClienteNoExiste() {
        when(gestorCliente.buscarClienteID(99L)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> gestorCuenta.crearCuenta(99L)
        );

        assertEquals("ERROR: No hay clientes registrados", exception.getMessage());
    }

    /**
     * Comprobar que usca una cuenta que existe
     */
    @Test
    void buscarCuentaCorrecto() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumero_cuenta("ES123");

        repositorio.cuentas.put("ES123", cuenta);

        Cuenta encontrada = gestorCuenta.buscarCuenta("ES123");

        assertNotNull(encontrada);
        assertEquals("ES123", encontrada.getNumero_cuenta());
    }

    /**
     * Comprobar información correcta
     */
    @Test
    void informacionCuentaExistente() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumero_cuenta("ES123");
        cuenta.setTitular("Pepe Ruiz");
        cuenta.setSaldo(BigDecimal.TEN);

        repositorio.cuentas.put("ES123", cuenta);

        assertDoesNotThrow(() -> gestorCuenta.informacionCuenta("ES123"));
    }
}
