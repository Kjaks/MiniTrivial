import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int eleccion;

        System.out.println("Bienvenido a MiniTrivial elige una opcion:");

        do {
            System.out.println("1. Añadir nuevas preguntas");
            System.out.println("2. Listar todas las preguntas");
            System.out.println("3. Buscar pregunta");
            System.out.println("4. Modificar pregunta");
            System.out.println("5. Borrar pregunta");
            System.out.println("6. Elegir una pregunta al azar");
            System.out.println("7. Salir del programa");
            System.out.print("Seleccione una opción: ");
            eleccion = sc.nextInt();

            switch (eleccion) {
                case 1:
                int tipo = 0;
                char[] pregunta = new char[1024];
                int categoria = 0;
                String texto = "";

                    System.out.println("Hay 3 tipos de pregunta:\n 1.Respuesta simple\n 2.Respuesta multiple\n 3.Respuesta si/no\nIngrese el tipo de pregunta:");
                    do{
                        tipo = sc.nextInt();
                        if(tipo < 1 || tipo > 3) System.out.println("Escriba un numero del 1 al 3!");
                    } while (tipo < 1 || tipo > 3);

                    do{

                        System.out.println("Ingrese la pregunta: ");
                        texto = sc.next();
                        if(texto.length() > 1024) System.out.println("La pregunta no puede tener más de 1024 caracteres!");

                    } while(texto.length() > 1024);

                    for(int i = 0; i < texto.length(); i++){
                        pregunta[i] = texto.charAt(i);
                    }

                    System.out.println("Ingrese la categoría de la pregunta hay 6: ");
                    do{
                        categoria = sc.nextInt();
                        if(tipo < 1 || tipo > 6) System.out.println("Escriba un numero del 1 al 6!");
                    } while(categoria < 1 || categoria > 6);
                    
                    switch(tipo){
                        case 1:
                            System.out.println("1.Respuesta simple\nIngresa la respuesta: ");
                            String respuesta = sc.next();
                            char[] respuestaChar = new char[1024];
                            for(int i = 0; i < respuesta.length(); i++){
                                respuestaChar[i] = respuesta.charAt(i);
                            }

                            break;
                        case 2:
                            System.out.println("2.Respuesta multiple\nIngrese la respuesta correcta: ");
                            String respuestaCorrecta = sc.next();
                            char[] respuestaCorrectaChar = new char[1024];

                            for(int i = 0; i < respuestaCorrecta.length(); i++){
                                respuestaCorrectaChar[i] = respuestaCorrecta.charAt(i);
                            }

                            System.out.println("Ingrese la respuesta incorrecta: ");
                            String respuestaIncorrecta = sc.next();
                            char[] respuestaIncorrectaChar = new char[1024];
                            for(int i = 0; i < respuestaIncorrecta.length(); i++){
                                respuestaIncorrectaChar[i] = respuestaIncorrecta.charAt(i);
                            }
                            
                            break;
                        case 3:
                            System.out.println("Ingrese la respuesta correcta: ");
                            String respuestaCorrectaSN = sc.next();
                            char[] respuestaCorrectaSNChar = new char[1024];
                            for(int i = 0; i < respuestaCorrectaSN.length(); i++){
                                respuestaCorrectaSNChar[i] = respuestaCorrectaSN.charAt(i);
                            }
                            break;
                    }

                    break;
                case 2:
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

    public static void guardarPregunta(String tipo, String pregunta, boolean borrada, String categoria) {
        MiniTrivial mt = new MiniTrivial();

        mt.guardar(null, 0, 0, borrada, null);
    }

}