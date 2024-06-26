package LA;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
    @Test
    void givenTwoRealVectors_thenReturnTheirDotProduct1() {
      var v1 = new Vector(ComplexNumber.rectangular(2, 0), ComplexNumber.rectangular(6, 0));
      var v2 = new Vector(ComplexNumber.rectangular(4, 0), ComplexNumber.rectangular(1, 0));

      assertEquals(ComplexNumber.rectangular(14, 0), Vector.innerProduct(v1, v2));
    }

    @Test
    void givenTwoComplexVectors_thenReturnTheirDotProduct() {
      var v11 = ComplexNumber.rectangular(0, 2);
      var v12 = ComplexNumber.rectangular(3, 4);
      var v21 = ComplexNumber.rectangular(5, 6);
      var v22 = ComplexNumber.rectangular(7, 8);
      var v1 = new Vector(v11, v12);
      var v2 = new Vector(v21, v22);

      assertEquals(ComplexNumber.rectangular(65, -14), Vector.innerProduct(v1, v2));
    }

    @Test
    void givenTwoIdenticalVectors_thenReturnSumOfSquares() {
      var v1 = new Vector(ComplexNumber.rectangular(2, 3), ComplexNumber.rectangular(4, 5));

      // (2-3i)*(2+3i) + (4-5i)*(4+5i) = 54
      assertEquals(ComplexNumber.real(54), Vector.innerProduct(v1, v1));
    }

    @Test
    void givenZeroVectors_thenReturnZero() {
      var v1 = new Vector(ComplexNumber.rectangular(0, 0), ComplexNumber.rectangular(0, 0));
      var v2 = new Vector(ComplexNumber.rectangular(0, 0), ComplexNumber.rectangular(0, 0));

      assertEquals(ComplexNumber.rectangular(0, 0), Vector.innerProduct(v1, v2));
    }

    @Test
    void givenOrthogonalVectors_thenReturnZero() {
      var v1 = new Vector(ComplexNumber.rectangular(1, 0), ComplexNumber.rectangular(0, 1));
      var v2 = new Vector(ComplexNumber.rectangular(0, 1), ComplexNumber.rectangular(1, 0));

      // (1+0i)*(0+1i) + (0+1i)*(1+0i) = (0+i) + (i+0) = 0+0i
      assertEquals(ComplexNumber.rectangular(0, 0), Vector.innerProduct(v1, v2));
    }
  }

  @Test
  void norm() {
    var v1 =
        new Vector(
            ComplexNumber.rectangular(1, 0),
            ComplexNumber.rectangular(0, 0),
            ComplexNumber.rectangular(0, 0));
    assertEquals(1, v1.norm());

    var v2 =
        new Vector(
            ComplexNumber.rectangular(3, 0),
            ComplexNumber.rectangular(4, 0),
            ComplexNumber.rectangular(0, 0));
    assertEquals(5, v2.norm());

    var v3 = new Vector(ComplexNumber.rectangular(1, 7), ComplexNumber.rectangular(2, -6));
    assertEquals(Math.round(3 * Math.sqrt(10)), Math.round(v3.norm()));
  }

  @Nested
  class Add {
    @Test
    void addVectors_realVectors_sumOfRealParts() {
      Vector v1 = new Vector(ComplexNumber.rectangular(2, 0), ComplexNumber.rectangular(6, 0));
      Vector v2 = new Vector(ComplexNumber.rectangular(4, 0), ComplexNumber.rectangular(1, 0));
      Vector expected = new Vector(ComplexNumber.rectangular(6, 0), ComplexNumber.rectangular(7, 0));
      assertEquals(expected, Vector.addVectors(v1, v2));
    }

    @Test
    void addVectors_complexVectors_sumOfComplexParts() {
      Vector v1 = new Vector(ComplexNumber.rectangular(1, 2), ComplexNumber.rectangular(3, 4));
      Vector v2 = new Vector(ComplexNumber.rectangular(5, 6), ComplexNumber.rectangular(7, 8));
      Vector expected = new Vector(ComplexNumber.rectangular(6, 8), ComplexNumber.rectangular(10, 12));
      assertEquals(expected, Vector.addVectors(v1, v2));
    }

    @Test
    void addVectors_vectorAndZeroVector_originalVector() {
      Vector v1 = new Vector(ComplexNumber.rectangular(1, 2), ComplexNumber.rectangular(3, 4));
      Vector zeroVector = new Vector(ComplexNumber.rectangular(0, 0), ComplexNumber.rectangular(0, 0));
      assertEquals(v1, Vector.addVectors(v1, zeroVector));
    }
    @Test
    void addVectors_shouldHandleVectorWithNegativeComponents() {
      Vector v1 = new Vector(ComplexNumber.rectangular(-2, -3), ComplexNumber.rectangular(-4, -5));
      Vector v2 = new Vector(ComplexNumber.rectangular(1, 2), ComplexNumber.rectangular(3, 4));
      Vector result = Vector.addVectors(v1, v2);

      assertEquals(ComplexNumber.rectangular(-1, -1), result.getComponent(1));
      assertEquals(ComplexNumber.rectangular(-1, -1), result.getComponent(2));
    }

    @Test
    void addVectors_shouldHandleVectorsWithDifferentSizes() {
      Vector v1 = new Vector(ComplexNumber.rectangular(1, 2), ComplexNumber.rectangular(3, 4));
      Vector v2 = new Vector(ComplexNumber.rectangular(5, 6));

      assertThrows(IllegalArgumentException.class, () -> Vector.addVectors(v1, v2));
    }

    @Test
    void addVectors_shouldHandleLargeComplexNumbers() {
      Vector v1 = new Vector(ComplexNumber.rectangular(1000, 2000), ComplexNumber.rectangular(-3000, 4000));
      Vector v2 = new Vector(ComplexNumber.rectangular(5000, -6000), ComplexNumber.rectangular(7000, 8000));
      Vector result = Vector.addVectors(v1, v2);

      assertEquals(ComplexNumber.rectangular(6000, -4000), result.getComponent(1));
      assertEquals(ComplexNumber.rectangular(4000, 12000), result.getComponent(2));
    }

    @Test
    void addVectors_shouldHandleVectorAdditionWithZeroVectors() {
      Vector v1 = new Vector(ComplexNumber.rectangular(1, 2), ComplexNumber.rectangular(3, 4));
      Vector zeroVector = new Vector(ComplexNumber.rectangular(0, 0), ComplexNumber.rectangular(0, 0));
      Vector result1 = Vector.addVectors(v1, zeroVector);
      Vector result2 = Vector.addVectors(zeroVector, v1); // Test commutativity

      assertEquals(v1, result1);
      assertEquals(v1, result2);
    }
  }
}
