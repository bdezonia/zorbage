package nom.bdezonia.zorbage.basic.procedure;

public interface Procedure<U> {

	@SuppressWarnings("unchecked")
	void call(U result, U... inputs);

}
