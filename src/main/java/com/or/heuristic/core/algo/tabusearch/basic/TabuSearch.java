package com.or.heuristic.core.algo.tabusearch.basic;

import com.or.heuristic.core.util.AlgorithmEnum;
import com.or.heuristic.core.util.Name;
import com.or.heuristic.core.util.ObjectiveSense;
import com.or.heuristic.core.util.Optimizable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * This class implements a basic workflow of the tabu search algorithm.
 *
 * @author Kunlei Lian
 */
@Getter
@Setter
@Slf4j
public class TabuSearch<K> implements Name {
  /**
   * algorithm name
   */
  private String algorithmName;
  /**
   * indicates whether to maximize or minimize the objective
   */
  private ObjectiveSense objectiveSense;
  /**
   * configuration
   */
  private TsConfig tsConfig;
  /**
   * starting solution
   */
  private TsApplicable<K> startingSolution;
  /**
   * best solution encountered during the search process
   */
  private TsApplicable<K> bestSolution;
  /**
   * tabu table
   */
  private final Map<K, Integer> tabuTable;

  public TabuSearch() {
    this.algorithmName = AlgorithmEnum.TABU_SEARCH.getName();
    this.tsConfig = null;
    this.startingSolution = null;
    this.tabuTable = new HashMap<>();
  }

  public void solve() {
    // obtain algorithm parameters
    int maxIter = tsConfig.getMaxIter();
    int maxIterNoImprove = tsConfig.getMaxIterNoImprove();
    int maxRuntimeInSecs = tsConfig.getMaxRuntimeInSecs();
    int neighborSize = tsConfig.getNeighborSize();
    int tabuLength = tsConfig.getTabuLength();

    // use the input solution as the current and best solution
    TsApplicable<K> currSolution = startingSolution;
    bestSolution = startingSolution;

    long startTime = System.currentTimeMillis();
    int iterNoImprove = 0;
    int iter = 0;
    while (true) {
      // create neighboring solutions
      List<? extends TsApplicable<K>> neighborSolutions = currSolution.getNeighbors(neighborSize);

      // evaluate neighboring solutions
      neighborSolutions.forEach(Optimizable::computeObjective);

      // sort neighboring solutions based on objective sense
      if (objectiveSense == ObjectiveSense.MAXIMIZE) {
        neighborSolutions.sort(Comparator.comparingDouble(TsApplicable<K>::getObjective).reversed());
      } else {
        neighborSolutions.sort(Comparator.comparingDouble(TsApplicable<K>::getObjective));
      }

      // choose the next solution to move to
      boolean currSolutionUpdated = false;
      boolean bestSolutionUpdated = false;
      K tabuKey = null;
      for (int n = 0; n < neighborSize; n++) {
        TsApplicable<K> neighbor = neighborSolutions.get(n);
        tabuKey = neighbor.getTabuKey();
        if (objectiveSense == ObjectiveSense.MAXIMIZE) {
          if (!tabuTable.containsKey(tabuKey) || tabuTable.get(tabuKey) < iter) {
            currSolution = neighbor;
            currSolutionUpdated = true;
            if (currSolution.getObjective() > bestSolution.getObjective()) {
              bestSolution = currSolution;
              bestSolutionUpdated = true;
              iterNoImprove = 0;
              break;
            }
          } else {
            // check aspiration criterion
            if (neighbor.getObjective() > bestSolution.getObjective()) {
              currSolution = neighbor;
              bestSolution = currSolution;
              currSolutionUpdated = true;
              bestSolutionUpdated = true;
              iterNoImprove = 0;
              break;
            }
          }
        } else {
          if (!tabuTable.containsKey(tabuKey) || tabuTable.get(tabuKey) < iter) {
            currSolution = neighbor;
            currSolutionUpdated = true;
            if (currSolution.getObjective() < bestSolution.getObjective()) {
              bestSolution = currSolution;
              bestSolutionUpdated = true;
              iterNoImprove = 0;
              break;
            }
          } else {
            // check aspiration criterion
            if (neighbor.getObjective() < bestSolution.getObjective()) {
              currSolution = neighbor;
              bestSolution = currSolution;
              currSolutionUpdated = true;
              bestSolutionUpdated = true;
              iterNoImprove = 0;
              break;
            }
          }
        }
      }

      // update tabu table if applicable
      if (currSolutionUpdated) {
        tabuTable.put(tabuKey, iter + tabuLength);
      }

      if (!bestSolutionUpdated) {
        iterNoImprove++;
      }

      // check if maximum iteration count has been reached or not
      iter++;
      if (iter >= maxIter) {
        break;
      }

      // check whether the best objective has not been improved within allowed number of iterations
      if (iterNoImprove >= maxIterNoImprove) {
        break;
      }

      // check runtime limit
      long currTime = System.currentTimeMillis();
      if (TimeUnit.SECONDS.convert(currTime - startTime, TimeUnit.MILLISECONDS) > maxRuntimeInSecs) {
        break;
      }
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
