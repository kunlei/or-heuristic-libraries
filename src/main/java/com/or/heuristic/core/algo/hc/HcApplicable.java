package com.or.heuristic.core.algo.hc;

import com.or.heuristic.core.util.Optimizable;

/**
 *
 * @author Kunlei Lian
 */
public interface HcApplicable extends Optimizable {
  /**
   *
   * @return neighboring solution
   */
  HcApplicable getNeighbor();
}
