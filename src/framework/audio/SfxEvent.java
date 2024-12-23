package framework.audio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.util.concurrent.TimeUnit;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

import framework.Window;

public class SfxEvent {
	public String name;
	public int source;
	public FloatBuffer sourcePos;
	public FloatBuffer sourceVel;
	public FloatBuffer listenerPos;
	public FloatBuffer listenerVel;
	public FloatBuffer listenerOri;	
	public WaveData waveFile;

	public SfxEvent(String name, float listenerPosX, float listenerPosY, float x, float y, float pitch, float volume) {
		this.name = name;
		
		sourcePos = BufferUtils.createFloatBuffer(3).put(new float[] { x, y, 0.0f });
		sourceVel = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
		listenerPos = BufferUtils.createFloatBuffer(3).put(new float[] { listenerPosX, listenerPosY, 0.0f });
		listenerVel = BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f });
		listenerOri = BufferUtils.createFloatBuffer(6).put(new float[] { listenerPosX, listenerPosY, -1.0f, 0.0f, 1.0f, 0.0f });
		
	    sourcePos.flip();
	    sourceVel.flip();
	    listenerPos.flip();
	    listenerVel.flip();
	    listenerOri.flip();

		try {
			waveFile = WaveData.create(new BufferedInputStream(new FileInputStream("./res/" + name + ".wav")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int buffer = AudioBuffer.get(name);
		AL10.alBufferData(buffer, waveFile.format, waveFile.data, waveFile.samplerate);
	    waveFile.dispose();
	    
	    source = AL10.alGenSources();
	    if (AL10.alGetError() != AL10.AL_NO_ERROR) {
	    	System.out.println("OpenAL source Error");
	    	return;
	    }	    

	    AL10.alSourcei(source, AL10.AL_BUFFER, buffer);
	    AL10.alSourcef(source, AL10.AL_PITCH, pitch);
	    AL10.alSourcef(source, AL10.AL_GAIN, volume);
	    AL10.alSource (source, AL10.AL_POSITION, sourcePos);
	    AL10.alSource (source, AL10.AL_VELOCITY, sourceVel);

	    AL10.alListener(AL10.AL_POSITION, listenerPos);
	    AL10.alListener(AL10.AL_VELOCITY, listenerVel);
	    AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
	    	    
	    Window.audio.sfxs.put(Window.audio.count + "_" + name, this);
	    Window.audio.count++;
	    
	    AL10.alSourcePlay(source);
	    
	    Runnable task = () -> cleanup();
	    //TODO: get actual length of wave file
	    Window.scheduler.schedule(task, 4, TimeUnit.SECONDS);
	}
	
	public void cleanup() {
		System.out.println("Cleaned up source " + source);
	    AL10.alDeleteSources(source);
	}
}
