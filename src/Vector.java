import java.util.ArrayList;
import java.util.Arrays;

public class Vector {
    private final ArrayList<ComplexNumber> components;

    public Vector(ComplexNumber... c) {
        this.components = new ArrayList<>(Arrays.asList(c));
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

    public double norm() {
        return Math.sqrt(
                components.stream()
                        .map(z -> ComplexNumber.product(z, z.conjugate()))
                        .mapToDouble(ComplexNumber::realPart)
                        .sum());
    }

    public double dimension() {
        return components.size();
    }
}
