package com.or.heuristic.core.algo.tabusearch.basic;

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
public class TabuSearch<K> implements Name, Solver {
  /**
   * algorithm name
   */
  private String algorithmName;
  /**
   * indicates whether to maximize or minimize the objective
   */
  private ObjectiveSense objectiveSense;
  /**
   * configuration
   */
  private TsConfig tsConfig;
  /**
   * starting solution
   */
  private TsApplicable<K> startingSolution;
  /**
   * best solution encountered during the search process
   */
  private TsApplicable<K> bestSolution;
  /**
   * tabu table
   */
  private final Map<K, Integer> tabuTable;

  /**
   * Constructor
   *
   * @param objectiveSense objective sense that dictates whether to maximize of minimize the objective function
   * @param tsConfig algorithm configurations
   * @param startingSolution starting solution
   */
  public TabuSearch(ObjectiveSense objectiveSense, TsConfig tsConfig, TsApplicable<K> startingSolution) {
    this.algorithmName = AlgorithmEnum.TABU_SEARCH.getName();
    this.objectiveSense = objectiveSense;
    this.tsConfig = tsConfig;
    this.startingSolution = startingSolution;
    this.tabuTable = new HashMap<>();
  }

  @Override
  public void solve() {
    // obtain algorithm parameters
    int maxIter = tsConfig.getMaxIter();
    int maxIterNoImprove = tsConfig.getMaxIterNoImprove();
    int maxRuntimeInSecs = tsConfig.getMaxRuntimeInSecs();
    int neighborSize = tsConfig.getNeighborSize();
    int tabuLength = tsConfig.getTabuLength();

    // use the input solution as the current and best solution
    TsApplicable<K> currSolution = startingSolution;
    bestSolution = startingSolution;

    long startTime = System.currentTimeMillis();
    int iterNoImprove = 0;
    int iter = 0;
    while (true) {
      log.info("iter = {}, currSolution.obj = {}, bestSolution.obj = {}",
        iter, currSolution.getObjective(), bestSolution.getObjective());
      // create neighboring solutions and compute their objective values
      List<? extends TsApplicable<K>> neighborSolutions = currSolution.getNeighbors(neighborSize);
      neighborSolutions.forEach(Optimizable::computeObjective);

      // sort neighboring solutions based on objective sense
      if (objectiveSense == ObjectiveSense.MAXIMIZE) {
        neighborSolutions.sort(Comparator.comparingDouble(TsApplicable<K>::getObjective).reversed());
      } else {
        neighborSolutions.sort(Comparator.comparingDouble(TsApplicable<K>::getObjective));
      }

      // choose the next solution to move to
      boolean currSolutionUpdated = false;
      boolean bestSolutionUpdated = false;
      K tabuKey = null;
      for (int n = 0; n < neighborSize; n++) {
        TsApplicable<K> neighbor = neighborSolutions.get(n);
        tabuKey = neighbor.getTabuKey();
        if (objectiveSense == ObjectiveSense.MAXIMIZE) {
          if (!tabuTable.containsKey(tabuKey) || tabuTable.get(tabuKey) < iter) {
            currSolution = neighbor;
            currSolutionUpdated = true;
            if (currSolution.getObjective() > bestSolution.getObjective()) {
              bestSolution = currSolution;
              bestSolutionUpdated = true;
              break;
            }
          } else {
            // check aspiration criterion
            if (neighbor.getObjective() > bestSolution.getObjective()) {
              currSolution = neighbor;
              bestSolution = currSolution;
              currSolutionUpdated = true;
              bestSolutionUpdated = true;
              break;
            }
          }
        } else {
          if (!tabuTable.containsKey(tabuKey) || tabuTable.get(tabuKey) < iter) {
            currSolution = neighbor;
            currSolutionUpdated = true;
            if (currSolution.getObjective() < bestSolution.getObjective()) {
              bestSolution = currSolution;
              bestSolutionUpdated = true;
              break;
            }
          } else {
            // check aspiration criterion
            if (neighbor.getObjective() < bestSolution.getObjective()) {
              currSolution = neighbor;
              bestSolution = currSolution;
              currSolutionUpdated = true;
              bestSolutionUpdated = true;
              break;
            }
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

  @Override
  public String getName() {
    return this.algorithmName;
  }

  @Override
  public void setName(String name) {
    this.algorithmName = name;
  }
}
