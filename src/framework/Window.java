package framework;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import framework.audio.Audio;
import framework.gui.Widget;
import framework.rendering.DrawList;
import framework.rendering.Shader;
import framework.rendering.Tex;
import framework.rendering.Text;
import framework.rendering.TileSheet;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public abstract class Window {
	public static int width = 640, height = 480;
	public static boolean running = true;

	public static Tex atlas;
	public static TileSheet atlasTileSheet, fontTileSheet;
	public static DrawList dl = new DrawList();
	public static Audio audio;

	public static int kbFrames[] = new int[Keyboard.getKeyCount()];
	public static boolean kbPressed[] = new boolean[Keyboard.getKeyCount()];
	public static boolean kbHeld[] = new boolean[Keyboard.getKeyCount()];
	public static boolean kbReleased[] = new boolean[Keyboard.getKeyCount()];

	//public static Shader shader;

	public static Widget widget;// global selected widget
	public static List<Widget> widgets;
	private static boolean windowFocusState;
	private static boolean lastWindowFocusState;
	
	private static long framesRendered = 0;
	private static long frameTimeTakenNs = 0;
	private static int fps_frames = 0;
    private static long lastTime = System.nanoTime();
    private static long deltaTime = 0, cumTime = 0;
    public static int fps;
	public static boolean showFps;
	
	public static Object guiTop, guiTopEOF;

    public void tickFps() {
        fps_frames++;
        long now = System.nanoTime();
        deltaTime = now - lastTime;
        cumTime += deltaTime;
        lastTime = now;
        if (cumTime >= 1000000000L) {
            fps = fps_frames;
            fps_frames = 0;
            cumTime = 0;
        }
    }
	
	public static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public static void setWidget(Widget w) {
		widget = w;
	}
	
	public Window(String title, int width, int height, String atlasLoc, int atlasW, int atlasH, int winLocX, int winLocY) {
    	try {
			File resFolder = new File("./res/");
	        if (!(resFolder.exists() && resFolder.isDirectory())) {
				throw new RuntimeException("res folder doesn't exist! can't continue!");
	        }
	        File atlasFile = new File("./res/" + atlasLoc);
	        if (!atlasFile.exists()) {
				throw new RuntimeException("atlas doesn't exist! can't continue!");
	        }
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		Window.width = width;
		Window.height = height;

		widgets = new ArrayList<>();
		
		try {
			//Display.setInitialBackground(0.2f, 0, 0);
			if(winLocX + winLocY != 0) Display.setLocation(winLocX, winLocY);
			Display.setTitle(title);
			//Display.setIcon(null);
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create(new PixelFormat(), new ContextAttribs(2, 1).withForwardCompatible(false));
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		GL11.glViewport(0, 0, width, height);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, -1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);		
        GL11.glClearColor(0, 0, 0, 1);
        
        int mts = GL11.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE);
        System.out.println("Max texture size: " + mts);
		
		//System.out.println("OpenGL Version: " + GL11.glGetString(GL11.GL_VERSION));

		atlas = new Tex(atlasLoc);
		atlasTileSheet = new TileSheet(atlas, atlasW, atlasH);
		fontTileSheet = new TileSheet(atlas, atlasW * 2, atlasH);
		//shader = new Shader();
		
		audio = new Audio();

		//all framework features must be initialized before calling init!
		init();

		while (running) {
			tick();
		}
		
		cleanup();
		
		audio.cleanup();
		Display.destroy();
	}
	
	private void tick() {
		Shader.unbindAll();
		long n = System.nanoTime();
		tickFps();
		
		windowFocusState = Display.isActive();
		if(windowFocusState != lastWindowFocusState && windowFocusState) {
			atlas.reload();
			//System.out.println("Reloaded texture atlas");
		}
		lastWindowFocusState = windowFocusState;

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

		for (int i = 0; i < kbFrames.length; ++i) {
			if (Keyboard.isKeyDown(i)) {
				kbFrames[i]++;
				kbHeld[i] = true;
				keyHeld(i);
			} else if (kbFrames[i] > 0) {
				kbFrames[i] = 0;
				kbHeld[i] = false;
				kbReleased[i] = true;
				onKeyRelease(i);
			}
			if (kbFrames[i] == 1) {
				kbPressed[i] = true;
				onKeyPress(i);
			}
		}

		atlas.bind();
		//shader.bind();

		for (Widget w : widgets) {
			w.update();
		}

		dl.clear();
		frame();

		if(showFps) 
			Text.draw(dl, 0, 0, 1, 0, 0, new Color(1, 0, 0, 1), new Color(1, 0, 1, 1), "Fps: " + fps);
		
		dl.sendToGPU();
		dl.render();
		Display.update();

		for (int i = 0; i < kbReleased.length; ++i) {
			kbReleased[i] = false;
			kbPressed[i] = false;
		}	

		// Display.sync(20);
		if (Display.isCloseRequested()) running = false;
		frameTimeTakenNs = System.nanoTime() - n;
		framesRendered++;
		
		guiTopEOF = guiTop;
		guiTop = null;

		try {
			if(framesRendered > 1 && frameTimeTakenNs > 0 && frameTimeTakenNs < 1000000) {
				Thread.sleep(0, 1000000 - (int)frameTimeTakenNs);
			} else {
				//Thread.sleep(1, 0);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected abstract void init();
	protected void cleanup() {}
	protected abstract void frame();
	
	public void onKeyPress(int i) {}
	public void keyHeld(int i) {}
	public void onKeyRelease(int i) {}
}
