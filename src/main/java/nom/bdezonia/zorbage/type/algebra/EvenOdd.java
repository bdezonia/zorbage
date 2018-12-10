package nom.bdezonia.zorbage.type.algebra;

import nom.bdezonia.zorbage.function.Function1;

/**
 * 
 * @author Barry DeZonia
 *
 */
public interface EvenOdd<U> {
	Function1<Boolean,U> isEven();
	Function1<Boolean,U> isOdd();
}
