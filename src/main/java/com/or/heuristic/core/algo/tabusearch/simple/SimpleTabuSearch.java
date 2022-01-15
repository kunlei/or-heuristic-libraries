package com.or.heuristic.core.algo.tabusearch.simple;

import com.or.heuristic.core.util.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * This class implements the simple tabu search algorithm described in the paper:
 * <ul>
 *   <li>Glover, Fred. “Tabu Search—Part I.” ORSA Journal on Computing 1, no. 3 (August 1, 1989): 190–206.
 *   https://doi.org/10.1287/ijoc.1.3.190.</li>
 * </ul>
 *
 * <p>{@code K} denotes the type of the move that leads to a neighboring solution.
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
   * starting solutions
   */
  private List<? extends SimpleTsApplicable<K>> startingSolutions;
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
   * @param startingSolutions starting solutions
   */
  public SimpleTabuSearch(ObjectiveSense objectiveSense, SimpleTsConfig simpleTsConfig,
                          List<? extends SimpleTsApplicable<K>> startingSolutions) {
    super(AlgorithmEnum.TABU_SEARCH.getName());
    this.objectiveSense = objectiveSense;
    this.simpleTsConfig = simpleTsConfig;
    this.startingSolutions = startingSolutions;
    this.tabuTable = new HashMap<>();
    this.solutionComparator = new SolutionComparator<>(this.objectiveSense);
    this.bestSolution = startingSolutions.stream()
      .sorted(solutionComparator)
      .limit(1)
      .collect(Collectors.toList())
      .get(0);
  }

  @Override
  public void solve() {
    // obtain algorithm parameters
    int maxIter = simpleTsConfig.getMaxIter();
    int maxIterNoImprove = simpleTsConfig.getMaxIterNoImprove();
    int maxRuntimeInSecs = simpleTsConfig.getMaxRuntimeInSecs();
    int neighborSize = simpleTsConfig.getNeighborSize();
    int tabuLength = simpleTsConfig.getTabuLength();

    // use the best solution from the given set of starting solution as the current solution
    SimpleTsApplicable<K> currSolution = bestSolution;

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
      K tabuKey;
      for (SimpleTsApplicable<K> neighbor : neighborSolutions) {
        tabuKey = neighbor.getTabuKey();
        if (!tabuTable.containsKey(tabuKey) || tabuTable.get(tabuKey) < iter) {
          // in this case, the move that leads to the neighboring solution is
          // either not in the tabu table or its tabu status has expired
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

      // in case no move is possible, choose the best neighbor
      if (!currSolutionUpdated) {
        currSolution = neighborSolutions.iterator().next();
      }

      // update tabu table if applicable
      tabuKey = currSolution.getTabuKey();
      tabuTable.put(tabuKey, iter + tabuLength);

      // cleanup tabu table
      for (Iterator<Map.Entry<K, Integer>> it = tabuTable.entrySet().iterator(); it.hasNext();) {
        Map.Entry<K, Integer> entry = it.next();
        if (entry.getValue() < iter) {
          it.remove();
        }
      }

      // check stopping criteria
      iterNoImprove = bestSolutionUpdated ? 0 : iterNoImprove + 1;
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