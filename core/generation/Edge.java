package core.generation;

public class Edge implements Comparable<Edge> {

    public Point p1;
    public Point p2;
    public double priority_num;

    public Edge(Point p1 ,Point p2) {
        this.p1 = p1;
        this.p2 = p2;

        int dx = p1.getX() - p2.getX();
        int dy = p1.getY() - p2.getY();

        this.priority_num = Math.hypot(dx, dy);
    }

    @Override
    public int compareTo(Edge o) {
        return Double.compare(this.priority_num, o.priority_num);
    }

}
