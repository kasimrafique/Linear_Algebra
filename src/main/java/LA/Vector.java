package LA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * A class that creates a vector made up of complex numbers.
 */
public class Vector {
    private final ArrayList<ComplexNumber> components;

    /**
     * Create a new vector from complex numbers.
     *
     * @param c The complex numbers to add to the vector
     */
    public Vector(ComplexNumber... c) {
        this.components = new ArrayList<>(Arrays.asList(c));
    }

    /**
     * Create a vector from real numbers.
     *
     * @param cs The real numbers to add
     * @return Vector represented as complex numbers with real entries
     */
    public static Vector fromReals(double... cs) {
        return new Vector(Arrays.stream(cs).mapToObj(ComplexNumber::real).toArray(ComplexNumber[]::new));
    }

    /**
     * Calculate the inner product of 2 vectors defined by
     * <ol>
     *     <li> &lt;v,v> >= 0 and equals 0 iff v = 0 </li>
     *     <li> &lt;u+w,v> = &lt;u,v> + &lt;w,v></li>
     *     <li> &lt;v,Kw> = K&lt;v,w> </li>
     * </ol>
     *
     * @param v1 Vector 1
     * @param v2 Vector 2
     * @return The inner product
     */
    public static ComplexNumber innerProduct(Vector v1, Vector v2) {
        if (v1.size() != v2.size()) throw new IllegalArgumentException("Vectors must have the same number of components");

        var dotProd = ComplexNumber.ZERO;

        for (int i = 0; i < v1.size(); i++) {
            dotProd = ComplexNumber.sum(
                    dotProd,
                    ComplexNumber.product(
                            v1.components.get(i).conjugate(),
                            v2.components.get(i)
                    ));
        }

        return dotProd;
    }

    /**
     * 1 indexed position to get.
     *
     * @param i The position to return
     * @return The ith component
     */
    public ComplexNumber getComponent(int i) {
        if (i<1 || i>components.size()) throw new IllegalArgumentException("Accessing out of bound component");
        return components.get(i - 1);
    }

    /**
     * Calculates the norm of the vector.
     *
     * @return The norm
     */
    public double norm() {
        return Math.sqrt(
                components.stream()
                        .map(z -> ComplexNumber.product(z, z.conjugate()))
                        .mapToDouble(ComplexNumber::realPart)
                        .sum());
    }

    /**
     * @return The size of the vector
     */
    public int size() {
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

    @Override
    public int hashCode() {
        return Objects.hash(components);
    }
}
