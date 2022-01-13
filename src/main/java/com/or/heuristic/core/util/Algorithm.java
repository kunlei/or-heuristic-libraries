package com.or.heuristic.core.util;

import lombok.Getter;
import lombok.Setter;

/**
 * This class defines an abstract base class to be inherited by all algorithms.
 * @author Kunlei Lian
 */
@Getter
@Setter
public abstract class Algorithm implements Name {
  /**
   * name of the algorithm
   */
  private String name;

  public Algorithm(String name) {
    this.name = name;
  }

  /**
   * solving method
   */
  public abstract void solve();
}
