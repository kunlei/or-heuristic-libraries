package com.or.heuristic.core.util;

import lombok.Getter;

/**
 * @author klian
 */
@Getter
public enum ProblemEnum {
  /**
   * two-dimensional knapsack problem
   */
  KNAPSACK_2D("Knapsack 2D");

  /**
   * name of the problem
   */
  private final String name;

  ProblemEnum(String name) {
    this.name = name;
  }
}
