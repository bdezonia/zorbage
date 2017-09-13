package zorbage.type.storage.coder;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface FloatCoder<V> {

	int floatCount();
	void arrayToValue(float[] arr, int index, V value);
	void valueToArray(float[] arr, int index, V value);
	void fileToValue(RandomAccessFile raf, V value) throws IOException;
	void valueToFile(RandomAccessFile raf, V value) throws IOException;
}
