package nom.bdezonia.zorbage.procedure;

public interface Procedure<U> {

	@SuppressWarnings("unchecked")
	void call(U result, U... inputs);

}
