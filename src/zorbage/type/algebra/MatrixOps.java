package zorbage.type.algebra;

public interface MatrixOps<U,W> {
	void transpose(U a, U b);
	void conjugateTranspose(U a, U b);
	void det(U a, W b);
}
