import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MiniTrivial {
    private int contador = conteo();

     public void guardar(String pregunta, int tipo, int categoria, boolean borrado, String respuesta){
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
            preguntaFormateada.setLength(1024);

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
        String pregunta = "";
        try {
            RandomAccessFile ReaderPreg = new RandomAccessFile("Preguntas.dat", "r");
            ReaderPreg.seek(0);
    
            pregunta += "id: " + ReaderPreg.readInt() + "\n";
    
            pregunta += "tipo: " + ReaderPreg.readInt() + "\n";
    
            pregunta += "categoria: " + ReaderPreg.readInt() + "\n";
    
            pregunta += "pregunta: ";
    
            for (int i = 0; i < 1024; i++){
                pregunta += ReaderPreg.readChar();
            }

            pregunta += "\n";
            System.out.println(ReaderPreg.length());

            ReaderPreg.close();

            RandomAccessFile ReaderResp = new RandomAccessFile("Respuestas.dat", "r");
            ReaderResp.seek(0);
    
            pregunta += "respuesta: ";
            for (int i = 0; i < 1024; i++){
                pregunta += ReaderResp.readChar();
            }
    
            ReaderResp.close();  
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        return pregunta;
    }

    public int conteo() {
    int conteo = 0;
    File archivo = new File("Preguntas.dat");

    if (archivo.exists()) {
        try (RandomAccessFile ReaderPreg = new RandomAccessFile(archivo, "r")) {
            conteo = (int) (ReaderPreg.length() / 2061);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    return conteo;
}

}