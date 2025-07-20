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
package nom.bdezonia.zorbage.type.universal;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.G;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class AlgebraLibrary {

	private static Set<Algebra<?,?>> algebras = new HashSet<>();

	/**
	 * No matter how many libraries are constructed they only
	 * update the singleton algebras data structure.
	 */
	public AlgebraLibrary() {
		
		registerAlgebra(G.ARGB);
		registerAlgebra(G.BOOL);
		registerAlgebra(G.CDBL);
		registerAlgebra(G.CDBL_MAT);
		registerAlgebra(G.CDBL_TEN);
		registerAlgebra(G.CDBL_VEC);
		registerAlgebra(G.CFLT);
		registerAlgebra(G.CFLT_MAT);
		registerAlgebra(G.CFLT_TEN);
		registerAlgebra(G.CFLT_VEC);
		registerAlgebra(G.CHAIN);
		registerAlgebra(G.CHAR);
		registerAlgebra(G.CHLF);
		registerAlgebra(G.CHLF_MAT);
		registerAlgebra(G.CHLF_TEN);
		registerAlgebra(G.CHLF_VEC);
		registerAlgebra(G.CHP);
		registerAlgebra(G.CHP_MAT);
		registerAlgebra(G.CHP_TEN);
		registerAlgebra(G.CHP_VEC);
		registerAlgebra(G.CIEXYZ);
		registerAlgebra(G.CQUAD);
		registerAlgebra(G.CQUAD_MAT);
		registerAlgebra(G.CQUAD_TEN);
		registerAlgebra(G.CQUAD_VEC);
		registerAlgebra(G.DBL);
		registerAlgebra(G.DBL_MAT);
		registerAlgebra(G.DBL_TEN);
		registerAlgebra(G.DBL_VEC);
		registerAlgebra(G.FLT);
		registerAlgebra(G.FLT_MAT);
		registerAlgebra(G.FLT_TEN);
		registerAlgebra(G.FLT_VEC);
		registerAlgebra(G.FSTRING);
		registerAlgebra(G.GAUSS16);
		registerAlgebra(G.GAUSS32);
		registerAlgebra(G.GAUSS64);
		registerAlgebra(G.GAUSS8);
		registerAlgebra(G.GAUSSU);
		registerAlgebra(G.HLF);
		registerAlgebra(G.HLF_MAT);
		registerAlgebra(G.HLF_TEN);
		registerAlgebra(G.HLF_VEC);
		registerAlgebra(G.HP);
		registerAlgebra(G.HP_MAT);
		registerAlgebra(G.HP_TEN);
		registerAlgebra(G.HP_VEC);
		registerAlgebra(G.INT1);
		registerAlgebra(G.INT10);
		registerAlgebra(G.INT11);
		registerAlgebra(G.INT12);
		registerAlgebra(G.INT128);
		registerAlgebra(G.INT13);
		registerAlgebra(G.INT14);
		registerAlgebra(G.INT15);
		registerAlgebra(G.INT16);
		registerAlgebra(G.INT2);
		registerAlgebra(G.INT3);
		registerAlgebra(G.INT32);
		registerAlgebra(G.INT4);
		registerAlgebra(G.INT5);
		registerAlgebra(G.INT6);
		registerAlgebra(G.INT64);
		registerAlgebra(G.INT7);
		registerAlgebra(G.INT8);
		registerAlgebra(G.INT9);
		registerAlgebra(G.ODBL);
		registerAlgebra(G.ODBL_MAT);
		registerAlgebra(G.ODBL_RMOD);
		registerAlgebra(G.ODBL_TEN);
		registerAlgebra(G.OFLT);
		registerAlgebra(G.OFLT_MAT);
		registerAlgebra(G.OFLT_RMOD);
		registerAlgebra(G.OFLT_TEN);
		registerAlgebra(G.OHLF);
		registerAlgebra(G.OHLF_MAT);
		registerAlgebra(G.OHLF_RMOD);
		registerAlgebra(G.OHLF_TEN);
		registerAlgebra(G.OHP);
		registerAlgebra(G.OHP_MAT);
		registerAlgebra(G.OHP_RMOD);
		registerAlgebra(G.OHP_TEN);
		registerAlgebra(G.OQUAD);
		registerAlgebra(G.OQUAD_MAT);
		registerAlgebra(G.OQUAD_RMOD);
		registerAlgebra(G.OQUAD_TEN);
		registerAlgebra(G.POINT);
		registerAlgebra(G.QDBL);
		registerAlgebra(G.QDBL_MAT);
		registerAlgebra(G.QDBL_RMOD);
		registerAlgebra(G.QDBL_TEN);
		registerAlgebra(G.QFLT);
		registerAlgebra(G.QFLT_MAT);
		registerAlgebra(G.QFLT_RMOD);
		registerAlgebra(G.QFLT_TEN);
		registerAlgebra(G.QHLF);
		registerAlgebra(G.QHLF_MAT);
		registerAlgebra(G.QHLF_RMOD);
		registerAlgebra(G.QHLF_TEN);
		registerAlgebra(G.QHP);
		registerAlgebra(G.QHP_MAT);
		registerAlgebra(G.QHP_RMOD);
		registerAlgebra(G.QHP_TEN);
		registerAlgebra(G.QQUAD);
		registerAlgebra(G.QQUAD_MAT);
		registerAlgebra(G.QQUAD_RMOD);
		registerAlgebra(G.QQUAD_TEN);
		registerAlgebra(G.QUAD);
		registerAlgebra(G.QUAD_MAT);
		registerAlgebra(G.QUAD_TEN);
		registerAlgebra(G.QUAD_VEC);
		registerAlgebra(G.RAT);
		registerAlgebra(G.RGB);
		registerAlgebra(G.STRING);
		registerAlgebra(G.UINT1);
		registerAlgebra(G.UINT10);
		registerAlgebra(G.UINT11);
		registerAlgebra(G.UINT12);
		registerAlgebra(G.UINT128);
		registerAlgebra(G.UINT13);
		registerAlgebra(G.UINT14);
		registerAlgebra(G.UINT15);
		registerAlgebra(G.UINT16);
		registerAlgebra(G.UINT2);
		registerAlgebra(G.UINT3);
		registerAlgebra(G.UINT32);
		registerAlgebra(G.UINT4);
		registerAlgebra(G.UINT5);
		registerAlgebra(G.UINT6);
		registerAlgebra(G.UINT64);
		registerAlgebra(G.UINT7);
		registerAlgebra(G.UINT8);
		registerAlgebra(G.UINT9);
		registerAlgebra(G.UNBOUND);
	}
	
	/**
	 * Allow anyone to add an Algebra to the AlgebraLibrary. This
	 * makes the zorbage type system extensible.
	 * 
	 * @param alg The Algebra you'd like to register with the system.
	 */
	public void registerAlgebra(Algebra<?,?> alg) {
		
		algebras.add(alg);
	}
	
	/**
	 * Query the AlbegraLibrary to find Algebras that implement
	 * the multiple interfaces/classes passed as method parameters.
	 * 
	 * @param interfaces The collection of interfaces that we want
	 *                   any found Algebra to implement.
	 * 
	 * @return A List of Algebras that satisfy the user passed constraints.
	 */
	
	public <T extends Algebra<T,U>, U>
	
		List<Algebra<?,?>>
	
			findAlgebras(Class<?>[] algebraInterfaces)
	{
		
		List<Algebra<?,?>> candidates = new LinkedList<>();
		
		for (Algebra<?,?> alg : algebras) {

			boolean implementsAll = true;
			
			for (Class<?> iface : algebraInterfaces) {
				
				if (!iface.isAssignableFrom(alg.getClass())) {
					
					implementsAll = false;
					
					break;
				}
			}
			
			if (implementsAll) {
				
				candidates.add(alg);
			}
		}
		
		return candidates;
	}

	/**
	 * 
	 * @param algebraInterfaces
	 * @param typeInterfaces
	 * @return
	 */
	public <T extends Algebra<T,U>, U>
	
		List<Algebra<?,?>> findAlgebras(Class<?>[] algebraInterfaces, Class<?>[] typeInterfaces)
	{
		List<Algebra<?,?>> earlyCandidates = findAlgebras(algebraInterfaces);
		
		List<Algebra<?,?>> lateCandidates = new LinkedList<>();

		Iterator<Algebra<?,?>> iter = earlyCandidates.iterator();
		
		while (iter.hasNext()) {

			Algebra<?,?> theAlg = iter.next();
			
			@SuppressWarnings("unchecked")
			T alg = (T) theAlg;
			
			U type = alg.construct();
			
			boolean implementsAll = true;

			for (Class<?> iface : typeInterfaces) {
				
				if (!iface.isAssignableFrom(type.getClass())) {
					
					implementsAll = false;
					
					break;
				}
			}
			
			if (implementsAll) {
				
				lateCandidates.add(alg);
			}
		}
		
		return lateCandidates;
	}
}
