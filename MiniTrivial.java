import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MiniTrivial {
    // Saber cuantas preguntas tenemos disponible
    private int contador;
    // Esto es una variable de la clase ya que la manejamos con dos metodos
    // sirve para saber cuantas respuestas tiene una pregunta
    private String[] tipoRespuesta = new String[0];

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
            e.printStackTrace();
        }
    }

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
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        return pregunta;
    }

    public void conteo() {
    // Comprobaremos si el archivo existe y dividiremos su longitud entre el tamaño de cada registro para saber cuantos registros hay
    // si el archivo no existe dara 0
    int conteo = 0;
    File archivo = new File("Preguntas.dat");

    if (archivo.exists()) {
        try (RandomAccessFile ReaderPreg = new RandomAccessFile(archivo, "r")) {
            conteo = (int) (ReaderPreg.length() / 2061);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else conteo = 0;

    contador = conteo;
    
    }

    public String buscar(int id){
        String pregunta = "";
        boolean borrado = false;
        // Lo mismo que el metodo leer pero solo mostrara la pregunta que se busca y si esta borrado no se muestra
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

    public int getConteo(){
        // Con este metodo conseguimos actualizar el contador
        conteo();
        return contador;
    }

    public void modificarPregunta(int id,int tipoCambio,String cambioString){
        // El stringbuffer para formatear el String
        StringBuffer cambioStringFormateado = new StringBuffer(cambioString);
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
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void borrar(int id){
        // Simplemente busca el registro exacto y cambia el boolean a true
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] tipoRespuesta(int id){
        int tipo;
        // Se busca en el registro que tipo de pregunta es y devolvemos un array de Strings dependiendo de si la respuesta es multiple o no
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
    } catch (IOException e) {
        e.printStackTrace(); 
    }
    return tipoRespuesta;
    }

    public int preguntaCorrecta(String respuesta){
        // Este metodo se encarga de comprobar si la respuesta es correcta o no. Convertimos la respuesta del usuario y la 
        // respuesta a la pregunta a minusculas y comprobamos. 
        if(tipoRespuesta[1].toLowerCase().equals("null" + respuesta.toLowerCase() + "*")) return 1;
        else return 0; 
    }
}
