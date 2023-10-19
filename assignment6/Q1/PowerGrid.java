import java.util.*;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class Node {
  int city;
  int bridgeCount = 0;
  Node parent;
  int level;
  ArrayList<Node> children = new ArrayList<Node>();

  public Node(int city, int bridgeCount, Node parent, int level) {
    this.city = city;
    this.bridgeCount = bridgeCount;
    this.parent = parent;
    this.level = level;
  }

}

class PowerLine {
  String cityA;
  String cityB;

  public PowerLine(String cityA, String cityB) {
    this.cityA = cityA;
    this.cityB = cityB;
  }
}

// Students can define new classes here

public class PowerGrid {
  int numCities;
  int numLines;
  String[] cityNames;
  PowerLine[] powerLines;

  // Students can define private variables here
  private HashMap<String, Integer> citiesSI = new HashMap<>();
  private HashMap<Integer, String> citiesIS = new HashMap<>();
  private ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();
  private Map<Map.Entry<Integer, Integer>, Integer> isBridge = new HashMap<>();
  ArrayList<PowerLine> ans = new ArrayList<PowerLine>();
  private int timer = 1;
  HashMap<Integer, Node> nodeMap = new HashMap<>();
  ArrayList<ArrayList<Integer>> ancestorTable = new ArrayList<ArrayList<Integer>>();
  ArrayList<Integer> parents = new ArrayList<Integer>();
  Node root;
  int ceil =30;

  public PowerGrid(String filename) throws Exception {
    File file = new File(filename);
    BufferedReader br = new BufferedReader(new FileReader(file));
    numCities = Integer.parseInt(br.readLine());
    numLines = Integer.parseInt(br.readLine());
    cityNames = new String[numCities];
    for (int i = 0; i < numCities; i++) {
      cityNames[i] = br.readLine();
    }
    powerLines = new PowerLine[numLines];
    for (int i = 0; i < numLines; i++) {
      String[] line = br.readLine().split(" ");
      powerLines[i] = new PowerLine(line[0], line[1]);
    }

    // TO be completed by students
    // initializing the adjacency list
    for (int i = 0; i < numCities; i++) {
      adj.add(new ArrayList<Integer>());
    }

    for (int i = 0; i < numCities; i++) {
      citiesSI.put(cityNames[i], i);
      citiesIS.put(i, cityNames[i]);
    }

    // adding the edges to the adjacency list
    for (int i = 0; i < numLines; i++) {
      adj.get(citiesSI.get(powerLines[i].cityA)).add(citiesSI.get(powerLines[i].cityB));
      adj.get(citiesSI.get(powerLines[i].cityB)).add(citiesSI.get(powerLines[i].cityA));
      // put the powerline sorted in the hashmap

      int u = citiesSI.get(powerLines[i].cityA);
      int v = citiesSI.get(powerLines[i].cityB);
      if (u < v) {
        isBridge.put(Map.entry(u, v), 0);
      } else {
        isBridge.put(Map.entry(v, u), 0);
      }
    }

  }

  public ArrayList<PowerLine> criticalLines() {
    /*
     * Implement an efficient algorithm to compute the critical transmission lines
     * in the power grid.
     * 
     * Expected running time: O(m + n), where n is the number of cities and m is the
     * number of transmission lines.
     */

    ArrayList<Integer> in = new ArrayList<Integer>();
    ArrayList<Integer> low = new ArrayList<Integer>();
    ArrayList<Integer> vis = new ArrayList<Integer>();

    for (int i = 0; i < numCities; i++) {
      in.add(0);
      low.add(0);
      vis.add(0);
    }
    dfs(citiesSI.get(cityNames[0]), -1, in, low, vis, ans);

    return ans;
  }

  private void dfs(int node, int parent, ArrayList<Integer> in, ArrayList<Integer> low, ArrayList<Integer> vis,
      ArrayList<PowerLine> ans) {
    vis.set(node, 1);
    in.set(node, timer);
    low.set(node, timer);
    timer = timer + 1;
    for (Integer s : adj.get(node)) {
      if (s.equals(parent))
        continue;
      if (vis.get(s) == 0) {
        dfs(s, node, in, low, vis, ans);
        low.set(node, Math.min(low.get(node), low.get(s)));
        if (low.get(s) > in.get(node)) {
          ans.add(new PowerLine(citiesIS.get(s), citiesIS.get(node)));
        }
      } else {
        low.set(node, Math.min(low.get(node), in.get(s)));
      }
    }
  }

