package nom.bdezonia.zorbage.type.algebra;

public interface TensorMember<A> extends Dimensioned {
	// n dims
	void dims(long[] dims);
	void init(long[] dims);
	void reshape(long[] dims);
	void v(long[] index, A value);
	void setV(long[] index, A value);
}
