package zorbage.type.storage.coder;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface IntCoder<V> {

	int intCount();
	void arrayToValue(int[] arr, int index, V value);
	void valueToArray(int[] arr, int index, V value);
	void fileToValue(RandomAccessFile raf, V value) throws IOException;
	void valueToFile(RandomAccessFile raf, V value) throws IOException;
}
