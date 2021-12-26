package com.or.heuristic.core.algo.tabusearch.basic;

import com.or.heuristic.core.util.ObjectiveSense;

import java.util.*;

/**
 * This class implements the basic workflow of the tabu search algorithm.
 *
 * @author Kunlei Lian
 */
public class TabuSearch<K> {
  private ObjectiveSense objectiveSense;
  private TsConfig tsConfig;
  private TsApplicable startingSolution;
  private TsApplicable bestSolution;
  private final Map<K, Integer> tabuTable;

  public TabuSearch() {
    this.tsConfig = null;
    this.startingSolution = null;
    this.tabuTable = new HashMap<>();
  }

  public void solve() {
    int maxIter = tsConfig.getMaxIter();
    int maxIterNoImprove = tsConfig.getMaxIterNoImprove();

    int iterNoImprove = 0;
    for (int iter = 0; iter < maxIter; iter++) {

    }
  }
}
