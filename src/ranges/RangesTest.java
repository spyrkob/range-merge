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
  public void overlappingRanges_areMergedAndReturnedWithDetachedRanges()
      throws Exception {
    assertThat(join(range(1, 3), range(2, 4), range(5, 6)),
        contains(range(1, 4), range(5, 6)));
  }

  @Test
  public void threeOverlappingRanges_areMergedToOne() throws Exception {
    assertThat(join(range(1, 3), range(2, 5), range(4, 6)),
        contains(range(1, 6)));
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

  private List<Range> joinRanges(List<Range> ranges) {
    if (ranges == null || ranges.isEmpty())
      return Collections.emptyList();

    ArrayList<Range> joinedRanges = new ArrayList<>();

    for (int i = 0; i < ranges.size() - 1; i++) {
      Range currentRange = ranges.get(i);
      Range nextRange = ranges.get(i + 1);
      if (currentRange.intersects(nextRange)) {
        ranges.set(i + 1, currentRange.plus(nextRange));
      } else {
        joinedRanges.add(currentRange);
      }
    }
    joinedRanges.add(ranges.get(ranges.size() - 1));

    return joinedRanges;
  }

}