  public void preprocessImportantLines() {
    /*
     * Implement an efficient algorithm to preprocess the power grid and build
     * required data structures which you will use for the numImportantLines()
     * method. This function is called once before calling the numImportantLines()
     * method. You might want to define new classes and data structures for this
     * method.
     * 
     * Expected running time: O(n * logn), where n is the number of cities.
     */
    // preprocessing the critical lines if not called before
    if (ans.size() == 0)
      criticalLines();
    for (PowerLine temp : ans) {
      int u = citiesSI.get(temp.cityA);
      int v = citiesSI.get(temp.cityB);
      if (u < v) {
        isBridge.put(Map.entry(u, v), 1);
      } else {
        isBridge.put(Map.entry(v, u), 1);
      }
    }

    // // all edges not_bridge:0 bridge:1
    // for(Map.Entry<Map.Entry<Integer,Integer>,Integer> temp:isBridge.entrySet()){
    // Map.Entry<Integer, Integer> edge = temp.getKey();
    // System.out.println(edge.getKey()+" "+edge.getValue()+" "+temp.getValue());
    // }
    // making a dfs tree of the graph
    root = new Node(citiesSI.get(cityNames[0]), 0, null, 0);
    ArrayList<Integer> visited = new ArrayList<Integer>();
    for (int i = 0; i < numCities; i++) {
      visited.add(0);
      parents.add(i);
    }

    buildTree(root, null, visited);

    // labelling the nodes of the tree
    nodeMapper();
    // System.out.println(nodeMap);

    // System.out.println();
    // printTree();

    // using binary lifting to preprocess the tree
    // implementing binary lifting in java
    for (int i = 0; i < numCities; i++) {
      ancestorTable.add(new ArrayList<Integer>());
    }
    
    for (int i = 0; i < numCities; i++) {
      for (int j = 0; j < ceil; j++)
      {
        if(i==0){
          ancestorTable.get(i).add(root.city);
        }
        else ancestorTable.get(i).add(0);
      }
    }
    // System.out.println(ancestorTable);


    binaryParentBuilder(ceil);
    // System.out.println(ancestorTable);

    return;
  }

  // this function will map all the nodes to a hashmap
  private void nodeMapper() {
    Queue<Node> q = new LinkedList<Node>();
    Node temp1 = root;
    q.add(temp1);

    while (!q.isEmpty()) {
      Node temp = q.poll();
      nodeMap.put(temp.city, temp);
      for (Node child : temp.children) {
        q.add(child);
      }
    }

  }

  private void binaryParentBuilder(int ceil) {
    // System.out.println(ancestorTable);
    for(int i=1;i<numCities;i++)
      ancestorTable.get(i).set(0,parents.get(i));
    
    for(int i=1;i<numCities;i++){
      for(int j=1;j<ceil;j++){
        int par = ancestorTable.get(i).get(j-1);
        ancestorTable.get(i).set(j, ancestorTable.get(par).get(j-1));
      }
    }
  }

 

  private Node binaryLifter(Node x, int k) {

    for (int i = ceil - 1; i >= 0; i--) {
      if (k == 0 || x.city == -1)
        break;

      if (k >= Math.pow(2, i)) {
        // System.out.println("k " + k);
        k -= (int) Math.pow(2, i);
        // System.out.println("k " + k);
        x = nodeMap.get(ancestorTable.get(x.city).get(i));
        // System.out.println("x: " + citiesIS.get(x.city));
      }
    }
    return x;
  }

