package com.or.heuristic.app.knapsack;

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
public class KsDataCenter {
  private final List<KsItem> items;
  private int capacity;

  public KsDataCenter() {
    this.items = new ArrayList<>();
    this.capacity = 0;
  }
}
