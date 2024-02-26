package LA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Vector {
    private final ArrayList<ComplexNumber> components;

    public Vector(ComplexNumber... c) {
        this.components = new ArrayList<>(Arrays.asList(c));
    }

    public static Vector fromReals(double... cs) {
        return new Vector(Arrays.stream(cs).mapToObj(ComplexNumber::real).toArray(ComplexNumber[]::new));
    }

    public static ComplexNumber innerProduct(Vector v1, Vector v2) {
        if (v1.dimension() != v2.dimension()) throw new IllegalArgumentException("Vectors must have the same number of components");

        var dotProd = ComplexNumber.ZERO;

        for (int i = 0; i < v1.dimension(); i++) {
            dotProd = ComplexNumber.sum(
                    dotProd,
                    ComplexNumber.product(
                            v1.components.get(i).conjugate(),
                            v2.components.get(i)
                    ));
        }

        return dotProd;
    }

    public ComplexNumber getComponent(int i) {
        return components.get(i - 1);
    }

    public double norm() {
        return Math.sqrt(
                components.stream()
                        .map(z -> ComplexNumber.product(z, z.conjugate()))
                        .mapToDouble(ComplexNumber::realPart)
                        .sum());
    }

    public int dimension() {
        return components.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Objects.equals(components, vector.components);
    }

    @Override
    public String toString() {
        return "LA.Vector{" + components + '}';
    }
}
