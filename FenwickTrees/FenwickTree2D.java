package FenwickTrees;

public class FenwickTree2D {
    private int[][] tree;
    private int rows, cols;

    public FenwickTree2D(int n, int m) {
        rows = n;
        cols = m;
        tree = new int[rows + 1][cols + 1]; // 1-based indexing
    }

    // Point update: add delta to cell (r, c)
    public void update(int r, int c, int delta) {
        r++; c++; // 1-based
        for (int i = r; i <= rows; i += i & -i) {
            for (int j = c; j <= cols; j += j & -j) {
                tree[i][j] += delta;
            }
        }
    }

    // Prefix sum from (0,0) to (r,c)
    public int query(int r, int c) {
        r++; c++;
        int sum = 0;
        for (int i = r; i > 0; i -= i & -i) {
            for (int j = c; j > 0; j -= j & -j) {
                sum += tree[i][j];
            }
        }
        return sum;
    }

    // Rectangle sum: top-left (r1,c1), bottom-right (r2,c2)
    public int query(int r1, int c1, int r2, int c2) {
        return query(r2, c2) 
             - query(r1 - 1, c2) 
             - query(r2, c1 - 1) 
             + query(r1 - 1, c1 - 1);
    }

    public static void main(String[] args) {
        // 3x3 grid example
        FenwickTree2D ft = new FenwickTree2D(3, 3);

        // Add some values
        ft.update(0, 0, 5);
        ft.update(0, 1, 3);
        ft.update(1, 1, 2);
        ft.update(2, 2, 7);

        // Query single cell sums
        System.out.println(ft.query(0,0)); // 5
        System.out.println(ft.query(1,1)); // sum from (0,0) to (1,1) = 5+3+2=10

        // Query rectangle sum (top-left to bottom-right)
        System.out.println(ft.query(0,0,2,2)); // sum of all cells = 5+3+2+7=17
        System.out.println(ft.query(1,1,2,2)); // sum of bottom-right 2x2 = 2+7=9
    }
}

