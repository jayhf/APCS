public class TransformationMatrix {
	private double matrix[][];
	
	public TransformationMatrix(double width, double height) {
		matrix = new double[][] { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };
	}
	
	private double[][] multiply(double[][] mat1, double[][] mat2) {
		double[][] result = new double[mat1.length][mat2[0].length];
		for (int i = 0; i < mat1.length; i++)
			for (int j = 0; j < mat2.length; j++)
				for (int k = 0; k < mat1[0].length; k++)
					result[i][j] += mat1[i][k] * mat2[k][j];
		return result;
	}
	
	public double[]transform(double... coords) {
		double[][] result=multiply(matrix, new double[][]{{coords[0]},{coords[1]},{coords[2]}});
		return new double[]{result[0][0],result[1][0],result[2][0]};
	}
	public 
}
