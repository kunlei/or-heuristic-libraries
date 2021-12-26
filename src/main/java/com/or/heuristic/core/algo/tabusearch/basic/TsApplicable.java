package com.or.heuristic.core.algo.tabusearch.basic;

import com.or.heuristic.core.util.Optimizable;

import java.util.List;

/**
 * @author Kunlei Lian
 */
public interface TsApplicable {
  /**
   *
   * @return
   */
  List<? extends TsApplicable> getNeighbors();
}
