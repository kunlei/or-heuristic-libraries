package com.or.heuristic.app.knapsack.problem;

import java.util.List;

/**
 * This class helps create a list of benchmarking problems for the knapsack problem.
 *
 * @author Kunlei Lian
 */
public class KsProblemGenerator {
  /**
   * This problem corresponds to the first problem listed on the webpage:
   * <a href="https://people.sc.fsu.edu/~jburkardt/datasets/knapsack_01/knapsack_01.html">problem 1</a>
   *
   * @return a knapsack benchmarking problem with optimal selection of
   * {1, 1, 1, 1, 0, 1, 0, 0, 0, 0}
   */
  public KsProblem getInstance1() {
    int capacity = 165;
    int[] weights = {23, 31, 29, 44, 53, 38, 63, 85, 89, 82};
    int[] profits = {92, 57, 49, 68, 60, 43, 67, 84, 87, 72};
    return createProblem(capacity, weights, profits);
  }

  /**
   * This problem corresponds to the first problem listed on the webpage:
   * <a href="https://people.sc.fsu.edu/~jburkardt/datasets/knapsack_01/knapsack_01.html">problem 1</a>
   *
   * @return a knapsack benchmarking problem with optimal selection of
   * {0, 1, 1, 1, 0}
   */
  public KsProblem getInstance2() {
    int capacity = 26;
    int[] weights = {12, 7, 11, 8, 9};
    int[] profits = {24, 13, 23, 15, 16};
    return createProblem(capacity, weights, profits);
  }

  /**
   * This problem corresponds to the first problem listed on the webpage:
   * <a href="https://people.sc.fsu.edu/~jburkardt/datasets/knapsack_01/knapsack_01.html">problem 1</a>
   *
   * @return a knapsack benchmarking problem with optimal selection of
   * {1, 1, 0, 0, 1, 0}
   */
  public KsProblem getInstance3() {
    int capacity = 190;
    int[] weights = {56, 59, 80, 64, 75, 17};
    int[] profits = {50, 50, 64, 46, 50, 5};
    return createProblem(capacity, weights, profits);
  }

  /**
   * This problem corresponds to the first problem listed on the webpage:
   * <a href="https://people.sc.fsu.edu/~jburkardt/datasets/knapsack_01/knapsack_01.html">problem 1</a>
   *
   * @return a knapsack benchmarking problem with optimal selection of
   * {1, 0, 0, 1, 0, 0, 0}
   */
  public KsProblem getInstance4() {
    int capacity = 50;
    int[] weights = {31, 10, 20, 19, 4, 3, 6};
    int[] profits = {70, 20, 39, 37, 7, 5, 10};
    return createProblem(capacity, weights, profits);
  }

  /**
   * This problem corresponds to the first problem listed on the webpage:
   * <a href="https://people.sc.fsu.edu/~jburkardt/datasets/knapsack_01/knapsack_01.html">problem 1</a>
   *
   * @return a knapsack benchmarking problem with optimal selection of
   * {1, 0, 1, 1, 1, 0, 1, 1}
   */
  public KsProblem getInstance5() {
    int capacity = 104;
    int[] weights = {25, 35, 45, 5, 25, 3, 2, 2};
    int[] profits = {350, 400, 450, 20, 70, 8, 5, 5};
    return createProblem(capacity, weights, profits);
  }

  /**
   * This problem corresponds to the first problem listed on the webpage:
   * <a href="https://people.sc.fsu.edu/~jburkardt/datasets/knapsack_01/knapsack_01.html">problem 1</a>
   *
   * @return a knapsack benchmarking problem with optimal selection of
   * {0, 1, 0, 1, 0, 0, 1}
   */
  public KsProblem getInstance6() {
    int capacity = 170;
    int[] weights = {41, 50, 49, 59, 55, 57, 60};
    int[] profits = {442, 525, 511, 593, 546, 564, 617};
    return createProblem(capacity, weights, profits);
  }

  /**
   * This problem corresponds to the first problem listed on the webpage:
   * <a href="https://people.sc.fsu.edu/~jburkardt/datasets/knapsack_01/knapsack_01.html">problem 1</a>
   *
   * @return a knapsack benchmarking problem with optimal selection of
   * {1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1}
   */
  public KsProblem getInstance7() {
    int capacity = 750;
    int[] weights = {70, 73, 77, 80, 82, 87, 90, 94, 98, 106, 110, 113, 115, 118, 120};
    int[] profits = {135, 139, 149, 150, 156, 163, 173, 184, 192, 201, 210, 214, 221, 229, 240};
    return createProblem(capacity, weights, profits);
  }

  /**
   * This problem corresponds to the first problem listed on the webpage:
   * <a href="https://people.sc.fsu.edu/~jburkardt/datasets/knapsack_01/knapsack_01.html">problem 1</a>
   *
   * @return a knapsack benchmarking problem with optimal selection of
   * {1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1}
   */
  public KsProblem getInstance8() {
    int capacity = 6404180;
    int[] weights = {382745, 799601, 909247, 729069, 467902, 44328, 34610, 698150, 823460, 903959, 853665, 551830,
      610856, 670702, 488960, 951111, 323046, 446298, 931161, 31385, 496951, 264724, 224916, 169684};
    int[] profits = {825594, 1677009, 1676628, 1523970, 943972, 97426, 69666, 1296457, 1679693, 1902996, 1844992,
      1049289, 1252836, 1319836, 953277, 2067538, 675367, 853655, 1826027, 65731, 901489, 577243, 466257, 369261};
    return createProblem(capacity, weights, profits);
  }

  /**
   * This function helps to create a knapsack instance from given inputs
   *
   * @param capacity knapsack capacity
   * @param weights item weights
   * @param profits item profits
   * @return benchmarking problem
   */
  private KsProblem createProblem(int capacity, int[] weights, int[] profits) {
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
