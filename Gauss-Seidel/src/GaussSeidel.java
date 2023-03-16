public class GaussSeidel
{

	public static double A[][] = {
			{4, -1, 0, -1, 0, 0},
			{-1, 4, -1, 0, -1, 0},
			{0, -1, 4, 0, 0, -1},
			{-1, 0, 0, 4, -1, 0},
			{0, -1, 0, -1, 4, -1},
			{0, 0, -1, 0, -1, 4}
	};
	
	public static double B[] = {0, 5, 0, 6, -2, 6};
	public static double x[];
	public static int n;
	public static int k = 0;
	public static double euclidean = 0;
	public static double diff;
	
	public static void main(String args[]) 
	{
		/**
		* This uses the Gauss-Seidel numerical method
		* to solve a system of equations of n x n matrix
		*
		* CSC 2262 Programming project Gauss-Seidel
		*
		* @author Trevor Spinosa
		* @since 11/22/2020
		*
		*/
		n = B.length;
		x = new double[n];
		
		//print the table rubric
		System.out.print("k       ");
		for(int i = 0; i < n; i++)
			System.out.printf("x%d \t", i);
		System.out.println("diff");
		
		iterate();
		while(Math.abs(diff) > 0.0001) //while the euclidean norm > 0.0001
		{
			iterate();
		}
		
		System.out.println("Ax = B");
		printArray(A, "A");
		printArray(x, "x");
		printArray(B, "B");
		
	}
	
	public static void iterate() 
	{
		/**
		* This method iterates the Gauss-Seidel 
		* system equations
		*
		* method: iterate
		*
		* return type: void
		*
		* parameters:
		*
		*
		* @author Trevor Spinosa
		* @since 11/22/2020
		*
		*/
		
			//do gaussseidel iteration
			int temp = 0;
			for(int i = 0; i < n; i++) 
			{
				x[i] = (1/A[i][i]);
				double sum = B[i];
				for(int j = 0; j < n; j++)
				{				
					if(j != i)
						sum -= A[i][j] * x[j];					
				}
				x[i] *= sum;
				temp += x[i]*x[i];
			}
			k++;
			
			//calculate new euclidean 
			diff = Math.abs(euclidean) - Math.abs(Math.sqrt(temp));
			euclidean = Math.abs(Math.sqrt(temp));
			
			//print k(iteration number) and then all x values and then the euclidean norm
			System.out.printf("%d\t",k);
			for(int i = 0; i < n; i++)
				System.out.printf("%.4f\t", x[i]);
			System.out.printf("%.4f \n", diff);

	}
	
	public static void printArray(double[][] a, String name) 
	{
		/**
		* This method prints a 2D array
		*
		* method: printArray
		*
		* return type: void
		*
		* parameters:
		* double[][] a: 2D array to print
		* String name: name of printed array
		*
		* @author Trevor Spinosa
		* @since 11/22/2020
		*
		*/
		System.out.println(name + " = ");
		for(int i = 0; i < n; i++) 
		{
			for(int j = 0; j < n; j++) 
			{
				System.out.print("|" + a[i][j] + "|");
			}
			System.out.println();
		}
		
	}
	
	public static void printArray(double[] a, String name)
	{
		/**
		* This method prints a 1D array
		*
		* method: printArray
		*
		* return type: void
		*
		* parameters:
		* double[] a: 1D array to print
		* String name: name of printed array
		*
		* @author Trevor Spinosa
		* @since 11/22/2020
		*
		*/
		System.out.println(name + " = ");
		for(int i = 0; i < n; i++) 
		{
			System.out.printf("|%.4f|", a[i]);
		}
		System.out.println();
	}
	
}
