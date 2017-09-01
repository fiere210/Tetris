
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;

/**
 * Ejemplo para obtener los tipos de ficheros de sonido soportados
 * @author chuidiang
 * http://www.chuidiang.com
 */
public class TiposAudio {

    /**
     * Muestra los tipos de fichero de sonido soportados
     * @param args
     */
    public static void main(String[] args) {
        // Obtenemos los tipos
        AudioFileFormat.Type[] tipos = AudioSystem.getAudioFileTypes();
        
        // y los sacamos por pantalla
        for (AudioFileFormat.Type tipo : tipos)
            System.out.println(tipo.getExtension());
    }

}