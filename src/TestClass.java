// TODO: FIRST TOP DOWN THEN BOTTOM UP AND INCLUDE TOP DOWN WHILE DOING BOTTOM UP
import java.io.File;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Kuldeep on 2/15/2018.
 */
public class TestClass {
    static int[][] basegraph;
    static PrintWriter writer;
    static int[][] inputgraph;
    static int base_node_count;
    static int input_node_count;
    static int base_edge_count;
    static int input_edge_count;
    static int[] base_degreecount;
    static int[] input_degreecount;
    static String[] base_labels;
    static String[] input_labels;
    static int simicnt;
    static int dissimcnt;
    static int fromNodes = 1;
    static int toNodes = 50;
    static int loopGraph = 1;
    static int permLoop = 1;
    static long modulo = 1000000007;
    public static void main(String s[]) throws Exception
    {
        String filename = "result";
        long glbsimi=0;
        long glbdis=0;
        String time = new Date().getTime() + "";
        File fa = new File("GIT/meta_" + time + " ");
        PrintWriter main_writer = new PrintWriter(fa, "UTF-8");
        for(int loop=fromNodes;loop<=toNodes;loop++) {
            System.out.println(loop);
            File ff = new File("GIT/" + time);
            ff.mkdir();
            File f = new File("GIT/" + time + "/result_" + loop);
            f.mkdir();
            long lclsimi = 0;
            long lcldis = 0;
            for (int loop2 = 0; loop2 < loopGraph; loop2++)
            {
                System.out.println(loop2);
                filename = f + "/new_result_" + loop + "_" + loop2 +".txt";
                System.out.println(filename);
                writer = new PrintWriter(filename, "UTF-8");
                prime_provider.fillprimes();
                //Scanner scanner = new Scanner(System.in);
                //base_node_count = scanner.nextInt();
                base_node_count = loop;
                writer.println(base_node_count);
                base_edge_count = 0;
                simicnt = 0;
                dissimcnt = 0;
                base_labels = new String[base_node_count];
                //GRAPHS AS ADJ MATRICES
                basegraph = new int[base_node_count][base_node_count];
                //DEGREE COUNT i.e. index 5 contains no. of node with degree 5
                base_degreecount = new int[base_node_count];
                GraphCreator graphCreator = new GraphCreator();
                basegraph = graphCreator.createGraph(base_node_count);
                for (int i = 0; i < base_node_count; i++) {
                    int currdegree = 0;
                    for (int j = 0; j < base_node_count; j++) {
                        writer.print(basegraph[i][j] + " ");
                     //           basegraph[i][j] = scanner.nextInt();
                        if (basegraph[i][j] == 1) {
                            base_edge_count++;
                            currdegree++;
                        }
                    }
                    writer.println();
                    base_degreecount[currdegree]++;
                }
                base_edge_count = base_edge_count / 2;
                ////writer.println("DONE WITH BASE");
                //input_node_count = scanner.nextInt();
                int testcnt = permLoop;
                while (testcnt-- > 0) {
                    input_node_count = base_node_count;
                    input_labels = new String[input_node_count];
                    input_edge_count = 0;
                    inputgraph = new int[input_node_count][input_node_count];
                    ArrayList permutes = permute_generator(basegraph.length);
                    int[][] graph = permute_graph(basegraph,permutes);
                    input_degreecount = new int[input_node_count];
                    for (int i = 0; i < input_node_count; i++) {
                        int currdegree = 0;
                        for (int j = 0; j < input_node_count; j++) {
                            inputgraph[i][j] = graph[i][j];
                            //inputgraph[i][j] = scanner.nextInt();
                            if (inputgraph[i][j] == 1) {
                                input_edge_count++;
                                currdegree++;
                            }
                        }
                        input_degreecount[currdegree]++;
                    }
                    input_edge_count = input_edge_count / 2;
                    //complexity - 22cn + 15cn^2 + 3n^5 + 4n^2 + 4n^4 + 9n^3 + n + c
                    if (checkgraph()) {
                        ArrayList<label> base_graph_labels = new ArrayList<>();
                        //complexity - 22cn + 15cn^2 + 3n^5 + 3n^2 + 4n^4 + 9n^3
                        for (int xz = 0; xz < basegraph.length; xz++) {
                            generate_label generate_label = new generate_label();
                            base_graph_labels.add(generate_label.generate(basegraph, xz));
                            base_labels[xz] = printlabel(base_graph_labels.get(base_graph_labels.size() - 1));
                        }
                        ////writer.println("BASE_GRAPH_OVER");
                        ArrayList<label> input_graph_labels = new ArrayList<>();
                        //complexity - n(22c + 15cn + cn^2 + 3n^4 + 3n + 4n^3 + 9n^2) = 22cn + 15cn^2 + 3n^5 + 3n^2 + 4n^4 + 9n^3
                        for (int xz = 0; xz < inputgraph.length; xz++) {
                            //complexity - c
                            generate_label generate_label = new generate_label();
                            //complexity - 20c + 14cn + 3n + 4n^3 + 9n^2
                            input_graph_labels.add(generate_label.generate(inputgraph, xz));
                            //complexity - 3n^4 + cn^2 + cn + c
                            input_labels[xz] = printlabel(input_graph_labels.get(input_graph_labels.size() - 1));
                        }

                        //complexity - n^2 + n + c
                        matchlabels(basegraph,inputgraph,base_labels, input_labels,permutes);
                    } else {
                        writer.println("NOT SIMILAR MAJOR");
                    }
                }
                main_writer.println(filename);
                main_writer.println(lclsimi + " " + lcldis);
                main_writer.println(glbsimi + " " + glbdis);
                lcldis += dissimcnt;
                lclsimi += simicnt;
                glbdis += dissimcnt;
                glbsimi += simicnt;
                writer.println(simicnt + " " + dissimcnt);
                writer.println(lclsimi + " " + lcldis);
                writer.println(glbsimi + " " + glbdis);
                System.out.println(lclsimi + " " + lcldis);
                System.out.println(glbsimi + " " + glbdis);
                writer.close();
            }
        }
        main_writer.close();
    }
}
