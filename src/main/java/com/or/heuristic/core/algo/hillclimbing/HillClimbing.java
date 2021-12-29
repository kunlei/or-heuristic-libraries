package com.or.heuristic.core.algo.hillclimbing;

import com.or.heuristic.core.util.AlgorithmEnum;
import com.or.heuristic.core.util.Name;
import com.or.heuristic.core.util.ObjectiveSense;
import com.or.heuristic.core.util.Solver;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Kunlei Lian
 */
@Getter
@Setter
@Slf4j
public class HillClimbing implements Name, Solver {
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
