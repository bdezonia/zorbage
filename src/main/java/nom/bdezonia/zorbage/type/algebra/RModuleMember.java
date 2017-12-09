package nom.bdezonia.zorbage.type.algebra;

public interface RModuleMember<A> extends Dimensioned {
	// 1 dims
	long length();
	void init(long len);
	void reshape(long len);
	void v(long i, A value);
	void setV(long i, A value);
}

