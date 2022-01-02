package com.or.heuristic.core.algo.tabusearch.basic;

import com.or.heuristic.core.util.Optimizable;

import java.util.List;

/**
 * This interface defines functions that should be implemented by solutions that can be improved using tabu search.
 *
 * @author Kunlei Lian
 */
public interface TsApplicable<K> extends Optimizable {
  /**
   * This function creates a list of neighboring solutions from the current solution.
   *
   * @param count number of neighboring solutions to generate
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
