package com.or.heuristic.core.util;

/**
 * This interface defines functions that let users define and retrieve the name of an algorithm.
 *
 * @author Kunlei Lian
 */
public interface Algorithm {
  /**
   * This function retrieves the name of the algorithm
   *
   * @return algorithm name in string
   */
  String getName();

  /**
   * This function sets the name for an algorithm
   *
   * @param name algorithm name
   */
  void setName(String name);
}
