package com.or.heuristic.app.knapsack.problem;

import com.or.heuristic.app.util.Problem;
import com.or.heuristic.core.util.ProblemEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines a knapsack problem instance, which contains of a list of items that could potentially be put into
 * the knapsack with limited capacity.
 *
 * @author Kunlei Lian
 */
@Getter
@Setter
public class KsProblem extends Problem {
  /**
   * a list of candidate items to be put into the knapsack
   */
  private final List<KsItem> items;
  /**
   * capacity of the knapsack
   */
  private int capacity;

  public KsProblem() {
    super(ProblemEnum.KNAPSACK_2D.getName());
    this.items = new ArrayList<>();
    this.capacity = 0;
  }
}
