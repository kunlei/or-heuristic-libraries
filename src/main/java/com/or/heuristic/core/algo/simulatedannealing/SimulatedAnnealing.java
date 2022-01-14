package com.or.heuristic.core.algo.simulatedannealing;

import com.or.heuristic.core.util.Algorithm;
import com.or.heuristic.core.util.AlgorithmEnum;
import com.or.heuristic.core.util.ObjectiveSense;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;

/**
 * @author Kunlei Lian
 */
@Getter
@Setter
@Slf4j
public class SimulatedAnnealing extends Algorithm {
  /**
   * name of the algorithm, defaulted to 'simulated annealing'
   */
  private String algorithmName;
  /**
   * indicates whether to maximize or minimize the objective
   */
  private ObjectiveSense objectiveSense;
  /**
   * algorithm configuration
   */
  private SaConfig saConfig;
  /**
   * initial solution to start the search
   */
  private SaApplicable startingSolution;
  /**
   * best solution found during the search
   */
  private SaApplicable bestSolution;

  public SimulatedAnnealing() {
    super(AlgorithmEnum.SIMULATED_ANNEALING.getName());
    this.objectiveSense = null;
    this.saConfig = null;
    this.startingSolution = null;
    this.bestSolution = null;
  }

  @Override
  public void solve() {
    double startingTemperature = saConfig.getStartingTemperature();
    double endingTemperature = saConfig.getEndingTemperature();
    double annealingRate = saConfig.getAnnealingRate();

    SaApplicable currSolution = startingSolution;
    bestSolution = startingSolution;

    SecureRandom random = new SecureRandom();
    double currTemperature = startingTemperature;
    while (currTemperature >= endingTemperature) {
      // create a neighboring solution
      SaApplicable neighbor = currSolution.getNeighbor();

      // evaluate neighbor solution
      neighbor.computeObjective();

      double neighborObj = neighbor.getObjective();
      double currObj = currSolution.getObjective();
      double bestObj = bestSolution.getObjective();
      if (objectiveSense == ObjectiveSense.MAXIMIZE) {
        if (neighborObj > currObj) {
          currSolution = neighbor;
          if (neighborObj > bestObj) {
            bestSolution = currSolution;
          }
        } else {
          if (Math.exp((neighborObj - currObj) / currTemperature) > random.nextDouble()) {
            currSolution = neighbor;
          }
        }
      } else {
        if (neighborObj < currObj) {
          currSolution = neighbor;
          if (neighborObj < bestObj) {
            bestSolution = currSolution;
          }
        } else {
          if (Math.exp((currObj - neighborObj) / currTemperature) > random.nextDouble()) {
            currSolution = neighbor;
          }
        }
      }
      currTemperature *= annealingRate;
    }
  }

  @Override
  public String getName() {
    return this.algorithmName;
  }

  @Override
  public void setName(String name) {
    this.algorithmName = name;
  }
}
