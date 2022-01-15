package com.or.heuristic.core.algo.geneticalgo;

import com.or.heuristic.core.util.Optimizable;

/**
 * @author klian
 */
public interface GaApplicable extends Optimizable {
  GaApplicable crossover(GaApplicable other);
  GaApplicable mutation();
}
