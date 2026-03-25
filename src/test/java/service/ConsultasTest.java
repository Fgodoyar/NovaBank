package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.model.Cuenta;
import org.repositorio.Repositorio;
import org.service.GestorConsultas;
import org.service.GestorCuenta;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultasTest {

    @Mock
    private GestorCuenta gestorCuenta;

    private Repositorio repositorio;

    @InjectMocks
    private GestorConsultas gestorConsultas;

    private Cuenta cuenta;

    /**
     * Cargar datos antes de las pruebas
     */
    @BeforeEach
    void setUp() {
        repositorio = new Repositorio();
        gestorConsultas = new GestorConsultas(repositorio, gestorCuenta);

        cuenta = new Cuenta();
        cuenta.setNumero_cuenta("ES123");
        cuenta.setSaldo(BigDecimal.valueOf(1000.0));
    }

    /**
     * Verificar saldo correcto
     */
    @Test
    void consultarSaldoCorrecto() {
        when(gestorCuenta.buscarCuenta("ES123")).thenReturn(cuenta);

        assertDoesNotThrow(() -> gestorConsultas.consultarSaldo("ES123"));

        verify(gestorCuenta, times(1)).buscarCuenta("ES123");
    }

    /**
     * Verificar una cuenta inexistente
     */
    @Test
    void consultarSaldoCuentaNoExiste() {
        when(gestorCuenta.buscarCuenta("ES999")).thenReturn(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> gestorConsultas.consultarSaldo("ES999")
        );

        assertEquals("ERROR: La cuenta no se ha encontrado.", exception.getMessage());
    }


}
