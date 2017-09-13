package zorbage.type.storage.coder;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface DoubleCoder<V> {

	int doubleCount();
	void toValue(double[] arr, int index);
	void toArray(double[] arr, int index);
	void toValue(RandomAccessFile raf) throws IOException;
	void toFile(RandomAccessFile raf) throws IOException;
}
