package com.or.heuristic.core.algo.geneticalgo;

import lombok.Builder;
import lombok.Getter;

/**
 * This class defines the parameters of the genetic algorithm.
 *
 * @author Kunlei Lian
 */
@Getter
@Builder
public final class GaConfig {
  /**
   * population size
   */
  private final int populationSize;
  /**
   * mutation probability
   */
  private final double mutationProbability;
  /**
   * preserve proportion of elite solutions
   */
  private final double elitePreserveProportion;
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
