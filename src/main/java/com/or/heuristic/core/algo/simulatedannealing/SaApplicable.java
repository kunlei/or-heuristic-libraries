package com.or.heuristic.core.algo.simulatedannealing;

import com.or.heuristic.core.util.Optimizable;

/**
 * This interface needs to be implemented by all solutions to be optimized using simulated annealing.
 *
 * @author Kunlei Lian
 */
public interface SaApplicable extends Optimizable {
  /**
   * This function returns a neighboring solution of the current solution.
   *
   * @return a neighboring solution independent of the current solution
   */
  SaApplicable getNeighbor();
}
