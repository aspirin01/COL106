import java.util.ArrayList;

public class PointQuadtree {

    enum Quad {
        NW, NE, SW, SE
    }

    public PointQuadtreeNode root;

    public PointQuadtree() {
        this.root = null;
    }

    public boolean insert(CellTower a) {
        // TO be completed by students
        boolean repeat = true;
        // System.out.println("line 20 " + repeat);
        if (a == null)
            return false;
        // System.out.println(a.cost+" "+a.x+" "+a.y);
        PointQuadtreeNode temp = new PointQuadtreeNode(a);
        this.root = insertHelper(temp, this.root, repeat);
        // System.out.println(this.root);
        // if (this.root != null) {
        // System.out.println(0 + " " + this.root.celltower.x + "," +
        // this.root.celltower.y + ","+ this.root.celltower.cost + "");
        // }
        // visualizer(this.root, 1);
        // System.out.println("line 30 " + repeat);
        return repeat;
    }

    public boolean cellTowerAt(int x, int y) {
        // TO be completed by students
        boolean repeat = false;
        PointQuadtreeNode temp = this.root;
        repeat = cellTowerAtHelper(x, y, temp, repeat);
        return repeat;
    }

    public CellTower chooseCellTower(int x, int y, int r) {
        // TO be completed by students
        // this arraylist stores all the elements inside the circle of radius r
        // coordinates of surrounding rectangle are: (x-r,y),(x,y+r),(x+r,y),(X,y-r) and
        // then another condition wrt the L2 norm distance is imposed before adding the
        // point
        ArrayList<PointQuadtreeNode> a1 = new ArrayList<PointQuadtreeNode>();
        PointQuadtreeNode temp = this.root;
        a1 = chooseCellTowerHelper(x, y, r, temp);
        PointQuadtreeNode minm = a1.get(0);
        for (PointQuadtreeNode a : a1) {
            // System.out.println(a.celltower.cost + " " + a.celltower.x + " " +
            // a.celltower.y);
            if (a.celltower.cost < minm.celltower.cost)
                minm = a;
        }
        System.out.println(minm.celltower.x + "," + minm.celltower.y + " ==>  " + minm.celltower.cost);
        // printBFS(temp);

        return minm.celltower;
    }

    // helper functions

    public PointQuadtreeNode insertHelper(PointQuadtreeNode a, PointQuadtreeNode head, boolean repeat) {
        if (head == null)
            return a;

        // System.out.println("line53 "+head);
        // handling the case of repitition
        if (a.celltower.x == head.celltower.x && a.celltower.y == head.celltower.y) {
            repeat = false;
            return head;
        }
        if (repeat == true) {
            // handling the NW
            if (a.celltower.x < head.celltower.x && a.celltower.y >= head.celltower.y)
                head.quadrants[Quad.NW.ordinal()] = insertHelper(a, head.quadrants[Quad.NW.ordinal()], repeat);
            // handling the NE
            else if (a.celltower.x >= head.celltower.x && a.celltower.y > head.celltower.y)
                head.quadrants[Quad.NE.ordinal()] = insertHelper(a, head.quadrants[Quad.NE.ordinal()], repeat);
            // handling the SW
            else if (a.celltower.x <= head.celltower.x && a.celltower.y < head.celltower.y)
                head.quadrants[Quad.SW.ordinal()] = insertHelper(a, head.quadrants[Quad.SW.ordinal()], repeat);
            // handling the SE
            else if (a.celltower.x > head.celltower.x && a.celltower.y <= head.celltower.y)
                head.quadrants[Quad.SE.ordinal()] = insertHelper(a, head.quadrants[Quad.SE.ordinal()], repeat);
        }
        return head;
    }

    public boolean cellTowerAtHelper(int x, int y, PointQuadtreeNode head, boolean exists) {
        if (head == null)
            return exists;
        // handling the case of repitition
        if (x == head.celltower.x && y == head.celltower.y) {
            exists = true;
            return exists;
        }
        if (!exists) {
            // handling the NW
            if (x < head.celltower.x && y >= head.celltower.y)
                return cellTowerAtHelper(x, y, head.quadrants[Quad.NW.ordinal()], exists);
            // handling the NE
            else if (x >= head.celltower.x && y > head.celltower.y)
                return cellTowerAtHelper(x, y, head.quadrants[Quad.NE.ordinal()], exists);
            // handling the SW
            else if (x <= head.celltower.x && y < head.celltower.y)
                return cellTowerAtHelper(x, y, head.quadrants[Quad.SW.ordinal()], exists);
            // handling the SE
            else if (x > head.celltower.x && y <= head.celltower.y)
                return cellTowerAtHelper(x, y, head.quadrants[Quad.SE.ordinal()], exists);
        }
        return exists;
    }

    // 1.c
    public ArrayList<PointQuadtreeNode> chooseCellTowerHelper(int x, int y, int r, PointQuadtreeNode head) {
        ArrayList<PointQuadtreeNode> ans = new ArrayList<PointQuadtreeNode>();
        chooseHelper(x, y, r, head, ans);
        // System.out.println(ans.size());
        return ans;
    }

