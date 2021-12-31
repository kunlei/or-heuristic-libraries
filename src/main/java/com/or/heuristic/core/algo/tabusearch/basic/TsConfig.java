package com.or.heuristic.core.algo.tabusearch.basic;

import lombok.Builder;
import lombok.Getter;

/**
 * This class defines the required parameters of the tabu search algorithm.
 *
 * @author Kunlei Lian
 */
@Getter
@Builder
public final class TsConfig {
  /**
   * the number of iterations that a move is forbidden
   */
  private final int tabuLength;
  /**
   * the number of neighboring solutions to explore at each iteration
   */
  private final int neighborSize;
  /**
   * the maximum number of iterations to search
   */
  private final int maxIter;
  /**
   * the maximum number of iterations that the best solution couldn't be improved
   */
  private final int maxIterNoImprove;
  /**
   * maximum runtime in seconds
   */
  private final int maxRuntimeInSecs;
}
