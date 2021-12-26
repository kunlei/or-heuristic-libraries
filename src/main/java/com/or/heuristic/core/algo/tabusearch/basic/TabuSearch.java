package com.or.heuristic.core.algo.tabusearch.basic;

import com.or.heuristic.core.util.Algorithm;
import com.or.heuristic.core.util.ObjectiveSense;
import com.or.heuristic.core.util.Optimizable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * This class implements the basic workflow of the tabu search algorithm.
 *
 * @author Kunlei Lian
 */
@Getter
@Setter
@Slf4j
public class TabuSearch<K> implements Algorithm {
  private String algorithmName;
  private ObjectiveSense objectiveSense;
  private TsConfig tsConfig;
  private TsApplicable<K> startingSolution;
  private TsApplicable<K> bestSolution;
  private final Map<K, Integer> tabuTable;

  public TabuSearch() {
    this.algorithmName = "Tabu Search";
    this.tsConfig = null;
    this.startingSolution = null;
    this.tabuTable = new HashMap<>();
  }

  public void solve() {
    int maxIter = tsConfig.getMaxIter();
    int maxIterNoImprove = tsConfig.getMaxIterNoImprove();
    int maxRuntimeInSecs = tsConfig.getMaxRuntimeInSecs();
    int neighborSize = tsConfig.getNeighborSize();

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
      boolean bestSolutionUpdated = false;
      for (int n = 0; n < neighborSize; n++) {
        TsApplicable<K> neighbor = neighborSolutions.get(n);
        K tabuKey = neighbor.getTabuKey();
        if (objectiveSense == ObjectiveSense.MAXIMIZE) {
          if (!tabuTable.containsKey(tabuKey) || tabuTable.get(tabuKey) < iter) {
            currSolution = neighbor;
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
              bestSolutionUpdated = true;
              iterNoImprove = 0;
              break;
            }
          }
        } else {
          if (!tabuTable.containsKey(tabuKey) || tabuTable.get(tabuKey) < iter) {
            currSolution = neighbor;
            if (currSolution.getObjective() < bestSolution.getObjective()) {
              bestSolution = currSolution;
              iterNoImprove = 0;
              break;
            }
          } else {
            // check aspiration criterion
            if (neighbor.getObjective() < bestSolution.getObjective()) {
              currSolution = neighbor;
              bestSolution = currSolution;
              iterNoImprove = 0;
              break;
            }
          }
        }
      }

      if (!bestSolutionUpdated) {
        iterNoImprove++;
      }

      iter++;
      if (iter >= maxIter) {
        break;
      }

      // check stopping criteria
      if (iterNoImprove >= maxIterNoImprove) {
        break;
      }

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
