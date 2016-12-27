package zorbage.type.data;

import java.util.Arrays;
import java.util.List;

import zorbage.type.parse.OctonionRepresentation;
import zorbage.type.parse.TensorStringRepresentation;
import zorbage.type.storage.ArrayStorageFloat64;

public final class TensorMember {

	private int[] dims;
	private ArrayStorageFloat64 storage;
	private int[] upperIndices;  // contravariant
	private int[] lowerIndices;  // covariant

	public TensorMember(int[] dims, int[] upperIndices) {
		this.dims = dims.clone();
		this.upperIndices = upperIndices.clone();
		Arrays.sort(this.upperIndices);
		this.lowerIndices = new int[0];  // TODO
		int k = 0;
		for (int i = 0; i < upperIndices.length; i++) {
			for (int j = k; j < upperIndices[i]; j++) {
				this.lowerIndices[k++] = j;
			}
		}
		long total = 1;
		for (int i = 0; i < dims.length; i++) {
			total *= dims[i];
		}
		this.storage = new ArrayStorageFloat64(total);
	}
	
	public TensorMember() {
		dims = new int[0];
		this.storage = new ArrayStorageFloat64(0);
		this.upperIndices = new int[0];
		this.lowerIndices = new int[0];
	}
	
	public TensorMember(TensorMember other) {
		this.dims = other.dims.clone();
		this.upperIndices = other.upperIndices.clone();
		this.lowerIndices = other.lowerIndices.clone();
		this.storage = new ArrayStorageFloat64(other.storage.size());
		Float64Member tmp = new Float64Member();
		for (long i = 0; i < storage.size(); i++) {
			other.storage.get(i, tmp);
			storage.put(i, tmp);
		}
	}

	public TensorMember(String s) {
		TensorStringRepresentation rep = new TensorStringRepresentation(s);
		dims = rep.dimensions().clone();
		upperIndices = new int[0];  // TODO
		lowerIndices = new int[0];  // TODO
		List<OctonionRepresentation> values = rep.values();
		storage = new ArrayStorageFloat64(values.size());
		Float64Member tmp = new Float64Member();
		for (int i = 0; i < values.size(); i++) {
			OctonionRepresentation value = values.get(i);
			tmp.setV(value.r().doubleValue());
			storage.put(i, tmp);
		}
	}
		
	public int tensorRank() { return -1; }  // TODO
	public int dimension() { return -1; }  // TODO

	public void v(int[] index, Float64Member val) {
		throw new IllegalArgumentException("TODO");
	}

	public void setV(int[] index, Float64Member val) {
		throw new IllegalArgumentException("TODO");
	}
}
