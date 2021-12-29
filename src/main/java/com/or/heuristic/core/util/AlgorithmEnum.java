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
  TABU_SEARCH("Tabu Search"),
  /**
   * simulated annealing
   */
  SIMULATED_ANNEALING("Simulated Annealing"),
  /**
   * hill climbing
   */
  HILL_CLIMBING("Hill Climbing"),
  /**
   * genetic algorithm
   */
  GENETIC_ALGORITHM("Genetic Algorithm");

  /**
   * name of the algorithm
   */
  private final String name;

  AlgorithmEnum(String name) {
    this.name = name;
  }
}
