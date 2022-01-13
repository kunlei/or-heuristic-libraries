package com.or.heuristic.core.algo.tabusearch.simple;

import com.or.heuristic.core.util.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * This class implements the simple tabu search algorithm described in the following paper:
 * <ul>
 *   <li>Glover, Fred. “Tabu Search—Part I.” ORSA Journal on Computing 1, no. 3 (August 1, 1989): 190–206.
 *   https://doi.org/10.1287/ijoc.1.3.190.</li>
 * </ul>
 *
 * <p>{@code K} denotes the type of the key that enters the tabu table.
 *
 * @author Kunlei Lian
 */
@Getter
@Setter
@Slf4j
public class SimpleTabuSearch<K> extends Algorithm {
  /**
   * indicates whether to maximize or minimize the objective
   */
  private ObjectiveSense objectiveSense;
  /**
   * configuration
   */
  private SimpleTsConfig simpleTsConfig;
  /**
   * starting solution
   */
  private SimpleTsApplicable<K> startingSolution;
  /**
   * best solution encountered during the search process
   */
  private SimpleTsApplicable<K> bestSolution;
  /**
   * tabu table
   */
  private final Map<K, Integer> tabuTable;
  /**
   * solution comparator
   */
  private final SolutionComparator<SimpleTsApplicable<K>> solutionComparator;

  /**
   * Constructor
   *
   * @param objectiveSense objective sense that dictates whether to maximize of minimize the objective function
   * @param simpleTsConfig algorithm configurations
   * @param startingSolution starting solution
   */
  public SimpleTabuSearch(ObjectiveSense objectiveSense, SimpleTsConfig simpleTsConfig, SimpleTsApplicable<K> startingSolution) {
    super(AlgorithmEnum.TABU_SEARCH.getName());
    this.objectiveSense = objectiveSense;
    this.simpleTsConfig = simpleTsConfig;
    this.startingSolution = startingSolution;
    this.bestSolution = startingSolution;
    this.tabuTable = new HashMap<>();
    this.solutionComparator = new SolutionComparator<>(this.objectiveSense);
  }

  @Override
  public void solve() {
    // obtain algorithm parameters
    int maxIter = simpleTsConfig.getMaxIter();
    int maxIterNoImprove = simpleTsConfig.getMaxIterNoImprove();
    int maxRuntimeInSecs = simpleTsConfig.getMaxRuntimeInSecs();
    int neighborSize = simpleTsConfig.getNeighborSize();
    int tabuLength = simpleTsConfig.getTabuLength();

    // use the starting solution as the current solution
    SimpleTsApplicable<K> currSolution = startingSolution;

    long startTime = System.currentTimeMillis();
    int iterNoImprove = 0;
    int iter = 0;
    while (true) {
      log.info("iter = {}, currSolution.obj = {}, bestSolution.obj = {}",
        iter, currSolution.getObjective(), bestSolution.getObjective());
      // create neighboring solutions and compute their objective values
      List<? extends SimpleTsApplicable<K>> neighborSolutions = currSolution.getNeighbors(neighborSize);
      neighborSolutions.forEach(Optimizable::computeObjective);

      // sort neighboring solutions based on objective sense
      neighborSolutions.sort(this.solutionComparator);

      // choose the next solution to move to
      boolean currSolutionUpdated = false;
      boolean bestSolutionUpdated = false;
      K tabuKey = null;
      for (SimpleTsApplicable<K> neighbor : neighborSolutions) {
        tabuKey = neighbor.getTabuKey();
        if (!tabuTable.containsKey(tabuKey) || tabuTable.get(tabuKey) < iter) {
          // in this case, the move that leads to the neighboring solution is either not in the tabu table or its tabu
          // status has expired
          currSolution = neighbor;
          currSolutionUpdated = true;
          if (solutionComparator.compare(currSolution, bestSolution) < 0) {
            bestSolution = currSolution;
            bestSolutionUpdated = true;
            break;
          }
        } else {
          // check aspiration criterion
          if (solutionComparator.compare(neighbor, bestSolution) < 0) {
            currSolution = neighbor;
            bestSolution = currSolution;
            currSolutionUpdated = true;
            bestSolutionUpdated = true;
            break;
          }
        }
      }

      // update tabu table if applicable
      if (currSolutionUpdated) {
        tabuTable.put(tabuKey, iter + tabuLength);
      }
      iterNoImprove = bestSolutionUpdated ? 0 : iterNoImprove + 1;

      // check stopping criteria
      long elapsedSecs = TimeUnit.SECONDS.convert(System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS);
      if (iter++ >= maxIter || iterNoImprove >= maxIterNoImprove || elapsedSecs >= maxRuntimeInSecs) {
        break;
      }
    }
  }
}
