package framework.audio;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

import framework.Window;

public class SfxEvent {
	public String name;
	//public int source;
	public FloatBuffer sourcePos;
	public FloatBuffer sourceVel;
	public FloatBuffer listenerPos;
	public FloatBuffer listenerVel;
	public FloatBuffer listenerOri;	

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

		int buffer = AudioBuffer.get(name);
	    
	   /* source = AL10.alGenSources();
	    int rv = AL10.alGetError();
	    if (rv != AL10.AL_NO_ERROR) {
	    	System.out.println("OpenAL source Error " + rv);
	    	return;
	    }	*/
	    
	    int source = Window.audio.pool.getFree(buffer);

	    //AL10.alSourcei(source, AL10.AL_BUFFER, buffer);
	    AL10.alSourcef(source, AL10.AL_PITCH, pitch);
	    AL10.alSourcef(source, AL10.AL_GAIN, volume);
	    AL10.alSource (source, AL10.AL_POSITION, sourcePos);
	    AL10.alSource (source, AL10.AL_VELOCITY, sourceVel);

	    AL10.alListener(AL10.AL_POSITION, listenerPos);
	    AL10.alListener(AL10.AL_VELOCITY, listenerVel);
	    AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
	    
	    /*Window.audio.sfxs.put(Window.audio.count + "_" + name, this);*/
//	    Window.audio.count++;
	    
	    AL10.alSourcePlay(source);
	}
}
