/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.algebra.type.markers;

/**
 * @author Barry DeZonia
 */
public interface CompoundType {

	// Dictionary definitions: a Compound is something that
	// is composed or made up of different elements or parts
	// in a particular/specific ratio
	
	// yes: a quaternion, a complex number, an rgb color, etc
	
	// no: a vector or matrix or tensor
	//   one, their sizes vary
	//   two they are considered a CompositeType
	
	// you can have a CompositeType made a CompoundTypes
	//   for instance a matrix (a CompositeType) of complex numbers
	//   (a CompoundType)
	
	// in other words a CompoundType is a U that has components
	
	// TODO: improve the type inference we support. Imagine we have
	//   a vector of complex doubles. We should query the outer algebra
	//   to see it is a composite type. And it is composed of the
	//   CompoundType complex number. Use the outer algebra to somehow
	//   get the inner algebra of the compound type made of Float64s.
	//   As it is defined now this is not really connected. Complex
	//   numbers are not CompoundTypes of Float64s. We need more
	//   expansive generics. Also note you could have a composite type
	//   of a compound type and that compound type might have a 
	//   composite type in it etc. This can get arbitrarily complex.
	//   And a smart type inference engine could figure out the best
	//   way to display such complex data.
}
