package com.or.heuristic.core.algo.tabusearch.basic;

import com.or.heuristic.core.util.Algorithm;
import com.or.heuristic.core.util.ObjectiveSense;
import com.or.heuristic.core.util.Optimizable;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * This class implements the basic workflow of the tabu search algorithm.
 *
 * @author Kunlei Lian
 */
@Getter
@Setter
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
    int neighborSize = tsConfig.getNeighborSize();

    TsApplicable<K> currSolution = startingSolution;
    bestSolution = startingSolution;

    int iterNoImprove = 0;
    for (int iter = 0; iter < maxIter; iter++) {
      // create neighboring solutions
      List<? extends TsApplicable<K>> neighborSolutions = currSolution.getNeighbors();

      // evaluate neighboring solutions
      neighborSolutions.forEach(Optimizable::computeObjective);

      // sort neighboring solutions based on objective sense
      if (objectiveSense == ObjectiveSense.MAXIMIZE) {
        neighborSolutions.sort(Comparator.comparingDouble(TsApplicable<K>::getObjective).reversed());
      } else {
        neighborSolutions.sort(Comparator.comparingDouble(TsApplicable<K>::getObjective));
      }

      // choose the next solution to move to
      for (int n = 0; n < neighborSize; n++) {
        TsApplicable<K> neighbor = neighborSolutions.get(n);
        if (objectiveSense == ObjectiveSense.MAXIMIZE) {
          if (neighbor.getObjective() > currSolution.getObjective()) {
            currSolution = neighbor;
            if (neighbor.getObjective() > bestSolution.getObjective()) {
              bestSolution = neighbor;
            }
            iterNoImprove = 0;
          } else {
            K tabuKey = neighbor.getTabuKey();
//            if (tabuTable)
          }
        } else {
          if (neighbor.getObjective() < currSolution.getObjective()) {
            currSolution = neighbor;
            if (neighbor.getObjective() < bestSolution.getObjective()) {
              bestSolution = neighbor;
            }
            iterNoImprove = 0;
          } else {
            K tabuKey = neighbor.getTabuKey();

          }
        }
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
