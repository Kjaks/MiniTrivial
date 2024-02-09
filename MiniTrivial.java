import java.io.RandomAccessFile;

public class MiniTrivial {
    private int contador = 1;
    private int busqueda = 0;

     public void guardar(char[] pregunta, int tipo, int categoria, boolean borrado, String[] respuesta){
        
        try{    
            RandomAccessFile WriterPreg = new RandomAccessFile("Preguntas.dat","rw");
                WriterPreg.seek(contador * 0);
                WriterPreg.writeInt(contador);

                WriterPreg.seek(contador * 4);
                WriterPreg.writeInt(tipo);

                WriterPreg.seek(contador * 8);
                WriterPreg.writeInt(categoria);

                if(contador == 1) busqueda = 12;
                else busqueda = 1036;

                for(int i = 0; i < 1024; i++){
                    WriterPreg.seek(contador * busqueda + i);
                    WriterPreg.writeChar(pregunta[i]);
                }

                WriterPreg.seek(contador * 1036);
                WriterPreg.writeBoolean(borrado);
                
            WriterPreg.close();

            RandomAccessFile WriterResp = new RandomAccessFile("Respuestas.dat","rw");      
            
                WriterResp.seek(contador * 0);
                WriterResp.write(contador);

                WriterResp.seek(contador * 12);
                for(int i = 0; i < respuesta.length; i++){
                    for(int j = 0; j < 1024; j++){
                        if(respuesta[i] != null && respuesta[i].charAt(j) != '\u0000') WriterResp.writeChar(respuesta[i].charAt(j));
                    }
                    if(respuesta.length > 1) WriterResp.writeChar('/');
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
        System.out.println(contador);
        try{
            RandomAccessFile ReaderPreg = new RandomAccessFile("Preguntas.dat","r");
            ReaderPreg.seek(contador * 0);

            pregunta += "id: " + ReaderPreg.readInt() + " ";

            ReaderPreg.seek(contador * 4);
            pregunta += " tipo: " + ReaderPreg.readInt() + " ";

            ReaderPreg.seek(contador * 8);
            pregunta += " categoria: " + ReaderPreg.readInt() + " ";
            
            pregunta += " pregunta: ";

            if(contador == 1) busqueda = 12;
            else busqueda = 1036;

            for(int i = 0; i < 1024; i++){
                if(ReaderPreg.readChar() == ' ') i = 1025;

                ReaderPreg.seek(contador * busqueda + i);
                pregunta += ReaderPreg.readChar();
            }

            ReaderPreg.close();

        }
        catch(Exception e){
            e.printStackTrace();
            return "Error";
        }

        return pregunta;
    }
}