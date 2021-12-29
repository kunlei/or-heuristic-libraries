package com.or.heuristic.core.algo.hc;

import lombok.Builder;
import lombok.Getter;

/**
 * This class contains all the configurations for the hill climbing algorithm.
 *
 * @author Kunlei Lian
 */
@Getter
@Builder
public class HcConfig {
  private int maxIter;
  private int maxIterNoImprove;
  private final int maxRuntimeInSecs;
}
