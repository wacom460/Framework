package framework.ecs;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//Example:
//public static Component<String> name = new Component<>(String.class);

public class Component<T> {
	private Class<T> ct;
	private Map<Integer, T> dict = Collections.synchronizedMap(new HashMap<>());
	
	public Component(Class<T> ct) {
		this.ct = ct;
	}
	
	public void remove(int id) {
		dict.remove(id);
	}
	
	public T get(int id) {
		return get(id, true);
	}
	
	public T get(int id, boolean makeNew) {
		if(id == 0) {
			try {
				throw new Exception("id 0 not allowed.");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		T ret = dict.get(id);
		if(ret == null && makeNew) {
			try {
				ret = ct.getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			dict.put(id, ret);
		}
		return ret;
	}
	
	public T set(int id, T obj) {
		return dict.put(id, obj);
	}
}
