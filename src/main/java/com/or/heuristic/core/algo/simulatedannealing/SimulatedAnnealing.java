package com.or.heuristic.core.algo.simulatedannealing;

import com.or.heuristic.core.util.Algorithm;
import com.or.heuristic.core.util.AlgorithmEnum;
import com.or.heuristic.core.util.ObjectiveSense;
import com.or.heuristic.core.util.SolutionComparator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * This class implements the simulated annealing algorithm.
 *
 * @author Kunlei Lian
 */
@Getter
@Setter
@Slf4j
public class SimulatedAnnealing extends Algorithm {
  /**
   * indicates whether to maximize or minimize the objective
   */
  private ObjectiveSense objectiveSense;
  /**
   * algorithm configuration
   */
  private SaConfig saConfig;
  /**
   * initial set of solutions
   */
  private List<? extends SaApplicable> startingSolutions;
  /**
   * best solution found during the search
   */
  private SaApplicable bestSolution;
  /**
   * solution comparator
   */
  private final SolutionComparator<SaApplicable> solutionComparator;

  /**
   * Constructor
   *
   * @param objectiveSense objective sense that dictates whether to maximize of minimize the objective function
   * @param saConfig algorithm configurations
   * @param startingSolutions starting solutions
   */
  public SimulatedAnnealing(ObjectiveSense objectiveSense, SaConfig saConfig,
                            List<? extends SaApplicable> startingSolutions) {
    super(AlgorithmEnum.SIMULATED_ANNEALING.getName());
    this.objectiveSense = objectiveSense;
    this.saConfig = saConfig;
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
    // obtain algorithm configurations
    double startingTemperature = saConfig.getStartingTemperature();
    double endingTemperature = saConfig.getEndingTemperature();
    double annealingRate = saConfig.getAnnealingRate();
    int maxIter = saConfig.getMaxIter();
    int maxIterNoImprove = saConfig.getMaxIterNoImprove();
    int maxRuntimeInSecs = saConfig.getMaxRuntimeInSecs();

    // use the best solution from the given set of starting solution as the current solution
    SaApplicable currSolution = bestSolution;

    SecureRandom random = new SecureRandom();
    double currTemperature = startingTemperature;
    long startTime = System.currentTimeMillis();
    int iter = 0;
    int iterNoImprove = 0;
    while (true) {
      // create a neighboring solution
      SaApplicable neighbor = currSolution.getNeighbor();

      // evaluate neighbor solution
      neighbor.computeObjective();

      if (solutionComparator.compare(neighbor, currSolution) < 0) {
        currSolution = neighbor;
        if (solutionComparator.compare(neighbor, bestSolution) < 0) {
          bestSolution = neighbor;
          iterNoImprove = 0;
        } else {
          iterNoImprove++;
        }
      } else {
        double currObj = currSolution.getObjective();
        double neighborObj = neighbor.getObjective();
        if (Math.exp((neighborObj - currObj) / currTemperature) > random.nextDouble()) {
          currSolution = neighbor;
        }
      }

      currTemperature *= annealingRate;
      long elapsedSecs = TimeUnit.SECONDS
        .convert(System.currentTimeMillis() - startTime,
          TimeUnit.MILLISECONDS);
      if (iter++ >= maxIter ||
        iterNoImprove >= maxIterNoImprove ||
        elapsedSecs >= maxRuntimeInSecs ||
        currTemperature < endingTemperature) {
        break;
      }
    }
  }
}
