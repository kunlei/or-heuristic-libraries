package com.or.heuristic.app.knapsack.solvers.util;

import com.or.heuristic.app.knapsack.problem.KsItem;
import com.or.heuristic.app.knapsack.problem.KsProblem;
import com.or.heuristic.core.algo.simulatedannealing.SaApplicable;
import com.or.heuristic.core.algo.tabusearch.simple.SimpleTsApplicable;
import lombok.Getter;
import lombok.Setter;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author klian
 */
@Getter
@Setter
public class KsSolution implements SimpleTsApplicable<Integer>, SaApplicable {
  private final KsProblem problem;
  private final List<Boolean> itemSelectionFlags;
  private Integer tabuItemIdx;
  private int totalProfits;

  public KsSolution(KsProblem problem) {
    this.problem = problem;
    int numItems = problem.getItems().size();
    this.itemSelectionFlags = new ArrayList<>(numItems);
    for (int i = 0; i < numItems; i++) {
      this.itemSelectionFlags.add(false);
    }
    this.tabuItemIdx = -1;
    this.totalProfits = 0;
  }

  public KsSolution(KsSolution other) {
    this.problem = other.problem;
    this.itemSelectionFlags = new ArrayList<>(other.itemSelectionFlags);
    this.tabuItemIdx = other.tabuItemIdx;
    this.totalProfits = other.totalProfits;
  }

  @Override
  public List<? extends SimpleTsApplicable<Integer>> getNeighbors(int count) {
    List<KsSolution> neighbors = new ArrayList<>(count);
    SecureRandom random = new SecureRandom();
    List<KsItem> items = problem.getItems();
    int numItems = problem.getItems().size();
    int capacity = problem.getCapacity();
    int usedCapacity = IntStream.range(0, numItems)
      .filter(this.itemSelectionFlags::get)
      .map(i -> items.get(i).getWeight())
      .sum();
    while (neighbors.size() < count) {
      KsSolution neighbor = new KsSolution(this);
      int idxToMutate = random.nextInt(numItems);
      if (this.itemSelectionFlags.get(idxToMutate)) {
        // in this case, the item is already selected
        neighbor.getItemSelectionFlags().set(idxToMutate, false);
        neighbor.setTabuItemIdx(idxToMutate);
        neighbors.add(neighbor);
      } else {
        // in this case, the item is not currently selected
        if (usedCapacity + items.get(idxToMutate).getWeight() <= capacity) {
          neighbor.getItemSelectionFlags().set(idxToMutate, true);
          neighbor.setTabuItemIdx(idxToMutate);
          neighbors.add(neighbor);
        }
      }
    }
    return neighbors;
  }

  @Override
  public Integer getTabuKey() {
    return this.tabuItemIdx;
  }

  @Override
  public SaApplicable getNeighbor() {
    List<KsItem> items = problem.getItems();
    int numItems = problem.getItems().size();
    int capacity = problem.getCapacity();
    int usedCapacity = IntStream.range(0, numItems)
      .filter(this.itemSelectionFlags::get)
      .map(i -> items.get(i).getWeight())
      .sum();
    SecureRandom random = new SecureRandom();
    KsSolution neighbor = new KsSolution(this);
    int idxToMutate = random.nextInt(numItems);
    if (this.itemSelectionFlags.get(idxToMutate)) {
      // in this case, the item is already selected
      neighbor.getItemSelectionFlags().set(idxToMutate, false);
      neighbor.setTabuItemIdx(idxToMutate);
      return neighbor;
    } else {
      // in this case, the item is not currently selected
      if (usedCapacity + items.get(idxToMutate).getWeight() <= capacity) {
        neighbor.getItemSelectionFlags().set(idxToMutate, true);
        neighbor.setTabuItemIdx(idxToMutate);
        return neighbor;
      }
    }
    return null;
  }

  @Override
  public void computeObjective() {
    this.totalProfits = 0;
    for (int i = 0; i < itemSelectionFlags.size(); i++) {
      if (itemSelectionFlags.get(i)) {
        this.totalProfits += problem.getItems().get(i).getProfit();
      }
    }
  }

  @Override
  public double getObjective() {
    return this.totalProfits;
  }
}
