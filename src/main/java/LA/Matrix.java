package LA;

import java.util.ArrayList;
import java.util.Arrays;

public class Matrix {
    private final Vector[] columns;

    private Matrix(Vector... columns) {
        boolean columnVectorsAllSameDimension = Arrays.stream(columns).map(Vector::dimension).distinct().count() == 1;
        if (!columnVectorsAllSameDimension) throw new IllegalArgumentException("Column vectors must all be of same dimension.");

        this.columns = columns;
    }

    public static Matrix fromColumns(Vector... columns) {
        return new Matrix(columns);
    }

    public static Matrix fromRows(Vector... rows) {
        return (new Matrix(rows)).transpose();
    }

    public static Matrix fromRealsWithSize(int m, int n, double... values){
        if (values.length != m*n || m<1 || n<1) throw new IllegalArgumentException("Invalid amount of parameters");
        Vector[] rows = new Vector[m];

        // Index for each vector in rows
        int a = 0;
        // Want to repeat for every row so i+m
        for (int i = 0; i < values.length; i=i+m){
            double[] value = new double[n];
            // Want to copy all values in the row, length n
           for (int j=i; j<i+n; j++) {
               value[j-i] = values[j];
           }
           rows[a] = Vector.fromReals(value);
           a++;
        }
        return Matrix.fromRows(rows);
    }

    public Matrix transpose() {
        return Matrix.fromColumns(getRows());
    }

    public int m() {
        return columns[0].dimension();
    }

    public int n() {
        return columns.length;
    }

    public Vector getRow(int i) {
        if (i<1 || i >m()) throw new IllegalArgumentException("Trying to get non-existent row");
       ComplexNumber[] components = new ComplexNumber[m()];

       for (int j = 0; j < m(); j++) {
           components[j] = columns[j].getComponent(i);
       }

       return new Vector(components);
    }

    public Vector[] getRows() {
        Vector[] rows = new Vector[n()];

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
