import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;

public class GraphCreator {
    int nodenum;

    public static void main(String[] args) {

    }

    public int[][] createGraph(int nodenum)
    {
        try {

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Random random = new Random();
        int[][] graph = new int[nodenum][nodenum];
        for(int i=0;i<nodenum;i++)
        {
            for(int j=0;j<nodenum;j++)
            {
                if(i==j) {
                    graph[i][j] = 0;
                    //graph[i][j] =  random.nextInt(100)%2==0 ? 1  : 0;
                }
                else if(i>j)
                {
                    graph[i][j] = graph[j][i];
                    //graph[i][j] =  random.nextInt(100)%2==0 ? 1  : 0;
                }
                else
                {
                    graph[i][j] =  random.nextInt(100)%2==0 ? 1  : 0;
                    //System.out.println(graph[i][j]);
                }
            }
        }
//        for(int i=0;i<nodenum;i++)
//        {
//            for(int j=0;j<nodenum;j++)
//            {
//         System.out.print(graph[i][j] + " ");
//            }
//            System.out.println();
//        }
        return graph;
    }
}
