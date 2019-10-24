//
// A zorbage example: Resampling.
//
//   Read an image and display it and three different resamplings
//
//   This code is in the public domain. Use however you wish.
//
package resample;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.ParallelResampleAveragedCubics;
import nom.bdezonia.zorbage.algorithm.ParallelResampleAveragedLinears;
import nom.bdezonia.zorbage.algorithm.ParallelResampleNearestNeighbor;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.multidim.MultiDimDataSource;
import nom.bdezonia.zorbage.multidim.MultiDimStorage;
import nom.bdezonia.zorbage.multidim.ProcedurePaddedMultiDimDataSource;
import nom.bdezonia.zorbage.oob.nd.MirrorNdOOB;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.tuple.Tuple3;
import nom.bdezonia.zorbage.type.data.float32.real.Float32Algebra;
import nom.bdezonia.zorbage.type.data.float32.real.Float32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Main {

	private static final long[] DIMS = new long[] {300,300};
	
	public static void main(String[] args) {
		File file = null;
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
		}
		BufferedImage img = null;
		try {
			img = ImageIO.read(file);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		Tuple3<MultiDimDataSource<Float32Member>,
				MultiDimDataSource<Float32Member>,
				MultiDimDataSource<Float32Member>>
			inputs = toFloat32(img);
		BufferedImage resamplingNN = resampleNN(inputs);
		BufferedImage resamplingLinear = resampleLinear(inputs);
		BufferedImage resamplingCubic = resampleCubic(inputs);
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(new JLabel(new ImageIcon(img)));
		panel.add(new JLabel(new ImageIcon(resamplingNN)));
		panel.add(new JLabel(new ImageIcon(resamplingLinear)));
		panel.add(new JLabel(new ImageIcon(resamplingCubic)));
		JScrollPane scrollPane = new JScrollPane(panel);
		frame.setContentPane(scrollPane);
		frame.pack();
		frame.setVisible(true);
	}

	private static Tuple3<MultiDimDataSource<Float32Member>,
							MultiDimDataSource<Float32Member>,
							MultiDimDataSource<Float32Member>>
		toFloat32(BufferedImage in)
	{
		int rows = in.getHeight();
		int cols = in.getWidth();
		Float32Member value = G.FLT.construct();
		MultiDimDataSource<Float32Member> rs = MultiDimStorage.allocate(new long[] {cols,rows}, value);
		MultiDimDataSource<Float32Member> gs = MultiDimStorage.allocate(new long[] {cols,rows}, value);
		MultiDimDataSource<Float32Member> bs = MultiDimStorage.allocate(new long[] {cols,rows}, value);
		IntegerIndex idx = new IntegerIndex(2);
		for (int r = 0; r < rows; r++) {
			idx.set(1, r);
			for (int c = 0; c < cols; c++) {
				idx.set(0, c);
				int rgb = in.getRGB(c, r);
				int R = (rgb & 0xff0000) >> 16;
				int G = (rgb & 0x00ff00) >> 8;
				int B = (rgb & 0x0000ff) >> 0;
				value.setV(R);
				rs.set(idx, value);
				value.setV(G);
				gs.set(idx, value);
				value.setV(B);
				bs.set(idx, value);
			}
		}
		return new Tuple3<>(rs,gs,bs);
	}
	
	private static BufferedImage toBufferedImage(MultiDimDataSource<Float32Member> rs,
													MultiDimDataSource<Float32Member> gs,
													MultiDimDataSource<Float32Member> bs)
	{
		int rows = (int) rs.dimension(1);
		int cols = (int) rs.dimension(0);
		BufferedImage img = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
		Float32Member value = G.FLT.construct();
		IntegerIndex idx = new IntegerIndex(2);
		for (int r = 0; r < rows; r++) {
			idx.set(1, r);
			for (int c = 0; c < cols; c++) {
				int component = 0;
				int rgb = 0;
				idx.set(0, c);
				rs.get(idx, value);
				component = Math.round(value.v());
				rgb = ((component << 16) & 0xff0000);
				gs.get(idx, value);
				component = Math.round(value.v());
				rgb |= (component << 8) & 0x00ff00;
				bs.get(idx, value);
				component = Math.round(value.v());
				rgb |= (component << 0) & 0x0000ff;
				img.setRGB(c, r, rgb);
			}
		}
		return img;
	}
	
	private static BufferedImage resampleNN(Tuple3<MultiDimDataSource<Float32Member>,MultiDimDataSource<Float32Member>,MultiDimDataSource<Float32Member>> inputs)
	{
		return resampleRGB(RESAMPLE_NN, inputs);
	}
	
	private static BufferedImage resampleLinear(Tuple3<MultiDimDataSource<Float32Member>,MultiDimDataSource<Float32Member>,MultiDimDataSource<Float32Member>> inputs)
	{
		return resampleRGB(RESAMPLE_LINEAR, inputs);
	}
	
	private static BufferedImage resampleCubic(Tuple3<MultiDimDataSource<Float32Member>,MultiDimDataSource<Float32Member>,MultiDimDataSource<Float32Member>> inputs)
	{
		return resampleRGB(RESAMPLE_CUBIC, inputs);
	}

	private static BufferedImage
		resampleRGB(Function3<MultiDimDataSource<Float32Member>,Float32Algebra,long[],MultiDimDataSource<Float32Member>> algo,
						Tuple3<MultiDimDataSource<Float32Member>,MultiDimDataSource<Float32Member>,MultiDimDataSource<Float32Member>> inputs)
	{
		MultiDimDataSource<Float32Member> r, g, b;
		r = resample(algo, inputs.a());
		g = resample(algo, inputs.b());
		b = resample(algo, inputs.c());
		return toBufferedImage(r,g,b);
	}
	
	private static MultiDimDataSource<Float32Member>
		resample(Function3<MultiDimDataSource<Float32Member>,Float32Algebra,long[],MultiDimDataSource<Float32Member>> algo,
					MultiDimDataSource<Float32Member> input)
	{
		MirrorNdOOB<Float32Member> mirror = new MirrorNdOOB<Float32Member>(input);
		MultiDimDataSource<Float32Member> padded =
				new ProcedurePaddedMultiDimDataSource<Float32Algebra, Float32Member>(G.FLT, input, mirror);
		return algo.call(G.FLT, DIMS, padded);
	}

	private static Function3<MultiDimDataSource<Float32Member>,Float32Algebra,long[],MultiDimDataSource<Float32Member>> RESAMPLE_NN
		= new Function3<MultiDimDataSource<Float32Member>, Float32Algebra, long[], MultiDimDataSource<Float32Member>>()
	{
		@Override
		public MultiDimDataSource<Float32Member> call(Float32Algebra alg, long[] dims,
				MultiDimDataSource<Float32Member> data)
		{
			return ParallelResampleNearestNeighbor.compute(alg, dims, data);
		}
	};
	
	private static Function3<MultiDimDataSource<Float32Member>,Float32Algebra,long[],MultiDimDataSource<Float32Member>> RESAMPLE_LINEAR
		= new Function3<MultiDimDataSource<Float32Member>, Float32Algebra, long[], MultiDimDataSource<Float32Member>>()
	{
		@Override
		public MultiDimDataSource<Float32Member> call(Float32Algebra alg, long[] dims,
				MultiDimDataSource<Float32Member> data)
		{
			return ParallelResampleAveragedLinears.compute(alg, dims, data);
		}
	};
	

	private static Function3<MultiDimDataSource<Float32Member>,Float32Algebra,long[],MultiDimDataSource<Float32Member>> RESAMPLE_CUBIC
		= new Function3<MultiDimDataSource<Float32Member>, Float32Algebra, long[], MultiDimDataSource<Float32Member>>()
	{
		@Override
		public MultiDimDataSource<Float32Member> call(Float32Algebra alg, long[] dims,
				MultiDimDataSource<Float32Member> data)
		{
			return ParallelResampleAveragedCubics.compute(alg, dims, data);
		}
	};
 }
