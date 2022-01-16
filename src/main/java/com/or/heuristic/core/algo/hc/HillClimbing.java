package com.or.heuristic.core.algo.hc;

import com.or.heuristic.core.util.Algorithm;
import com.or.heuristic.core.util.AlgorithmEnum;
import com.or.heuristic.core.util.ObjectiveSense;
import com.or.heuristic.core.util.SolutionComparator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *
 * @author Kunlei Lian
 */
@Getter
@Setter
@Slf4j
public class HillClimbing extends Algorithm {
  /**
   * indicates whether to maximize or minimize the objective
   */
  private ObjectiveSense objectiveSense;
  /**
   * configuration
   */
  private HcConfig hcConfig;
  /**
   * starting solutions
   */
  private List<? extends HcApplicable> startingSolutions;
  /**
   * best solution encountered during the search process
   */
  private HcApplicable bestSolution;
  /**
   * solution comparator
   */
  private final SolutionComparator<HcApplicable> solutionComparator;

  public HillClimbing(ObjectiveSense objectiveSense, HcConfig hcConfig,
                      List<? extends HcApplicable> startingSolutions) {
    super(AlgorithmEnum.HILL_CLIMBING.getName());
    this.objectiveSense = objectiveSense;
    this.hcConfig = hcConfig;
    this.startingSolutions = startingSolutions;
    this.solutionComparator = new SolutionComparator<>(this.objectiveSense);
    this.bestSolution = startingSolutions.stream()
      .sorted(solutionComparator)
      .limit(1)
      .collect(Collectors.toList())
      .get(0);
  }

  @Override
  public void solve() {
    int maxIter = hcConfig.getMaxIter();
    int maxIterNoImprove = hcConfig.getMaxIterNoImprove();
    int maxRuntimeInSecs = hcConfig.getMaxRuntimeInSecs();

    HcApplicable currSolution = bestSolution;

    long startTime = System.currentTimeMillis();
    int iterNoImprove = 0;
    int iter = 0;
    while (true) {
      HcApplicable neighbor = currSolution.getNeighbor();
      neighbor.computeObjective();

      if (solutionComparator.compare(neighbor, currSolution) < 0) {
        currSolution = neighbor;
        if (solutionComparator.compare(neighbor, bestSolution) < 0) {
          bestSolution = neighbor;
          iterNoImprove = 0;
        } else {
          iterNoImprove++;
        }
      }

      long elapsedSecs = TimeUnit.SECONDS
        .convert(System.currentTimeMillis() - startTime,
          TimeUnit.MILLISECONDS);
      if (iter++ >= maxIter ||
        iterNoImprove >= maxIterNoImprove ||
        elapsedSecs >= maxRuntimeInSecs) {
        break;
      }
    }
  }
}
