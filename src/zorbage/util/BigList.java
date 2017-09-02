package zorbage.util;

import java.util.ArrayList;
import java.util.List;

public class BigList<T> {
	
	// TODO: replace me with actual bigger container
	
	private List<T> list;
	
	public BigList() {
		list = new ArrayList<T>();
	}
	
	public BigList(long size) {
		if (size > Integer.MAX_VALUE)
			throw new IllegalArgumentException("Fake BigList size is too big");
		list = new ArrayList<T>((int) size);
	}
	
	public long size() {
		return list.size();
	}
	
	public T get(long i) {
		if (i > Integer.MAX_VALUE)
			throw new IllegalArgumentException("Fake BigList index is too big");
		return list.get((int) i);
	}

	public void set(long i, T v) {
		if (i > Integer.MAX_VALUE)
			throw new IllegalArgumentException("Fake BigList index is too big");
		list.set((int) i, v);
	}
	
	public void add(T val) {
		list.add(val);
	}

}
