//
// A zorbage example: Calculating the FFT planes of an image.
//
//   Instructions: Use the file chooser to pick an image and see the real and imaginary planes of the FFT.
//
//   This code is in the public domain. Use however you wish.
//
package fft;

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

import nom.bdezonia.zorbage.algorithm.FFT;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.float64.complex.ComplexFloat64Member;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

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
		Tuple2<BufferedImage,BufferedImage> planes = calcFftPlanes(img);
		BufferedImage imgR = planes.a();
		BufferedImage imgI = planes.b();
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(new JLabel(new ImageIcon(img)));
		panel.add(new JLabel(new ImageIcon(imgR)));
		panel.add(new JLabel(new ImageIcon(imgI)));
		JScrollPane scrollPane = new JScrollPane(panel);
		frame.setContentPane(scrollPane);
		frame.pack();
		frame.setVisible(true);
	}
	
	private static Tuple2<BufferedImage,BufferedImage> calcFftPlanes(BufferedImage img) {
		long size = FFT.enclosingPowerOf2(img.getHeight() * img.getWidth());
		long subSize = (long) Math.ceil(Math.sqrt(size));
		ComplexFloat64Member value = G.CDBL.construct();
		IndexedDataSource<ComplexFloat64Member> inData = Storage.allocate(size, value);
		IndexedDataSource<ComplexFloat64Member> outData = Storage.allocate(size, value);
		for (int r = 0, i = 0; r < img.getHeight(); r++) {
			for (int c = 0; c < img.getWidth(); c++, i++) {
				int rgb = img.getRGB(c, r);
				int R = (rgb & 0x00ff0000) >> 16;
				int G = (rgb & 0x0000ff00) >> 8;
				int B = (rgb & 0x000000ff) >> 0;
				double luminance = 0.2126*R + 0.7152*G + 0.0722*B;
				value.setR(luminance);
				value.setI(0);
				inData.set(i, value);
			}
		}
		FFT.compute(G.CDBL, G.DBL, inData, outData);
		BufferedImage planeR = new BufferedImage((int) subSize, (int) subSize, BufferedImage.TYPE_INT_ARGB);
		BufferedImage planeI = new BufferedImage((int) subSize, (int) subSize, BufferedImage.TYPE_INT_ARGB);
		double min = Double.POSITIVE_INFINITY;
		double max = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < outData.size(); i++) {
			outData.get(i, value);
			if (value.r() < min) min = value.r();
			if (value.i() < min) min = value.i();
			if (value.r() > max) max = value.r();
			if (value.i() > max) max = value.i();
		}
		// deal with outliers
		if (min < -1000) min = -1000;
		if (max > 1000) max = 1000;
		int r = 0;
		int c = 0;
		for (int i = 0; i < outData.size(); i++) {
			outData.get(i, value);
			int scaledR = (int) Math.round(255.0 * (value.r() - min) / (max - min));
			int scaledI = (int) Math.round(255.0 * (value.i() - min) / (max - min));
			// deal with outliers
			if (scaledR < 0) scaledR = 0;
			if (scaledR > 255) scaledR = 255;
			if (scaledI < 0) scaledI = 0;
			if (scaledI > 255) scaledI = 255;
			int rArgb = (0x7f000000) | (scaledR << 16) | (scaledR << 8) | (scaledR << 0);
			int iArgb = (0x7f000000) | (scaledI << 16) | (scaledI << 8) | (scaledI << 0);
			planeR.setRGB(c, r, rArgb);
			planeI.setRGB(c, r, iArgb);
			c++;
			if (c >= subSize) {
				c = 0;
				r++;
			}
		}
		return new Tuple2<>(planeR, planeI);
	}
}