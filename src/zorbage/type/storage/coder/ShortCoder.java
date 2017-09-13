package zorbage.type.storage.coder;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface ShortCoder<V> {

	int shortCount();
	void toValue(short[] arr, int index);
	void toArray(short[] arr, int index);
	void toValue(RandomAccessFile raf) throws IOException;
	void toFile(RandomAccessFile raf) throws IOException;
}
