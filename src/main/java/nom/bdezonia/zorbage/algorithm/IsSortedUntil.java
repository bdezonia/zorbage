package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Ordered;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class IsSortedUntil {

	/**
	 * 
	 * @param alg
	 * @param storage
	 * @return
	 */
	public static <T extends Algebra<T,U> & Ordered<U>,U>
		long compute(T alg, IndexedDataSource<U> storage)
	{
		U tmp1 = alg.construct();
		U tmp2 = alg.construct();
		long first = 0;
		long last = storage.size();
		if (first==last) return first;
		long next = first;
		while (++next != last) {
			storage.get(next, tmp1);
			storage.get(first, tmp2);
			if (alg.isLess().call(tmp1, tmp2))
				return next;
			first++;
		}
		return last;
	}
}
