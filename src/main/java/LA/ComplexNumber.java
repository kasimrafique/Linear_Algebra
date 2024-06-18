package LA;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/** A class representing a complex number. One where the square root of a negative is defined. */
public class ComplexNumber {
  private final double a;
  private final double bi;
  public static final ComplexNumber MULTIPLICATIVE_IDENTITY = new ComplexNumber(1, 0);
  public static final ComplexNumber ZERO = new ComplexNumber(0, 0);

  public static final ComplexNumber ADDITIVE_IDENTITY = ZERO;

  private ComplexNumber(double a, double bi) {
    this.a = a;
    this.bi = bi;
  }

  /**
   * Create a complex number with only a real element.
   *
   * @param x The real component
   * @return The new complex number
   */
  public static ComplexNumber real(double x) {
    return new ComplexNumber(x, 0);
  }

  /**
   * Create a complex number with a real and imaginary part.
   *
   * @param a The real part
   * @param bi The imaginary part
   * @return The new complex number
   */
  public static ComplexNumber rectangular(double a, double bi) {
    return new ComplexNumber(a, bi);
  }

  /**
   * Create a complex number from a polar co-ordinate.
   *
   * @param modulus The modulus
   * @param argument The argument
   * @return The new complex number
   */
  public static ComplexNumber polar(double modulus, double argument) {
    return new ComplexNumber(modulus * Math.cos(argument), modulus * Math.sin(argument));
  }

  /**
   * Calculate the sum of complex numbers.
   *
   * @param zs The complex numbers to sum together
   * @return The sum as a complex number
   */
  public static ComplexNumber sum(ComplexNumber... zs) {
    return Arrays.stream(zs)
        .reduce(
            ComplexNumber.ADDITIVE_IDENTITY,
            (sum, z) -> ComplexNumber.rectangular(sum.a + z.a, sum.bi + z.bi));
  }

  /**
   * Calculate the product of complex numbers.
   *
   * @param zs The complex numbers to multiply together
   * @return The product as a complex number
   */
  public static ComplexNumber product(ComplexNumber... zs) {
    return Arrays.stream(zs)
        .reduce(
            ComplexNumber.MULTIPLICATIVE_IDENTITY,
            (prod, z) ->
                ComplexNumber.rectangular(
                    prod.a * z.a - prod.bi * z.bi, prod.a * z.bi + prod.bi * z.a));
  }

  /**
   * @return Real component
   */
  public double realPart() {
    return this.a;
  }

  /**
   * @return Imaginary part
   */
  public double imaginaryPart() {
    return this.bi;
  }

  /**
   * @return The complex conjugate of a complex number
   */
  public ComplexNumber conjugate() {
    return new ComplexNumber(a, -bi);
  }

  /**
   * @return The modulus of the complex number
   */
  public double modulus() {
    return Math.sqrt(a * a + bi * bi);
  }

  /**
   * Calculates the square root of the complex number. The square root of an imaginary number is
   * imaginary.
   *
   * @see <a href="https://www.cuemath.com/algebra/square-root-of-complex-number/">Source</a>
   * @return The positive root of the complex number
   */
  public ComplexNumber sqrt() {
    // Handle all cases where b is 0
    if ((bi == 0) && (a >= 0)) return ComplexNumber.rectangular(Math.sqrt(a), 0);
    if ((bi == 0) && (a < 0)) return ComplexNumber.rectangular(0, Math.sqrt(-a));

    return ComplexNumber.rectangular(
        Math.sqrt(((this.modulus() + a) / 2.0)),
        (bi / Math.abs(bi)) * Math.sqrt((this.modulus() - a) / 2.0));
  }

  /**
   * Calculates the argument of a complex number.
   *
   * @return The argument between 0 and 2π
   */
  public Double argument() {
    double r = 0.0;
    if (a == 0 && bi == 0) r = 0.0;
    if (Math.round(a) == 0 && bi > 0) r = Math.PI / 2;
    if (Math.round(a) == 0 && bi < 0) r = 3 * Math.PI / 2;
    if (Math.round(a) > 0 && bi == 0) r = 0.0;
    if (Math.round(a) < 0 && bi == 0) r = Math.PI;

    if (a > 0 && bi > 0) r = Math.atan(bi / a);
    if (a < 0 && bi > 0) r = Math.PI - Math.atan(bi / -a);
    if (a > 0 && bi < 0) r = 2 * Math.PI - Math.atan(-bi / a);
    if (a < 0 && bi < 0) r = Math.PI + Math.atan(bi / a);

    return r;
  }

  /**
   * @return The additive inverse for the complex number
   */
  public ComplexNumber additiveInverse() {
    return ComplexNumber.rectangular(this.a * -1, this.bi * -1);
  }

  /**
   * Calculates the multiplicative inverse for the complex number. Note that {@link #ZERO} does not
   * have an inverse.
   *
   * @return Empty if {@link #ZERO} or an optional of the multiplicative inverse
   */
  public Optional<ComplexNumber> multiplicativeInverse() {
    if (a == 0 && bi == 0) return Optional.empty();

    return Optional.of(
        ComplexNumber.rectangular(a / (a * a + bi * bi), -1 * bi / (a * a + bi * bi)));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ComplexNumber that = (ComplexNumber) o;
    return Double.compare(a, that.a) == 0 && Double.compare(bi, that.bi) == 0;
  }

  @Override
  public String toString() {
    return "ComplexNumber{" + a + " + " + bi + "i}";
  }

  @Override
  public int hashCode() {
    return Objects.hash(a, bi);
  }
}
