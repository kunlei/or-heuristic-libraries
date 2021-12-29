package com.or.heuristic.core.algo.simulatedannealing;

import com.or.heuristic.core.util.Optimizable;

/**
 *
 * @author Kunlei Lian
 */
public interface SaApplicable extends Optimizable {
  /**
   *
   * @return
   */
  SaApplicable getNeighbor();
}
