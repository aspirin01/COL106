import java.util.ArrayList;

public class PointQuadtree {

    enum Quad {
        NW, NE, SW, SE
    }

    public PointQuadtreeNode root;

    // initializing quadrants array

    public PointQuadtree() {
        this.root = null;
    }

    public boolean insert(CellTower a) {
        // TO be completed by students
        boolean repeat = false;
        System.out.println("line 20 "+repeat);

        if(a==null)return repeat;
        PointQuadtreeNode temp = new PointQuadtreeNode(a);
        this.root = insertHelper(temp, this.root, repeat);
        // System.out.println(this.root);
        if (this.root != null) {
            System.out.println(0 + "      " + this.root.celltower.x + "," + this.root.celltower.y + ","+ this.root.celltower.cost + "");
        }
        visualizer(this.root, 1);
        System.out.println("line 30 "+repeat);
        return repeat;
    }

    


    public boolean cellTowerAt(CellTower a) {
        // TO be completed by students
        boolean repeat = false;
        PointQuadtreeNode temp = this.root;
        if(a==null)return repeat;
        repeat = cellTowerAtHelper(a, temp, repeat);
        return repeat;
    }

    public CellTower chooseCellTower(int x, int y, int r) {
        // TO be completed by students
        // this arraylist stores all the elements inside the circle of radius r
        // coordinates of surrounding rectangle are: (x-r,y),(x,y+r),(x+r,y),(X,y-r) and
        // then another condition wrt the L2 norm distance is imposed before adding the
        // point
        ArrayList<PointQuadtreeNode> a1 = new ArrayList<PointQuadtreeNode>();
        // PointQuadtreeNode temp = this.root;
        // a1 = chooseCellTowerHelper(x, y, r, temp);
        // PointQuadtreeNode minm = a1.get(0);
        // for (PointQuadtreeNode a : a1) {
        //     if (a.celltower.cost < minm.celltower.cost)
        //         minm = a;
        // }
        return null;
    }

    public PointQuadtreeNode insertHelper(PointQuadtreeNode a, PointQuadtreeNode head, boolean repeat) {
        if (head == null)
            return a;

        System.out.println("line53 "+head);
        // handling the case of repitition
        if (a.celltower.x == head.celltower.x && a.celltower.y == head.celltower.y) {
            repeat = true;
            return head;
        }
        if (!repeat) {
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


    public boolean cellTowerAtHelper(CellTower a, PointQuadtreeNode head, boolean repeat) {
        if (head == null)
            return repeat;
        // handling the case of repitition
        if (a.x == head.celltower.x && a.y == head.celltower.y) {
            repeat = true;
            return repeat;
        }
        if (!repeat) {
            // handling the NW
            if (a.x < head.celltower.x && a.y >= head.celltower.y)
                return cellTowerAtHelper(a, head.quadrants[Quad.NW.ordinal()], repeat);
            // handling the NE
            else if (a.x >= head.celltower.x && a.y > head.celltower.y)
                return cellTowerAtHelper(a, head.quadrants[Quad.NE.ordinal()], repeat);
            // handling the SW
            else if (a.x <= head.celltower.x && a.y < head.celltower.y)
                return cellTowerAtHelper(a, head.quadrants[Quad.SW.ordinal()], repeat);
            // handling the SE
            else if (a.x > head.celltower.x && a.y <= head.celltower.y)
                return cellTowerAtHelper(a, head.quadrants[Quad.SE.ordinal()], repeat);
        }
        return repeat;
    }

    public void visualizer(PointQuadtreeNode head, int i) {
        PointQuadtreeNode temp = head;
        for (PointQuadtreeNode c : temp.quadrants) {
            if (c != null)
                System.out.print(
                        i + "       " + c.celltower.x + "," + c.celltower.y + "," + c.celltower.cost + "        ");
        }
        i++;
        System.out.println();
        if (head.quadrants[0] != null)
            visualizer(head.quadrants[0], i);
        if (head.quadrants[1] != null)
            visualizer(head.quadrants[1], i);
        if (head.quadrants[2] != null)
            visualizer(head.quadrants[2], i);
        if (head.quadrants[3] != null)
            visualizer(head.quadrants[3], i);

    }

    public ArrayList<PointQuadtreeNode> chooseCellTowerHelper(int x, int y, int r, PointQuadtreeNode head) {
        ArrayList<PointQuadtreeNode> ans = new ArrayList<PointQuadtreeNode>();
        chooseHelper(x, y, r, head, ans);
        System.out.println(ans);
        return ans;
    }

    public void chooseHelper(int x, int y, int r, PointQuadtreeNode head, ArrayList<PointQuadtreeNode> ans) {
        if (head == null)
            return;
        System.out.println(head.celltower.x + " " + head.celltower.y + " " + head.celltower.cost);
        if (is_inside(head, x, y, r)) {
            ans.add(head);
            return;
        }
        // handling the NW
        // if (x < head.celltower.x && y >= head.celltower.y)
        chooseHelper(x, y, r, head.quadrants[Quad.NW.ordinal()], ans);
        // handling the NE
        // if ((x >= head.celltower.x && y > head.celltower.y)||(x >= head.celltower.x
        // && y > head.celltower.y))
        chooseHelper(x, y, r, head.quadrants[Quad.NE.ordinal()], ans);
        // handling the SW
        // if (x <= head.celltower.x && y < head.celltower.y)
        chooseHelper(x, y, r, head.quadrants[Quad.SW.ordinal()], ans);
        // handling the SE
        if (x > head.celltower.x && y <= head.celltower.y)
            chooseHelper(x, y, r, head.quadrants[Quad.SE.ordinal()], ans);

    }

    public boolean is_inside(PointQuadtreeNode head, int x, int y, int r) {
        System.out.println(head.celltower.distance(x, y));
        if (head.celltower.distance(x, y) <= r)
            return true;
        return false;
    }

    public static void main(String[] args) {

        PointQuadtree obj = new PointQuadtree();
        CellTower c2 = new CellTower(-2, 0, 4);
        CellTower c1 = null;
        CellTower c3 = new CellTower(2, 3, 10);
        CellTower c4 = new CellTower(-4, 6, 9);
        obj.insert(c2);
        obj.insert(c2);
        obj.insert(c1);
        obj.insert(c1);
        obj.insert(c1);
        obj.insert(c1);
        obj.insert(c1);
        obj.insert(c3);
        System.out.println(obj.cellTowerAt(c1));
        obj.insert(c4);

        CellTower c5 = new CellTower(-3, 7, 5);
        CellTower c6 = new CellTower(-3, 3, 4);
        CellTower c7 = new CellTower(-6, 7, 2);
        CellTower c8 = new CellTower(-5, 4, 9);
        obj.insert(c5);
        obj.insert(c6);
        obj.insert(c7);
        obj.insert(c8);
        System.out.println(obj.insert(c5));
        obj.insert(c1);

        obj.insert(c3);
        CellTower ans = obj.chooseCellTower(-2, 6, 2);
        // System.out.println(obj.cellTowerAt(c4));
        // System.out.println(ans.x + " " + ans.y + " " + ans.cost);
        // obj.chooseCellTower(-2, 6, 2); // returns c5

    }

}
