/**
 * An int array worker contains methods to evaluate, fill, and print a 2D array.
 */
public class IntArrayWorker {
  private int[][] matrix = null;
  
  /** 
   * Sets the matrix to the passed parameter.
   * 
   * @param theMatrix  the 2D array to be evaluated, filled, or printed
   */
  public void setMatrix(int[][] theMatrix) {
    matrix = theMatrix;
  }
  
  /** 
   * Fills the array with a pattern.
   */
  public void fillPattern() {
    for (int row = 0; row < matrix.length; row++) {
      for (int col = 0; col < matrix[0].length; col++) {
        if (row < col) {
          matrix[row][col] = 1;
        } else if (row == col) {
          matrix[row][col] = 2;
        } else {
          matrix[row][col] = 3;
        }
      }
    }
  }
  
  /**
   * Returns the total of the values in the array using nested for loops.
   * 
   * @return  the total of the values in the array
   */
  public int getTotalFor() {
    int total = 0;
    for (int row = 0; row < matrix.length; row++) {
      for (int col = 0; col < matrix[0].length; col++) {
        total = total + matrix[row][col];
      }
    }
    return total;
  }
  
  /**
   * Returns the total of the values in the array using nested for-each loops.
   * 
   * @return  the total of the values in the array
   */
  public int getTotalForEach() {
    int total = 0;
    for (int[] rowArray : matrix) {
      for (int item : rowArray) {
        total = total + item;
      }
    }
    return total;
  }
  
  /**
   * getCountFor method returns the number of times that param is found in the matrix,
   * using nested for loops.
   * 
   * @param num  the value to be searched for and counted
   * @return     the number of times num is found in the matrix
   * 
   * to be implemented in part (a)
   * must use nested for loops
   */
  public int getCountFor(int num) {
    int count = 0;
    for (int row = 0; row < matrix.length; row++) {
      for (int col = 0; col < matrix[0].length; col++) {
        if (matrix[row][col] == num) {
          count += 1;
        }
      }
    }
    return count;
  }
  
  /**
   * getCountForEach method returns the number of times that param is found in the matrix,
   * using nested for[-each loops.
   * 
   * @param num  the value to be searched for and counted
   * @return     the number of times num is found in the matrix
   * 
   * to be implemented in part (b)
   * must use nested for-each loops
   */
  public int getCountForEach(int num) {
    int count = 0;
    for (int[] row : matrix) {
      for (int item : row) {
        if (item == num) {
          count += 1;
        }
      }
    }
    return count;
  }
  
  /** 
   * getLargest method returns the largest element in the matrix.
   * 
   * @return  the largest element in the matrix
   * 
   * to be implemented in part (c)
   */
  public int getLargest() {
    int largest = matrix[0][0];
    for (int[] row : matrix) {
      for (int item : row) {
        if (item > largest) {
          largest = item;
        }
      }
    }
    return largest;
  }
  
  
  /**
   * getColTotal method returns the total of all elements in column param.
   * 
   * @param col  the column to be evaluated
   * @return     the total of all elements in col
   * 
   * to be implemented in part (d)
   */
  public int getColTotal(int col) {
    int total = 0;
    for (int row = 0; row < matrix.length; row++) {
      total += matrix[row][col];
    }
    return total;
  }
  
  /**
   * Prints the values in the array in rows and columns.
   */
  public void print() {
    for (int row = 0; row < matrix.length; row++) {
      for (int col = 0; col < matrix[0].length; col++) {
        System.out.print( matrix[row][col] + " " );
      }
      System.out.println();
    }
    System.out.println();
  }
  
}