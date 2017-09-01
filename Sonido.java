import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;

public class Sonido{
	private Clip clip;
	private AudioInputStream sonidoActual;

	public Sonido(){
		try{
			clip = AudioSystem.getClip();
			sonidoActual = AudioSystem.getAudioInputStream(new File("TetrisTheme.wav"));
			clip.open(sonidoActual);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}catch(Exception e){
			System.out.println("Ha ocurrido un error. No se puede reproducir el sonido");
		}	
	}
	
	public void reiniciar(){
		try{
			clip.stop();
			sonidoActual = AudioSystem.getAudioInputStream(new File("TetrisTheme.wav"));
			clip.start();
		}catch(Exception e){
			
		}	
	}
}