package nom.bdezonia.zorbage.type.algebra;

public interface NumberMember<A> extends Dimensioned {
	// 0 dims
	void v(A value);
	void setV(A value);
}
