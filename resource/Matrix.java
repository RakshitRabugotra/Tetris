package Tetris.resource;

public class Matrix {

    private int[][] matrix;
    public int rows;
    public int columns;

    // Default Constructor which builds a 2x2 Null Matrix
    public Matrix() {
        this.rows = 2;
        this.columns = 2;
        this.matrix = new int[][] {
            {0, 0},
            {0, 0}
        };
    }

    // Constructors for this class
    public Matrix(int rows, int columns, int fill) {
        // Create an empty 2D array first
        this.rows = rows;
        this.columns = columns;
        this.matrix = new int[rows][columns];

        // Fill this 2D Array
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.matrix[i][j] = fill;
            }
        }
    }

    // Generating the Matrix without using fill
    public Matrix(int rows, int columns) {
        // Just like default constructor, with fill = 0
        this.rows = rows;
        this.columns = columns;
        this.matrix = new int[rows][columns];
        
        // Fill this 2D Array
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.matrix[i][j] = 0;
            }
        }
    }

    // Generating the matrix using a 2D array
    public Matrix(int[][] array2D) {
        // We will simply copy this array to our array
        this.matrix = array2D.clone();
        // But also fetch the size of this array
        this.rows = matrix.length;
        this.columns = matrix[0].length;
    }

    // To copy some other Matrix
    public Matrix(Matrix A) {
        this.rows = A.rows;
        this.columns = A.columns;

        // Deep copy this array
        this.matrix = new int[this.rows][this.columns];
        
        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.columns; j++) {
                set(i, j, A.get(i, j));
            }
        }

    }

    // Setters and Getters for the private elements in 2D array
    public int get(int i, int j) {
        return this.matrix[i][j];
    }

    public void set(int i, int j, int val) {
        this.matrix[i][j] = val;
    }

    // Get for the array
    public int[][] getShape() {
        return this.matrix;
    }

    // To print this Matrix
    public void print() {
        for (int[] row : this.matrix) {
            System.out.print("[ ");
            for(int element : row) {
                if(element == 0) System.out.print("  ");
                else System.out.print(element + " ");
            }
            System.out.println("]");
        }
        System.out.println();
    }

    /*
     * Static Methods for Creating special matrices
     */
    public static Matrix unitMatrix(int size) {
        // Create a new matrix of this size
        Matrix newMatrix = new Matrix(size, size);

        // Fill the diagonals with 1
        for(int d = 0; d < size; d++) {
            newMatrix.set(d, d, 1);
        }

        return newMatrix;
    }
    public static Matrix scalarMatrix(int size, int fill) {
    	// Create a new matrix and fill it with 'fill'
    	Matrix matrix = new Matrix(size, size);
    	
    	// Fill the diagonals with 'fill'
    	for(int d = 0; d < size; d++) {
    		matrix.set(d, d, fill);
    	}
    	
    	return matrix;
    }

    /*
     * Static Methods (Operators) (add, sub, mul, transpose, etc...)
     */
    public static Matrix scale(Matrix A, float factor) {
        // We will element-wise multiply the new Matrix by factor
        Matrix X = new Matrix(A.rows, A.columns);

        for(int j = 0; j < X.columns; j++) {
            for(int i = 0; i < X.rows; i++) {
                // The new scaled element
                int newElement = (int) (A.get(i, j) * factor);
                // Set this element in matrix
                X.set(i, j, newElement);
            }
        }

        return X;
    }
    
    public static Matrix add(Matrix A, Matrix B) {
        // Let's check whether the two Matrices have same size or not?
        if (A.rows != B.rows || A.columns != B.columns) {
            // We cannot perform the addition operation
            throw new IllegalArgumentException("Cannot Perform Addition, The size of Matrix A doesn't match Matrix B");
        }

        // Now, we will perform element-wise addition of Matrices
        // Create a new Matrix to store the result
        Matrix C = new Matrix(A.rows, A.columns);

        for (int j = 0; j < A.columns; j++) {
            for (int i = 0; i < A.rows; i++) {
                C.set(i, j, (A.get(i, j) + B.get(i, j)));
            }
        }

        return C;
    }
    public static Matrix sub(Matrix A, Matrix B) {
        // We will simply negate the Matrix B and Add it to the A
        return Matrix.add(A, Matrix.scale(B, -1));
    }
    public static Matrix multiply(Matrix A, Matrix B) {
    	// Returns the result of matrix multiplication performed on (A, B)
    	// Let's assume the size of matrix a is (m x n) and that of 
		// b is (p x r)... the first condition to check is whether n == p
		int m = A.rows;
		int n = A.columns;
	
		int p = B.rows;
		int r = B.columns;
	
		// Let's check if the matrix multiplication is possible or not
		if(m != p) {			
			return new Matrix();
		}
	
		/*
		We will follow the traditional approach,
		if C = A x B
		then,
			C(ij) = Summation(from-1, to-n, A(ik) * B(kj))
	
		the size of C is (m x r)
		*/
	
		// Initialize a new Matrix
		Matrix C = new Matrix(m, r, 0);
		
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				// Initialize the value of c(ij) to be 0
				for(int k = 0; k < n; k++) {					
					// This statement is equivalent to:
					// C[i, j] += A[i, k] * B[k, j]
					C.set(i, j, C.get(i, j) + (A.get(i, k) * B.get(k, j)));
				}
			}
		}
		
		return C;
    }
    
    /*
     * Static methods for the Relational Operator (==)
     */
    public static boolean areEqual(Matrix A, Matrix B) {
        // First of all check the size of the matrices
        if(A.rows != B.rows || A.columns != B.columns) return false;
        
        // Now, let's compare element-wise
        for(int j = 0; j < A.columns; j++) {
            for(int i = 0; i < A.rows; i++) {
                if(A.get(i, j) != B.get(i, j)) return false;
            }
        }

        // After all this, we only have one conclusion that... these are equal matrices
        return true;
    }

    /*
     * Special Static Methods, unique to the Matrices
     */
    public static Matrix transpose(Matrix A) {
        // Copy this instance
        Matrix B = new Matrix(A);

        // Check if the matrix is square or not?
        if(!isSquare(B)) return new Matrix(B.rows, B.columns, 0);

        // Now let's swap the rows with columns
        for(int j = 0; j < B.columns; j++) {
            for(int i = j+1; i < B.rows; i++) {
                // Swap the elements [i][j] with [j][i]
                
                // If the elements are same then don't bother doing all this
                if(B.get(i, j) == B.get(j, i)) continue;
                
                // Else continue the swapping
                int temp = B.get(i, j);
                B.set(i, j, B.get(j, i));
                B.set(j, i, temp);
            }
        }
        // Return the new Transposed Matrix
        return B;
    }
    
    public static Matrix removeRow(Matrix A, int rowIndex) {
    	// This function removes the row 'rowIndex' from this given matrix A
    	// Check if the row is valid
    	if(!(0 <= rowIndex && rowIndex < A.rows)) {
    		// Don't remove anything and return the original Matrix
    		return A;
    	}
    	
    	// Wrap the Matrix A into new matrix, so we don't modify the original Matrix
    	Matrix copyA = new Matrix(A);

    	int i = rowIndex;
    	// Now remove the row
    	while(i + 1 < copyA.rows) {
    		copyA.matrix[i] = copyA.matrix[++i];
    	}
    	
    	// Now remove the last index from the modified A
    	Matrix resultant = new Matrix(A.rows-1, A.columns);
    
    	for(int k = 0; k < resultant.rows; k++) {
    		resultant.matrix[k] = copyA.matrix[k];
    	}
    	
    	// Return the new matrix
    	return resultant;
    }
    
    public static Matrix removeColumn(Matrix A, int columnIndex) {
    	// This function removes the row 'rowIndex' from this given matrix A
    	// Check if the row is valid
    	if(!(0 <= columnIndex && columnIndex < A.columns)) {
    		// Don't remove anything and return the original Matrix
    		return A;
    	}
    	
    	// Wrap the Matrix A into new Matrix, so we don't modify the original paramter
    	Matrix copyA = new Matrix(A);
    	
    	// The resultant matrix of this operation
    	Matrix resultant = new Matrix(copyA.rows, copyA.columns-1);
    	
    	
    	// Additional traversal integer k
    	int k = -1;
    	for(int row = 0; row < copyA.rows; row++) {
    		// This iteration removes the column from this row :)

    		// Now start remove the element at 'columnIndex' from all the rows
        	int j = columnIndex;
        	
        	while(j + 1 < copyA.columns) {    			
    			copyA.matrix[row][j] = copyA.matrix[row][++j];
    		}
    		// Now remove the last index from the modified A
    		for(k = 0; k < resultant.columns; k++) {
    			resultant.matrix[row][k] = copyA.matrix[row][k];
    		}
    	}
    	
    	// Return the resultant here!
    	return resultant;
    }
    
    public static Matrix inverse(Matrix A) {
    	// If the matrix isn't square then there is no inverse
    	if(!isSquare(A)) return new Matrix(A.rows, A.columns);
    	
    	// Calculate the determinant
    	int detA = A.determinant();
    	
    	// If the determinant is zero, then also we can't calculate the determinant
    	if(detA == 0) return new Matrix(A.rows, A.columns);
    	
    	// Now we've passed corner cases
    	// Calculating the inverse is just adjoint(A) / det(A);
    	return Matrix.scale(A.adjoint(), (float) (1/detA));
    }
    
    /*
     * Special non-static methods, unique to the Matrices
     */
    public int determinant() {
    	/*
    	 * Calculates the determinant of this matrix
    	 */
    	// The matrix should be square for this
    	if(!isSquare(this)) return (int) Float.NaN;
    	
    	// Fetch the size of this matrix
    	int size = this.rows;
    	
    	// We have some special cases
    	if(size == 1) return this.matrix[0][0];
    	
    	if(size == 2) {
    		// There is a standard formula for this case
    		return this.matrix[0][0] * this.matrix[1][1] - this.matrix[1][0] * this.matrix[0][1];
    	}
    	
    	if(size == 3) {
    		// There is also standard result for 3x3 matrices
    		return (
    			this.matrix[0][0]*(this.matrix[1][1]*this.matrix[2][2] - this.matrix[1][2]*this.matrix[2][1])
    			-this.matrix[0][1]*(this.matrix[1][0]*this.matrix[2][2] - this.matrix[2][0]*this.matrix[1][2])
    			+this.matrix[0][2]*(this.matrix[1][0]*this.matrix[2][1] - this.matrix[2][0]*this.matrix[1][1])
    		);	
    	}
    	
    	// If the size is greater than 3, 
    	// then we will calculate the determinant using co-factor expansion
    	int det = 0;
    	
    	for(int k = 0; k < this.columns; k++) {
    		det += this.get(0, k) * this.cofactor(0, k);
    	}
    	
    	return det;
    }
    
    public Matrix minor(int i, int j) {
    	/*
    	 * Calculates the minor of matrix at (i ,j) 
    	 */
    	// First of all, it should be a square matrix
    	if(!isSquare(this)) return new Matrix(i, j, 0);
    	
    	// Make a new Matrix which removes the row i and column j from this matrix
    	Matrix minorMatrix = Matrix.removeRow(this, i);
    	minorMatrix = Matrix.removeColumn(minorMatrix, j);
    	
    	return minorMatrix;
    }
    
    public int cofactor(int i, int j) {
    	/*
    	 * The CoFactor of this matrix for (ij) 
    	 */
    	return (((i+j) % 2 == 0) ? minor(i, j).determinant() : -minor(i, j).determinant());
    } 
    
    public Matrix adjoint() {
    	/*
    	 * Returns the adjoint of A, which is transpose of CoFactor Matrix
    	 */
    	// If the matrix is square, then only we can calculate the adjoint
    	if(!isSquare(this)) return new Matrix(this.rows, this.columns);
    	
    	// Fetch the size of this matrix
    	int size = this.rows;
    	
    	// Now, calculate the cofactors and put them in cofactor matrix
    	Matrix coFactorMatrix = new Matrix(size, size);
    	
    	// Traverse through the matrix
    	int i = -1, j = -1;
    	for(int k = 0; k < size*size; k++) {
    		i = k / size;
    		j = k % size;
    		
    		coFactorMatrix.set(i, j, this.cofactor(i, j));
    	}
    	
    	// Return the tranpose of this cofactor matrix
    	return Matrix.transpose(coFactorMatrix);
    }
    
    /*
     * Static methods specific to Matrices, defining what type of Matrix is this
     */
    public static boolean isSquare(Matrix A) {
    	return A.rows == A.columns;
    }
    
    public static boolean isSymmetric(Matrix A) {
        // First of all, check if the Matrix is square or not?
        if(!isSymmetric(A)) return false;

        // Now let's check if the transpose is equal to this matrix?
        return Matrix.areEqual(Matrix.transpose(A), A);
    }

    public static boolean isSkewSymmetric(Matrix A) {
        // First of all, check if the Matrix is square or not?
        if(!isSymmetric(A)) return false;

        // Now let's check if the transpose is equal to negative of this matrix?
        return Matrix.areEqual(Matrix.transpose(A), Matrix.scale(A, -1));
    }
    
    public static boolean isScalar(Matrix A) {
    	// Check whether the given matrix is scalar or not?
    	
    	// First check whether the matrix is square or not
    	if(!isSquare(A)) return false;
    	
    	// Fetch the size of this Matrix
    	int n = A.rows;
    	
    	// We should check that the diagonal elements must be same,
    	// And anywhere else the elements should be zero
    	int diag = A.get(0, 0);
    	
    	for(int j = 0; j < n; j++) {
    		for(int i = 0; i < n; i++) {
    			// For checking the diagonals
    			if(i == j) {
    				// Diagonals doesn't match
    				if(A.get(i, j) != diag) return false;
    			}
    			// For checking everywhere else
    			else {
    				// Elements must be zero
    				if(A.get(i, j) != 0) return false;
    			}
    		}
    	}
    	
    	// Now as everything matches up, the matrix is indeed scalar
    	return true;
    }

    /*
     * Special Static Methods, (like Rotation, etc...)
     */
    public static Matrix rowMirrorImage(Matrix A) {
        /*
         * Returns the Mirror Image of Matrix A, i.e, flipped by 180 deg
         * 
         * example:
         *  0 1 0
         *  1 0 2
         *  3 0 1
         * 
         * gives:
         *  0 1 0
         *  2 0 1
         *  1 0 3
         */

        /*
         * Approach, reversing each row
         */
        // Copy this matrix
        Matrix B = new Matrix(A);

        for(int j = 0; j < B.columns; j++) {
            for(int i = 0; i < B.rows/2; i++) {
                // Swap the elements here [i][j] with [rows-i-1][j]
                int temp = B.get(i, j);
                B.set(i, j, B.get(B.rows-i-1, j));
                B.set(B.rows-i-1, j, temp);
            }
        }

        // Return this new matrix
        return B;
    }

    public static Matrix columnMirrorImage(Matrix A) {
        /*
         * Returns the Mirror Image of Matrix A, i.e, flipped by 180 deg
         * 
         * example:
         *  0 1 0
         *  1 0 2
         *  3 0 1
         * 
         * gives:
         *  3 0 1
         *  1 0 2
         *  0 1 0
         */

        /*
         * Approach, reversing each row
         */
        // Copy this matrix
        Matrix B = new Matrix(A);

        for(int i = 0; i < B.rows; i++) {
            for(int j = 0; j < B.columns/2; j++) {
                // Swap the elements here [i][j] with [i][columns-j-1]
                int temp = B.get(i, j);
                B.set(i, j, B.get(i, B.columns-j-1));
                B.set(i, B.columns-j-1, temp);
            }
        }

        // Return this new matrix
        return B;
    }

    // For Rotating the matrix Clockwise by 90 deg
    public static Matrix rotateClockWise(Matrix A) {
        // STEP 1: Take the transpose
        Matrix B = transpose(A);

        // STEP 2: Take the mirror image of this transpose
        B = rowMirrorImage(B);

        return B;
    }
    // For Rotating the matrix Counter-ClockWise by 90 deg
    public static Matrix rotateCounterClockWise(Matrix A) {
        // STEP 1: Take the transpose
        Matrix B = transpose(A);

        // STEP 2: Take the mirror image of this transpose
        B = columnMirrorImage(B);

        return B;
    }
}
