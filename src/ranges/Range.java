package ranges;

public class Range {

  public final int lower;
  public final int upper;

  public Range(int lower, int upper) {
    this.lower = lower;
    this.upper = upper;
  }

  @Override
  public String toString() {
    return "Range [lower=" + lower + ", upper=" + upper + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + lower;
    result = prime * result + upper;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Range other = (Range) obj;
    if (lower != other.lower)
      return false;
    if (upper != other.upper)
      return false;
    return true;
  }

  boolean intersects(Range that) {
    return this.upper > that.lower;
  }

  boolean contains(Range that) {
    return this.upper > that.upper;
  }

  Range plus(Range that) {
    if (this.contains(that)) {
      return this;
    } else {
      return new Range(this.lower, that.upper);
    }
  }
}
