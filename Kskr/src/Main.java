import java.util.Scanner;

public class Main {

	static float[][] matrixB = { { 9, 1 }, { 4, 18 }, };
	static double deter = 0;
	static double opr;

	public static void main(String[] args) {
		
		System.out.println("Введите размерность матрицы");
		Scanner sc = new Scanner(System.in);
		int  a = sc.nextInt() ;
		int b=a;
		if (a <= 1) {
			return;
		}
		float[][] matrixA = new float[a][a];
		float[][] matrixB = new float[b][1];
		System.out.println("Введите матрицу");
		for (int i = 0; i < a; i++) {
			for (int j = 0; j < a; j++) {
				matrixA[i][j] = sc.nextInt();
			}
		}

		System.out.println("Ваша матрица");
		printArr(matrixA, a);
		
		System.out.println("Введите столбец свободных коэф");
		for(int i=0; i<b; i++) {
			matrixB[i][0]=sc.nextInt();
		}
		//System.out.println("Ваш толбец свободных коэф");
		//printArr(matrixB, b);
		
		
		//System.out.println("Определитель матрицы ");
		opr = CalculateMatrix(matrixA);
		//System.out.println("" + opr);
		

		// создаем присоединенную матрицу
		double[][] matrixPris = new double[a][a];
		// заполняем ее определителями миноров
		for (int i = 0; i < a; i++) {
			for (int j = 0; j < a; j++) {
				matrixPris[i][j] = CalculateMatrix(GetMinor(matrixA, i, j));
			}
		}
		
		if (opr != 0) {
			//System.out.println(" матрица миноров");
			//printArr(matrixPris, a);
			//System.out.println(" матрица транспонированая");
			transpon(matrixPris);
			//printArr(matrixPris, a);
			//System.out.println(" матрица детерминированная");
			deter(matrixPris);
			//printArr(matrixPris, a);
			reshYr(matrixA,matrixB);
			
			
		}else {
			System.out.println(" матрица не имеет обратной");
			}
		
		}
	//решение уравнения
	public static void reshYr(float[][] matrixA, float[][] matrixB) {
		float[][] mA = matrixA;
        
        float[][] mB =matrixB;

        
        int m = mA.length;
        int n = mB[0].length;
        int o = mB.length;
        int[][] res = new int[m][n];
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < o; k++) {
                    res[i][j] += mA[i][k] * mB[k][j]; 
                }
            }
        }
        
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                System.out.format("%6d ", res[i][j]);
            }
            System.out.println();
        }


	}

	public static void deter(double[][] A) {
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A.length; j++) {
				A[i][j] = A[i][j] / opr;
			}
		}
	}

	public static void transpon(double[][] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = i + 1; j < a.length; j++) {
				double temp = a[i][j];
				a[i][j] = a[j][i];
				a[j][i] = temp;
			}
		}
	}

	// вывод матрицы в консоль
	public static void printArr(float[][] A, int a) {
		for (int i = 0; i < a; i++) {
			for (int j = 0; j < A.length; j++) {
				System.out.print(A[i][j] + " \t");
			}
			System.out.println("");
		}
	}

	public static void printArr(float[] A, int a) {
		for (int i = 0; i < a; i++) {
			System.out.println(A[i]);

		}
	}

	public static void printArr(double[][] A, int a) {
		for (int i = 0; i < a; i++) {
			for (int j = 0; j < a; j++) {
				System.out.print(A[i][j] + " \t");
			}
			System.out.println("");
		}
	}

	// определитель матрицы 2 на 2
	public static void CalculateMatrix2(float[][] matrix) {
		float calcResult = 0.0f;
		calcResult = matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];

		System.out.println("Определитель матрицы 2 на 2 равен " + calcResult);
	}

	// функция раскладывает на миноры. Для каждого минора вычисляем ЕГО
	// определитель, рекурсивно вызывая ту же функцию..
	public static float CalculateMatrix(float[][] matrix) {
		float calcResult = 0.0f;
		if (matrix.length == 2) {
			return matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];
		}
		int koeff = 1;
		// разложение по первой строке (i+0)
		for (int i = 0; i < matrix.length; i++) {
			if (i % 2 == 1) { // если не четное, то коэфициент с минусом
				koeff = -1;
			} else {
				koeff = 1;
			}
			;
			// собственно разложение:
			calcResult += koeff * matrix[0][i] * CalculateMatrix(GetMinor(matrix, 0, i));
		}

		// возвращаем ответ
		return calcResult;
	}

	// функция, к-я возвращает нужный нам минор. На входе - определитель, из к-го
	// надо достать минор и номера строк-столбцов, к-е надо вычеркнуть.
	public static float[][] GetMinor(float[][] matrix, int row, int column) {
		int minorLength = matrix.length - 1;
		float[][] minor = new float[minorLength][minorLength];
		int dI = 0;// эти переменные для того, чтобы "пропускать" ненужные нам строку и столбец
		int dJ = 0;
		// пока не заполним минор
		for (int i = 0; i <= minorLength; i++) {
			// переменную для строки обнуляем
			dJ = 0;
			for (int j = 0; j <= minorLength; j++) {
				// если работаем с строкой, которую нужно пропустить, запоминаем ее номер и
				// пропускаем
				if (i == row) {
					dI = 1;
				} else {
					// если рботаем с колонкой, которую нужно пропустить, запонминаем ее номер и
					// пропрускаем
					if (j == column) {
						dJ = 1;
					}
					// если строки прорпускать не нужно, записываем данные в минорную матрицу
					else {
						minor[i - dI][j - dJ] = matrix[i][j];
					}
				}
			}
		}

		return minor;

	}

	// обратная матрица
	static void inversion(float[][] A, int N) {
		// временная перменная
		double temp;
		// создаем единичную матрицу
		float[][] E = new float[N][N];
		// все заполняем нулями
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				E[i][j] = 0f;
				// кроме главной диагонали
				if (i == j)
					E[i][j] = 1f;
			}
		// проходим весь массив
		for (int k = 0; k < N; k++) {
			// в переменную заносим значение элемента с главной диагонали
			temp = A[k][k];
			// пока проходим все элементы матрицы
			for (int j = 0; j < N; j++) {
				// каждый элемент в единичной и нашей матрицы делим на элемент главной диагонали
				A[k][j] /= temp;
				E[k][j] /= temp;
			}

			for (int i = k + 1; i < N; i++) {
				temp = A[i][k];

				for (int j = 0; j < N; j++) {
					A[i][j] -= A[k][j] * temp;
					E[i][j] -= E[k][j] * temp;
				}
			}
		}

		for (int k = N - 1; k > 0; k--) {
			for (int i = k - 1; i >= 0; i--) {
				temp = A[i][k];

				for (int j = 0; j < N; j++) {
					A[i][j] -= A[k][j] * temp;
					E[i][j] -= E[k][j] * temp;
				}
			}
		}

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				A[i][j] = E[i][j];

		printArr(A, 2);

	}

}
