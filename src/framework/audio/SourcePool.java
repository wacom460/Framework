package framework.audio;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.openal.AL10;

public class SourcePool {
	public List<Integer> sources = new ArrayList<>();
	public static final int maxSources = 256;
	
	public SourcePool() {
		reset();
	}
	
	public void reset() {
		for(Integer i : sources) {
			AL10.alDeleteSources(i);
		}
		for(int i = 0; i < maxSources; i++) {
			sources.add(genSource());
		}
	}
	
	public int getFree(int buffer) {
		for(Integer i : sources) {
			if(!isSourcePlaying(i)) {
				AL10.alSourcei(i, AL10.AL_BUFFER, buffer);
				return i;
			}
		}
		return -1;
	}
	
	public static int genSource() {
		int source = AL10.alGenSources();
	    int rv = AL10.alGetError();
	    if (rv != AL10.AL_NO_ERROR) {
	    	System.out.println("OpenAL source Error " + rv);
	    	return -1;
	    }
	    return source;
	}
	
	public static boolean isSourcePlaying(int source) {
        int state = AL10.alGetSourcei(source, AL10.AL_SOURCE_STATE);
        return state == AL10.AL_PLAYING;
    }
}
