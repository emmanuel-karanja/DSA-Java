package Graph;

/**
 * PROBLEM STATEMENT: THE ELECTRIC COURIER (GRID-BASED RESOURCE ROUTING)
 * * You are tasked with finding the most efficient delivery route in a city grid of size N x M.
 * - Start: (0, 0) | Destination: (N-1, M-1).
 * - Terrain: Grid cells contain weights representing travel time (e.g., 1 for Pavement, 3 for Mud).
 * - Obstacles: Walls are represented by -1 and are impassable.
 * - Battery Constraint: You start with 'B' units of battery. Each move costs 1 unit. 
 * If battery hits 0, you cannot move unless the current cell is a Charging Station.
 * - Charging Stations: Entering a station cell instantly restores battery to 'B'.
 * - Goal: Return the minimum TIME to reach the destination, or -1 if unreachable.
 * * -------------------------------------------------------------------------
 * ARCHITECTURAL REASONING & SELECTION CRITERIA:
 * * 1. DOMAIN & TOPOLOGY (The Map): 
 * The problem is a 2D Grid, but the "State" is 3D. A node is uniquely identified 
 * by (x, y, current_battery). We cannot use standard 2D Dijkstra because reaching 
 * a cell with more battery is objectively different/better than reaching it with less.
 * * 2. CONSTRAINTS & VARIABLES (The Rules):
 * - Gate: Battery capacity (B).
 * - Objective: Minimize Time. 
 * - Monotonicity: None. Taking a longer path to hit a charger may be necessary.
 * * 3. OPTIMIZATION SELECTION: 
 * - Why Dijkstra? The weights are non-negative but non-uniform (1 vs 3). 
 * BFS only works for unit weights.
 * - State Space: N * M * (B+1). 
 * - Complexity: O(E log V) where V = N*M*B and E ≈ 4V.
 * * 4. NORMALIZATION:
 * Recharge events are "State Transitions" triggered by cell coordinates.
 * -------------------------------------------------------------------------
 */

import java.util.*;

public class ElectricCourier {
    // Standard 4-directional movement
    private static final int[][] DIRS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    // Represents a unique "State" in our expanded 3D graph
    static class Node {
        int x, y, battery, time;

        Node(int x, int y, int battery, int time) {
            this.x = x;
            this.y = y;
            this.battery = battery;
            this.time = time;
        }
    }

    public int findMinTime(int[][] grid, int B, Set<String> chargers) {
        int N = grid.length;
        int M = grid[0].length;

        // DP table for State Expansion: minTime[row][col][battery_remaining]
        // Why B+1? Battery is 1-indexed i.e. an ordinal value, we must normalize this to the same co-ordinate system 
        // as the others. i.b. B is an ordinate and if we had 5 we'd need 0,1,2,3,4,5 to be able to use up all at 5
        // Ask is the value a boundary or a co-ordinate. If we mix boundaries and co-ordinates, boundaries are normalized
        // to BoundaryMax+1
        // Where is time? it;s the valu at [x,y,b]
        int[][][] minTime = new int[N][M][B + 1];
    
        for (int[][] matrix : minTime) {
            for (int[] row : matrix) {
                Arrays.fill(row, Integer.MAX_VALUE);
            }
        }

        // Dijkstra PriorityQueue: Always process the state with the lowest accumulated time
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.time));

        // Initial State
        minTime[0][0][B] = 0;
        pq.offer(new Node(0, 0, B, 0));

        while (!pq.isEmpty()) {
            Node curr = pq.poll();

            // Optimization: If we've already found a better way to this specific state, discard
            if (curr.time > minTime[curr.x][curr.y][curr.battery]) continue;

            // Target Check
            if (curr.x == N - 1 && curr.y == M - 1) return curr.time;

            for (int[] d : DIRS) {
                int nx = curr.x + d[0];
                int ny = curr.y + d[1];

                // 1. Topology & Wall Check
                if (nx >= 0 && nx < N && ny >= 0 && ny < M && grid[nx][ny] != -1) {
                    
                    // 2. Resource Gate Check
                    if (curr.battery > 0) {
                        int batteryAfterMove = curr.battery - 1;
                        
                        // 3. Normalization (Charging Logic)
                        int nextBattery = chargers.contains(nx + "," + ny) ? B : batteryAfterMove;
                        
                        // 4. Weight Calculation
                        int nextTime = curr.time + grid[nx][ny];

                        // 5. Relaxation
                        if (nextTime < minTime[nx][ny][nextBattery]) {
                            minTime[nx][ny][nextBattery] = nextTime;
                            pq.offer(new Node(nx, ny, nextBattery, nextTime));
                        }
                    }
                }
            }
        }

        return -1; // Gate closed: No path satisfies the battery constraint
    }
}