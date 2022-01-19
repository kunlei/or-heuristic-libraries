package com.or.heuristic.core.algo.geneticalgo;

import com.or.heuristic.core.util.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
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
  private final SecureRandom random;

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
    this.random = new SecureRandom();
  }

  @Override
  public void solve() {
    int populationSize = gaConfig.getPopulationSize();
    int maxIter = gaConfig.getMaxIter();
    int maxIterNoImprove = gaConfig.getMaxIterNoImprove();
    int maxRuntimeInSecs = gaConfig.getMaxRuntimeInSecs();
    double mutationProbability = gaConfig.getMutationProbability();
    double elitePreserveProportion = gaConfig.getElitePreserveProportion();
    int numEliteSolutionsToPreserve = (int) (populationSize * elitePreserveProportion);

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
      newPopulation.addAll(currPopulation);
      currPopulation.clear();
      newPopulation.stream()
          .sorted(this.solutionComparator)
          .limit(numEliteSolutionsToPreserve)
          .forEach(currPopulation::add);

      List<GaApplicable> nonEliteSolutions = newPopulation.stream()
        .sorted(this.solutionComparator)
        .skip(numEliteSolutionsToPreserve)
        .collect(Collectors.toList());
      while (currPopulation.size() < populationSize) {
        currPopulation.add(nonEliteSolutions.get(random.nextInt(nonEliteSolutions.size())));
      }

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
    DoubleSummaryStatistics objSummary = objectives.stream()
      .mapToDouble(Double::doubleValue)
      .summaryStatistics();
    List<Double> normalizedObjectives = new ArrayList<>(populationSize);
    if (objectiveSense == ObjectiveSense.MAXIMIZE) {
      objectives.stream()
        .map(o -> o / objSummary.getMax())
        .forEach(normalizedObjectives::add);
    } else {
      objectives.stream()
        .map(o -> objSummary.getMax() - o)
        .map(o -> o / objSummary.getMax() + 1.0)
        .forEach(normalizedObjectives::add);
    }
    double sumNormalizedObj = normalizedObjectives.stream()
      .mapToDouble(Double::doubleValue)
      .summaryStatistics()
      .getSum();

    List<GaApplicable> newPopulation = new ArrayList<>(populationSize);
    while (newPopulation.size() < populationSize) {
      int parent1Idx = rouletteWheelSelection(normalizedObjectives, sumNormalizedObj, random.nextDouble());
      int parent2Idx = rouletteWheelSelection(normalizedObjectives, sumNormalizedObj, random.nextDouble());
      GaApplicable child = currPopulation.get(parent1Idx).crossover(currPopulation.get(parent2Idx));
      child.computeObjective();
      newPopulation.add(child);
    }
    return newPopulation;
  }

  private int rouletteWheelSelection(List<Double> objectives, double totalObj, double probability) {
    double total = 0;
    for (int i = 0; i < objectives.size(); i++) {
      total += objectives.get(i);
      if (total / totalObj >= probability) {
        return i;
      }
    }
    return objectives.size() - 1;
  }
}
