package com.or.heuristic.core.algo.simulatedannealing;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Kunlei
 */
@Getter
@Builder
public class SaConfig {
  private double startingTemperature;
  private double endingTemperature;
  private double annealingRate;
}
