package framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Tools {
	public static boolean makeDirectory(String path) {
		File dir = new File(path);
		return dir.mkdir();
	}
	
	public static boolean saveObject(String path, Object obj) {
		try {
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(obj);
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
		return false;
	}
	
	public static Object loadObject(String path) {
		try {
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Object obj = in.readObject();
			in.close();
			return obj;
		} catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
		return null;
	}
}
