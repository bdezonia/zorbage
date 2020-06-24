package nom.bdezonia.zorbage.storage.coder;

public interface CharCoder {

	int charCount();
	void fromCharArray(char[] arr, int index);
	void toCharArray(char[] arr, int index);
}
