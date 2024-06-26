package LA;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ComplexNumberTest {

  @Nested
  class Instantiation {
    @SuppressWarnings("DuplicateExpressions")
    @Test
    public void whenInstantiatedInPolarForm_thenHasCorrectRectangularCoords() {
      ComplexNumber z1 = ComplexNumber.polar(1, 0);
      assertEquals(1, z1.realPart(), 0.01);
      assertEquals(0, z1.imaginaryPart(), 0.01);

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
      assertEquals(-1, z6.imaginaryPart(), 0.01);
    }

    @Test
    public void whenInstantiatedInRectangularForm_thenHasCorrectArgument() {
      ComplexNumber z1 = ComplexNumber.rectangular(1, 1);
      assertEquals(Math.PI / 4, z1.argument(), 0.001);

      ComplexNumber z2 = ComplexNumber.rectangular(-1, 1);
      assertEquals(3 * Math.PI / 4, z2.argument(), 0.001);

      ComplexNumber z3 = ComplexNumber.polar(1, 3 * Math.PI / 2);
      assertEquals(3 * Math.PI / 2, z3.argument(), 0.001);

      ComplexNumber z4 = ComplexNumber.rectangular(-3, 4);
      assertEquals(2.2143, z4.argument(), 0.001);

      assertEquals(5.878, ComplexNumber.rectangular(7, -3).argument(), 0.01);

      assertEquals(0, ComplexNumber.rectangular(0, 0).argument(), 0.001);
    }
  }

  @Nested
  class Operators {
    @Test
    public void testProductOfComplexNumbers() {
      var z1 = ComplexNumber.rectangular(1, 2);
      var z2 = ComplexNumber.rectangular(2, 3);
      var z3 = ComplexNumber.rectangular(3, 4);
      assertEquals(-4, Math.round(ComplexNumber.product(z1, z2).realPart()));
      assertEquals(7, Math.round(ComplexNumber.product(z1, z2).imaginaryPart()));
      assertEquals(-40, Math.round(ComplexNumber.product(z1, z2, z3).realPart()));
      assertEquals(5, Math.round(ComplexNumber.product(z1, z2, z3).imaginaryPart()));
      assertEquals(-5, Math.round(ComplexNumber.product(z1, z3).realPart()));
      assertEquals(10, Math.round(ComplexNumber.product(z1, z3).imaginaryPart()));
      assertEquals(0, Math.round(ComplexNumber.product(z1, z2, ComplexNumber.ZERO, z3).realPart()));
      assertEquals(
          0, Math.round(ComplexNumber.product(z1, z2, ComplexNumber.ZERO, z3).imaginaryPart()));
    }

    @Test
    public void addComplexNumbers() {
      assertEquals(
          ComplexNumber.rectangular(5, 10),
          ComplexNumber.rectangular(3, 9).add(ComplexNumber.rectangular(2, 1)));
      assertEquals(
          ComplexNumber.rectangular(5, 10),
          ComplexNumber.rectangular(9, 9).add(ComplexNumber.rectangular(-4, 1)));
      assertEquals(
          ComplexNumber.rectangular(5, 10),
          ComplexNumber.ADDITIVE_IDENTITY.add(ComplexNumber.rectangular(5, 10)));
      assertEquals(
          ComplexNumber.rectangular(6, 12),
          ComplexNumber.ADDITIVE_IDENTITY
              .add(ComplexNumber.rectangular(1, 2))
              .add(ComplexNumber.rectangular(2, 4))
              .add(ComplexNumber.rectangular(3, 6)));
    }

    @Test
    public void subtractComplexNumbers() {
      assertEquals(
          ComplexNumber.rectangular(1, 8),
          ComplexNumber.rectangular(3, 9).subtract(ComplexNumber.rectangular(2, 1)));
      assertEquals(
          ComplexNumber.rectangular(13, 8),
          ComplexNumber.rectangular(9, 9).subtract(ComplexNumber.rectangular(-4, 1)));
      assertEquals(
          ComplexNumber.rectangular(-5, -10),
          ComplexNumber.ADDITIVE_IDENTITY.subtract(ComplexNumber.rectangular(5, 10)));
      assertEquals(
          ComplexNumber.rectangular(-6, -12),
          ComplexNumber.ADDITIVE_IDENTITY
              .subtract(ComplexNumber.rectangular(1, 2))
              .subtract(ComplexNumber.rectangular(2, 4))
              .subtract(ComplexNumber.rectangular(3, 6)));

      // Mix of both
      assertEquals(
          ComplexNumber.rectangular(-2, -4),
          ComplexNumber.ADDITIVE_IDENTITY
              .subtract(ComplexNumber.rectangular(1, 2))
              .add(ComplexNumber.rectangular(2, 4))
              .subtract(ComplexNumber.rectangular(3, 6)));
    }

    @Test
    public void testMultiplyComplexNumbers() {
      // Test Case 1: (1 + 8i) * (3 + 9i)
      assertEquals(
          ComplexNumber.rectangular(-69, 33),
          ComplexNumber.rectangular(1, 8).multiply(ComplexNumber.rectangular(3, 9)));

      // Test Case 2: (9 + 9i) * (-4 + 1i)
      assertEquals(
          ComplexNumber.rectangular(-45, -27),
          ComplexNumber.rectangular(9, 9).multiply(ComplexNumber.rectangular(-4, 1)));

      // Test Case 3: 0 * (5 + 10i)
      assertEquals(
          ComplexNumber.ADDITIVE_IDENTITY,
          ComplexNumber.ADDITIVE_IDENTITY.multiply(ComplexNumber.rectangular(5, 10)));

      // Test Case 4: (1 + 2i) * (2 + 4i) * (3 + 6i)
      assertEquals(
          ComplexNumber.rectangular(-66, -12),
          ComplexNumber.rectangular(1, 2)
              .multiply(ComplexNumber.rectangular(2, 4))
              .multiply(ComplexNumber.rectangular(3, 6)));
    }

    @Test
    public void testRealScalarMultiply() {
      // Test Case 1: (1 + 8i) * 2
      assertEquals(
          ComplexNumber.rectangular(2, 16), ComplexNumber.rectangular(1, 8).realScalarMultiply(2));

      // Test Case 2: (9 + 9i) * -1
      assertEquals(
          ComplexNumber.rectangular(-9, -9),
          ComplexNumber.rectangular(9, 9).realScalarMultiply(-1));

      // Test Case 3: (5 + 10i) * 0
      assertEquals(
          ComplexNumber.ADDITIVE_IDENTITY, ComplexNumber.rectangular(5, 10).realScalarMultiply(0));

      // Test Case 4: (1 + 2i) * 3
      assertEquals(
          ComplexNumber.rectangular(3, 6), ComplexNumber.rectangular(1, 2).realScalarMultiply(3));
    }

    @Test
    public void testDivide() {
      // Test Case 1: (1 + 8i) / (3 + 9i)
      assertEquals(
          ComplexNumber.rectangular(5.0 / 6, 1.0 / 6),
          ComplexNumber.rectangular(1, 8).divide(ComplexNumber.rectangular(3, 9)));

      // Test Case 2: (5 + 10i) / (1 + 0i)
      assertEquals(
          ComplexNumber.rectangular(5, 10),
          ComplexNumber.rectangular(5, 10).divide(ComplexNumber.rectangular(1, 0)));

      // Test Case 3: (1 + 2i) / (2 + 4i)
      assertEquals(
          ComplexNumber.rectangular(0.5, 0),
          ComplexNumber.rectangular(1, 2).divide(ComplexNumber.rectangular(2, 4)));

      // Test Case 4: (4 + 2i) / (2 + 1i)
      assertEquals(
          ComplexNumber.rectangular(2, 0),
          ComplexNumber.rectangular(4, 2).divide(ComplexNumber.rectangular(2, 1)));

      // Test Case 5: (10 + 5i) / (2 + 1i)
      assertEquals(
          ComplexNumber.rectangular(5, 0),
          ComplexNumber.rectangular(10, 5).divide(ComplexNumber.rectangular(2, 1)));

      assertThrows(
          IllegalArgumentException.class,
          () -> ComplexNumber.real(1).divide(ComplexNumber.ADDITIVE_IDENTITY));
    }

    @Test
    public void testSumOfComplexNumbers() {
      var z1 = ComplexNumber.rectangular(1, 2);
      var z2 = ComplexNumber.rectangular(2, 3);
      var z3 = ComplexNumber.rectangular(3, 4);
      assert (ComplexNumber.rectangular(6, 9).equals(ComplexNumber.sum(z1, z2, z3)));
      assert (ComplexNumber.rectangular(3, 5).equals(ComplexNumber.sum(z1, z2)));
      assert (ComplexNumber.rectangular(4, 6).equals(ComplexNumber.sum(z1, z3)));
      assert (ComplexNumber.rectangular(4, 6)
          .equals(ComplexNumber.sum(z1, z3, ComplexNumber.ZERO)));
      assert (ComplexNumber.ZERO.equals(ComplexNumber.sum(ComplexNumber.ZERO)));
    }
  }

  @Test
  public void testModulusOfComplexNumbers() {
    assertEquals(5, ComplexNumber.rectangular(3, 4).modulus(), 0.001);
    assertEquals(10, ComplexNumber.polar(10, 0).modulus(), 0.001);
    assertEquals(39, ComplexNumber.rectangular(15, 36).modulus(), 0.001);
    assertEquals(0, ComplexNumber.polar(0, 0).modulus(), 0.001);
    assertEquals(18 * Math.sqrt(626) / 5, ComplexNumber.rectangular(3.6, 90).modulus(), 0.001);
  }

  @Test
  public void testSqrtOfComplexNumbers() {
    assert (ComplexNumber.rectangular(1, 2).equals(ComplexNumber.rectangular(-3, 4).sqrt()));
    assert (ComplexNumber.rectangular(0, 3).equals(ComplexNumber.rectangular(-9, 0).sqrt()));
    assert (ComplexNumber.rectangular(Math.sqrt(2), 0))
        .equals((ComplexNumber.rectangular(2, 0).sqrt()));
    assert (ComplexNumber.rectangular(8, -6).equals(ComplexNumber.rectangular(28, -96).sqrt()));
    assert (ComplexNumber.rectangular(24, 63)
        .equals(ComplexNumber.rectangular(-3393, 3024).sqrt()));
    assert (ComplexNumber.rectangular(0.24, 0.5)
        .equals(ComplexNumber.rectangular(-0.1924, 0.24).sqrt()));
    // Note tried a test with -2-7i but this will never happen when returning +ve sqrt
    assert (ComplexNumber.rectangular(2, 7).equals(ComplexNumber.rectangular(-45, 28).sqrt()));
  }

  @SuppressWarnings("EqualsBetweenInconvertibleTypes")
  @Test
  public void testEquals() {
    ComplexNumber a = ComplexNumber.rectangular(3, 4);
    assert (!ComplexNumber.rectangular(1, 2).equals(a));
    ComplexNumber b = ComplexNumber.rectangular(4.6, 1);
    assert (b.equals(ComplexNumber.rectangular(4.6, 1)));
    assert (!b.equals(new ArrayList<String>()));

    // Problem with rounding when comparing - these 2 are identical but doesn't match due to way of
    // implementation
    assertEquals(3, Math.round(ComplexNumber.polar(5, Math.atan(4.0 / 3.0)).realPart()));
    assertEquals(4, Math.round(ComplexNumber.polar(5, Math.atan(4.0 / 3.0)).imaginaryPart()));
  }

  @Nested
  class MultiplicativeInverse {
    @Test
    void multiplicativeInverse_realNumber() {
      ComplexNumber z = ComplexNumber.rectangular(2, 0); // Complex number 2
      ComplexNumber result = z.multiplicativeInverse().orElseThrow();

      assertEquals(ComplexNumber.rectangular(0.5, -0.0), result);
    }

    @Test
    void multiplicativeInverse_complexNumber() {
      ComplexNumber z = ComplexNumber.rectangular(1, 2); // Complex number 1 + 2i
      ComplexNumber result = z.multiplicativeInverse().orElseThrow();

      assertEquals(
          ComplexNumber.rectangular(0.2, -0.4), result); // Expected: 1/(1+2i) = (1/5) - (2/5)i
    }

    @Test
    void multiplicativeInverse_zero() {
      ComplexNumber z = ComplexNumber.rectangular(0, 0); // Complex number 0

      assertEquals(Optional.empty(), z.multiplicativeInverse());
    }

    @Test
    void multiplicativeInverse_largeNumbers() {
      ComplexNumber z = ComplexNumber.rectangular(100, -200); // Complex number 100 - 200i
      ComplexNumber result = z.multiplicativeInverse().orElseThrow();

      assertEquals(
          ComplexNumber.rectangular(0.002, 0.004),
          result); // Expected: 1/(100-200i) = 0.002 + 0.004i
    }
  }
}
