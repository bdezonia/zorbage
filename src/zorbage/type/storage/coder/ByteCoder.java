package zorbage.type.storage.coder;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface ByteCoder<V> {

	int byteCount();
	void arrayToValue(byte[] arr, int index, V value);
	void valueToArray(byte[] arr, int index, V value);
	void fileToValue(RandomAccessFile raf, V value) throws IOException;
	void valueToFile(RandomAccessFile raf, V value) throws IOException;
}
