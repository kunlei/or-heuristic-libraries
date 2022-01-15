package com.or.heuristic.core.algo.simulatedannealing;

import lombok.Builder;
import lombok.Getter;

/**
 * This class defines the configurations needed by the simulated annealing algorithm.
 *
 * @author Kunlei
 */
@Getter
@Builder
public class SaConfig {
  /**
   * starting temperature
   */
  private double startingTemperature;
  /**
   * ending temperature
   */
  private double endingTemperature;
  /**
   * annealing rate
   */
  private double annealingRate;
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
