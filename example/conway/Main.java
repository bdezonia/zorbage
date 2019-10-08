//
// A zorbage example: Conway's Game of Life.
//
//   See https://en.wikipedia.org/wiki/Conway's_Game_of_Life
//
//   This code is in the public domain. Use however you wish.
//
package conway;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.multidim.MultiDimDataSource;
import nom.bdezonia.zorbage.multidim.MultiDimStorage;
import nom.bdezonia.zorbage.multidim.ProcedurePaddedMultiDimDataSource;
import nom.bdezonia.zorbage.oob.nd.CyclicNdOOB;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.data.int1.UnsignedInt1Algebra;
import nom.bdezonia.zorbage.type.data.int1.UnsignedInt1Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Main {

	private static final int GENERATIONS = 20;
	private static final int ROWS = 25;
	private static final int COLS = 80;
	
	public static void main(String[] args) {
		UnsignedInt1Member value = G.UINT1.construct();
		MultiDimDataSource<UnsignedInt1Member> img1 = MultiDimStorage.allocate(new long[] {COLS,ROWS}, value);
		MultiDimDataSource<UnsignedInt1Member> img2 = MultiDimStorage.allocate(new long[] {COLS,ROWS}, value);
		CyclicNdOOB<UnsignedInt1Member> oob1 = new CyclicNdOOB<UnsignedInt1Member>(img1);
		CyclicNdOOB<UnsignedInt1Member> oob2 = new CyclicNdOOB<UnsignedInt1Member>(img2);
		MultiDimDataSource<UnsignedInt1Member> frame1 = new ProcedurePaddedMultiDimDataSource<UnsignedInt1Algebra,UnsignedInt1Member>(G.UINT1, img1, oob1);
		MultiDimDataSource<UnsignedInt1Member> frame2 = new ProcedurePaddedMultiDimDataSource<UnsignedInt1Algebra,UnsignedInt1Member>(G.UINT1, img2, oob2);
		initData(frame1);
		displayFrame(frame1);
		for (int i = 0; i < GENERATIONS; i++) {
			MultiDimDataSource<UnsignedInt1Member> dstFrame = i % 2 == 0 ? frame2 : frame1;
			MultiDimDataSource<UnsignedInt1Member> srcFrame = i % 2 == 0 ? frame1 : frame2;
			evolve(srcFrame, dstFrame);
			displayFrame(dstFrame);
		}
	}
	
	private static void initData(MultiDimDataSource<UnsignedInt1Member> frame) {
		UnsignedInt1Member value = G.UINT1.construct();
		IntegerIndex idx = new IntegerIndex(2);
		for (int r = 0; r < ROWS; r++) {
			idx.set(1, r);
			for (int c = 0; c < COLS; c++) {
				idx.set(0, c);
				G.UINT1.random().call(value);
				frame.set(idx, value);
			}
		}
	}
	
	private static void displayFrame(MultiDimDataSource<UnsignedInt1Member> frame) {
		UnsignedInt1Member value = G.UINT1.construct();
		for (int c = 0; c < COLS; c++) {
			System.out.print("-");
		}
		System.out.println();
		IntegerIndex idx = new IntegerIndex(2);
		for (int r = 0; r < ROWS; r++) {
			idx.set(1, r);
			for (int c = 0; c < COLS; c++) {
				idx.set(0, c);
				frame.get(idx, value);
				if (value.v() == 1)
					System.out.print("o");
				else
					System.out.print(" ");
			}
			System.out.println();
		}
	}

	private static void evolve(MultiDimDataSource<UnsignedInt1Member> srcFrame,
								MultiDimDataSource<UnsignedInt1Member> dstFrame)
	{
		UnsignedInt1Member alive = new UnsignedInt1Member(1);
		UnsignedInt1Member dead = new UnsignedInt1Member(0);
		UnsignedInt1Member currState = new UnsignedInt1Member();
		IntegerIndex idx = new IntegerIndex(2);
		for (int r = 0; r < ROWS; r++) {
			idx.set(1, r);
			for (int c = 0; c < COLS; c++) {
				idx.set(0, c);
				srcFrame.get(idx, currState);
				int numNeighs = numNeighs(srcFrame, idx);
				UnsignedInt1Member nextState;
				switch(numNeighs) {
					case 0:
						nextState = dead;
						break;
					case 1:
						nextState = dead;
						break;
					case 2:
						nextState = currState;
						break;
					case 3:
						nextState = alive;
						break;
					case 4:
						nextState = dead;
						break;
					case 5:
						nextState = dead;
						break;
					case 6:
						nextState = dead;
						break;
					case 7:
						nextState = dead;
						break;
					case 8:
						nextState = dead;
						break;
					default:
						throw new IllegalArgumentException("oops");
				}
				dstFrame.set(idx, nextState);
			}
		}
	}
	
	private static int numNeighs(MultiDimDataSource<UnsignedInt1Member> frame, IntegerIndex idx) {
		UnsignedInt1Member value = G.UINT1.construct();
		int neighs = 0;
		IntegerIndex tmpIdx = new IntegerIndex(2);

		long r = idx.get(1);
		long c = idx.get(0);
		
		tmpIdx.set(0, c-1);
		tmpIdx.set(1, r-1);
		frame.get(tmpIdx, value);
		if (value.v() == 1) neighs++;

		tmpIdx.set(0, c-1);
		tmpIdx.set(1, r);
		frame.get(tmpIdx, value);
		if (value.v() == 1) neighs++;

		tmpIdx.set(0, c-1);
		tmpIdx.set(1, r+1);
		frame.get(tmpIdx, value);
		if (value.v() == 1) neighs++;

		tmpIdx.set(0, c);
		tmpIdx.set(1, r-1);
		frame.get(tmpIdx, value);
		if (value.v() == 1) neighs++;

		tmpIdx.set(0, c);
		tmpIdx.set(1, r+1);
		frame.get(tmpIdx, value);
		if (value.v() == 1) neighs++;

		tmpIdx.set(0, c+1);
		tmpIdx.set(1, r-1);
		frame.get(tmpIdx, value);
		if (value.v() == 1) neighs++;

		tmpIdx.set(0, c+1);
		tmpIdx.set(1, r);
		frame.get(tmpIdx, value);
		if (value.v() == 1) neighs++;

		tmpIdx.set(0, c+1);
		tmpIdx.set(1, r+1);
		frame.get(tmpIdx, value);
		if (value.v() == 1) neighs++;
		
		return neighs;
	}
}