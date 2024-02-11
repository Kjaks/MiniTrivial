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
                // Añadir pregunta
                case 1:
                    int tipo = 0;
                    String pregunta;
                    int categoria = 0;
                    String texto = "";
                    String respuesta = "";

                    // En esta parte se elige el tipo de pregunta
                    System.out.println("\nHay 3 tipos de pregunta:\n 1.Respuesta simple\n 2.Respuesta multiple\n 3.Respuesta si/no\nIngrese el tipo de pregunta:");
                    do {
                        tipo = sc.nextInt();
                        if (tipo < 1 || tipo > 3)
                            System.out.println("Escriba un numero del 1 al 3!");
                    } while (tipo < 1 || tipo > 3);

                    // Aqui se puede elegir la categoria de la pregunta
                    System.out.println("\nIngrese la categoría de la pregunta hay 6: ");
                    do {
                        categoria = sc.nextInt();
                        if (categoria < 1 || categoria > 3)
                            System.out.println("Escriba un numero del 1 al 3!");
                    } while (categoria < 1 || categoria > 3);

                    sc.nextLine();

                    // Aqui se escribe la pregunta en si
                    do {
                        System.out.println("Ingrese la pregunta: ");
                        pregunta = sc.nextLine();
                        if (texto.length() > 1024)
                            System.out.println("La pregunta no puede tener más de 1024 caracteres!");

                    } while (texto.length() > 1024);
                                        
                    switch (tipo) {
                        // Aqui pondremos las respuestas la respuesta que tenga el astericos "*" sera la correcta
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

                    // Insertamos la marca de que no esta borrado
                    guardarPregunta(pregunta, tipo, categoria, false, respuesta);

                    break;
                // Ver las preguntas

                case 2:
                // Aqui se muestran todas las preguntas formateadas.
                    showTable(mt.leer());
                    break;
                // Buscar pregunta
                case 3:
                    // Conseguims el numero de preguntas que hay
                    int contador = mt.getConteo();

                    System.out.println("Ingrese la id de la pregunta que quires buscar (Ingresa un numero del 0 al " + (contador - 1)+ "): ");
                    
                    do {
                        eleccion = sc.nextInt();
                        if (eleccion < 0 || eleccion > (contador - 1))
                            System.out.println("Escriba un numero del 1 al " + (contador - 1) + "!");
                    } while (eleccion < 0 || eleccion > (contador - 1));

                    // Una vez elegida sale la pregunta formateada
                    showTable(mt.buscar(eleccion));

                    break;
                // Modificar pregunta
                case 4:
                    int id; 

                    showTable(mt.leer());
                    System.out.println("Elige el id de la pregunta que quieres modificar: ");

                    // Buscamos por id
                    do {
                        id = sc.nextInt();
                        if (id < 0 || id > (mt.getConteo() - 1))
                            System.out.println("Escriba un numero del 0 al " + (mt.getConteo() - 1) + "!");
                    } while (id < 0 || id > (mt.getConteo() - 1));

                    showTable(mt.buscar(id));

                    // Preguntamos que quiere modificar
                    System.out.println("Que quieres modificar?\n1. Pregunta\n2. Respuesta\n");
                    do {
                        eleccion = sc.nextInt();
                        if (eleccion < 1 || eleccion > 3)
                            System.out.println("Escriba un numero del 1 al 3!");
                    } while (eleccion < 1 || eleccion > 3);

                    // Limpiamos el buffer
                    sc.nextLine();

                    // Modificamos la pregunta o respuesta aqui
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
                // Borrar pregunta
                case 5: 

                showTable(mt.leer());
                System.out.println("Elige el id de la pregunta que quieres borrar: ");

                do {
                    id = sc.nextInt();
                    if (id < 0 || id > (mt.getConteo() - 1))
                        System.out.println("Escriba un numero del 0 al " + (mt.getConteo() - 1) + "!");
                } while (id < 0 || id > (mt.getConteo() - 1));

                mt.borrar(id);

                    break;
                // Pregunta al azar
                case 6:
                String[] respuestaStrings = new String[0];
                int random;
                // El limitador sigue una logica para que si no hay preguntas disponibles lance un mensaje de que no hay preguntas
                int limitador = 0;
                String preguntaAzar = "";
                System.out.println("Pregunta al azar!\n");

                do{
                    // Elegimos un numero random para escoger una pregunta
                    random = (int) (Math.random() * mt.getConteo());
                    limitador++;

                    if(limitador > mt.getConteo()){ 
                    System.out.println("No hay preguntas disponibles!");
                    }

                } while(mt.buscar(random).equals("-1") || limitador > mt.getConteo());

                // Si todo sale bien mostramos la pregunta
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

                // Lo que conseguimos aqui es que en las preguntas de respuesta multiple se muestren las respuestas de manera aleatoria
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

                // Limpiado de Buffer
                sc.nextLine();

                // Introducimos la respuesta y si sale 1 significa que hemos acertado.
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

    // Guardamos la pregunta
    public static void guardarPregunta(String pregunta, int tipo, int categoria, boolean borrado, String respuesta) {
        MiniTrivial mt = new MiniTrivial();

        mt.guardar(pregunta, tipo, categoria, borrado, respuesta);
    }

    public static void showTable(String tabla){
        // Formateamos la tabla para que no contenga los caracteres "*" o "_"
        for (char c : tabla.toCharArray()) {
            if (c == '*' || c == '_') {
                System.out.print(" ");
            } else{
                System.out.print(c);                    
            }
        }
    }
}