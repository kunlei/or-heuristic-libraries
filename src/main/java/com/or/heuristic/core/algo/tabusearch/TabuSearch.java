package com.or.heuristic.core.algo.tabusearch;

import java.util.HashSet;
import java.util.Map;
import java.util.PrimitiveIterator;
import java.util.Set;

/**
 * This class implements the basic workflow of the tabu search algorithm.
 *
 * @author Kunlei Lian
 */
public class TabuSearch<K> {
  private TsConfig tsConfig;
  private TsApplicable startingSolution;
  private Map<K, Integer> tabuTable;

  public TabuSearch() {
  }

  public void solve() {

  }
}
