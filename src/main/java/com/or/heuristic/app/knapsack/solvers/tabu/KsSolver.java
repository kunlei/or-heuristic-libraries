package com.or.heuristic.app.knapsack.solvers.tabu;

import com.or.heuristic.app.knapsack.problem.KsProblem;
import com.or.heuristic.app.knapsack.problem.KsProblemGenerator;
import com.or.heuristic.app.knapsack.solvers.util.KsSolution;
import com.or.heuristic.core.algo.tabusearch.simple.SimpleTabuSearch;
import com.or.heuristic.core.algo.tabusearch.simple.SimpleTsConfig;
import com.or.heuristic.core.util.ObjectiveSense;

import java.util.Collections;
import java.util.List;

/**
 * @author klian
 */
public final class KsSolver {

  public static void main(String[] args) {
    // create problem instance
    KsProblemGenerator problemGenerator = new KsProblemGenerator();
    KsProblem instance1 = problemGenerator.getInstance1();

    // create starting solution
    List<KsSolution> startingSolutions = Collections.singletonList(new KsSolution(instance1));


    SimpleTsConfig simpleTsConfig = SimpleTsConfig.builder()
      .maxIter(1000)
      .maxIterNoImprove(50)
      .maxRuntimeInSecs(600)
      .neighborSize(instance1.getItems().size())
      .tabuLength(instance1.getItems().size())
      .build();

    SimpleTabuSearch<Integer> tabuSearch = new SimpleTabuSearch<Integer>(ObjectiveSense.MAXIMIZE,
      simpleTsConfig,
      startingSolutions);

    tabuSearch.solve();
  }
}
