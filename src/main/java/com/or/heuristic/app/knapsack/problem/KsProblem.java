package com.or.heuristic.app.knapsack.problem;

import com.or.heuristic.core.util.Name;
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
public class KsProblem implements Name {
  /**
   * name of the problem
   */
  private String problemName;
  /**
   * a list of candidate items to be put into the knapsack
   */
  private final List<KsItem> items;
  /**
   * capacity of the knapsack
   */
  private int capacity;

  public KsProblem() {
    this.problemName = ProblemEnum.KNAPSACK_2D.getName();
    this.items = new ArrayList<>();
    this.capacity = 0;
  }

  @Override
  public String getName() {
    return this.problemName;
  }

  @Override
  public void setName(String name) {
    this.problemName = name;
  }
}
