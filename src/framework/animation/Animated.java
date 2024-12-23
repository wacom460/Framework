package framework.animation;

import java.util.ArrayList;
import java.util.List;

public class Animated<T extends Number> {
	public T value;
	Class<T> ct;
	List<Keyframe<T>> keyframes = new ArrayList<>();
	
	public Keyframe<T> newKeyframe(float timeMs, T value) {
		Keyframe<T> kf = new Keyframe<>(timeMs, value);
		
		return kf;
	}
	
	public Animated(Class<T> ct) {
		this.ct = ct;
		value = newTinst();		
	}
	
	T newTinst() {
		try {
			return ct.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
