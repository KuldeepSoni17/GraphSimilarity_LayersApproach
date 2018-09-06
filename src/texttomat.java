import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

public class texttomat {

    public static void convert() throws Exception {
        Scanner scanner = new Scanner(System.in);
        String filename = "graph" + ".txt";
        //String filename2 = "graph_2" + ".txt";
        System.out.println(filename);
        PrintWriter writer = new PrintWriter(filename, "UTF-8");
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
