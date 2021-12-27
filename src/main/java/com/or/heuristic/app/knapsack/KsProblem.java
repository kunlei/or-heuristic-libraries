package com.or.heuristic.app.knapsack;

import com.or.heuristic.core.util.Name;
import com.or.heuristic.core.util.ProblemEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kunlei Lian
 */
@Getter
@Setter
public class KsProblem implements Name {
  private String problemName;
  private final List<KsItem> items;
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
