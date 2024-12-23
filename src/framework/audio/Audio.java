package framework.audio;
import java.util.Hashtable;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;

public class Audio {
	public Hashtable<String, SfxEvent> sfxs = new Hashtable<>();
	public int count = 0;
	
	public Audio() {
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
//		for(Entry<String, SfxEvent> ent : sfxs.entrySet()) {
//			
//		}
	}
	
	public void cleanup() {
		AL.destroy();
	}
	
	public void playSfx(String name) {
		playSfx(name, 0, 0, 0, 0, 1, 1);
	}
	
	public void playSfx(String name, float listenerPosX, float listenerPosY, float x, float y, float pitch, float volume) {
		new SfxEvent(name, listenerPosX, listenerPosY, x, y, pitch, volume);
	}
}
