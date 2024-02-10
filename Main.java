import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int eleccion;
        MiniTrivial mt = new MiniTrivial();

        System.out.println("Bienvenido a MiniTrivial elige una opcion:");

        do {
            System.out.println("\n1. Añadir nuevas preguntas");
            System.out.println("2. Listar todas las preguntas");
            System.out.println("3. Buscar pregunta");
            System.out.println("4. Modificar pregunta");
            System.out.println("5. Borrar pregunta");
            System.out.println("6. Elegir una pregunta al azar");
            System.out.println("7. Salir del programa");
            System.out.println("Seleccione una opción: ");
            eleccion = sc.nextInt();

            switch (eleccion) {
                case 1:
                    int tipo = 0;
                    String pregunta;
                    int categoria = 0;
                    String texto = "";
                    String respuesta = "";

                    System.out.println("\nHay 3 tipos de pregunta:\n 1.Respuesta simple\n 2.Respuesta multiple\n 3.Respuesta si/no\nIngrese el tipo de pregunta:");
                    do {
                        tipo = sc.nextInt();
                        if (tipo < 1 || tipo > 3)
                            System.out.println("Escriba un numero del 1 al 3!");
                    } while (tipo < 1 || tipo > 3);

                    System.out.println("\nIngrese la categoría de la pregunta hay 6: ");
                    do {
                        categoria = sc.nextInt();
                        if (tipo < 1 || tipo > 6)
                            System.out.println("Escriba un numero del 1 al 6!");
                    } while (categoria < 1 || categoria > 6);

                    sc.nextLine();

                    do {

                        System.out.println("Ingrese la pregunta: ");
                        pregunta = sc.nextLine();
                        if (texto.length() > 1024)
                            System.out.println("La pregunta no puede tener más de 1024 caracteres!");

                    } while (texto.length() > 1024);
                                        
                    switch (tipo) {
                        case 1:
                            System.out.println("1.Respuesta simple\nIngresa la respuesta: ");
                            do {
                                respuesta = sc.nextLine();
                                if (respuesta.length() > 1024)
                                    System.out.println("La respuesta no puede tener más de 1024 caracteres!");
                            } while (respuesta.length() > 1024);
                            
                            break;
                        case 2:
                        do {
                            System.out.println("2.Respuesta multiple\nIngrese la respuesta correcta: ");
                                respuesta += "/" + sc.nextLine() + "*" + "/";

                            System.out.println("Ingrese la respuesta incorrecta 1: ");
                                respuesta += "/" + sc.nextLine() + "/";

                            System.out.println("Ingrese la respuesta incorrecta 2: ");
                                respuesta += "/" + sc.nextLine() + "/";

                            if(respuesta.length() > 1024) System.out.println("Las respuestas no pueden tener más de 1024 caracteres!");
                        } while (respuesta.length() > 1024);

                            break;
                        case 3:
                            System.out.println("Ingrese la respuesta correcta: ");
                            do {
                                respuesta = sc.nextLine();
                                if (!respuesta.equals("si") || !respuesta.equals("no"))
                                    System.out.println("La respuesta debe ser si o no!");
                            } while (!respuesta.equals("si") || !respuesta.equals("no"));

                            break;
                    }

                    guardarPregunta(pregunta, tipo, categoria, false, respuesta);

                    break;
                case 2:
                    System.out.println(mt.leer());
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    System.out.println("¡Adios!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }
        } while (eleccion != 7);
    }

    public static void guardarPregunta(String pregunta, int tipo, int categoria, boolean borrado, String respuesta) {
        MiniTrivial mt = new MiniTrivial();

        mt.guardar(pregunta, tipo, categoria, borrado, respuesta);
    }

}