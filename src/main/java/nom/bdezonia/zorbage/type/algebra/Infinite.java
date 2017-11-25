package nom.bdezonia.zorbage.type.algebra;

public interface Infinite<T> {
	boolean isNaN(T a);
	boolean isInfinite(T a);
	// positive or negative can be determined with signum() for Ordered groups.
	// Complex has no concept of pos inf and neg inf.
}
