package com.or.heuristic.core.algo.geneticalgo;

import com.or.heuristic.core.util.Optimizable;

/**
 * This interface defines functions that need to be implemented by solutions used by the genetic algorithm.
 *
 * @author Kunlei Lian
 */
public interface GaApplicable extends Optimizable {
  /**
   * This function uses the current solution and the other input solution to create a child solution. It's assumed that
   * the child solution is independent of two parent solutions.
   *
   * @param other the other solution
   * @return a child solution
   */
  GaApplicable crossover(GaApplicable other);

  /**
   * This function creates a new solution by making a slight modification of the current solution. It's assumed that the
   * mutated solution refers to the same solution as the current solution and no copy is made.
   */
  void mutation();
}
