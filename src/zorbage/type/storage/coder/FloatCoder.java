package zorbage.type.storage.coder;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface FloatCoder<V> {

	int floatCount();
	void toValue(float[] arr, int index);
	void toArray(float[] arr, int index);
	void toValue(RandomAccessFile raf) throws IOException;
	void toFile(RandomAccessFile raf) throws IOException;
}
