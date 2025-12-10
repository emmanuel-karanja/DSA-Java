package Graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**Filla given area of an image with a new color given an oldcolor starting from a given pixel.
 */

class Point{
    public int x;
    public int y;

    public Point( int x,int y){
        this.x=x;
        this.y=y;
    }
}
public class FloodFill {

    public static int[][] fill(int[][] image, int oldColor, int newColor, int sx,int sy){
        if(newColor==oldColor) return image;

        if(image==null || image.length==0){
            throw new IllegalArgumentException("Image is empty.");
        }

        final int ROWS=image.length;
        final int COLS=image[0].length;

        final int[][] DIRECTIONS={{1,0},{0,1},{-1,0},{0,-1}};
    
        Queue<Point> queue=new ArrayDeque<>();

        queue.add(new Point(sx,sy)); //start from the src

        while(!queue.isEmpty()){
            Point curPixel=queue.poll();
            int x=curPixel.x;
            int y=curPixel.y;

            //bounds check
            if(x<0 || x>=ROWS || y<0 ||  y>=COLS || image[x][y]!=oldColor){
                continue;
            }

            //change color
            image[x][y]=newColor;

            for(int d[]: DIRECTIONS){
                int nx=x+d[0];
                int ny=y+d[1];

                queue.add(new Point(nx,ny));
            }

           
        }

        return image;
        
    }

    public static void main(String[] args) {

        int[][] image = {
            {1, 1, 1, 2},
            {1, 1, 0, 2},
            {1, 0, 1, 2},
            {2, 2, 2, 2}
        };

        int oldColor = 1;
        int newColor = 9;
        int startX = 0;
        int startY = 0;

        System.out.println("Before fill:");
        printGrid(image);

        int[][] result = FloodFill.fill(image, oldColor, newColor, startX, startY);

        System.out.println("\nAfter fill:");
        printGrid(result);
    }

    private static void printGrid(int[][] grid) {
        for (int[] row : grid) {
            System.out.println(Arrays.toString(row));
        }
    }   
}
