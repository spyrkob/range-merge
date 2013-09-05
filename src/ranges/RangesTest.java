package ranges;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.*;

import org.junit.*;

public class RangesTest {

  @Test
  public void null_returnsEmptyList() {
    assertEquals(noRanges(), joinRanges(null));
  }

  @Test
  public void emptyList_returnEmptyList() throws Exception {
    assertEquals(noRanges(), joinRanges(noRanges()));
  }

  @Test
  public void singleRange_returnsFullRange() throws Exception {
    assertThat(join(range(1, 2)), contains(range(1, 2)));
  }

  @Test
  public void detachedRanges_returnSameList() throws Exception {
    assertThat(join(range(1, 2), range(3, 4)),
        contains(range(1, 2), range(3, 4)));
  }

  @Test
  public void twoOverlappingRanges_areMerged() throws Exception {
    assertThat(join(range(1, 3), range(2, 4)), contains(range(1, 4)));
  }

  @Test
  public void threeOverlappingRanges_areMergedToOne() throws Exception {
    assertThat(join(range(1, 3), range(2, 5), range(4, 6)),
        contains(range(1, 6)));
  }

  @Test
  public void detachedRangeAfterOverlappingRanges_isIncludedInResult()
      throws Exception {
    assertThat(join(range(1, 3), range(2, 5), range(6, 7)),
        contains(range(1, 5), range(6, 7)));
  }

  @Test
  public void detachedRangeBeforeOverlappingRanges_isIncludedInResult()
      throws Exception {
    assertThat(join(range(1, 2), range(2, 5), range(4, 7)),
        contains(range(1, 2), range(2, 7)));
  }

  @Test
  public void twoSetsOfOverlappingResults_areMergedIntoTwoDetachedRanges()
      throws Exception {
    assertThat(join(range(1, 3), range(2, 5), range(6, 8), range(7, 9)),
        contains(range(1, 5), range(6, 9)));
  }

  @Test
  public void rangeContainsSmaller_producesLargerRange() throws Exception {
    assertThat(join(range(1, 6), range(2, 3)), contains(range(1, 6)));
  }
  private List<Range> join(Range... r) {
    return joinRanges(Arrays.asList(r));
  }

  private Range range(int lower, int upper) {
    return new Range(lower, upper);
  }

  private List<Range> noRanges() {
    return Collections.<Range> emptyList();
  }

  public List<Range> joinRanges(List<Range> ranges) {
    if (ranges == null || ranges.isEmpty())
      return Collections.emptyList();

    ArrayList<Range> detachedRanges = new ArrayList<>();

    Range previousRange = ranges.get(0);

    for (int i = 1; i < ranges.size(); i++) {
      Range currentRange = ranges.get(i);
      if (previousRange.intersects(currentRange)) {
        previousRange = previousRange.plus(currentRange);
      } else {
        detachedRanges.add(previousRange);
        previousRange = currentRange;
      }
    }
    detachedRanges.add(previousRange);
    return detachedRanges;
  }
}
