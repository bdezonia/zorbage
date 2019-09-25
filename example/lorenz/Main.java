//
// A zorbage example: visualizing the Lorenz system using jMonkeyEngine.
//
//   See https://en.wikipedia.org/wiki/Lorenz_system
//
//   This code is in the public domain. Use however you wish.
//
package lorenz;

import com.jme3.app.*;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Line;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.ClassicRungeKutta;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.data.float32.real.Float32Member;
import nom.bdezonia.zorbage.type.data.float32.real.Float32VectorMember;
import nom.bdezonia.zorbage.type.storage.datasource.ArrayDataSource;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Main extends SimpleApplication {

	@Override
	public void simpleInitApp() {

		// zorbage code
		
		Procedure3<Float32Member, Float32VectorMember, Float32VectorMember> lorenz =
				new Procedure3<Float32Member, Float32VectorMember, Float32VectorMember>()
		{
			// the Lorenz constants
			
			private final float SIGMA = 10;
			private final float RHO = 28;
			private final float BETA = 8f/3;

			// the derivative equation for y' = f(t,y)
			
			@Override
			public void call(Float32Member t, Float32VectorMember y, Float32VectorMember outputVec) {
				
				// Java's optimizer sees to it that these get built on the stack as primitives

				Float32Member xc = G.FLT.construct();
				Float32Member yc = G.FLT.construct();
				Float32Member zc = G.FLT.construct();
				Float32Member v = G.FLT.construct();
				
				if (y.length() != 4)
					throw new IllegalArgumentException("oops");
				outputVec.alloc(4);
				y.v(0, xc);
				y.v(1, yc);
				y.v(2, zc);
				v.setV(SIGMA * (yc.v()-xc.v()));
				outputVec.setV(0, v);
				v.setV(xc.v() * (RHO-zc.v()) - yc.v());
				outputVec.setV(1, v);
				v.setV(xc.v()*yc.v() - BETA*zc.v());
				outputVec.setV(2, v);
				v.setV(1);
				outputVec.setV(3, v);
			}
		};
		
		// 4d starting location (x, y, z, t)
		Float32VectorMember y0 = G.FLT_VEC.construct("[0.5,0.5,0.1,0]");
		
		// t0 = 0
		Float32Member t0 = G.FLT.construct();
		
		// dt = 1/64
		Float32Member dt = G.FLT.construct(((Double)(1.0 / 64)).toString());
		
		// number of intermediate 4-d points to generate
		int numSteps = 50000;
		
		// the intermediate 4-d points
		IndexedDataSource<Float32VectorMember> outputVecs = ArrayDataSource.construct(G.FLT_VEC, numSteps);
		
		// solve the 4-d differential equation
		ClassicRungeKutta.compute(G.FLT_VEC, G.FLT, lorenz, t0, y0, numSteps, dt, outputVecs);
		
		// format output as JMonkeyEngine wants
		float[] xs = new float[numSteps];
		float[] ys = new float[numSteps];
		float[] zs = new float[numSteps];
		float[] ts = new float[numSteps];
		Float32VectorMember vector = G.FLT_VEC.construct();
		Float32Member component = G.FLT.construct();
		for (int i = 0; i < numSteps; i++) {
			outputVecs.get(i, vector);
			vector.v(0, component);
			xs[i] = component.v();
			vector.v(1, component);
			ys[i] = component.v();
			vector.v(2, component);
			zs[i] = component.v();
			vector.v(3, component);
			ts[i] = component.v();
		}
		
		// jMonkeyEngine code
		
		Node origin = new Node("origin");
		Material mat = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
		mat.setColor("Color", ColorRGBA.Blue);  // set color of material to blue
		for (int i = 1; i < numSteps; i++) {
			Vector3f start = new Vector3f(xs[i-1], ys[i-1], zs[i-1]);
			Vector3f end = new Vector3f(xs[i], ys[i], zs[i]);
			Line l = new Line(start, end);
			Geometry geom = new Geometry("Line", l);  // create geometry from the line
			geom.setMaterial(mat);  // set the line's material
			origin.attachChild(geom);
		}
		rootNode.attachChild(origin);  // make the geometry appear in the scene
	}

	public static void main(String[] args) {
		Main app = new Main();
		app.start();  // start the viewer
	}

}
