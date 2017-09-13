package zorbage.type.storage.coder;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface BooleanCoder<V> {

	int booleanCount();
	void toValue(boolean[] arr, int index);
	void toArray(boolean[] arr, int index);
	void toValue(RandomAccessFile raf) throws IOException;
	void toFile(RandomAccessFile raf) throws IOException;
}
