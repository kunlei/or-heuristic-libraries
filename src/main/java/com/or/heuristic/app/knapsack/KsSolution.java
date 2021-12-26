package com.or.heuristic.app.knapsack;

import com.or.heuristic.core.algo.tabusearch.basic.TsApplicable;

import java.util.List;

/**
 * @author klian
 */
public class KsSolution implements TsApplicable {

  @Override
  public List<? extends TsApplicable> getNeighbors() {
    return null;
  }

  @Override
  public Object getTabuKey() {
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
