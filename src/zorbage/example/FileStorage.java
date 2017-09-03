package zorbage.example;

import zorbage.type.data.ComplexFloat64Member;
import zorbage.type.storage.linear.file.FileStorageComplexFloat64;

public class FileStorage {

	public void run() {
		ComplexFloat64Member v = new ComplexFloat64Member();
		
		FileStorageComplexFloat64 store = null;
		
		try {
			store = new FileStorageComplexFloat64(600);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		for (long i = 0; i < store.size(); i++) {
			v.setR(i);
			v.setI(i);
			store.set(i, v);
		}
		for (long i = 0; i < store.size(); i++) {
			store.get(i, v);
			if ((v.r() != i) || (v.i() != i))
				System.out.println("Data value mismatch i = " + i + " real = " + v.r() + " imag = " + v.i());
		}
		if (store != null) store.dispose();
	}
}
