package zorbage.type.storage.coder;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface ShortCoder<V> {

	int shortCount();
	void arrayToValue(short[] arr, int index, V value);
	void valueToArray(short[] arr, int index, V value);
	void fileToValue(RandomAccessFile raf, V value) throws IOException;
	void valueToFile(RandomAccessFile raf, V value) throws IOException;
}
