package zorbage.type.storage.coder;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface IntCoder<V> {

	int intCount();
	void toValue(int[] arr, int index);
	void toArray(int[] arr, int index);
	void toValue(RandomAccessFile raf) throws IOException;
	void toFile(RandomAccessFile raf) throws IOException;
}
