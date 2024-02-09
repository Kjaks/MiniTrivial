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
                    char[] pregunta = new char[1024];
                    int categoria = 0;
                    String texto = "";
                    String respuesta = "";
                    String[] respuestas = null;

                    for(int i = 0; i < 1024; i++){
                        pregunta[i] = ' ';
                    }

                    System.out.println("\nHay 3 tipos de pregunta:\n 1.Respuesta simple\n 2.Respuesta multiple\n 3.Respuesta si/no\nIngrese el tipo de pregunta:");
                    do {
                        tipo = sc.nextInt();
                        if (tipo < 1 || tipo > 3)
                            System.out.println("Escriba un numero del 1 al 3!");
                    } while (tipo < 1 || tipo > 3);

                    do {

                        System.out.println("Ingrese la pregunta: ");
                        texto = sc.nextLine();
                        if (texto.length() > 1024)
                            System.out.println("La pregunta no puede tener más de 1024 caracteres!");

                    } while (texto.length() > 1024);

                    sc.nextLine();

                    for (int i = 0; i < texto.length(); i++) {
                        pregunta[i] = texto.charAt(i);
                    }

                    System.out.println("\nIngrese la categoría de la pregunta hay 6: ");
                    do {
                        categoria = sc.nextInt();
                        if (tipo < 1 || tipo > 6)
                            System.out.println("Escriba un numero del 1 al 6!");
                    } while (categoria < 1 || categoria > 6);

                    switch (tipo) {
                        case 1:
                            respuestas = new String[1];
                            System.out.println("1.Respuesta simple\nIngresa la respuesta: ");
                            do {
                                respuesta = sc.next();
                                if (respuesta.length() > 1024)
                                    System.out.println("La respuesta no puede tener más de 1024 caracteres!");
                            } while (respuesta.length() > 1024);

                            respuestas[0] = respuesta;

                            break;
                        case 2:
                            respuestas = new String[3];
                            int aleatorio = (int) (Math.random() * 3);

                            System.out.println("2.Respuesta multiple\nIngrese la respuesta correcta: ");
                            do {
                                respuesta = sc.next() + "*";
                                if (respuesta.length() > 1024)
                                    System.out.println("La respuesta no puede tener más de 1024 caracteres!");

                            } while (respuesta.length() > 1024);

                            respuestas[aleatorio] = respuesta;

                            System.out.println("Ingrese la respuesta incorrecta 1: ");
                            do {
                                respuesta = sc.next();
                                if (respuesta.length() > 1024)
                                    System.out.println("La respuesta no puede tener más de 1024 caracteres!");

                            } while (respuesta.length() > 1024);

                            for (int i = 0; i < 3; i++) {
                                if (respuestas[i] == null) {
                                    respuestas[i] = respuesta;
                                    i = 3;
                                }
                            }

                            System.out.println("Ingrese la respuesta incorrecta 2: ");
                            do {
                                respuesta = sc.next();
                                if (respuesta.length() > 1024)
                                    System.out.println("La respuesta no puede tener más de 1024 caracteres!");

                            } while (respuesta.length() > 1024);

                            for (int i = 0; i < 3; i++) {
                                if (respuestas[i] == null) {
                                    respuestas[i] = respuesta;
                                    i = 3;
                                }
                            }

                            break;
                        case 3:
                            respuestas = new String[1];
                            System.out.println("Ingrese la respuesta correcta: ");
                            do {
                                respuesta = sc.next();
                                if (!respuesta.equals("si") || !respuesta.equals("no"))
                                    System.out.println("La respuesta debe ser si o no!");
                            } while (!respuesta.equals("si") || !respuesta.equals("no"));

                            respuestas[0] = respuesta;
                            break;
                    }

                    guardarPregunta(pregunta, tipo, categoria, false, respuestas);

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

    public static void guardarPregunta(char[] pregunta, int tipo, int categoria, boolean borrado, String[] respuesta) {
        MiniTrivial mt = new MiniTrivial();

        mt.guardar(pregunta, tipo, categoria, borrado, respuesta);
    }

}