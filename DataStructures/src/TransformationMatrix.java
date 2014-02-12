
/**
 * A class that can transform 3D coordinates. It supports rotation, scaling and translation.
 * 
 * @author Jay Fleischer
 * @version 1.0 (1-25-14)
 */
public class TransformationMatrix {
	/**
	 * The identity transformation
	 */
	public static final TransformationMatrix IDENTITY = new TransformationMatrix();

	private static double[][] multiply(double[][] mat1, double[][] mat2) {
		if (mat1[0].length != mat2.length)
			throw new IllegalArgumentException();
		double[][] result = new double[mat1.length][mat2[0].length];
		for (int i = 0; i < mat1.length; i++)
			for (int i2 = 0; i2 < mat2[0].length; i2++)
				for (int i3 = 0; i3 < mat1[0].length; i3++)
					result[i][i2] += mat1[i][i3] * mat2[i3][i2];
		return result;
	}

	private double matrix[][];

	/**
	 * Creates a Transformation Matrix that does nothing to the input coordinates. (The identity matrix)
	 */
	public TransformationMatrix() {
		matrix = new double[][] {
				{1, 0, 0, 0},
				{0, 1, 0, 0},
				{0, 0, 1, 0},
				{0, 0, 0, 1}};
	}

	/**
	 * Creates a Transformation Matrix using the passed matrix
	 * 
	 * @param matrix
	 *            - a 4x4 double[][]
	 */
	public TransformationMatrix(double[][] matrix) {
		this.matrix = matrix;
	}

	/**
	 * Appends the passed matrix to this matrix
	 * 
	 * @param matrix
	 *            - the matrix to transform this matrix with
	 * @return this, for method chaining
	 */
	public TransformationMatrix append(TransformationMatrix matrix) {
		this.matrix = multiply(this.matrix, matrix.matrix);
		return this;
	}

	/**
	 * Creates a new transformation matrix that represents the transformation of this matrix multiplied by the other
	 * matrix
	 * 
	 * @param matrix
	 *            - the matrix
	 * @return the new matrix
	 */
	public TransformationMatrix combine(TransformationMatrix matrix) {
		return new TransformationMatrix(multiply(this.matrix, matrix.matrix));
	}

	/**
	 * Prepends the passed matrix to this matrix
	 * 
	 * @param matrix
	 *            - the matrix to transform this matrix with
	 * @return this, for method chaining
	 */
	public TransformationMatrix prepend(TransformationMatrix matrix) {
		this.matrix = multiply(matrix.matrix, this.matrix);
		return this;
	}

	/**
	 * Rotates along the axis parallel to the x axis
	 * 
	 * @param o
	 *            - the angle to rotate
	 * @return - this, for method chaining
	 */
	public TransformationMatrix rotatex(double o) {
		matrix = multiply(matrix, new double[][] {
				{1, 0, 0, 0},
				{0, Math.cos(o), -Math.sin(o), 0},
				{0, Math.sin(o), Math.cos(o), 0},
				{0, 0, 0, 1}
		});
		return this;
	}

	/**
	 * Rotates along the axis parallel to the y axis
	 * 
	 * @param o
	 *            - the angle to rotate
	 * @return - this, for method chaining
	 */
	public TransformationMatrix rotatey(double o) {
		matrix = multiply(matrix, new double[][] {
				{Math.cos(o), 0, -Math.sin(o), 0},
				{0, 1, 0, 0},
				{Math.sin(o), 0, Math.cos(o), 0},
				{0, 0, 0, 1}
		});
		return this;
	}

	/**
	 * Rotates along the axis parallel to the z axis
	 * 
	 * @param o
	 *            - the angle to rotate
	 * @return - this, for method chaining
	 */
	public TransformationMatrix rotatez(double o) {
		matrix = multiply(matrix, new double[][] {
				{Math.cos(o), -Math.sin(o), 0, 0},
				{Math.sin(o), Math.cos(o), 0, 0},
				{0, 0, 1, 0},
				{0, 0, 0, 1}
		});
		return this;
	}

	/**
	 * Scales by the passed scaleFactor
	 * 
	 * @param scaleFactor
	 *            - the amount to scale
	 * @return - this, for method chaining
	 */
	public TransformationMatrix scale(double scaleFactor) {
		return scale(scaleFactor, scaleFactor, scaleFactor);
	}

	/**
	 * Scales by the passed scaleFactors
	 * 
	 * @param xFactor
	 *            - the x scaleFactor
	 * @param yFactor
	 *            - the y scaleFactor
	 * @param zFactor
	 *            - the z scaleFactor
	 * @return - this, for method chaining
	 */
	public TransformationMatrix scale(double xFactor, double yFactor, double zFactor) {
		matrix = multiply(matrix, new double[][] {
				{xFactor, 0, 0, 0},
				{0, yFactor, 0, 0},
				{0, 0, zFactor, 0},
				{0, 0, 0, 1}});
		return this;
	}

	/**
	 * Transforms the coordinates based on all of the previous transformations
	 * 
	 * @param coords
	 *            - the coordinates to transform
	 * @return the transformed coordinates
	 */
	public double[] transform(double... coords) {
		double[][] result = multiply(matrix, new double[][] { {coords[0]}, {coords[1]}, {coords[2]}, {1}});
		return new double[] {result[0][0], result[1][0], result[2][0]};
	}

	/**
	 * translates the coordinates in each direction
	 * 
	 * @param x
	 *            - the amount to translate in the x direction
	 * @param y
	 *            - the amount to translate in the y direction
	 * @param z
	 *            - the amount to translate in the z direction
	 * @return - this, for method chaining
	 */
	public TransformationMatrix translate(double x, double y, double z) {
		matrix = multiply(matrix, new double[][] {
				{1, 0, 0, x},
				{0, 1, 0, y},
				{0, 0, 1, z},
				{0, 0, 0, 1}});
		return this;
	}
}
