package nom.bdezonia.zorbage.data;

import java.util.HashMap;
import java.util.Map;

import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.coordinates.CoordinateSpace;
import nom.bdezonia.zorbage.coordinates.IdentityCoordinateSpace;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.misc.LongUtils;
import nom.bdezonia.zorbage.sampling.IntegerIndex;

/**
 * Wraps any 1-d list as a multidimensional n-d data source.
 * 
 * @author Barry DeZonia
 *
 * @param <U>
 */
public class MultiDimWrapper<U> 
	implements DimensionedDataSource<U>
{
	private final long[] dims;
	private final IndexedDataSource<U> oneDdata;
	private final Map<String,String> metadata;
	private final String[] axisTypes;
	private final String[] axisUnits;
	private CoordinateSpace coordSpace;
	private String name;
	private String source;
	private String valueType;
	private String valueUnit;
	
	/**
	 * Wrap any 1-d list as a multidimensional n-d data source. The
	 * number of elements in the list must be compatible with the
	 * number of elements represented by the passed in dimensions.
	 * 
	 * @param list
	 * @param dims
	 */
	public MultiDimWrapper(IndexedDataSource<U> list, long[] dims) {
		
		if (LongUtils.numElements(dims) != list.size())
			throw new IllegalArgumentException("mismatched dimension sizes for list");
		this.oneDdata = list;
		this.dims = dims.clone();
		this.metadata = new HashMap<>();
		this.coordSpace = new IdentityCoordinateSpace(dims.length);
		this.axisTypes = new String[dims.length];
		this.axisUnits = new String[dims.length];
	}

	/**
	 * Returns the size of dimension number d in this multidim data set
	 */
	@Override
	public long dimension(int d) {
		
		if (d < 0)
			throw new IllegalArgumentException("negative index exception");
		
		if (d >= dims.length)
			throw new IllegalArgumentException("index out of bounds exception");
		
		return dims[d];
	}

	/**
	 * Returns the number of dimensions in this multidim data set
	 */
	@Override
	public int numDimensions() {
		
		return dims.length;
	}

	/**
	 * Returns the 1-d list that resides within the core of this
	 * multidim data set and which stores all its values.
	 */
	@Override
	public IndexedDataSource<U> rawData() {
		
		return oneDdata;
	}

	/**
	 * Return the storage type if this multidim data set.
	 */
	@Override
	public StorageConstruction storageType() {
		
		return oneDdata.storageType();
	}

	/**
	 * Returns the total number of elements in this data set
	 */
	@Override
	public long numElements() {

		return oneDdata.size();
	}

	/**
	 * Return the name for the n-d data source (something like "Lateral Brain Scan")
	 */
	@Override
	public String getName() {

		return name;
	}

	/**
	 * Set the name for the n-d data source (something like "Lateral Brain Scan")
	 */
	@Override
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * Return the source for the n-d data source. The source might be a filename
	 * or a URL.
	 */
	@Override
	public String getSource() {

		return source;
	}

	/**
	 * Set the source for the n-d data source. The source might be a filename
	 * or a URL.
	 */
	@Override
	public void setSource(String locator) {

		this.source = locator;
	}

	/**
	 * Return the value type for the n-d data source. The value type is typically
	 * the type of data that is represented by the values (temperature, distance,
	 * weight, etc.).
	 */
	@Override
	public String getValueType() {

		return valueType;
	}

	/**
	 * Set the value type for the n-d data source. The value type is typically
	 * the type of data that is represented by the values (temperature, distance,
	 * weight, etc.).
	 */
	@Override
	public void setValueType(String type) {

		this.valueType = type;
	}

	/**
	 * Return the value unit for the n-d data source. The value unit is related
	 * to the value type. So if value type is "temperature" then value unit will
	 * be something like "degrees C" or "degrees F", or "degrees K".
	 */
	@Override
	public String getValueUnit() {

		return valueUnit;
	}

	/**
	 * Set the value unit for the n-d data source. The value unit is related
	 * to the value type. So if value type is "temperature" then value unit will
	 * be something like "degrees C" or "degrees F", or "degrees K".
	 */
	@Override
	public void setValueUnit(String unit) {

		this.valueUnit = unit;
	}

	/**
	 * Return the coordinate space that maps the grid values to numeric values
	 * (for instance if you wanted to display a coordinate while mousing over
	 * a set of data).
	 */
	@Override
	public CoordinateSpace getCoordinateSpace() {

		return coordSpace;
	}

	/**
	 * Set the coordinate space that maps the grid values to numeric values
	 * (for instance if you wanted to display a coordinate while mousing over
	 * a set of data).
	 */
	@Override
	public void setCoordinateSpace(CoordinateSpace cspace) {

		this.coordSpace = cspace;
	}

	/**
	 * Get the axis unit associated with a dimension/coordinate axis.
	 * An axis unit might be something like "mm" or 'deciliters" etc.
	 */
	@Override
	public String getAxisUnit(int d) {
		
		if (d < 0)
			throw new IllegalArgumentException("negative index exception");
		
		if (d >= dims.length)
			throw new IllegalArgumentException("index out of bounds exception");
		
		return axisUnits[d];
	}

	/**
	 * Set the axis unit associated with a dimension/coordinate axis.
	 * An axis unit might be something like "mm" or 'deciliters" etc.
	 */
	@Override
	public void setAxisUnit(int d, String unit) {
		
		if (d < 0)
			throw new IllegalArgumentException("negative index exception");
		
		if (d >= dims.length)
			throw new IllegalArgumentException("index out of bounds exception");
		
		axisUnits[d] = unit;
	}

	/**
	 * Get the axis type associated with a dimension/coordinate axis.
	 * An axis type might be something like "pressure" or "acceleration" etc.
	 */
	@Override
	public String getAxisType(int d) {
		
		if (d < 0)
			throw new IllegalArgumentException("negative index exception");
		
		if (d >= dims.length)
			throw new IllegalArgumentException("index out of bounds exception");
		
		return axisTypes[d];
	}

	/**
	 * Set the axis type associated with a dimension/coordinate axis.
	 * An axis type might be something like "pressure" or "acceleration" etc.
	 */
	@Override
	public void setAxisType(int d, String type) {
		
		if (d < 0)
			throw new IllegalArgumentException("negative index exception");
		
		if (d >= dims.length)
			throw new IllegalArgumentException("index out of bounds exception");
		
		axisTypes[d] = type;
	}

	/**
	 * Gets the value associated with this data source's n-d coord space
	 * as stored in the underlying 1-d list of data. No checking is done
	 * in regards to the validity of the input coordinate.
	 */
	@Override
	public void get(IntegerIndex index, U value) {

		long idx = IndexUtils.indexToLong(dims, index);
		oneDdata.get(idx, value);
	}

	/**
	 * Gets the value associated with this data source's n-d coord space
	 * as stored in the underlying 1-d list of data. The input coordinate
	 * is checked for validity and if it is out of the bounds of this n-d
	 * data source then an exception is thrown.
	 */
	@Override
	public void safeGet(IntegerIndex index, U value) {

		if (oob(index))
			throw new IllegalArgumentException("index out of bounds");
		get(index, value);
	}

	/**
	 * Sets the value associated with this data source's n-d coord space
	 * as stored in the underlying 1-d list of data. No checking is done
	 * in regards to the validity of the input coordinate.
	 */
	@Override
	public void set(IntegerIndex index, U value) {

		long idx = IndexUtils.indexToLong(dims, index);
		oneDdata.set(idx, value);
	}

	/**
	 * Gets the value associated with this data source's n-d coord space
	 * as stored in the underlying 1-d list of data. The input coordinate
	 * is checked for validity and if it is out of the bounds of this n-d
	 * data source then an exception is thrown.
	 */
	@Override
	public void safeSet(IntegerIndex index, U value) {

		if (oob(index))
			throw new IllegalArgumentException("index out of bounds");
		set(index, value);
	}

	/**
	 * Return true if a given index represents coordinates outside this
	 * data source's bounds.
	 */
	@Override
	public boolean oob(IntegerIndex index) {
		
		if (index.numDimensions() != dims.length)
			throw new IllegalArgumentException(
					"index dimension count does not equal dataset dimension count");
		
		for (int d = 0; d < dims.length; d++) {
			long i = index.get(d);
			if (i < 0 || i >= dims[d])
				return true;
		}
		
		return false;
	}

	/**
	 * Return a 1-d piped (PIE-PED) "slice" of this data source.
	 */
	@Override
	public IndexedDataSource<U> piped(int dim, IntegerIndex coord) {
	
		return new PipedDataSource<>(this, dim, coord);
	}

	/**
	 * Return the metadata structure associated with this data.
	 */
	@Override
	public Map<String, String> metadata() {

		return metadata;
	}
}
