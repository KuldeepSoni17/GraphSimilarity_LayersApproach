package graphiso;


// TODO: FIRST TOP DOWN THEN BOTTOM UP AND INCLUDE TOP DOWN WHILE DOING BOTTOM UP
import java.io.File;
import java.io.PrintWriter;
import java.util.*;
import graphiso.DataStructure.label;
import graphiso.DataStructure.layer;
import graphiso.DataStructure.node;
import graphiso.DataStructure.nodeedge;
import graphiso.DataStructure.edge;

import static graphiso.Helper.*;


/**
 * Created by Kuldeep on 2/15/2018.
 */
public class BatchTestClass {
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
    static writeHelper owriteHelper;
    public static void main(String s[]) throws Exception
    {
        String filename = "result";
        long glbsimi=0;
        long glbdis=0;
        prime_provider.fillprimes();
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
                owriteHelper = new writeHelper();
                owriteHelper.wWriter = writer;
                owriteHelper.sWriter = null;
                owriteHelper.isPrintOn = true;
                //Scanner scanner = new Scanner(System.in);
                //base_node_count = scanner.nextInt();
                base_node_count = loop;
                writer.println(base_node_count);
                base_edge_count = 0;
                simicnt = 0;
                dissimcnt = 0;
                //base_labels = new String[base_node_count];
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
                    //input_labels = new String[input_node_count];
                    input_edge_count = 0;
                    inputgraph = new int[input_node_count][input_node_count];
                    ArrayList permutes = permute_generator(basegraph.length, owriteHelper);
                    int[][] graph = permute_graph(basegraph,permutes, owriteHelper);
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
                    if(similarityChecker(basegraph, inputgraph,base_node_count, input_node_count, base_edge_count,input_edge_count,base_degreecount,input_degreecount,permutes,owriteHelper)){
                        simicnt++;
                    }
                    else
                    {
                        dissimcnt++;
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
