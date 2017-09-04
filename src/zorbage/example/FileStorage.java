package zorbage.example;

import java.io.File;

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
			v.setI(i+1);
			store.set(i, v);
		}
		for (long i = 0; i < store.size(); i++) {
			store.get(i, v);
			if ((v.r() != i) || (v.i() != i+1))
				System.out.println("Data value mismatch i = " + i + " real = " + v.r() + " imag = " + v.i());
		}
		store.flush();
		
		if (store != null) store.dispose();

		try {
			store = new FileStorageComplexFloat64(new File(store.filename()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		for (long i = 0; i < store.size(); i++) {
			store.get(i, v);
			if ((v.r() != i) || (v.i() != i+1))
				System.out.println("Data value mismatch i = " + i + " real = " + v.r() + " imag = " + v.i());
		}
		
		try {
			store = new FileStorageComplexFloat64(2000, new File("/tmp/junk.stuff"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		for (long i = 0; i < store.size(); i++) {
			v.setR(i);
			v.setI(i+1);
			store.set(i, v);
		}
		for (long i = 0; i < store.size(); i++) {
			store.get(i, v);
			if ((v.r() != i) || (v.i() != i+1))
				System.out.println("Data value mismatch i = " + i + " real = " + v.r() + " imag = " + v.i());
		}
		store.flush();

		try {
			store = new FileStorageComplexFloat64(new File("/tmp/junk.stuff"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		for (long i = 0; i < store.size(); i++) {
			store.get(i, v);
			if ((v.r() != i) || (v.i() != i+1))
				System.out.println("Data value mismatch i = " + i + " real = " + v.r() + " imag = " + v.i());
		}
		
		FileStorageComplexFloat64 dup = store.duplicate();
		for (long i = 0; i < dup.size(); i++) {
			dup.get(i, v);
			if ((v.r() != i) || (v.i() != i+1))
				System.out.println("Data value mismatch i = " + i + " real = " + v.r() + " imag = " + v.i());
		}
		
		try {
			dup.setFilename("/tmp/RENAMED");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		store.dispose();

		dup.dispose();
	}
}
