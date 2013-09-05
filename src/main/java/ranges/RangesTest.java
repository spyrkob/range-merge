package ranges;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.*;

import org.junit.*;

public class RangesTest {

  RangeMerger merger = new RangeMerger();

  @Test
  public void null_returnsEmptyList() {
    assertEquals(noRanges(), merger.joinRanges(null));
  }

  @Test
  public void emptyList_returnEmptyList() throws Exception {
    assertEquals(noRanges(), merger.joinRanges(noRanges()));
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
    return merger.joinRanges(Arrays.asList(r));
  }

  private Range range(int lower, int upper) {
    return new Range(lower, upper);
  }

  private List<Range> noRanges() {
    return Collections.<Range> emptyList();
  }
}
