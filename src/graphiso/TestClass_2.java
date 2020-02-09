package graphiso;

// TODO: FIRST TOP DOWN THEN BOTTOM UP AND INCLUDE TOP DOWN WHILE DOING BOTTOM UP
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import graphiso.DataStructure.label;
import graphiso.DataStructure.layer;
import graphiso.DataStructure.node;
import graphiso.DataStructure.nodeedge;
import graphiso.DataStructure.edge;

import static graphiso.Helper.*;


/**
 * Created by Kuldeep on 2/15/2018.
 */

public class TestClass_2 {
    static int[][] basegraph;
    static PrintStream writer;
    static int[][] inputgraph;
    static int base_node_count;
    static int input_node_count;
    static int base_edge_count;
    static int input_edge_count;
    static int[] base_degreecount;
    static int[] input_degreecount;
    static writeHelper owriteHelper;

    static int simicnt;
    static int dissimcnt;
    static long modulo = 1000000007;
    public static void main(String s[]) throws Exception
    {
                writer = System.out;
                prime_provider.fillprimes();

                Scanner scanner = new Scanner(System.in);
                base_node_count = scanner.nextInt();
                base_edge_count = 0;
                simicnt = 0;
                dissimcnt = 0;
                //GRAPHS AS ADJ MATRICES
                basegraph = new int[base_node_count][base_node_count];
                //DEGREE COUNT i.e. index 5 contains no. of node with degree 5
                base_degreecount = new int[base_node_count];
                for (int i = 0; i < base_node_count; i++) {
                    int currdegree = 0;
                    for (int j = 0; j < base_node_count; j++) {
                        basegraph[i][j] = scanner.nextInt();
                        if (basegraph[i][j] == 1) {
                            base_edge_count++;
                            currdegree++;
                        }
                    }
                    base_degreecount[currdegree]++;
                }
                base_edge_count = base_edge_count / 2;
                writer.println("DONE WITH BASE");
                owriteHelper = new writeHelper();
                owriteHelper.sWriter = writer;
                owriteHelper.wWriter = null;
                owriteHelper.isPrintOn = true;
                //input_node_count = scanner.nextInt();
                int testcnt = 1;
                while (testcnt-- > 0) {
                    input_node_count = base_node_count;
                    input_edge_count = 0;
                    inputgraph = new int[input_node_count][input_node_count];
                    ArrayList permutes = permute_generator(basegraph.length, owriteHelper);
                    int[][] graph = permute_graph(basegraph,permutes,owriteHelper);
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
                writer.println(simicnt + " " + dissimcnt);
    }


}
