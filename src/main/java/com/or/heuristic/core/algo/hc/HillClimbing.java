package com.or.heuristic.core.algo.hc;

import com.or.heuristic.core.util.AlgorithmEnum;
import com.or.heuristic.core.util.Name;
import com.or.heuristic.core.util.ObjectiveSense;
import com.or.heuristic.core.util.Algorithm;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author Kunlei Lian
 */
@Getter
@Setter
@Slf4j
public class HillClimbing implements Name, Algorithm {
  /**
   * name of the algorithm, defaulted to 'simulated annealing'
   */
  private String algorithmName;
  /**
   * indicates whether to maximize or minimize the objective
   */
  private ObjectiveSense objectiveSense;
  private HcConfig hcConfig;
  private HcApplicable startingSolution;
  private HcApplicable bestSolution;

  public HillClimbing() {
    this.algorithmName = AlgorithmEnum.HILL_CLIMBING.getName();
    this.objectiveSense = null;
    this.hcConfig = null;
    this.startingSolution = null;
    this.bestSolution = null;
  }

  @Override
  public void solve() {
    int maxIter = hcConfig.getMaxIter();
    int maxIterNoImprove = hcConfig.getMaxIterNoImprove();
    int maxRuntimeInSecs = hcConfig.getMaxRuntimeInSecs();

    HcApplicable currSolution = startingSolution;
    bestSolution = startingSolution;

    long startTime = System.currentTimeMillis();
    int iterNoImprove = 0;
    for (int iter = 0; iter < maxIter; iter++) {
      HcApplicable neighbor = currSolution.getNeighbor();

      neighbor.computeObjective();

      double neighborObj = neighbor.getObjective();
      double currObj = currSolution.getObjective();
      double bestObj = bestSolution.getObjective();
      if (objectiveSense == ObjectiveSense.MAXIMIZE) {
        if (neighborObj > currObj) {
          currSolution = neighbor;
          if (neighborObj > bestObj) {
            bestSolution = neighbor;
            iterNoImprove = 0;
          }
        } else {
          iterNoImprove++;
        }
      } else {
        if (neighborObj < currObj) {
          currSolution = neighbor;
          if (neighborObj < bestObj) {
            bestSolution = neighbor;
            iterNoImprove = 0;
          }
        } else {
          iterNoImprove++;
        }
      }

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
    return algorithmName;
  }

  @Override
  public void setName(String name) {
    this.algorithmName = name;
  }
}
