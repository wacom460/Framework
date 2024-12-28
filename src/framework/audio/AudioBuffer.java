package framework.audio;

import java.io.BufferedInputStream;
import java.util.Hashtable;

import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class AudioBuffer {
	public static Hashtable<String, Integer> bufs = new Hashtable<>();
	
	public static int get(String name) {
		if(bufs.containsKey(name)) return bufs.get(name);
		
		int buffer = AL10.alGenBuffers();
		int rv = AL10.alGetError();
		if(rv != AL10.AL_NO_ERROR) {
			System.out.println("OpenAL buffer Error " + rv);
			return 0;
		}		

		WaveData waveFile = WaveData.create(new BufferedInputStream(AudioBuffer.class.getResourceAsStream("/" + name + ".wav")));
		AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
	    waveFile.dispose();
		bufs.put(name, buffer);
		return buffer;
		//AL10.alDeleteBuffers(buffer);
	}
}
