package zorbage.type.storage.coder;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface ByteCoder<V> {

	int byteCount();
	void toValue(byte[] arr, int index);
	void toArray(byte[] arr, int index);
	void toValue(RandomAccessFile raf) throws IOException;
	void toFile(RandomAccessFile raf) throws IOException;
}
