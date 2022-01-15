package com.or.heuristic.core.algo.geneticalgo;

import com.or.heuristic.core.util.Algorithm;
import com.or.heuristic.core.util.AlgorithmEnum;
import com.or.heuristic.core.util.ObjectiveSense;
import com.or.heuristic.core.util.SolutionComparator;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author klian
 */
public class GeneticAlgorithm extends Algorithm {
  private final ObjectiveSense objectiveSense;
  private final GaConfig gaConfig;
  private final List<? extends GaApplicable> startingPopulation;
  private GaApplicable bestSolution;
  private final SolutionComparator<GaApplicable> solutionComparator;

  public GeneticAlgorithm(ObjectiveSense objectiveSense, GaConfig gaConfig,
                          List<? extends GaApplicable> startingPopulation) {
    super(AlgorithmEnum.GENETIC_ALGORITHM.getName());
    this.objectiveSense = objectiveSense;
    this.gaConfig = gaConfig;
    this.startingPopulation = startingPopulation;
    this.solutionComparator = new SolutionComparator<>(this.objectiveSense);
    this.bestSolution = startingPopulation.stream()
      .sorted(solutionComparator)
      .limit(1)
      .collect(Collectors.toList())
      .get(0);
  }

  @Override
  public void solve() {
    int maxIter = gaConfig.getMaxIter();
    int maxIterNoImprove = gaConfig.getMaxIterNoImprove();
    int maxRuntimeInSecs = gaConfig.getMaxRuntimeInSecs();

    long startTime = System.currentTimeMillis();
    int iterNoImprove = 0;
    int iter = 0;
    while (true) {
      // crossover

      // mutation

      // check stopping criteria
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
