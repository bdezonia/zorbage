package zorbage.example;

import zorbage.type.ctor.MemoryConstruction;
import zorbage.type.ctor.StorageConstruction;
import zorbage.type.data.Float64Group;
import zorbage.type.data.Float64Matrix;
import zorbage.type.data.Float64MatrixMember;
import zorbage.type.data.Float64Member;

public class BigMatrixExample {

	public void run() {
		System.out.println("Making a huge virtual matrix > 2 gig entries");
		Float64Matrix g = new Float64Matrix();
		Float64Group g2 = new Float64Group();
		Float64MatrixMember m =
			new Float64MatrixMember(MemoryConstruction.DENSE, StorageConstruction.FILE, 50000, 50000);
		g.unity(m);
		Float64Member one = new Float64Member(1);
		Float64Member zero = new Float64Member(0);
		Float64Member value = new Float64Member();
		for (long r = 0; r < m.rows(); r++) {
			for (long c = 0; c < m.cols(); c++) {
				m.v(r, c, value);
				if (r == c) {
					if (!g2.isEqual(value, one))
						System.out.println("data mismatch error: not one");
				}
				else {
					if (!g2.isEqual(value, zero))
						System.out.println("data mismatch error: not zero");
				}
			}
		}
		System.out.println("  Success.");
	}
}
