package com.or.heuristic.core.util;

/**
 * This interface defines functions that should be implemented by solutions that can be improved by any of the
 * meta-heuristic algorithms in the package.
 *
 * @author Kunlei Lian
 */
public interface Optimizable {
  /**
   * This function computes the objective value of the solution.
   */
  void computeObjective();

  /**
   * This function retrieves the objective value of the current solution.
   * @return objective value
   */
  double getObjective();
}
