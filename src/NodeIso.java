import com.sun.java.swing.plaf.windows.WindowsInternalFrameTitlePane;

import java.util.Scanner;

public class NodeIso {
    public static boolean checkNodeIso(int[][] graph, int node1, int node2) {
        int[][] origraph = new int[graph.length][graph.length];
        int[][] newgraph = graph.clone();
        for(int i=0;i<graph.length;i++)
        {
            for(int j=0;j<graph.length;j++){
             origraph[i][j] = graph[i][j];
            }
        }
        for(int i=0;i<graph.length;i++)
        {
            newgraph[i][node1] = origraph[i][node2];
            newgraph[i][node2] = origraph[i][node1];
            newgraph[node1][i] = origraph[node2][i];
            newgraph[node2][i] = origraph[node1][i];
        }
        for(int i=0;i<graph.length;i++)
        {
            for(int j=0;j<graph.length;j++){
                System.out.print(origraph[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();System.out.println();
        for(int i=0;i<graph.length;i++)
        {
            for(int j=0;j<graph.length;j++){
                System.out.print(newgraph[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();System.out.println();
        boolean[] orichecked = new boolean[graph.length];
        for(int i=0;i<graph.length;i++)
        {
            boolean mainmatch = false;
            for(int j=0;j<graph.length;j++){
                if(!orichecked[j])
                {   boolean match = true;
                    for(int k=0;k<graph.length;k++) {
                        if(!(newgraph[i][k]==origraph[j][k]))
                        {
                            match = false;
                            break;
                        }
                    }
                    if(match)
                    {
                        orichecked[j] = true;
                        mainmatch = true;
                        break;
                    }
                }
            }
            if(!mainmatch)
            {
             System.out.println("NOT SIMILAR " + node1 + " " + node1);
             return false;
            }
        }
        System.out.println("SIMILAR " + node1 + " " + node2);
        return true;


    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        int[][] graph = new int[x][x];
        for(int i=0;i<x;i++)
        {
            for(int j=0;j<x;j++){
                graph[i][j] = scanner.nextInt();
            }
        }
        for(int i=0;i<graph.length;i++)
        {
            for(int j=0;j<graph.length;j++){
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        checkNodeIso(graph, 1,2);
    }
}