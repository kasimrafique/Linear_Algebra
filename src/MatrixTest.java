import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

    @Nested
    class Constructor {
        @Test
        void givenColumnVectorsOfVaryingDimension_thenThrowException() {
            assertThrows(IllegalArgumentException.class, () -> Matrix.fromColumns(
                    new Vector(
                            ComplexNumber.real(1)
                    ),
                    new Vector(
                            ComplexNumber.real(0),
                            ComplexNumber.real(1))
            ));
        }

        @Test
        void givenASquareMatrix_thenInstantiate() {
            Matrix a = Matrix.fromColumns(
                            new Vector(
                                    ComplexNumber.real(1),
                                    ComplexNumber.real(0)
                            ),
                            new Vector(
                                    ComplexNumber.real(0),
                                    ComplexNumber.real(1))
                    );

            assertEquals(2, a.m());
            assertEquals(2, a.n());
        }


        @Test
        void givenARectangularMatrix_thenInstantiate() {
            Matrix a = Matrix.fromColumns(
                    new Vector(
                            ComplexNumber.real(1),
                            ComplexNumber.real(0),
                            ComplexNumber.real(0)
                    ),
                    new Vector(
                            ComplexNumber.real(0),
                            ComplexNumber.real(1),
                            ComplexNumber.real(0)
                    )
            );

            assertEquals(3, a.m());
            assertEquals(2, a.n());
        }
    }


    @Nested
    class Transpose {
        @Test
        public void givenASquareMatrix_thenReturnItsTranspose() {
            Matrix a = Matrix.fromRows(
                    Vector.fromReals(1, 2, 3),
                    Vector.fromReals(4, 5, 6),
                    Vector.fromReals(7, 8, 9)
            );

            assertEquals(Matrix.fromRows(
                    Vector.fromReals(1, 4, 7),
                    Vector.fromReals(2, 5, 8),
                    Vector.fromReals(3, 6, 9)
                    ), a.transpose());
        }
    }

    @Nested
    class GetRow {
        @Test
        public void givenAMatrix_thenReturnTheRequestedRow() {
        Matrix a = Matrix.fromColumns(
                Vector.fromReals(1, 2, 3),
                Vector.fromReals(4, 5, 6),
                Vector.fromReals(7, 8, 9)
        );

        assertEquals(
                Vector.fromReals(2, 5, 8),
                a.getRow(2)
                );
        }
    }
}