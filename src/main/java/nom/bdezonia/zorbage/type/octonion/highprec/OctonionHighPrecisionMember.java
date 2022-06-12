/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.type.octonion.highprec;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.storage.coder.BigDecimalCoder;
import nom.bdezonia.zorbage.misc.Hasher;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;
import nom.bdezonia.zorbage.type.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.universal.PrimitiveRepresentation;
import nom.bdezonia.zorbage.type.universal.TensorOctonionRepresentation;
import nom.bdezonia.zorbage.type.universal.TensorStringRepresentation;
import nom.bdezonia.zorbage.type.universal.UniversalRepresentation;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class OctonionHighPrecisionMember
	implements
		NumberMember<OctonionHighPrecisionMember>,
		BigDecimalCoder,
		Allocatable<OctonionHighPrecisionMember>, Duplicatable<OctonionHighPrecisionMember>,
		Settable<OctonionHighPrecisionMember>, Gettable<OctonionHighPrecisionMember>,
		PrimitiveConversion,
		UniversalRepresentation, SetOctonion<HighPrecisionMember>, GetOctonion<HighPrecisionMember>,
		SetFromBigDecimal, SetFromBigInteger, SetFromDouble, SetFromLong,
		GetAsBigDecimalArray
{

	private BigDecimal r, i, j, k, l, i0, j0, k0;
	
	public OctonionHighPrecisionMember() {
		primitiveInit();
	}
	
	public OctonionHighPrecisionMember(OctonionHighPrecisionMember value) {
		set(value);
	}

	public OctonionHighPrecisionMember(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		setR(val.r());
		setI(val.i());
		setJ(val.j());
		setK(val.k());
		setL(val.l());
		setI0(val.i0());
		setJ0(val.j0());
		setK0(val.k0());
	}
	
	public OctonionHighPrecisionMember(BigDecimal ... vals) {
		setFromBigDecimal(vals);
	}
	
	public OctonionHighPrecisionMember(BigInteger ... vals) {
		setFromBigInteger(vals);
	}
	
	public OctonionHighPrecisionMember(double ... vals) {
		setFromDouble(vals);
	}
	
	public OctonionHighPrecisionMember(long ... vals) {
		setFromLong(vals);
	}

	public BigDecimal r() { return r; }
	
	public BigDecimal i() { return i; }
	
	public BigDecimal j() { return j; }
	
	public BigDecimal k() { return k; }
	
	public BigDecimal l() { return l; }
	
	public BigDecimal i0() { return i0; }
	
	public BigDecimal j0() { return j0; }
	
	public BigDecimal k0() { return k0; }
	
	public void setR(BigDecimal val) { r = val; }
	
	public void setI(BigDecimal val) { i = val; }
	
	public void setJ(BigDecimal val) { j = val; }
	
	public void setK(BigDecimal val) { k = val; }
	
	public void setL(BigDecimal val) { l = val; }
	
	public void setI0(BigDecimal val) { i0 = val; }
	
	public void setJ0(BigDecimal val) { j0 = val; }
	
	public void setK0(BigDecimal val) { k0 = val; }
	
	@Override
	public void set(OctonionHighPrecisionMember other) {
		//if (this == other) return;
		r = other.r;
		i = other.i;
		j = other.j;
		k = other.k;
		l = other.l;
		i0 = other.i0;
		j0 = other.j0;
		k0 = other.k0;
	}

	@Override
	public void get(OctonionHighPrecisionMember other) {
		//if (this == other) return;
		other.r = r;
		other.i = i;
		other.j = j;
		other.k = k;
		other.l = l;
		other.i0 = i0;
		other.j0 = j0;
		other.k0 = k0;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append('{');
		builder.append(r());
		builder.append(',');
		builder.append(i());
		builder.append(',');
		builder.append(j());
		builder.append(',');
		builder.append(k());
		builder.append(',');
		builder.append(l());
		builder.append(',');
		builder.append(i0());
		builder.append(',');
		builder.append(j0());
		builder.append(',');
		builder.append(k0());
		builder.append('}');
		return builder.toString();
	}

	@Override
	public int bigDecimalCount() {
		return 8;
	}

	@Override
	public void fromBigDecimalArray(BigDecimal[] arr, int index) {
		r = arr[index];
		i = arr[index+1];
		j = arr[index+2];
		k = arr[index+3];
		l = arr[index+4];
		i0 = arr[index+5];
		j0 = arr[index+6];
		k0 = arr[index+7];
	}

	@Override
	public void toBigDecimalArray(BigDecimal[] arr, int index) {
		arr[index] = r;
		arr[index+1] = i;
		arr[index+2] = j;
		arr[index+3] = k;
		arr[index+4] = l;
		arr[index+5] = i0;
		arr[index+6] = j0;
		arr[index+7] = k0;
	}

	@Override
	public OctonionHighPrecisionMember allocate() {
		return new OctonionHighPrecisionMember();
	}

	@Override
	public OctonionHighPrecisionMember duplicate() {
		return new OctonionHighPrecisionMember(this);
	}

	@Override
	public int numDimensions() {
		return 0;
	}

	@Override
	public void getV(OctonionHighPrecisionMember value) {
		get(value);
	}

	@Override
	public void setV(OctonionHighPrecisionMember value) {
		set(value);
	}

	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		rep.setValue(
			new OctonionRepresentation(
				r(),
				i(),
				j(),
				k(),
				l(),
				i0(),
				j0(),
				k0()
			)
		);
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		OctonionRepresentation v = rep.getValue();
		setR(v.r());
		setI(v.i());
		setJ(v.j());
		setK(v.k());
		setL(v.l());
		setI0(v.i0());
		setJ0(v.j0());
		setK0(v.k0());
	}

	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.BIGDECIMAL;
	}

	@Override
	public long dimension(int i) {
		return 0;
	}

	@Override
	public int componentCount() {
		return 8;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					this.setR(BigDecimal.valueOf(v));
				else // component == 1
					this.setI(BigDecimal.valueOf(v));
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					this.setJ(BigDecimal.valueOf(v));
				else // component == 3
					this.setK(BigDecimal.valueOf(v));
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					this.setL(BigDecimal.valueOf(v));
				else // component == 5
					this.setI0(BigDecimal.valueOf(v));
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					this.setJ0(BigDecimal.valueOf(v));
				else // component == 7
					this.setK0(BigDecimal.valueOf(v));
			}
		}
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					this.setR(BigDecimal.valueOf(v));
				else // component == 1
					this.setI(BigDecimal.valueOf(v));
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					this.setJ(BigDecimal.valueOf(v));
				else // component == 3
					this.setK(BigDecimal.valueOf(v));
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					this.setL(BigDecimal.valueOf(v));
				else // component == 5
					this.setI0(BigDecimal.valueOf(v));
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					this.setJ0(BigDecimal.valueOf(v));
				else // component == 7
					this.setK0(BigDecimal.valueOf(v));
			}
		}
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					this.setR(BigDecimal.valueOf(v));
				else // component == 1
					this.setI(BigDecimal.valueOf(v));
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					this.setJ(BigDecimal.valueOf(v));
				else // component == 3
					this.setK(BigDecimal.valueOf(v));
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					this.setL(BigDecimal.valueOf(v));
				else // component == 5
					this.setI0(BigDecimal.valueOf(v));
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					this.setJ0(BigDecimal.valueOf(v));
				else // component == 7
					this.setK0(BigDecimal.valueOf(v));
			}
		}
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					this.setR(BigDecimal.valueOf(v));
				else // component == 1
					this.setI(BigDecimal.valueOf(v));
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					this.setJ(BigDecimal.valueOf(v));
				else // component == 3
					this.setK(BigDecimal.valueOf(v));
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					this.setL(BigDecimal.valueOf(v));
				else // component == 5
					this.setI0(BigDecimal.valueOf(v));
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					this.setJ0(BigDecimal.valueOf(v));
				else // component == 7
					this.setK0(BigDecimal.valueOf(v));
			}
		}
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					this.setR(BigDecimal.valueOf(v));
				else // component == 1
					this.setI(BigDecimal.valueOf(v));
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					this.setJ(BigDecimal.valueOf(v));
				else // component == 3
					this.setK(BigDecimal.valueOf(v));
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					this.setL(BigDecimal.valueOf(v));
				else // component == 5
					this.setI0(BigDecimal.valueOf(v));
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					this.setJ0(BigDecimal.valueOf(v));
				else // component == 7
					this.setK0(BigDecimal.valueOf(v));
			}
		}
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					this.setR(BigDecimal.valueOf(v));
				else // component == 1
					this.setI(BigDecimal.valueOf(v));
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					this.setJ(BigDecimal.valueOf(v));
				else // component == 3
					this.setK(BigDecimal.valueOf(v));
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					this.setL(BigDecimal.valueOf(v));
				else // component == 5
					this.setI0(BigDecimal.valueOf(v));
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					this.setJ0(BigDecimal.valueOf(v));
				else // component == 7
					this.setK0(BigDecimal.valueOf(v));
			}
		}
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					this.setR(new BigDecimal(v));
				else // component == 1
					this.setI(new BigDecimal(v));
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					this.setJ(new BigDecimal(v));
				else // component == 3
					this.setK(new BigDecimal(v));
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					this.setL(new BigDecimal(v));
				else // component == 5
					this.setI0(new BigDecimal(v));
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					this.setJ0(new BigDecimal(v));
				else // component == 7
					this.setK0(new BigDecimal(v));
			}
		}
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					this.setR(v);
				else // component == 1
					this.setI(v);
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					this.setJ(v);
				else // component == 3
					this.setK(v);
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					this.setL(v);
				else // component == 5
					this.setI0(v);
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					this.setJ0(v);
				else // component == 7
					this.setK0(v);
			}
		}
	}

	@Override
	public void primComponentSetByteSafe(IntegerIndex index, int component, byte v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						this.setR(BigDecimal.valueOf(v));
					else // component == 1
						this.setI(BigDecimal.valueOf(v));
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						this.setJ(BigDecimal.valueOf(v));
					else // component == 3
						this.setK(BigDecimal.valueOf(v));
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						this.setL(BigDecimal.valueOf(v));
					else // component == 5
						this.setI0(BigDecimal.valueOf(v));
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						this.setJ0(BigDecimal.valueOf(v));
					else // component == 7
						this.setK0(BigDecimal.valueOf(v));
				}
			}
		}
	}

	@Override
	public void primComponentSetShortSafe(IntegerIndex index, int component, short v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						this.setR(BigDecimal.valueOf(v));
					else // component == 1
						this.setI(BigDecimal.valueOf(v));
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						this.setJ(BigDecimal.valueOf(v));
					else // component == 3
						this.setK(BigDecimal.valueOf(v));
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						this.setL(BigDecimal.valueOf(v));
					else // component == 5
						this.setI0(BigDecimal.valueOf(v));
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						this.setJ0(BigDecimal.valueOf(v));
					else // component == 7
						this.setK0(BigDecimal.valueOf(v));
				}
			}
		}
	}

	@Override
	public void primComponentSetIntSafe(IntegerIndex index, int component, int v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						this.setR(BigDecimal.valueOf(v));
					else // component == 1
						this.setI(BigDecimal.valueOf(v));
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						this.setJ(BigDecimal.valueOf(v));
					else // component == 3
						this.setK(BigDecimal.valueOf(v));
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						this.setL(BigDecimal.valueOf(v));
					else // component == 5
						this.setI0(BigDecimal.valueOf(v));
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						this.setJ0(BigDecimal.valueOf(v));
					else // component == 7
						this.setK0(BigDecimal.valueOf(v));
				}
			}
		}
	}

	@Override
	public void primComponentSetLongSafe(IntegerIndex index, int component, long v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						this.setR(BigDecimal.valueOf(v));
					else // component == 1
						this.setI(BigDecimal.valueOf(v));
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						this.setJ(BigDecimal.valueOf(v));
					else // component == 3
						this.setK(BigDecimal.valueOf(v));
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						this.setL(BigDecimal.valueOf(v));
					else // component == 5
						this.setI0(BigDecimal.valueOf(v));
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						this.setJ0(BigDecimal.valueOf(v));
					else // component == 7
						this.setK0(BigDecimal.valueOf(v));
				}
			}
		}
	}

	@Override
	public void primComponentSetFloatSafe(IntegerIndex index, int component, float v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						this.setR(BigDecimal.valueOf(v));
					else // component == 1
						this.setI(BigDecimal.valueOf(v));
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						this.setJ(BigDecimal.valueOf(v));
					else // component == 3
						this.setK(BigDecimal.valueOf(v));
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						this.setL(BigDecimal.valueOf(v));
					else // component == 5
						this.setI0(BigDecimal.valueOf(v));
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						this.setJ0(BigDecimal.valueOf(v));
					else // component == 7
						this.setK0(BigDecimal.valueOf(v));
				}
			}
		}
	}

	@Override
	public void primComponentSetDoubleSafe(IntegerIndex index, int component, double v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						this.setR(BigDecimal.valueOf(v));
					else // component == 1
						this.setI(BigDecimal.valueOf(v));
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						this.setJ(BigDecimal.valueOf(v));
					else // component == 3
						this.setK(BigDecimal.valueOf(v));
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						this.setL(BigDecimal.valueOf(v));
					else // component == 5
						this.setI0(BigDecimal.valueOf(v));
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						this.setJ0(BigDecimal.valueOf(v));
					else // component == 7
						this.setK0(BigDecimal.valueOf(v));
				}
			}
		}
	}

	@Override
	public void primComponentSetBigIntegerSafe(IntegerIndex index, int component, BigInteger v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			if (v.signum() != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						this.setR(new BigDecimal(v));
					else // component == 1
						this.setI(new BigDecimal(v));
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						this.setJ(new BigDecimal(v));
					else // component == 3
						this.setK(new BigDecimal(v));
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						this.setL(new BigDecimal(v));
					else // component == 5
						this.setI0(new BigDecimal(v));
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						this.setJ0(new BigDecimal(v));
					else // component == 7
						this.setK0(new BigDecimal(v));
				}
			}
		}
	}

	@Override
	public void primComponentSetBigDecimalSafe(IntegerIndex index, int component, BigDecimal v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			if (v.signum() != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						this.setR(v);
					else // component == 1
						this.setI(v);
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						this.setJ(v);
					else // component == 3
						this.setK(v);
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						this.setL(v);
					else // component == 5
						this.setI0(v);
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						this.setJ0(v);
					else // component == 7
						this.setK0(v);
				}
			}
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r().byteValue();
				else // component == 1
					return i().byteValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().byteValue();
				else // component == 3
					return k().byteValue();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return l().byteValue();
				else // component == 5
					return i0().byteValue();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return j0().byteValue();
				else // component == 7
					return k0().byteValue();
			}
		}
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r().shortValue();
				else // component == 1
					return i().shortValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().shortValue();
				else // component == 3
					return k().shortValue();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return l().shortValue();
				else // component == 5
					return i0().shortValue();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return j0().shortValue();
				else // component == 7
					return k0().shortValue();
			}
		}
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r().intValue();
				else // component == 1
					return i().intValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().intValue();
				else // component == 3
					return k().intValue();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return l().intValue();
				else // component == 5
					return i0().intValue();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return j0().intValue();
				else // component == 7
					return k0().intValue();
			}
		}
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r().longValue();
				else // component == 1
					return i().longValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().longValue();
				else // component == 3
					return k().longValue();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return l().longValue();
				else // component == 5
					return i0().longValue();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return j0().longValue();
				else // component == 7
					return k0().longValue();
			}
		}
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r().floatValue();
				else // component == 1
					return i().floatValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().floatValue();
				else // component == 3
					return k().floatValue();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return l().floatValue();
				else // component == 5
					return i0().floatValue();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return j0().floatValue();
				else // component == 7
					return k0().floatValue();
			}
		}
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r().doubleValue();
				else // component == 1
					return i().doubleValue();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().doubleValue();
				else // component == 3
					return k().doubleValue();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return l().doubleValue();
				else // component == 5
					return i0().doubleValue();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return j0().doubleValue();
				else // component == 7
					return k0().doubleValue();
			}
		}
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r().toBigInteger();
				else // component == 1
					return i().toBigInteger();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j().toBigInteger();
				else // component == 3
					return k().toBigInteger();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return l().toBigInteger();
				else // component == 5
					return i0().toBigInteger();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return j0().toBigInteger();
				else // component == 7
					return k0().toBigInteger();
			}
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component < 4) {
			if (component < 2) {
				// 0 <= component <= 1
				if (component == 0)
					return r();
				else // component == 1
					return i();
			}
			else {
				// 2 <= component <= 3
				if (component == 2)
					return j();
				else // component == 3
					return k();
			}
		} else {
			// component >= 4
			if (component < 6) {
				// 4 <= component <= 5
				if (component == 4)
					return l();
				else // component == 5
					return i0();
			}
			else {
				// 6 <= component <= 7
				if (component == 6)
					return j0();
				else // component == 7
					return k0();
			}
		}
	}

	@Override
	public byte primComponentGetAsByteSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return r().byteValue();
					else // component == 1
						return i().byteValue();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return j().byteValue();
					else // component == 3
						return k().byteValue();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return l().byteValue();
					else // component == 5
						return i0().byteValue();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return j0().byteValue();
					else // component == 7
						return k0().byteValue();
				}
			}
		}
	}

	@Override
	public short primComponentGetAsShortSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return r().shortValue();
					else // component == 1
						return i().shortValue();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return j().shortValue();
					else // component == 3
						return k().shortValue();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return l().shortValue();
					else // component == 5
						return i0().shortValue();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return j0().shortValue();
					else // component == 7
						return k0().shortValue();
				}
			}
		}
	}

	@Override
	public int primComponentGetAsIntSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return r().intValue();
					else // component == 1
						return i().intValue();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return j().intValue();
					else // component == 3
						return k().intValue();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return l().intValue();
					else // component == 5
						return i0().intValue();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return j0().intValue();
					else // component == 7
						return k0().intValue();
				}
			}
		}
	}

	@Override
	public long primComponentGetAsLongSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return r().longValue();
					else // component == 1
						return i().longValue();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return j().longValue();
					else // component == 3
						return k().longValue();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return l().longValue();
					else // component == 5
						return i0().longValue();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return j0().longValue();
					else // component == 7
						return k0().longValue();
				}
			}
		}
	}

	@Override
	public float primComponentGetAsFloatSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return r().floatValue();
					else // component == 1
						return i().floatValue();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return j().floatValue();
					else // component == 3
						return k().floatValue();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return l().floatValue();
					else // component == 5
						return i0().floatValue();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return j0().floatValue();
					else // component == 7
						return k0().floatValue();
				}
			}
		}
	}

	@Override
	public double primComponentGetAsDoubleSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return r().doubleValue();
					else // component == 1
						return i().doubleValue();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return j().doubleValue();
					else // component == 3
						return k().doubleValue();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return l().doubleValue();
					else // component == 5
						return i0().doubleValue();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return j0().doubleValue();
					else // component == 7
						return k0().doubleValue();
				}
			}
		}
	}

	@Override
	public BigInteger primComponentGetAsBigIntegerSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return BigInteger.ZERO;
		}
		else {
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return r().toBigInteger();
					else // component == 1
						return i().toBigInteger();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return j().toBigInteger();
					else // component == 3
						return k().toBigInteger();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return l().toBigInteger();
					else // component == 5
						return i0().toBigInteger();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return j0().toBigInteger();
					else // component == 7
						return k0().toBigInteger();
				}
			}
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimalSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 7;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return BigDecimal.ZERO;
		}
		else {
			if (component < 4) {
				if (component < 2) {
					// 0 <= component <= 1
					if (component == 0)
						return r();
					else // component == 1
						return i();
				}
				else {
					// 2 <= component <= 3
					if (component == 2)
						return j();
					else // component == 3
						return k();
				}
			} else {
				// component >= 4
				if (component < 6) {
					// 4 <= component <= 5
					if (component == 4)
						return l();
					else // component == 5
						return i0();
				}
				else {
					// 6 <= component <= 7
					if (component == 6)
						return j0();
					else // component == 7
						return k0();
				}
			}
		}
	}

	@Override
	public void primitiveInit() {
		r = i = j = k = l = i0 = j0 = k0 = BigDecimal.ZERO;
	}

	@Override
	public void setR(HighPrecisionMember val) {
		setR(val.v());
	}

	@Override
	public void setI(HighPrecisionMember val) {
		setI(val.v());
	}

	@Override
	public void setJ(HighPrecisionMember val) {
		setJ(val.v());
	}

	@Override
	public void setK(HighPrecisionMember val) {
		setK(val.v());
	}

	@Override
	public void setL(HighPrecisionMember val) {
		setL(val.v());
	}

	@Override
	public void setI0(HighPrecisionMember val) {
		setI0(val.v());
	}

	@Override
	public void setJ0(HighPrecisionMember val) {
		setJ0(val.v());
	}

	@Override
	public void setK0(HighPrecisionMember val) {
		setK0(val.v());
	}

	@Override
	public void getR(HighPrecisionMember v) {
		v.setV(r);
	}

	@Override
	public void getI(HighPrecisionMember v) {
		v.setV(i);
	}

	@Override
	public void getJ(HighPrecisionMember v) {
		v.setV(j);
	}

	@Override
	public void getK(HighPrecisionMember v) {
		v.setV(k);
	}

	@Override
	public void getL(HighPrecisionMember v) {
		v.setV(l);
	}

	@Override
	public void getI0(HighPrecisionMember v) {
		v.setV(i0);
	}

	@Override
	public void getJ0(HighPrecisionMember v) {
		v.setV(j0);
	}

	@Override
	public void getK0(HighPrecisionMember v) {
		v.setV(k0);
	}

	@Override
	public int hashCode() {
		int v = 1;
		v = Hasher.PRIME * v + Hasher.hashCode(r);
		v = Hasher.PRIME * v + Hasher.hashCode(i);
		v = Hasher.PRIME * v + Hasher.hashCode(j);
		v = Hasher.PRIME * v + Hasher.hashCode(k);
		v = Hasher.PRIME * v + Hasher.hashCode(l);
		v = Hasher.PRIME * v + Hasher.hashCode(i0);
		v = Hasher.PRIME * v + Hasher.hashCode(j0);
		v = Hasher.PRIME * v + Hasher.hashCode(k0);
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof OctonionHighPrecisionMember) {
			return G.OHP.isEqual().call(this, (OctonionHighPrecisionMember) o);
		}
		return false;
	}

	@Override
	public void setFromLong(long... vals) {
		setR(BigDecimal.valueOf(vals[0]));
		setI(BigDecimal.valueOf(vals[1]));
		setJ(BigDecimal.valueOf(vals[2]));
		setK(BigDecimal.valueOf(vals[3]));
		setL(BigDecimal.valueOf(vals[4]));
		setI0(BigDecimal.valueOf(vals[5]));
		setJ0(BigDecimal.valueOf(vals[6]));
		setK0(BigDecimal.valueOf(vals[7]));
	}

	@Override
	public void setFromDouble(double... vals) {
		setR(BigDecimal.valueOf(vals[0]));
		setI(BigDecimal.valueOf(vals[1]));
		setJ(BigDecimal.valueOf(vals[2]));
		setK(BigDecimal.valueOf(vals[3]));
		setL(BigDecimal.valueOf(vals[4]));
		setI0(BigDecimal.valueOf(vals[5]));
		setJ0(BigDecimal.valueOf(vals[6]));
		setK0(BigDecimal.valueOf(vals[7]));
	}

	@Override
	public void setFromBigInteger(BigInteger... vals) {
		setR(new BigDecimal(vals[0]));
		setI(new BigDecimal(vals[1]));
		setJ(new BigDecimal(vals[2]));
		setK(new BigDecimal(vals[3]));
		setL(new BigDecimal(vals[4]));
		setI0(new BigDecimal(vals[5]));
		setJ0(new BigDecimal(vals[6]));
		setK0(new BigDecimal(vals[7]));
	}

	@Override
	public void setFromBigDecimal(BigDecimal... vals) {
		setR(vals[0]);
		setI(vals[1]);
		setJ(vals[2]);
		setK(vals[3]);
		setL(vals[4]);
		setI0(vals[5]);
		setJ0(vals[6]);
		setK0(vals[7]);
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		return new BigDecimal[] {r(), i(), j(), k(), l(), i0(), j0(), k0()};
	}
}
