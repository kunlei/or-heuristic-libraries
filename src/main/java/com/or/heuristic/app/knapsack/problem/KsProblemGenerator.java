package com.or.heuristic.app.knapsack.problem;

import java.util.List;

/**
 * This class helps create a list of benchmarking problems for the knapsack problem.
 *
 * @author Kunlei Lian
 */
public class KsProblemGenerator {
  /**
   * This problem corresponds to the first problem listed on the webpage: <a href="https://people.sc.fsu.edu/~jburkardt/datasets/knapsack_01/knapsack_01.html">problem 1</a>
   * @return a knapsack benchmarking problem with optimal selection of below:
   * {1, 1, 1, 1, 0, 1, 0, 0, 0, 0}
   */
  public KsProblem getInstance1() {
    int capacity = 165;
    int[] weights = {23, 31, 29, 44, 53, 38, 63, 85, 89, 82};
    int[] profits = {92, 57, 49, 68, 60, 43, 67, 84, 87, 72};
    KsProblem problem = new KsProblem();
    List<KsItem> items = problem.getItems();
    for (int i = 0; i < weights.length; i++) {
      KsItem item = KsItem.builder()
        .id("item_" + i)
        .weight(weights[i])
        .profit(profits[i])
        .build();
      items.add(item);
    }
    problem.setCapacity(capacity);
    return problem;
  }
}
