package org.ui;

import org.repositorio.Repositorio;
import org.service.GestorCliente;
import org.service.GestorConsultas;
import org.service.GestorCuenta;
import org.service.GestorOperaciones;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Clase Menu.
 * Su principal función es mantener la clase Main limpia y permitir el acceso a sus métodos
 */
public class Menu {
    /**
     * Atributos de la clase
     */
    private static Repositorio repositorio = new Repositorio();
    private static GestorCliente gestorCliente = new GestorCliente(repositorio);
    private static GestorCuenta gestorCuenta = new GestorCuenta(repositorio, gestorCliente);
    private static GestorOperaciones gestorOperaciones = new GestorOperaciones(repositorio, gestorCuenta);
    private static GestorConsultas gestorConsultas = new GestorConsultas(repositorio, gestorCuenta);

    /**
     * Menú de cada parte del proyecto
     */
    private static final String MOSTRARTEXTOMENU = """
            ==================================== 
              NOVABANK - SISTEMA DE OPERACIONES 
            ==================================== 
            1. Gestión de clientes
            2. Gestión de cuentas
            3. Operaciones financieras
            4. Consultas
            5. Salir
            """;
    private static final String MOSTRARTEXTOCLIENTES = """
            --- GESTIÓN DE CLIENTES --- 
            1. Crear cliente 
            2. Buscar cliente 
            3. Listar clientes 
            4. Volver
            """;
    private static final String MOSTRARTEXTOCUENTAS = """
            --- GESTIÓN DE CUENTAS --- 
            1. Crear cuenta 
            2. Listar cuentas de cliente 
            3. Ver información de cuenta 
            4. Volver
            """;
    private static final String BUSQUEDA = """
            --- Seleccione una de las opciones de búsqueda ---
            1. Por ID de Cliente.
            2. Por DNI de Cliente.
            """;
    private static final String MOSTRARTEXTOOPERACIONES = """
            --- OPERACIONES FINANCIERAS --- 
            1. Depositar dinero 
            2. Retirar dinero 
            3. Transferencia entre cuentas 
            4. Volver
            """;
    private static final String MOSTRARTEXTOCONSULTAS = """
            --- CONSULTAS --- 
            1. Consultar saldo 
            2. Historial de movimientos 
            3. Movimientos por rango de fechas 
            4. Volver
            """;

    /**
     * Menú que llama a los métodos de GestorClientes
     */
    public static void menuCliente(){
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do{
            System.out.println(MOSTRARTEXTOCLIENTES);
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion){
                case 1:
                    System.out.println("Introduzca el nombre del cliente: ");
                    String nombre = scanner.nextLine();
                    System.out.println("Introduzca los apellidos del cliente: ");
                    String apellidos = scanner.nextLine();
                    System.out.println("Introduzca el DNI del cliente: ");
                    String dni = scanner.nextLine();
                    System.out.println("Introduzca el email del cliente: ");
                    String email = scanner.nextLine();
                    System.out.println("Introduzca el telefono del cliente: ");
                    String telefono = scanner.nextLine();

                    gestorCliente.insertarCliente(nombre, apellidos, dni, email, telefono);
                    System.out.println(repositorio.clientes.size());
                    break;

                case 2:
                    System.out.println(BUSQUEDA);
                    long busqueda = Long.parseLong(scanner.nextLine());
                    if(busqueda == 1){
                        System.out.println("Introduzca el ID del cliente: ");
                        Long buscarId = Long.parseLong(scanner.nextLine());

                        gestorCliente.mostrarClientePorId(buscarId);
                    }else if (busqueda == 2){
                        System.out.println("Introduzca el DNI del cliente: ");
                        String buscarDni = scanner.nextLine();
                        System.out.println(gestorCliente.buscarClienteDNI(buscarDni));

                    }else {
                        System.out.println("ERROR: Opción incorrecta, por favor, seleccione una opción válida");
                    }
                    break;

                case 3:
                    gestorCliente.listarClientes();
                    break;

                case 4:
                    menuPrincipal();
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

        }while(opcion != 4);
    }

