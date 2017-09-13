package zorbage.type.storage.coder;

public interface BitCoder<V> {

	int bitCount();
	void arrayToValue(long[] arr, int index, V value);
	void valueToArray(long[] arr, int index, V value);
}
