package nom.bdezonia.zorbage.type.algebra;

public interface MatrixMember<A> extends Dimensioned {
	// 2 dims
	long rows();
	long cols();
	void init(long rows, long cols);
	void reshape(long rows, long cols);
	void v(long r, long c, A value);
	void setV(long r, long c, A value);
}

