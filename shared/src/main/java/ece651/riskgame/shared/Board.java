package ece651.riskgame.shared;

import java.io.Serializable;
import java.util.*;

public class Board implements Serializable {
  //private static final long serialVersionUID = -5196897839043527760L;
  private HashMap<Territory, List<Territory>> adjacency;
  private Map<String, Territory> territories;
  
  public Board() {
    adjacency = new HashMap<Territory, List<Territory>>();
    territories = new LinkedHashMap<>();
  }

  /**
   * @param neighbors List of territories neighboring the @param territory
   */
  public void putEntry(Territory territory, List<Territory> neighbors) {
    adjacency.put(territory, neighbors);
  }

  public List<Territory> getNeighbors(Territory territory) {
    return adjacency.get(territory);
  }

  public void addTerritory(Territory t) {
    territories.put(t.getName(), t);
    adjacency.put(t, new LinkedList<>());
  }

  public Set<Territory> getTerritoriesSet() {
    return adjacency.keySet();
  }
  
  public List<Territory> getTerritoriesList() {
    return new ArrayList<Territory>(territories.values());
  }

  public Collection<String> getTerritoryNames() {
    return territories.keySet();
  }

  public boolean containsTerritory(String name) {
    return territories.containsKey(name);
  }
  
  /**
   * Get territory by its name
   * @throws IllegalArgumentException if no territory in list has the name
   */
  public Territory getTerritory(String name) {
    if (territories.containsKey(name)) {
      return territories.get(name);
    }
    throw new IllegalArgumentException("Territory name not found.");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Board board = (Board) o;
    // compare list
    List<Territory> myList = this.getTerritoriesList();
    List<Territory> otherList = board.getTerritoriesList();
    if(myList.size() != otherList.size()) {
      return false;
    }
    for (int i = 0; i < myList.size(); i++) {
      if (!myList.get(i).equals(otherList.get(i))) {
        return false;
      }
    }

    // compare adjacency
    Set<Territory> mySet = this.getTerritoriesSet();
    Set<Territory> otherSet = board.getTerritoriesSet();
    if (mySet.size() != otherSet.size()) {
      return false;
    }
    Iterator ot = otherSet.iterator();
    for (Territory myt: mySet) {
      List<Territory> myNs = this.getNeighbors(myt);
      List<Territory> otherNs = board.getNeighbors((Territory)ot.next());
      if (myNs.size() != otherNs.size()) {
        return false;
      }
      for (int i = 0; i < myNs.size(); i++) {
        if (!myNs.get(i).equals(otherNs.get(i))) {
          return false;
        }
      }
    }

    return true;
  }
}
