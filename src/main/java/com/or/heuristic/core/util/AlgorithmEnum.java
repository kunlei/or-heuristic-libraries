package com.or.heuristic.core.util;

import lombok.Getter;

/**
 *
 * @author Kunlei Lian
 */
@Getter
public enum AlgorithmEnum {
  /**
   * tabu search
   */
  TABU_SEARCH("Tabu Search");

  /**
   * name of the algorithm
   */
  private final String name;

  AlgorithmEnum(String name) {
    this.name = name;
  }
}
