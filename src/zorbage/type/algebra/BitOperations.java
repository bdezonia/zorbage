package zorbage.type.algebra;

public interface BitOperations<T> extends Logical<T,T> {
	void shiftLeft(T a, T b);
	void shiftRight(T a, T b);
}
