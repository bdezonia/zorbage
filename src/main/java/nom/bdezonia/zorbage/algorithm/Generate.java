package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.basic.procedure.Procedure;
import nom.bdezonia.zorbage.basic.procedure.Procedure1;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.storage.linear.LinearStorage;

public class Generate {

	/**
	 * 
	 * @param group
	 * @param proc
	 * @param storage
	 */
	public static <T extends Group<T,U>,U>
		void compute(T group, Procedure1<U> proc, LinearStorage<?,U> storage)
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
	public static <T extends Group<T,U>,U>
		void compute(T group, Procedure<U> proc, LinearStorage<?,U> storage, U... inputs)
	{
		GenerateN.compute(group, proc, 0, storage.size(), storage, inputs);
	}

}