  private Node LCA(Node a, Node b) {
    if (a.level > b.level) {
      Node temp = a;
      a = b;
      b = temp;
    }
    int d = b.level - a.level;

    // System.out.println(a.level+" "+b.level+" "+ d);
    b = binaryLifter(b, d);

    if (a.city == b.city)
      return a;
    for (int i = ceil - 1; i >= 0; i--) {
      // System.out.println(citiesIS.get(a.city)+" "+citiesIS.get(b.city));
      if (a.city != b.city) {
        a = nodeMap.get(ancestorTable.get(a.city).get(i));
        b = nodeMap.get(ancestorTable.get(b.city).get(i));
      }
    }

    return binaryLifter(a, 0);
  }

  private Node buildTree(Node curr, Node parent, ArrayList<Integer> visited) {
    Node temp = new Node(curr.city, curr.bridgeCount, parent, curr.level);

    visited.set(curr.city, 1);
    for (Integer child : adj.get(curr.city)) {
      if (visited.get(child) == 0 && curr.city != child) {
        Node childTemp = new Node(child, curr.bridgeCount, curr, curr.level + 1);
        parents.set(childTemp.city, curr.city);

        curr.children.add(childTemp);
        // System.out.println(new
        // PowerLine(citiesIS.get(curr.city),citiesIS.get(child)));
        int u = curr.city;
        int v = child;
        if (u < v) {
          if (isBridge.get(Map.entry(u, v)) == 1) {
            childTemp.bridgeCount = childTemp.bridgeCount + 1;
          }
        } else {
          if (isBridge.get(Map.entry(v, u)) == 1) {
            childTemp.bridgeCount = childTemp.bridgeCount + 1;
          }
        }

        buildTree(childTemp, curr, visited);

      }

    }
    return temp;
  }

  public int numImportantLines(String cityA, String cityB) {
    /*
     * Implement an efficient algorithm to compute the number of important
     * transmission lines between two cities. Calls to numImportantLines will be
     * made only after calling the preprocessImportantLines() method once.
     * 
     * Expected running time: O(logn), where n is the number of cities.
     */
    int u = citiesSI.get(cityA);
    int v = citiesSI.get(cityB);
    Node a = nodeMap.get(u);
    Node b = nodeMap.get(v);
    Node lca = LCA(a, b);
    // System.out.println(citiesIS.get(root.children.get(1).city));
    // System.out.println(queryDfs(u,root));
    // System.out.println(lca.city);
    // System.out.println(citiesIS.get(a.city) + " " + a.bridgeCount);
    // System.out.println(citiesIS.get(b.city) + " " + b.bridgeCount);
    // System.out.println(citiesIS.get(lca.city) + " " + lca.bridgeCount);
    return a.bridgeCount + b.bridgeCount - 2 * lca.bridgeCount;
  }

  // private int queryDfs(int city,Node root){
  // if(city==root.city){
  // return root.bridgeCount;
  // }
  // for(Node s:root.children){
  // return queryDfs(city, s);
  // }
  // return -1;
  // }

  private void printTree() {
    // print all nodes with their children
    Queue<Node> q = new LinkedList<Node>();
    Node temp1 = root;
    q.add(temp1);
    while (!q.isEmpty()) {
      Node temp = q.poll();
      System.out.print(citiesIS.get(temp.city) + " " + temp.bridgeCount + " " + temp.level + " ");
      for (Node s : temp.children) {
        System.out.print(citiesIS.get(s.city) + " ");
        q.add(s);
      }
      System.out.println();
    }

  }

  // public static void main(String[] args) throws Exception {
  //   PowerGrid pg = new PowerGrid("../../input.txt");
  //   System.out.println(pg.criticalLines());
  //   pg.preprocessImportantLines();
  //   System.out.println(pg.numImportantLines("G", "J"));
  //   System.out.println(pg.numImportantLines("D", "E"));
  //   System.out.println(pg.numImportantLines("H", "O"));
  //   System.out.println(pg.numImportantLines("K", "N"));

  //   // criticalLines
  //   // []
  //   // [PowerLine@4338e8d0, PowerLine@4dff0ec7, PowerLine@49c083a0,
  //   // PowerLine@cc204fc, PowerLine@8b813dc, PowerLine@3d84ad3a]
  //   // preprocess
  //   // numImpLines G J
  //   // 2
  //   // numImpLines D E
  //   // 1
  //   // numImpLines H O
  //   // 0
  //   // numImpLines K N
  //   // 4
  // }
}
