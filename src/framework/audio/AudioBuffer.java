package framework.audio;

import java.util.Hashtable;

import org.lwjgl.openal.AL10;

public class AudioBuffer {
	public static Hashtable<String, Integer> bufs = new Hashtable<>();
	
	public static int get(String name) {
		if(bufs.contains(name)) 
			return bufs.get(name);
		int buffer = AL10.alGenBuffers();		
		if(AL10.alGetError() != AL10.AL_NO_ERROR) {
			System.out.println("OpenAL buffer Error");
			return 0;
		}		
		bufs.put(name, buffer);
		return buffer;
		//AL10.alDeleteBuffers(buffer);
	}
}
