package org.ui;

import org.service.GestorCliente;

import java.util.Scanner;

public class Menu {
    private static GestorCliente gestorCliente = new GestorCliente();

    private static final String MOSTRARTEXTOMENU = """
            ==================================== 
              NOVABANK - SISTEMA DE OPERACIONES 
            ==================================== 
            1. Gestión de clientes
            """;
    private static final String MOSTRARTEXTOCLIENTES = """
            --- GESTIÓN DE CLIENTES --- 
            1. Crear cliente 
            2. Buscar cliente 
            3. Listar clientes 
            4. Volver
            """;
    private static final String BUSQUEDA = """
            --- Seleccione una de las opciones de búsqueda ---
            1. Por ID de Cliente.
            2. Por DNI de Cliente.
            """;

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
                    break;

                case 2:
                    System.out.println(BUSQUEDA);
                    long busqueda = Long.parseLong(scanner.nextLine());
                    if(busqueda == 1){
                        System.out.println("Introduzca el ID del cliente: ");
                        long buscarId = Long.parseLong(scanner.nextLine());

                        gestorCliente.buscarClienteID(buscarId);
                    }else if (busqueda == 2){
                        System.out.println("Introduzca el DNI del cliente: ");
                        String buscarDni = scanner.nextLine();

                        gestorCliente.buscarClienteDNI(buscarDni);
                    }else {
                        System.out.println("ERROR: Opción incorrecta, por favor, seleccione una opción válida");
                    }
                    break;

                case 3:
                    gestorCliente.listarClientes();
                    break;

                case 4:
                    menuPrincipal();

            }

        }while(opcion != 4);
    }

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
            }

        }while (opcion != 1);
    }
}
