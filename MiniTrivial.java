import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MiniTrivial {
    private int contador;

     public void guardar(String pregunta, int tipo, int categoria, boolean borrado, String respuesta){
        conteo();
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
        try {
                RandomAccessFile ReaderPreg = new RandomAccessFile("Preguntas.dat", "r");
                RandomAccessFile ReaderResp = new RandomAccessFile("Respuestas.dat", "r");

                for(int i = 0; i < contador; i++){

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
            ReaderPreg.close();
            ReaderResp.close();  
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        return pregunta;
    }

    public void conteo() {
    int conteo = 0;
    File archivo = new File("Preguntas.dat");

    if (archivo.exists()) {
        try (RandomAccessFile ReaderPreg = new RandomAccessFile(archivo, "r")) {
            conteo = (int) (ReaderPreg.length() / 2061);
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    contador = conteo;
    
    }

    public String buscar(int id){
        String pregunta = "";
        try {
                RandomAccessFile ReaderPreg = new RandomAccessFile("Preguntas.dat", "r");
                RandomAccessFile ReaderResp = new RandomAccessFile("Respuestas.dat", "r");

                ReaderPreg.seek(id * 2061);
        
                pregunta += "\nid: " + ReaderPreg.readInt() + "\n";
        
                ReaderPreg.readInt();
        
                ReaderPreg.readInt();
                
                ReaderPreg.seek(id * 2061);

                pregunta += "pregunta: ";
        
                for (int j = 0; j < 1024; j++){
                    pregunta += ReaderPreg.readChar();
                }

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
        return pregunta;
    }

    public int getConteo(){
        conteo();
        return contador;
    }
    
}