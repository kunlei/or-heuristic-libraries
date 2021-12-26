package com.or.heuristic.core.algo.tabusearch;

import com.or.heuristic.core.util.Optimizable;

import java.util.List;

/**
 * @author Kunlei Lian
 */
public interface TsApplicable extends Optimizable {
  /**
   *
   * @return
   */
  List<? extends TsApplicable> getNeighbors();
}
