package com.or.heuristic.app.util;

import com.or.heuristic.core.util.Name;
import lombok.Getter;
import lombok.Setter;

/**
 * This defines an abstract problem class that should be inherited by all the problems to be solved.
 *
 * @author Kunlei Lian
 */
@Getter
@Setter
public abstract class Problem implements Name {
  /**
   * name of the problem instance
   */
  private String name;

  public Problem(String name) {
    this.name = name;
  }
}
