package zorbage.type.storage.coder;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface DoubleCoder<V> {

	int doubleCount();
	void arrayToValue(double[] arr, int index, V value);
	void valueToArray(double[] arr, int index, V value);
	void fileToValue(RandomAccessFile raf, V value) throws IOException;
	void valueToFile(RandomAccessFile raf, V value) throws IOException;
}
