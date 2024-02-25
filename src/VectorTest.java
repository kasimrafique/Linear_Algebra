import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VectorTest {

    @Nested
    class InnerProduct {
        @Test
        void givenTwoVectorsOfDifferentDimension_thenThrowException() {
            var v1 = new Vector(ComplexNumber.ZERO, ComplexNumber.ZERO);
            var v2 = new Vector(ComplexNumber.ZERO);

            assertThrows(IllegalArgumentException.class, () -> Vector.innerProduct(v1, v2));
        }

        @Test
        void givenTwoRealVectors_thenReturnTheirDotProduct() {
            var v1 = new Vector(ComplexNumber.rectangular(2, 0), ComplexNumber.rectangular(6, 0));
            var v2 = new Vector(ComplexNumber.rectangular(4, 0), ComplexNumber.rectangular(1, 0));

            assertEquals(ComplexNumber.rectangular(14, 0), Vector.innerProduct(v1, v2));
        }

        @Test
        void givenTwoComplexVectors_thenReturnTheirStandardComplexInnerProduct() {
            var v1 = new Vector(ComplexNumber.rectangular(2, 1), ComplexNumber.rectangular(6, 3));
            var v2 = new Vector(ComplexNumber.rectangular(4, 2), ComplexNumber.rectangular(1, 7));

            assertEquals(ComplexNumber.rectangular(37, 39), Vector.innerProduct(v1, v2));
        }
    }
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