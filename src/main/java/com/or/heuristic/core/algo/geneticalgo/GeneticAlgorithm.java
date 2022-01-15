package com.or.heuristic.core.algo.geneticalgo;

import com.or.heuristic.core.util.*;

import java.security.SecureRandom;
import java.util.ArrayList;
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
    double mutationProbability = gaConfig.getMutationProbability();
    double elitePreserveProportion = gaConfig.getElitePreserveProportion();

    SecureRandom random = new SecureRandom();
    long startTime = System.currentTimeMillis();
    int iterNoImprove = 0;
    int iter = 0;
    List<GaApplicable> currPopulation = new ArrayList<>(startingPopulation);
    while (true) {
      // crossover
      List<GaApplicable> newPopulation = createNewPopulation(currPopulation);

      // mutation
      newPopulation.forEach(solution -> {
        if (random.nextDouble() <= mutationProbability) {
          solution.mutation();
        }
      });

      // combine the two population
      currPopulation.addAll(newPopulation);
      currPopulation.sort(solutionComparator);

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

  /**
   * create a new population
   * @param currPopulation current population
   * @return a newly created population
   */
  private List<GaApplicable> createNewPopulation(List<GaApplicable> currPopulation) {
    int populationSize = currPopulation.size();
    List<Double> objectives = currPopulation.stream()
      .map(Optimizable::getObjective)
      .collect(Collectors.toList());

    List<GaApplicable> newPopulation = new ArrayList<>(populationSize);
    for (int i = 0; i < populationSize; i++) {

    }
    return newPopulation;
  }

  private int rouletteWheelSelection(List<Double> objectives, double probability) {

  }
}
