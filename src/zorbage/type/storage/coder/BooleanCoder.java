package zorbage.type.storage.coder;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface BooleanCoder<V> {

	int booleanCount();
	void arrayToValue(boolean[] arr, int index, V value);
	void valueToArray(boolean[] arr, int index, V value);
	void fileToValue(RandomAccessFile raf, V value) throws IOException;
	void valueToFile(RandomAccessFile raf, V value) throws IOException;
}