    public void chooseHelper(int x, int y, int r, PointQuadtreeNode head, ArrayList<PointQuadtreeNode> ans) {
        if (head == null) {
            // System.out.println("null");
            return;
        }
        // System.out.println(head.celltower.x+" "+head.celltower.y);
        // System.out.println(head.celltower.x+" "+head.celltower.y+"
        // "+head.celltower.cost);
        // System.out.println(head.celltower.x+" "+head.celltower.y+" : "+x+" "+y+ " "+
        // head.celltower.distance(x, y)+" " + is_inside(head, x, y, r));
        if (is_inside(head, x, y, r)) {
            ans.add(head);
            // return;
        }

        // CASE 4: when circle is inside 4 quadrants
        if (head.celltower.distance(x, y) <= r) {
            // NW, NE, SE, SW
            chooseHelper(x, y, r, head.quadrants[Quad.NE.ordinal()], ans);
            chooseHelper(x, y, r, head.quadrants[Quad.NW.ordinal()], ans);
            chooseHelper(x, y, r, head.quadrants[Quad.SE.ordinal()], ans);
            chooseHelper(x, y, r, head.quadrants[Quad.SW.ordinal()], ans);
        }


        // case 1 when circle in inside only one quadrant

        else if (x + r < head.celltower.x && y - r >= head.celltower.y) {
            // System.out.println("NW");
            chooseHelper(x, y, r, head.quadrants[Quad.NW.ordinal()], ans);
        } else if (x - r >= head.celltower.x && y - r > head.celltower.y) {
            // System.out.println("NE");
            chooseHelper(x, y, r, head.quadrants[Quad.NE.ordinal()], ans);
        } else if (x + r <= head.celltower.x && y + r < head.celltower.y) {
            // System.out.println("SW");
            chooseHelper(x, y, r, head.quadrants[Quad.SW.ordinal()], ans);
        } else if (x - r > head.celltower.x && y + r <= head.celltower.y) {
            // System.out.println("SE");
            chooseHelper(x, y, r, head.quadrants[Quad.SE.ordinal()], ans);
        }

        // case 2: when circle is inside 2 quadrants
        else if (x + r > head.celltower.x && y - r > head.celltower.y && x - r < head.celltower.x) {
            // System.out.println("NE");
            chooseHelper(x, y, r, head.quadrants[Quad.NE.ordinal()], ans);
            // System.out.println("SE");
            if (y - r == head.celltower.y && x > head.celltower.x) {
                chooseHelper(x, y, r, head.quadrants[Quad.SE.ordinal()], ans);
            }
            // System.out.println("NW");
            chooseHelper(x, y, r, head.quadrants[Quad.NW.ordinal()], ans);
        } else if (x + r > head.celltower.x && y + r > head.celltower.y && x - r < head.celltower.x) {
            // System.out.println("SE and SW");
            chooseHelper(x, y, r, head.quadrants[Quad.SW.ordinal()], ans);
            // System.out.println("NW");
            if (y + r == head.celltower.y && x < head.celltower.x) {
                chooseHelper(x, y, r, head.quadrants[Quad.NW.ordinal()], ans);
            }
            // System.out.println("SE");
            chooseHelper(x, y, r, head.quadrants[Quad.SE.ordinal()], ans);
        } else if (x - r > head.celltower.x && y + r > head.celltower.y && y - r < head.celltower.y) {
            // System.out.println("NE and SE");
            chooseHelper(x, y, r, head.quadrants[Quad.NE.ordinal()], ans);
            chooseHelper(x, y, r, head.quadrants[Quad.SE.ordinal()], ans);
            // System.out.println("NE");
            if (x + r == head.celltower.x && y > head.celltower.y) {
                chooseHelper(x, y, r, head.quadrants[Quad.NE.ordinal()], ans);
            }
        } else if (x + r < head.celltower.x && y + r > head.celltower.y && y - r < head.celltower.y) {
            // System.out.println("NW and SW");
            chooseHelper(x, y, r, head.quadrants[Quad.NW.ordinal()], ans);
            chooseHelper(x, y, r, head.quadrants[Quad.SW.ordinal()], ans);
            // System.out.println("SW");
            if (x - r == head.celltower.x && y < head.celltower.y) {
                chooseHelper(x, y, r, head.quadrants[Quad.SW.ordinal()], ans);
            }
        }
            // case 3: handling the case of 3 quadrants
            else if (x < head.celltower.x && y > head.celltower.y) {
                // System.out.println("NE and NW and SW");
                chooseHelper(x, y, r, head.quadrants[Quad.NE.ordinal()], ans);
                chooseHelper(x, y, r, head.quadrants[Quad.NW.ordinal()], ans);
                chooseHelper(x, y, r, head.quadrants[Quad.SW.ordinal()], ans);
            } else if (x > head.celltower.x && y > head.celltower.y) {
                // system.out.println("NE and NW and SE");
                chooseHelper(x, y, r, head.quadrants[Quad.NE.ordinal()], ans);
                chooseHelper(x, y, r, head.quadrants[Quad.NW.ordinal()], ans);
                chooseHelper(x, y, r, head.quadrants[Quad.SE.ordinal()], ans);
            } else if (x < head.celltower.x && y < head.celltower.y) {
                // system.out.println("NW and SW and SE");
                chooseHelper(x, y, r, head.quadrants[Quad.NW.ordinal()], ans);
                chooseHelper(x, y, r, head.quadrants[Quad.SW.ordinal()], ans);
                chooseHelper(x, y, r, head.quadrants[Quad.SE.ordinal()], ans);
            } else if (x > head.celltower.x && y < head.celltower.y) {
                // system.out.println("NE and NW and SE");
                chooseHelper(x, y, r, head.quadrants[Quad.NE.ordinal()], ans);
                chooseHelper(x, y, r, head.quadrants[Quad.SW.ordinal()], ans);
                chooseHelper(x, y, r, head.quadrants[Quad.SE.ordinal()], ans);
            }

    }

    public boolean is_inside(PointQuadtreeNode head, int x, int y, int r) {
        // System.out.println(head.celltower.distance(x, y));
        if (head.celltower.distance(x, y) <= r)
            return true;
        return false;
    }

}
