package zorbage.type.storage.coder;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface LongCoder<V> {

	int longCount();
	void toValue(long[] arr, int index);
	void toArray(long[] arr, int index);
	void toValue(RandomAccessFile raf) throws IOException;
	void toFile(RandomAccessFile raf) throws IOException;
}
