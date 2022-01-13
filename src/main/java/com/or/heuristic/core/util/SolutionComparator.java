package com.or.heuristic.core.util;

import lombok.RequiredArgsConstructor;

import java.util.Comparator;

/**
 * @author klian
 */
@RequiredArgsConstructor
public class SolutionComparator<T extends Optimizable> implements Comparator<T> {
  private final ObjectiveSense objectiveSense;

  @Override
  public int compare(T o1, T o2) {
    int compare = Double.compare(o1.getObjective(), o2.getObjective());
    return objectiveSense == ObjectiveSense.MAXIMIZE ? -compare : compare;
  }
}
