import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MiniTrivial {
    // Saber cuantas preguntas tenemos disponibles
    private int contador = 0;
    // Esto es una variable de la clase ya que la manejamos con dos metodos
    // sirve para saber cuantas respuestas tiene una pregunta
    private String[] tipoRespuesta = new String[0];

    // Este metodo se encarga de guardar las preguntas en el archivo
     public void guardar(String pregunta, int tipo, int categoria, boolean borrado, String respuesta){
        // Este metodo actualiza el contador
        conteo();

        // Con el StringBuffer podemos darle un tamaño fijo al String que en este caso sera 1024 caracteres
        StringBuffer preguntaFormateada = new StringBuffer(pregunta);
        preguntaFormateada.setLength(1024);
        
        try{    
            RandomAccessFile WriterPreg = new RandomAccessFile("Preguntas.dat","rw");
            WriterPreg.seek(WriterPreg.length());
                // Un int son 4 bytes
                WriterPreg.writeInt(contador);
                // 4 bytes
                WriterPreg.writeInt(tipo);
                // 4 bytes
                WriterPreg.writeInt(categoria);
                // Cadar char son 2 bytes por lo que si mide 1024 caracteres son 2048 bytes
                WriterPreg.writeChars(preguntaFormateada.toString());
                // Cada boolean 1 byte
                WriterPreg.writeBoolean(borrado);

            // Cada registro mide 4+4+4+2048+1 = 2061 bytes
            WriterPreg.close();
  
            RandomAccessFile WriterResp = new RandomAccessFile("Respuestas.dat","rw");

            StringBuffer respuestaFormateada = new StringBuffer(respuesta);
            respuestaFormateada.setLength(1024);

            WriterResp.seek(WriterResp.length());
                // Int 4 bytes
                WriterResp.writeInt(contador);
                // 2048 bytes
                WriterResp.writeChars(respuestaFormateada.toString());

            //Cada registro mide 2048+4 = 2052 bytes
            WriterResp.close();

            contador++;
        }
        catch(Exception e){
            System.err.println("Error al escribir el archivo");
        }
    }

    // Este metodo lee los registros y nos los muestra.
    public String leer(){
        conteo();
        String pregunta = "";
        // Si este boolean es true el metodo no mostrara la pregunta
        boolean borrado;
        try {
                RandomAccessFile ReaderPreg = new RandomAccessFile("Preguntas.dat", "r");
                RandomAccessFile ReaderResp = new RandomAccessFile("Respuestas.dat", "r");

                for(int i = 0; i < contador; i++){
                borrado = false;
            
                // Vamos a la posicion del registro y buscamos el Boolean
                ReaderPreg.seek(i * 2061);

                ReaderPreg.readInt();
                ReaderPreg.readInt();
                ReaderPreg.readInt();
                for (int j = 0; j < 1024; j++){
                    ReaderPreg.readChar();
                }

                borrado = ReaderPreg.readBoolean();
                
                if(borrado == false){
                
                    // Si no esta borrado mostramos la pregunta
                    ReaderPreg.seek(i * 2061);
            
                    pregunta += "\nid: " + ReaderPreg.readInt() + "\n";
            
                    pregunta += "tipo: " + ReaderPreg.readInt() + "\n";
            
                    pregunta += "categoria: " + ReaderPreg.readInt() + "\n";
            
                    pregunta += "pregunta: ";
            
                    for (int j = 0; j < 1024; j++){
                        pregunta += ReaderPreg.readChar();
                    }

                    pregunta += "\n";

                    ReaderResp.seek(i * 2052);
            
                    ReaderResp.readInt();

                    pregunta += "respuesta: ";
                    
                    for (int k = 0; k < 1024; k++){
                        pregunta += ReaderResp.readChar();
                    }

                    pregunta += "\n";

                    for(int j = 0; j < 132; j++){
                        pregunta += "-";
                    }
            }
            else pregunta += "";
                
            }
            ReaderPreg.close();
            ReaderResp.close();  
        } catch (Exception e) {
            System.err.println("Error al leer el archivo");
        }
        return pregunta;
    }

    // Comprobaremos si el archivo existe y dividiremos su longitud entre el tamaño de cada registro para saber cuantos registros hay
    // si el archivo no existe dara 0
    public void conteo() {
    int conteo = 0;
    File archivo = new File("Preguntas.dat");

    if (archivo.exists()) {
        try (RandomAccessFile ReaderPreg = new RandomAccessFile(archivo, "r")) {
            conteo = (int) (ReaderPreg.length() / 2061);
        } catch (Exception e) {
            System.err.print("");
        }
    } else conteo = 0;

    contador = conteo;
    
    }

    // Hace lo mismo que el metodo leer pero solo mostrara la pregunta que se busca y si esta borrado no se muestra
    public String buscar(int id){
        String pregunta = "";
        boolean borrado = false;
        try {
                RandomAccessFile ReaderPreg = new RandomAccessFile("Preguntas.dat", "r");
                RandomAccessFile ReaderResp = new RandomAccessFile("Respuestas.dat", "r");

                ReaderPreg.seek(id * 2061);
        
                pregunta += "\nid: " + ReaderPreg.readInt() + "\n";
        
                ReaderPreg.readInt();
        
                ReaderPreg.readInt();

                pregunta += "pregunta: ";
        
                for (int j = 0; j < 1024; j++){
                    pregunta += ReaderPreg.readChar();
                }

                borrado = ReaderPreg.readBoolean();

                pregunta += "\n";

                ReaderResp.seek(id * 2052);
        
                ReaderResp.readInt();

                pregunta += "respuesta: ";
                
                for (int k = 0; k < 1024; k++){
                    pregunta += ReaderResp.readChar();
                }

                pregunta += "\n";
        
            ReaderPreg.close();
            ReaderResp.close();  
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        if(borrado == true) pregunta = "-1";

        return pregunta;
    }

    // Nos devuelve el contador para saber cuantas preguntas hay
    public int getConteo(){
        // Con este metodo conseguimos actualizar el contador
        conteo();
        return contador;
    }

    // Este metodo se encarga de modificar la pregunta o la respuesta
    public void modificarPregunta(int id,int tipoCambio,String cambioString){
        // El stringbuffer para formatear el String
        StringBuffer cambioStringFormateado = new StringBuffer(cambioString);
        cambioStringFormateado.append("*_");
        cambioStringFormateado.setLength(1024);
        
        // Simplemente busca el registro exacto y se sobreescribe, el tipo de cambio sirve para saber si cambiar la respuesta o la pregunta
        try{
            if(tipoCambio == 1){    
                RandomAccessFile WriterPreg = new RandomAccessFile("Preguntas.dat","rw");
                    WriterPreg.seek(id * 2061);

                    WriterPreg.readInt();

                    WriterPreg.readInt();

                    WriterPreg.readInt();

                    WriterPreg.writeChars(cambioStringFormateado.toString());

                    WriterPreg.close();
            }

            if(tipoCambio == 2){
                RandomAccessFile WriterResp = new RandomAccessFile("Respuestas.dat","rw");
                WriterResp.seek(id * 2052);

                    WriterResp.readInt();

                    WriterResp.writeChars(cambioStringFormateado.toString());
                    WriterResp.close();
            }
            
        } catch (Exception e) {
            System.err.println("Error al leer el archivo"); 
        }
    }

    // Simplemente busca el registro exacto y cambia el boolean a true
    public void borrar(int id){
        try{
            RandomAccessFile WriterPreg = new RandomAccessFile("Preguntas.dat","rw");
                WriterPreg.seek(id * 2061);
                WriterPreg.readInt();
                WriterPreg.readInt();
                WriterPreg.readInt();
                for (int j = 0; j < 1024; j++){
                    WriterPreg.readChar();
                }
                WriterPreg.writeBoolean(true);
                WriterPreg.close();

        } catch (Exception e) {
            System.err.println("Error al leer el archivo"); 
        }
    }

    // Se busca en el registro que tipo de pregunta es y devolvemos un array de Strings dependiendo de si la respuesta es multiple o no
    public String[] tipoRespuesta(int id){
        int tipo;
        try {
            RandomAccessFile ReaderPreg = new RandomAccessFile("Preguntas.dat", "r");
            RandomAccessFile ReaderResp = new RandomAccessFile("Respuestas.dat", "r");

            ReaderPreg.seek(id * 2061);
        
            ReaderPreg.readInt();

            tipo = ReaderPreg.readInt();

            switch(tipo){
                // En la primera posicion del String guardaremos un mensaje diciendonos que tipo de pregunta es y en los demas espacios
                // colocaremos las respuestas en si
                case 1:
                tipoRespuesta = new String[2];

                    tipoRespuesta[0] = "Esta pregunta tiene una sola respuesta";
                    for (int k = 0; k < 1024; k++){
                        char c = (char) ReaderResp.readByte();
                        // El caracter "_" es de separacion
                        if (c != '_') {
                            if(c != '\u0000') tipoRespuesta[1] += c;

                        }
                        else k = 1025;
                        }   
                    break;
                case 2:
                    tipoRespuesta = new String[4];

                    tipoRespuesta[0] = "Esta pregunta tiene 3 posibles respuestas";
                    ReaderResp.seek(id * 2052);
    
                    ReaderResp.readInt();
                    
                    for(int j = 1; j < 4; j++){

                        for (int k = 0; k < 1024; k++){
                            char c = (char) ReaderResp.readByte(); // Leer byte en lugar de char
                            if (c != '_') {
                                if(c != '\u0000') tipoRespuesta[j] += c;

                            }
                            else k = 1025;
                            }
                    }
                    
                    break;
                case 3:
                tipoRespuesta = new String[2];

                    tipoRespuesta[0] = "Esta pregunta es de si o no!";
                    for (int k = 0; k < 1024; k++){
                        char c = (char) ReaderResp.readByte(); // Leer byte en lugar de char
                        if (c != '_') {
                            if(c != '\u0000') tipoRespuesta[1] += c;

                        }
                        else k = 1025;
                        }
                             
                    break;
            }
    
        ReaderPreg.close();
        ReaderResp.close();  
    } catch (Exception e) {
        System.err.println("Error al leer el archivo"); 
    }
    return tipoRespuesta;
    }

    // Este metodo se encarga de comprobar si la respuesta es correcta o no. Convertimos la respuesta del usuario y la 
    // respuesta a la pregunta a minusculas y comprobamos. 
    public int preguntaCorrecta(String respuesta){
        if(tipoRespuesta[1].toLowerCase().equals("null" + respuesta.toLowerCase() + "*")) return 1;
        else return 0; 
    }

    // Este metodo se encarga de devolver un array con las preguntas que pertenecen a una categoria
    public int[] preguntasCategoria(int categoria){
        conteo();
        int[] preguntasCategoria = new int[contador];

        // Llenaremos el array con -1, que significa que en esa posicion no hay preguntas de esa categoria
        for(int i = 0; i < preguntasCategoria.length; i++){
            preguntasCategoria[i] = -1;
        }
        // La unica forma en la que se puede saber cuantas preguntas hay de una categoria es recorriendo el archivo entero
        // en el array apuntaremos la posicion de la pregunta que pertenece a la categoria designada por el usuario
        try{
            RandomAccessFile ReaderPreg = new RandomAccessFile("Preguntas.dat", "r");

            for(int i = 0, j = 0; i < preguntasCategoria.length; i++){
            int id_categoria;
            int id;

            ReaderPreg.seek(i * 2061);
        
            id = ReaderPreg.readInt();

            ReaderPreg.readInt();

            id_categoria = ReaderPreg.readInt();

            // Apuntaremos el id de las preguntas de la categoria designada
            if (id_categoria == categoria) {
                preguntasCategoria[j] = id;
                j++;
            }
        }

        ReaderPreg.close();
        
        } catch (Exception e) {
            System.err.println("Error al leer el archivo"); 
        }

        return preguntasCategoria;
    }
}