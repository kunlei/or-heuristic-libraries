package com.or.heuristic.app.knapsack.problem;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Kunlei Lian
 */
@Getter
@Setter
@Builder
public final class KsItem {
  private String id;
  private int weight;
  private int profit;
}
