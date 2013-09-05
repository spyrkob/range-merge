package ranges;

import java.util.*;

public class RangeMerger {

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
