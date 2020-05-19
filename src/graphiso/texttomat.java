///Converts text to matrix adjacency
///Input
///3 -- number of nodes
///3 -- number of edges
/// 1 1 -- Node number 1
/// 2 2 -- Node number 2
/// 3 3 -- Node number 3 / Node number n

///output (in graphs.txt)
/// 1 0 0
/// 0 1 0
/// 0 0 1

package graphiso;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Scanner;

@SuppressWarnings("ConditionalBreakInInfiniteLoop")
public class texttomat {

    @SuppressWarnings("ConditionalBreakInInfiniteLoop")
    public static void convert() throws Exception {
        Scanner scanner = new Scanner(System.in);
        String filename = "graph" + ".txt";
        //String filename2 = "graph_2" + ".txt";
        System.out.println(filename);
        PrintWriter writer = new PrintWriter(filename, StandardCharsets.UTF_8);
        while(true) {
            int nodes = scanner.nextInt();
            int edges = scanner.nextInt();
            int[][] graph = new int[nodes][nodes];
            for (int i = 0; i < edges; i++) {
                String s = scanner.next();
                int node1 = scanner.nextInt();
                int node2 = scanner.nextInt();
                graph[node1 - 1][node2 - 1] = 1;
                graph[node2 - 1][node1 - 1] = 1;
                System.out.print(i+" ");
            }
            System.out.println(nodes + " ");
            for (int i = 0; i < nodes; i++) {
                System.out.print(i+" ");
                for (int j = 0; j < nodes; j++) {
                    System.out.println(j + " ");
                    writer.print(graph[i][j] + " ");
                }
                writer.println();
            }
            System.out.println();                System.out.println();                System.out.println();                System.out.println();                System.out.println();
            writer.close();
            if(!scanner.next().equals("BREAK"))
            {
            break;
            }
        }
    }

    public static void main(String[] args) throws Exception {
    convert();
    }
}
