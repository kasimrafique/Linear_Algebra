import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComplexNumberTest {

    @Test
    void whenInstantiatedInPolarForm_thenHasCorrectRectangularCoords() {
        ComplexNumber z1 = ComplexNumber.polar(1, 0);
        assertEquals(1, z1.realPart());
        assertEquals(0, z1.imaginaryPart());

        ComplexNumber z2 = ComplexNumber.polar(1, Math.PI / 4);
        assertEquals(Math.round(Math.sqrt(2) / 2), Math.round(z2.realPart()));
        assertEquals(Math.round(Math.sqrt(2) / 2), Math.round(z2.imaginaryPart()));

        ComplexNumber z3 = ComplexNumber.polar(1, 3 * Math.PI / 4);
        assertEquals(Math.round(-Math.sqrt(2) / 2), Math.round(z3.realPart()));
        assertEquals(Math.round(Math.sqrt(2) / 2), Math.round(z3.imaginaryPart()));

        ComplexNumber z4 = ComplexNumber.polar(1, 5 * Math.PI / 4);
        assertEquals(Math.round(-Math.sqrt(2) / 2), Math.round(z4.realPart()));
        assertEquals(Math.round(-Math.sqrt(2) / 2), Math.round(z4.imaginaryPart()));

        ComplexNumber z5 = ComplexNumber.polar(13, 5 * Math.PI / 4);
        assertEquals(Math.round(-13 * Math.sqrt(2) / 2), Math.round(z5.realPart()));
        assertEquals(Math.round(-13 * Math.sqrt(2) / 2), Math.round(z5.imaginaryPart()));

        ComplexNumber z6 = ComplexNumber.polar(1, 3 * (Math.PI / 2));
        assertEquals(0, Math.round(z6.realPart()));
        assertEquals(-1, z6.imaginaryPart());
    }
    @Test
    void product() {
    }

    @Test
    void magnitude() {
    }

    @Test
    void whenInstantiatedInRectangularForm_thenHasCorrectArgument() {
        ComplexNumber z1 = ComplexNumber.rectangular(1, 1);
        assertEquals(Math.PI / 4, z1.argument());

        ComplexNumber z2 = ComplexNumber.rectangular(-1, 1);
        assertEquals(3 * Math.PI / 4, z2.argument());

        ComplexNumber z3 = ComplexNumber.polar(1, 3 * Math.PI / 2);
        assertEquals(3 * Math.PI / 2, z3.argument());
    }
}