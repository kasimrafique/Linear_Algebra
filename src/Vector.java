import java.util.ArrayList;
import java.util.Arrays;

public class Vector {
    private final ArrayList<ComplexNumber> components;

    public Vector(ComplexNumber... c) {
        this.components = new ArrayList<>(Arrays.asList(c));
    }

    public double norm() {
        return Math.sqrt(
                components.stream()
                        .map(z -> ComplexNumber.product(z, z.conjugate()))
                        .mapToDouble(ComplexNumber::realPart)
                        .sum());
    }
}
