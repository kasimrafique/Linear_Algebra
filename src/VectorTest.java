import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VectorTest {

    @Test
    void norm() {
        var v1 = new Vector(
                ComplexNumber.rectangular(1, 0),
                ComplexNumber.rectangular(0, 0),
                ComplexNumber.rectangular(0, 0)
                );
        assertEquals(1, v1.norm());

        var v2 = new Vector(
                ComplexNumber.rectangular(3, 0),
                ComplexNumber.rectangular(4, 0),
                ComplexNumber.rectangular(0, 0)
        );
        assertEquals(5, v2.norm());

        var v3 = new Vector(
                ComplexNumber.rectangular(1, 7),
                ComplexNumber.rectangular(2, -6)
        );
        assertEquals(Math.round(3 * Math.sqrt(10)), Math.round(v3.norm()));
    }
}