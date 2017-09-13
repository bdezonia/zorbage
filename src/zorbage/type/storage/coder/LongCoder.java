package zorbage.type.storage.coder;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface LongCoder<V> {

	int longCount();
	void arrayToValue(long[] arr, int index, V value);
	void valueToArray(long[] arr, int index, V value);
	void fileToValue(RandomAccessFile raf, V value) throws IOException;
	void valueToFile(RandomAccessFile raf, V value) throws IOException;
}
