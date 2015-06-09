package auditory.sampled;

import javax.sound.sampled.LineUnavailableException;
import io.ResourceFinder;

/**
 * A factory class that creates and renders audio content
 * 
 * @author Zachary Bolan
 * @version 11/17/13
 *
 */
public class SoundFactory {
	
	private BoomBox					boombox;
	private BufferedSound			sound;
	private BufferedSoundFactory	bsf;
	
	/**
	 * The explicit value constructor that accepts a ResoouceFinder
	 * @param finder	ResourceFinder used to search for audio content
	 */
	public SoundFactory(ResourceFinder finder) {
		bsf = new BufferedSoundFactory(finder);
	}
	
	/**
	 * Plays a specified audio clip from a given name. The String given must 
	 * be in the format: nameOfAudio.audioExtension (audioClip.wav).
	 * 
	 * @param name	The name of the audio file to play
	 */
	public void playAudio(String name) {
		try {
			sound = bsf.createBufferedSound("rsc/" + name);
		} catch (Exception e) {
	        sound = bsf.createBufferedSound(200,       	// frequency
                     						250000,     // length
                     						4000.0f,    // sampling rate
                     						1000.0f);   // amplitude
		}
		
		boombox = new BoomBox(sound);
		try {
			boombox.start();
		} catch (LineUnavailableException e) {
			// Ignore
		}
	}

}
