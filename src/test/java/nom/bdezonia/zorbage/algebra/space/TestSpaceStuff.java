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
package nom.bdezonia.zorbage.algebra.space;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.real.float64.Float64Algebra;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

@SuppressWarnings("unused")
/**
 * @author Barry DeZonia
 */
public class TestSpaceStuff {

	private class Aff
		implements AffineSpace<Float64Algebra, Float64Member, Float64Member>
	{

		@Override
		public boolean isHomogeneous() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public long inductiveDimension() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long lebesgueCoveringDimension() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isCompact() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isLinear() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Procedure3<List<Float64Member>, List<Float64Member>, Float64Member> distance() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public double hausdorffDimension() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isComplete() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isSmooth() {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	private class Ban
		implements BanachSpace<Float64Algebra, Float64Member, Float64Member>
	{

		@Override
		public Procedure2<Float64Member, Float64Member> norm() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long inductiveDimension() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long lebesgueCoveringDimension() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isCompact() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isLinear() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Procedure3<List<Float64Member>, List<Float64Member>, Float64Member> distance() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public double hausdorffDimension() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isComplete() {
			// TODO Auto-generated method stub
			return false;
		}
	
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
	}

}
