package zorbage.type.storage.coder;

public interface BitCoder<V> {

	int bitCount();
	void toValue(long[] arr, int index);
	void toArray(long[] arr, int index);
}
