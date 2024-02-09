import java.io.RandomAccessFile;

public class MiniTrivial {
    private int contador = 0;

     public void guardar(char[] pregunta, int tipo, int categoria, boolean borrado, char[] respuesta){
        
        try{    
            RandomAccessFile WriterPreg = new RandomAccessFile("Preguntas.dat","rw");
            
                WriterPreg.write(contador);

                WriterPreg.write(tipo);

                WriterPreg.write(categoria);

                for(int i = 0; i < pregunta.length; i++){
                    WriterPreg.writeChar(pregunta[i]);
                }

                WriterPreg.writeBoolean(borrado);
                
            WriterPreg.close();

            RandomAccessFile WriterResp = new RandomAccessFile("Respuestas.dat","rw");      
            
                WriterResp.write(contador);

                for(int i = 0; i < respuesta.length; i++){
                    WriterResp.writeChar(respuesta[i]);
                }

            WriterResp.close();

            contador++;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public String leer(){
        String pregunta = "";
        try{
            RandomAccessFile ReaderPreg = new RandomAccessFile("Preguntas.dat","r");
            int tipo = ReaderPreg.readInt();
            int categoria = ReaderPreg.readInt();
            for(int i = 0; i < 1024; i++){
                if(ReaderPreg.readChar() == ' ') i = 1025;
                pregunta += ReaderPreg.readChar();
            }
            boolean borrado = ReaderPreg.readBoolean();
            ReaderPreg.close();

        }
        catch(Exception e){
            e.printStackTrace();
            return "Error";
        }

        return pregunta;
    }
}