package LA;

import java.util.Arrays;

/**
 * A class that represents a matrix made from columns comprised of complex numbers.
 */
public class Matrix {
    private final Vector[] columns;

    private Matrix(Vector... columns) {
        boolean columnVectorsAllSameDimension = Arrays.stream(columns).map(Vector::size).distinct().count() == 1;
        if (!columnVectorsAllSameDimension) throw new IllegalArgumentException("Column vectors must all be of same size.");

        this.columns = columns;
    }

    /**
     * Creates a matrix from columns passed in a parameters.
     *
     * @param columns Columns to add to the matrix
     * @return Matrix with the passed in columns
     */
    public static Matrix fromColumns(Vector... columns) {
        return new Matrix(columns);
    }

    /**
     * Creates a matrix from rows passed in as parameters.
     *
     * @param rows Rows to add to the matrix
     * @return Matrix with the passed in rows
     */
    public static Matrix fromRows(Vector... rows) {
        return (new Matrix(rows)).transpose();
    }

    /**
     * Creates a matrix with real elements of a specific size, with entries inputted going across. So the first
     * n doubles inputted correspond to the first row of the matrix.
     *
     * @param m The number of rows
     * @param n The number of columns
     * @param values Values to go inside the matrix
     * @return Matrix with the inputted values
     */
    public static Matrix fromRealsWithSize(int m, int n, double... values){
        if (values.length != m*n || m<1 || n<1) throw new IllegalArgumentException("Invalid amount of parameters");
        Vector[] rows = new Vector[m];

        int a = 0;
        int b = 0;
        for (int q = 0; q < m; q++) {
            double[] rowValues = new double[n];
            for (int r = 0; r < n; r++) {
                rowValues[r] = values[a];
                a++;
            }
            rows[b] = Vector.fromReals(rowValues);
            b++;
        }
        return Matrix.fromRows(rows);
    }

    /**
     * Transpose the matrix - every a_ij -> a_ji
     *
     * @return The transposed matrix
     */
    public Matrix transpose() {
        return Matrix.fromColumns(getRows());
    }

    /**
     * @return The number of rows, i.e. the size of the columns
     */
    public int m() {
        return columns[0].size();
    }

    /**
     * @return The number of columns
     */
    public int n() {
        return columns.length;
    }

    /**
     * @param i The row number to return (1 indexed)
     * @return The ith row from the matrix
     */
    public Vector getRow(int i) {
        if (i<1 || i > m()) throw new IllegalArgumentException("Trying to get non-existent row");
       ComplexNumber[] components = new ComplexNumber[n()];

       for (int j = 0; j < n(); j++) {
           components[j] = columns[j].getComponent(i);
       }

       return new Vector(components);
    }

    /**
     * Gets all the rows from a matrix.
     *
     * @return The rows of a matrix
     */
    public Vector[] getRows() {
        Vector[] rows = new Vector[m()];

        for (int i = 0; i < m(); i++) {
            rows[i] = getRow(i + 1);
        }

        return rows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;

        return Arrays.equals(columns, matrix.columns);
    }

    @Override
    public String toString() {
        return "LA.Matrix{" +
                "rows=" + Arrays.toString(getRows()) +
                '}';
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(columns);
    }
}
