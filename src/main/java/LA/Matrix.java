package LA;

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
}
