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
            System.out.println();

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
                                respuesta = sc.nextLine() + "*_";
                                if (respuesta.length() > 1024)
                                    System.out.println("La respuesta no puede tener más de 1024 caracteres!");
                            } while (respuesta.length() > 1024);
                            
                            break;
                        case 2:
                        do {
                            System.out.println("2.Respuesta multiple\nIngrese la respuesta correcta: ");
                                respuesta += sc.nextLine() + "*";

                            System.out.println("Ingrese la respuesta incorrecta 1: ");
                                respuesta += "_" + sc.nextLine() + "_";

                            System.out.println("Ingrese la respuesta incorrecta 2: ");
                                respuesta += sc.nextLine() + "_";

                            if(respuesta.length() > 1024) System.out.println("Las respuestas no pueden tener más de 1024 caracteres!");
                        } while (respuesta.length() > 1024);

                            break;
                        case 3:
                            System.out.println("Ingrese la respuesta correcta: ");
                            do {
                                respuesta = sc.nextLine() + "*_";
                                if (!respuesta.equals("si") && !respuesta.equals("no"))
                                    System.out.println("La respuesta debe ser si o no!");
                            } while (!respuesta.equals("si") && !respuesta.equals("no"));                            

                            break;
                    }

                    guardarPregunta(pregunta, tipo, categoria, false, respuesta);

                    break;
                case 2:
                    for (char c : mt.leer().toCharArray()) {
                        if (c == '*' || c == '_') {
                            System.out.print(" ");
                        } else{
                            System.out.print(c);
                        
                        }
                    }
                    break;
                case 3:
                    int contador = mt.getConteo();

                    System.out.println("Ingrese la id de la pregunta que quires buscar (Ingresa un numero del 0 al " + (contador - 1)+ "): ");
                    
                    do {
                        eleccion = sc.nextInt();
                        if (eleccion < 0 || eleccion > (contador - 1))
                            System.out.println("Escriba un numero del 1 al " + (contador - 1) + "!");
                    } while (eleccion < 0 || eleccion > (contador - 1));

                    for (char c : mt.buscar(eleccion).toCharArray()) {
                        if (c == '*' || c == '_') {
                            System.out.print(" ");
                        } else{
                            System.out.print(c);
                        
                        }
                    }

                    break;
                case 4:
                    int id; 

                    System.out.println(mt.leer());
                    System.out.println("Elige el id de la pregunta que quieres modificar: ");

                    do {
                        id = sc.nextInt();
                        if (id < 0 || id > (mt.getConteo() - 1))
                            System.out.println("Escriba un numero del 0 al " + (mt.getConteo() - 1) + "!");
                    } while (id < 0 || id > (mt.getConteo() - 1));

                    System.out.println(mt.buscar(id));

                    System.out.println("Que quieres modificar?\n1. Pregunta\n2. Respuesta\n");
                    do {
                        eleccion = sc.nextInt();
                        if (eleccion < 1 || eleccion > 3)
                            System.out.println("Escriba un numero del 1 al 3!");
                    } while (eleccion < 1 || eleccion > 3);

                    sc.nextLine();

                    switch(eleccion){
                        case 1:
                            System.out.println("Ingrese la nueva pregunta: ");
                            pregunta = sc.nextLine();
                            mt.modificarPregunta(id, eleccion, pregunta);
                            break;

                        case 2:
                            System.out.println("Ingrese la nueva respuesta: ");
                            respuesta = sc.nextLine();
                            mt.modificarPregunta(id, eleccion, respuesta);
                            break;

                    }

                    break;
                case 5: 

                System.out.println(mt.leer());
                System.out.println("Elige el id de la pregunta que quieres borrar: ");

                do {
                    id = sc.nextInt();
                    if (id < 0 || id > (mt.getConteo() - 1))
                        System.out.println("Escriba un numero del 0 al " + (mt.getConteo() - 1) + "!");
                } while (id < 0 || id > (mt.getConteo() - 1));

                mt.borrar(id);

                    break;
                case 6:
                String[] respuestaStrings = new String[0];
                int random;
                int limitador = 0;
                String preguntaAzar = "";
                System.out.println("Pregunta al azar!\n");

                do{
                    random = (int) (Math.random() * mt.getConteo());
                    limitador++;

                    if(limitador > mt.getConteo()){ 
                    System.out.println("No hay preguntas disponibles!");
                    }

                } while(mt.buscar(random).equals("-1") || limitador > mt.getConteo());

                if(random >= 0) {
                    preguntaAzar = mt.buscar(random);
                
                    for(int i = 0,j = 0; i < preguntaAzar.length(); i++){
                        if(preguntaAzar.charAt(i) == '\n') j++;
                        System.out.print(preguntaAzar.charAt(i));
                        if(j == 3) i = preguntaAzar.length() + 1;
                    }

                    respuestaStrings = mt.tipoRespuesta(random);
                }

                System.out.println("\n" + respuestaStrings[0]);

                if(respuestaStrings.length == 4){

                    random = (int) (Math.random() * 3) + 1;

                    for(int i = 4; i < respuestaStrings[random].length(); i++){
                        if(respuestaStrings[random].charAt(i) != '*') System.out.print(respuestaStrings[random].charAt(i));
                    }

                    System.out.print(" ");

                    for(int i = 1; i < 4;i++){
                        if(i != random){
                            for(int j = 4; j < respuestaStrings[i].length(); j++){
                                if(respuestaStrings[i].charAt(j) != '*') System.out.print(respuestaStrings[i].charAt(j));
                            }
                            System.out.print(" ");
                        } 
                    }

                } 

                sc.nextLine();

                System.out.println("\nIntroduce tu respuesta!");
                String respuestaUsuario = sc.nextLine();
                
                int resultado = mt.preguntaCorrecta(respuestaUsuario);

                if(resultado == 1) System.out.println("Respuesta correcta!");
                else System.out.println("Respuesta incorrecta!");

                    
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