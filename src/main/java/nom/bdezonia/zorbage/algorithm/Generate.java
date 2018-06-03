package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.procedure.Procedure;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

public class Generate {

	/**
	 * 
	 * @param group
	 * @param proc
	 * @param storage
	 */
	public static <T extends Group<T,U>,U>
		void compute(T group, Procedure1<U> proc, IndexedDataSource<?,U> storage)
	{
		GenerateN.compute(group, proc, 0, storage.size(), storage);
	}

	/**
	 * 
	 * @param group
	 * @param proc
	 * @param storage
	 * @param inputs
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Group<T,U>,U>
		void compute(T group, Procedure<U> proc, IndexedDataSource<?,U> storage, U... inputs)
	{
		GenerateN.compute(group, proc, 0, storage.size(), storage, inputs);
	}

}
