package com.or.heuristic.core.algo.tabusearch.basic;

import lombok.Builder;
import lombok.Getter;

/**
 *
 * @author Kunlei Lian
 */
@Getter
@Builder
public final class TsConfig {
  private final int tabuLength;
  private final int neighborSize;
  private final int maxIter;
  private final int maxIterNoImprove;
  private final int maxRuntimeInSecs;
}
