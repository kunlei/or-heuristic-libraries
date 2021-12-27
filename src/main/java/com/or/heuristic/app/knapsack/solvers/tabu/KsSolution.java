package com.or.heuristic.app.knapsack.solvers.tabu;

import com.or.heuristic.core.algo.tabusearch.basic.TsApplicable;

import java.util.List;

/**
 * @author klian
 */
public class KsSolution implements TsApplicable<Integer> {

  @Override
  public List<? extends TsApplicable<Integer>> getNeighbors(int count) {
    return null;
  }

  @Override
  public Integer getTabuKey() {
    return null;
  }

  @Override
  public void computeObjective() {

  }

  @Override
  public double getObjective() {
    return 0;
  }
}
