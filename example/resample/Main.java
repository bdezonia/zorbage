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
import nom.bdezonia.zorbage.algorithm.ResampleAveragedCubics;
import nom.bdezonia.zorbage.algorithm.ResampleAveragedLinears;
import nom.bdezonia.zorbage.algorithm.ResampleNearestNeighbor;
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
		Tuple3<BufferedImage,BufferedImage,BufferedImage> resamplings = resample(img);
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(new JLabel(new ImageIcon(img)));
		panel.add(new JLabel(new ImageIcon(resamplings.a())));
		panel.add(new JLabel(new ImageIcon(resamplings.b())));
		panel.add(new JLabel(new ImageIcon(resamplings.c())));
		JScrollPane scrollPane = new JScrollPane(panel);
		frame.setContentPane(scrollPane);
		frame.pack();
		frame.setVisible(true);
	}

	private static MultiDimDataSource<Float32Member> toFloat32(BufferedImage in) {
		int rows = in.getHeight();
		int cols = in.getWidth();
		Float32Member value = G.FLT.construct();
		MultiDimDataSource<Float32Member> nums = MultiDimStorage.allocate(new long[] {cols,rows}, value);
		IntegerIndex idx = new IntegerIndex(2);
		for (int r = 0; r < rows; r++) {
			idx.set(1, r);
			for (int c = 0; c < cols; c++) {
				idx.set(0, c);
				int rgb = in.getRGB(c,r);
				int R = (rgb & 0xff0000) >> 16;
				int G = (rgb & 0x00ff00) >> 8;
				int B = (rgb & 0x0000ff) >> 0;
				double luminance = 0.2126*R + 0.7152*G + 0.0722*B;
				if (luminance < 0) luminance = 0;
				if (luminance > 255) luminance = 255;
				value.setV((float) luminance);
				nums.set(idx, value);
			}
		}
		return nums;
	}
	
	private static BufferedImage toBufferedImage(MultiDimDataSource<Float32Member> in) {
		int rows = (int) in.dimension(1);
		int cols = (int) in.dimension(0);
		BufferedImage img = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
		Float32Member value = G.FLT.construct();
		IntegerIndex idx = new IntegerIndex(2);
		for (int r = 0; r < rows; r++) {
			idx.set(1, r);
			for (int c = 0; c < cols; c++) {
				idx.set(0, c);
				in.get(idx, value);
				int component = (int) value.v();
				int rgb = ((component << 16) & 0xff0000);
				rgb |= (component << 8) & 0x00ff00;
				rgb |= (component << 0) & 0x0000ff;
				img.setRGB(c, r, rgb);
			}
		}
		return img;
	}
	
	private static Tuple3<BufferedImage,BufferedImage,BufferedImage>
		resample(BufferedImage imageData)
	{
		MultiDimDataSource<Float32Member> input = toFloat32(imageData);
		MirrorNdOOB<Float32Member> mirror = new MirrorNdOOB<>(input);
		ProcedurePaddedMultiDimDataSource<Float32Algebra,Float32Member> padded
			= new ProcedurePaddedMultiDimDataSource<Float32Algebra, Float32Member>(G.FLT, input, mirror);
		long[] dims = new long[] {200, 200};
		MultiDimDataSource<Float32Member> nn = ResampleNearestNeighbor.compute(G.FLT, dims, padded);
		MultiDimDataSource<Float32Member> linear = ResampleAveragedLinears.compute(G.FLT, dims, padded);
		MultiDimDataSource<Float32Member> cubic = ResampleAveragedCubics.compute(G.FLT, dims, padded);
		BufferedImage nn_img = toBufferedImage(nn);
		BufferedImage linear_img = toBufferedImage(linear);
		BufferedImage cubic_img = toBufferedImage(cubic);
		return new Tuple3<>(nn_img, linear_img, cubic_img);
	}
}
