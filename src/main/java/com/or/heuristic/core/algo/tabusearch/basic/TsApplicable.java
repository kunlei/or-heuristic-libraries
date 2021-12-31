package com.or.heuristic.core.algo.tabusearch.basic;

import com.or.heuristic.core.util.Optimizable;

import java.util.List;

/**
 * This interface defines functions that should be implemented by solutions in order to enable tabu search.
 *
 * @author Kunlei Lian
 */
public interface TsApplicable<K> extends Optimizable {
  /**
   * This function create a list of neighboring solutions from the current solution.
   *
   * @return neighboring solutions
   */
  List<? extends TsApplicable<K>> getNeighbors(int count);

  /**
   * This function retrieves the tabu key of the current solution.
   *
   * @return tabu table key
   */
  K getTabuKey();
}