    /**
     * Menú que llama a los métodos de cuenta
     */
    public static void menuCuenta(){
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do{
            System.out.println(MOSTRARTEXTOCUENTAS);
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion){
                case 1:
                    try{
                        System.out.println("Introduce el ID del cliente: ");
                        Long id_cliente = Long.parseLong(scanner.nextLine());
                        gestorCuenta.crearCuenta(id_cliente);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try{
                        System.out.println("Introduce el ID del cliente: ");
                        Long id_cliente = Long.parseLong(scanner.nextLine());
                        gestorCuenta.listarCuentas(id_cliente);

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 3:
                    System.out.println("Introduce el número de la cuenta del cliente: ");
                    String numero_cuenta = scanner.nextLine();
                    gestorCuenta.informacionCuenta(numero_cuenta);
                    break;
                case 4:
                    menuPrincipal();
                default:
                    System.out.println("Opción no válida.");
            }

        }while(opcion != 4);
    }

    /**
     * Menú operaciones, encargado de llamar a los métodos de GestorCuenta
     */
    public static void menuOperaciones(){
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do{
            System.out.println(MOSTRARTEXTOOPERACIONES);
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion){
                case 1:
                    System.out.println("Introduzca su número de cuenta: ");
                    String numeroCuenta = scanner.nextLine();
                    System.out.println("Introduzca la cantidad a depositar: ");
                    BigDecimal cantidad_ingresar = new BigDecimal(scanner.nextLine());
                    gestorOperaciones.depositarDinero(numeroCuenta, cantidad_ingresar);
                    break;
                case 2:
                    System.out.println("Introduzca su número de cuenta: ");
                    String numero_cuenta = scanner.nextLine();
                    System.out.println("Introduzca la cantidad a retirar: ");
                    BigDecimal cantidad_retirar = new BigDecimal(scanner.nextLine());
                    gestorOperaciones.retirarDinero(numero_cuenta, cantidad_retirar);
                    break;
                case 3:
                    System.out.println("Introduzca su número de cuenta: ");
                    String numeroCuentaOrigen = scanner.nextLine();
                    System.out.println("Introduzca el número de cuenta de la persona a la que desea depositar: ");
                    String numeroCuentaDestino = scanner.nextLine();
                    System.out.println("Introduzca la cantidad que desea transferir: ");
                    BigDecimal cantidad_transaccion = new BigDecimal(scanner.nextLine());
                    gestorOperaciones.transaccion(numeroCuentaOrigen, numeroCuentaDestino, cantidad_transaccion);
                    break;
                case 4:
                    menuPrincipal();
                default:
                    System.out.println("Opción no válida.");
            }

            }while(opcion != 4);
    }

    /**
     * Menú consultas, encargado de
     */
    public static void menuConsultas(){
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do{
            System.out.println(MOSTRARTEXTOCONSULTAS);
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion){
                case 1:
                    System.out.println("Introduzca su número de cuenta: ");
                    String numeroCuenta = scanner.nextLine();
                    gestorConsultas.consultarSaldo(numeroCuenta);
                    break;
                case 2:
                    System.out.println("Introduzca su número de cuenta: ");
                    String numero_cuenta = scanner.nextLine();
                    gestorConsultas.historialMovimientos(numero_cuenta);
                    break;
                case 3:
                    System.out.println("Introduzca una fecha de inicio (yyyy-MM-dd): ");
                    LocalDate fechainicio = LocalDate.parse(scanner.nextLine());
                    System.out.println("Introduzca una fecha de fin (yyyy-MM-dd): ");
                    LocalDate fechafin = LocalDate.parse(scanner.nextLine());
                    gestorConsultas.movimientosPorFecha(fechainicio, fechafin);
                    break;
                case 4:
                    menuPrincipal();
                default:
                    System.out.println("Opción no válida.");
            }

        }while (opcion != 4);
    }

    /**
     * Menú principal, redirige a los diversos menús según la opción
     */
    public static void menuPrincipal(){
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do{
            System.out.println(MOSTRARTEXTOMENU);
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion){
                case 1:
                    menuCliente();
                    break;

                case 2:
                    menuCuenta();
                    break;

                case 3:
                    menuOperaciones();
                    break;

                case 4:
                    menuConsultas();
                    break;

                case 5:
                    System.out.println("Bye Bye!");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        }while (opcion != 5);
    }
}
