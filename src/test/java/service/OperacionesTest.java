package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.model.Cuenta;
import org.model.Movimiento;
import org.repositorio.Repositorio;
import org.service.GestorCuenta;
import org.service.GestorOperaciones;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OperacionesTest {

    @Mock
    private Repositorio repositorio;

    @Mock
    private GestorCuenta gestorCuenta;

    @InjectMocks
    private GestorOperaciones gestorOperaciones;

    private Cuenta cuentaOrigen;
    private Cuenta cuentaDestino;

    /**
     * Cargamos los datos antes de iniciar los test
     */
    @BeforeEach
    void setUp() {
        cuentaOrigen = new Cuenta();
        cuentaOrigen.setNumero_cuenta("123");
        cuentaOrigen.setSaldo(new BigDecimal("1000"));

        cuentaDestino = new Cuenta();
        cuentaDestino.setNumero_cuenta("456");
        cuentaDestino.setSaldo(new BigDecimal("500"));
    }

    /**
     * Verificamos que el método deposita el dinero con los datos correctos
     */
    @Test
    void depositarDinero_ok() {
        when(gestorCuenta.buscarCuenta("123")).thenReturn(cuentaOrigen);

        gestorOperaciones.depositarDinero("123", new BigDecimal("200"));

        assertEquals(new BigDecimal("1200"), cuentaOrigen.getSaldo());
        verify(repositorio, times(1)).guardarMovimiento(any(Movimiento.class));
    }

    /**
     * Verificamos que el método no deposita cuando la cuenta ingresada no existe.
     */
    @Test
    void depositarDinero_cuentaNoExiste() {
        when(gestorCuenta.buscarCuenta("999")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
                gestorOperaciones.depositarDinero("999", new BigDecimal("100"))
        );

        verify(repositorio, never()).guardarMovimiento(any());
    }

    /**
     * Comprobamos que el método retirar dinero funciona correctamente.
     */
    @Test
    void retirarDinero_ok() {
        when(gestorCuenta.buscarCuenta("123")).thenReturn(cuentaOrigen);

        gestorOperaciones.retirarDinero("123", new BigDecimal("300"));

        assertEquals(new BigDecimal("700"), cuentaOrigen.getSaldo());
        verify(repositorio, times(1)).guardarMovimiento(any(Movimiento.class));
    }

    /**
     * Comprobamos que al retirar más dinero del que tenemos nos da error
     */
    @Test
    void retirarDinero_saldoInsuficiente() {
        when(gestorCuenta.buscarCuenta("123")).thenReturn(cuentaOrigen);

        assertThrows(IllegalArgumentException.class, () ->
                gestorOperaciones.retirarDinero("123", new BigDecimal("2000"))
        );

        verify(repositorio, never()).guardarMovimiento(any());
    }

    /**
     * Verificamos que el método transacción funciona correctamente
     */
    @Test
    void transaccion_ok() {
        when(gestorCuenta.buscarCuenta("123")).thenReturn(cuentaOrigen);
        when(gestorCuenta.buscarCuenta("456")).thenReturn(cuentaDestino);

        gestorOperaciones.transaccion("123", "456", new BigDecimal("200"));

        assertEquals(new BigDecimal("800"), cuentaOrigen.getSaldo());
        assertEquals(new BigDecimal("700"), cuentaDestino.getSaldo());
        verify(repositorio, times(2)).guardarMovimiento(any(Movimiento.class));

    }

    /**
     * Probamos que no nos permita realizar una transacción a nuestra propia cuenta
     */
    @Test
    void transaccion_mismaCuenta() {
        assertThrows(IllegalArgumentException.class, () ->
                gestorOperaciones.transaccion("123", "123", new BigDecimal("100"))
        );
    }

    /**
     * Comprobamos que no podemos realizar una transacción si ingresamos una cantidad superior a la que tenemos
     */
    @Test
    void transaccion_saldoInsuficiente() {
        when(gestorCuenta.buscarCuenta("123")).thenReturn(cuentaOrigen);
        when(gestorCuenta.buscarCuenta("456")).thenReturn(cuentaDestino);

        assertThrows(IllegalArgumentException.class, () ->
                gestorOperaciones.transaccion("123", "456", new BigDecimal("2000"))
        );
    }
}
