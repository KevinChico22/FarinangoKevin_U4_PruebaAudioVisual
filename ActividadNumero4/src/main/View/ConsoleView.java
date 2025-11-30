package View;


import Controller.ContentController;
import Model.*;
import Repository.FileContentRepository;
import Service.ContentService;

import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class ConsoleView {

    private final Scanner scanner = new Scanner(System.in);
    private final ContentController controller;

    public ConsoleView() {
        this.controller = new ContentController(new ContentService(
                new FileContentRepository(Paths.get("src/main/resources/contents.csv"))
        ));
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("     Sistema de Contenido Audio Visual  ");
            System.out.println("1. Listar contenidos");
            System.out.println("2. Agregar contenido manualmente");
            System.out.println("3. Guardar y salir");
            System.out.println("4. Eliminar todo el contenido");
            System.out.println("0. Salir sin guardar el contenido");
            System.out.print("Ingrese su opcion de preferencia: ");
            String opt = scanner.nextLine();

            switch (opt) {
                case "1":
                    listar();
                    break;
                case "2":
                    agregarManual();
                    break;
                case "3":
                    System.out.println("Guardando y saliendo...");
                    running = false;
                    break;
                case "4":            // NUEVO CASE
                    eliminarContenido();
                    break;
                case "0":
                    System.out.println("Saliendo sin guardar...");
                    running = false;
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }
        scanner.close();
    }

    private void listar() {
        List<ContenidoAudioVisual> contenidos = controller.listar();
        if (contenidos.isEmpty()) {
            System.out.println("No hay contenidos registrados.");
            return;
        }
        int i = 1;
        for (ContenidoAudioVisual c : contenidos) {
            System.out.printf("%d. %s - %s%n", i++, c.getTitulo(), c.getClass().getSimpleName());
        }
    }

    private void agregarManual() {
        System.out.print("Tipo (Pelicula/Serie/Documental): ");
        String tipo = scanner.nextLine().trim();
        System.out.print("Título: ");
        String titulo = scanner.nextLine().trim();
        System.out.print("Año: ");
        int anio = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Director: ");
        String director = scanner.nextLine().trim();

        ContenidoAudioVisual c;

        switch (tipo.toLowerCase()) {
            case "pelicula":
                c = new Pelicula(titulo, anio, director);
                break;
            case "serie":
                c = new SerieTV(titulo, anio, director);
                break;
            case "documental":
                c = new Documental(titulo, anio, director);
                break;
            default:
                System.out.println("Tipo no reconocido. Se registrará como Documento genérico.");
                c = new Documental(titulo, anio, director);
                break;
        }

        controller.agregar(c);
        System.out.println("Contenido agregado correctamente.");
    }

    // MÉTODO NUEVO – ELIMINAR CONTENIDO
    private void eliminarContenido() {
        List<ContenidoAudioVisual> contenidos = controller.listar();
        if (contenidos.isEmpty()) {
            System.out.println("No hay contenidos para eliminar.");
            return;
        }

        System.out.println("Seleccione el número a eliminar:");
        for (int i = 0; i < contenidos.size(); i++) {
            System.out.printf("%d. %s (%s)%n",
                    i + 1, contenidos.get(i).getTitulo(), contenidos.get(i).getClass().getSimpleName());
        }

        try {
            System.out.print("Ingrese número: ");
            int index = Integer.parseInt(scanner.nextLine()) - 1;

            if (index < 0 || index >= contenidos.size()) {
                System.out.println("Número inválido.");
                return;
            }

            contenidos.remove(index);
            controller.actualizarLista(contenidos);
            System.out.println("Contenido eliminado.");

        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
    }
}
