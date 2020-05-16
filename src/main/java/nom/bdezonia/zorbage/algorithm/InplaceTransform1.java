package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class InplaceTransform1 {

	private InplaceTransform1() { }
	
	/**
	 * In place initialization of one whole list by a Procedure1.
	 * 
	 * @param alg
	 * @param proc
	 * @param a
	 */
	public static <T extends Algebra<T,U>, U>
		void compute(T alg, Procedure1<U> proc, IndexedDataSource<U> a)
	{
		Transform1.compute(alg, proc, a);
	}

}
