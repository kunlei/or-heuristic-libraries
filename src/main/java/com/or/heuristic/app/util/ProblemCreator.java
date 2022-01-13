package com.or.heuristic.app.util;

/**
 * This interface defines method that aims to read a problem instance from a file.
 *
 * @author Kunlei Lian
 */
public interface ProblemCreator {
  /**
   * This function reads from file a problem instance
   *
   * @param filename name of the file to read instance from
   * @return problem
   */
  default Problem readProblem(String filename) {
    return null;
  }

  /**
   * This function creates a problem instance.
   *
   * @return problem
   */
  default Problem createProblem() {
    return null;
  }
}
