package graphiso;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import static graphiso.Helper.*;
import static graphiso.Helper.arraylistcontains;

public class obsolete_code {

    //MAIN CLASS
    /*MAIN CLASS*/
/*package graphiso;

import java.io.PrintWriter;
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


    *//**
     * Created by Kuldeep on 1/25/2018.
     *//*

    public class mainclass {

        static int[][] basegraph;
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
        public static void main(String s[]) throws Exception
        {   PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
            prime_provider.fillprimes();
            Scanner scanner  = new Scanner(System.in);
            base_node_count = scanner.nextInt();
            base_edge_count = 0;
            simicnt = 0;
            dissimcnt = 0;
            base_labels = new String[base_node_count];
            //GRAPHS AS ADJ MATRICES
            basegraph = new int[base_node_count][base_node_count];
            //DEGREE COUNT i.e. index 5 contains no. of node with degree 5
            base_degreecount = new int[base_node_count];
            for(int i=0;i<base_node_count;i++)
            {
                int currdegree = 0;
                for(int j=0;j<base_node_count;j++)
                {
                    basegraph[i][j] = scanner.nextInt();
                    if(basegraph[i][j]==1)
                    {
                        base_edge_count++;
                        currdegree++;
                    }
                }
                base_degreecount[currdegree]++;
            }
            base_edge_count = base_edge_count/2;
//        System.out.println("DONE WITH BASE");
            //input_node_count = scanner.nextInt();
            int testcnt = 10000;
            while(testcnt-- > 0) {
                System.out.println("TEST NO:- " + (10000-testcnt));
                input_node_count = base_node_count;
                input_labels = new String[input_node_count];
                input_edge_count = 0;
                inputgraph = new int[input_node_count][input_node_count];
                ArrayList permutes = permute_generator(basegraph.length, writer);
                int[][] graph = permute_graph(basegraph, permutes,writer);
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
                if (checkgraph(base_node_count, input_node_count, base_edge_count, input_edge_count, base_degreecount, input_degreecount)) {
                    ArrayList<DataStructure.label> base_graph_labels = new ArrayList<>();
                    //complexity - 22cn + 15cn^2 + 3n^5 + 3n^2 + 4n^4 + 9n^3
                    for (int xz = 0; xz < basegraph.length; xz++) {
                        graphiso.mainclass.generate_label generate_label = new graphiso.mainclass.generate_label();
                        base_graph_labels.add(generate_label.generate(basegraph, xz));
                        base_labels[xz] = printlabel(base_graph_labels.get(base_graph_labels.size() - 1), writer);
                    }
//                 System.out.println("BASE_GRAPH_OVER");
                    ArrayList<DataStructure.label> input_graph_labels = new ArrayList<>();
                    //complexity - n(22c + 15cn + cn^2 + 3n^4 + 3n + 4n^3 + 9n^2) = 22cn + 15cn^2 + 3n^5 + 3n^2 + 4n^4 + 9n^3
                    for (int xz = 0; xz < inputgraph.length; xz++) {
                        //complexity - c
                        graphiso.mainclass.generate_label generate_label = new graphiso.mainclass.generate_label();
                        //complexity - 20c + 14cn + 3n + 4n^3 + 9n^2
                        input_graph_labels.add(generate_label.generate(inputgraph, xz));
                        //complexity - 3n^4 + cn^2 + cn + c
                        input_labels[xz] = printlabel(input_graph_labels.get(input_graph_labels.size() - 1), writer);
                    }

                    //complexity - n^2 + n + c
                    matchlabels(basegraph, inputgraph, base_labels, input_labels, permutes,  simicnt, dissimcnt, writer);
                } else {
                    System.out.println("NOT SIMILAR MAJOR");
                }
            }
            System.out.println(simicnt + " " + dissimcnt);
            writer.close();
        }

//    public static void generate_labels()
//    {
//     ArrayList<label> labels = new ArrayList<>();
//     for(int i=0;i<base_node_count;i++)
//         labels.add(generate_base_label(i));
//    }
//
//    public static label generate_base_label(int n)
//    {
//        label label = new label();
//        int graph_mat[][] = new int[base_node_count][base_node_count+1];
//        graph_mat[0][0] = n;
//        for(int i=0;i<base_node_count;i++)
//        {
//            if(basegraph[n][i]==1)
//            {
//                graph_mat[1][(graph_mat[1][base_node_count+1])++] = i;
//            }
//        }
//        inflatebasemat(graph_mat);
//        return label;
//    }
//
//    //TODO: CREATE A MAINTENANCE QUEUE WHICH WILL MAINTAIN INFLATED NODES
//    //TODO: CREATE A FUNCTION TO ADD ADJACENCY BETWEEN INFLATED NODES
//    //TODO: CREATE A FUNCTION TO CALCULATE NODEVALUE
//    //TODO: CREATE A FUNCTION TO REALIGN THE LABEL AFTER NEW RANKING(BASED ON CALCULATED NODEVALUE)
//    //TODO: CREATE A FUNCTION TO MATCH LABELS FROM BOTH GRAPHS

//    public static int[][] inflatelayer(int graph_mat[][],int DataStructure, int nodenum)
//    {
//        for(int i=0;i<base_node_count;i++)
//        {
//            if(basegraph[nodenum][i]==1)
//            {
//                graph_mat[DataStructure][(graph_mat[DataStructure][base_node_count+1])++] = i;
//            }
//        }
//        return graph_mat;
//    }

//    public static void inflatebasemat(int graphmat[][])
//    {
//    }

//    static class label
//    {
//        public label()
//        {
//        }
//        ArrayList<Integer> level_nodes_;
//        int adj_mat[][] = new int[base_node_count][base_node_count];
//        //No. of layers in tree
//        int layers_count;
//        //LAYER INFO.......1.node count, 2.incoming nodes from upper DataStructure, 3.outgoing nodes to lower DataStructure, 4.internal edges in DataStructure
//        int layer_info[][] = new int[base_node_count][4];
//        //FOR STORING EDGES IN TREE
//        ArrayList<edge> edges = new ArrayList<>();
//        //FOR STORING NODES IN TREE
//        ArrayList<node> nodes = new ArrayList<>();
//        //MAIN MATRIX WHICH WILL ACT AS TREE, DATA WILL BE INDEX OF NODE IN 'nodes'
//        int container[][] = new int[base_node_count][base_node_count];
//
//        public void change_node_pos(int old_row, int old_col, int node_index, int new_row, int new_col)
//        {
//
//        }
//
//        public void addedge(edge e)
//        {
//            edges.add(e);
//        }
//
//        public void addnode(node n)
//        {
//            nodes.add(n);
//        }
//
//        public void add_layer()
//        {
//
//        }
//
//    }

        static class generate_label
        {
            int[][] ori_graph;
            int root_node;
            boolean inflated_nodes[];
            int inflated_node_cnt = 0;
            ArrayList<DataStructure.layer> layers = new ArrayList<>();
            ArrayList<Integer> prevlay_node = new ArrayList<>();
            ArrayList<Integer> currlay_node = new ArrayList<>();

            public generate_label()
            {}

            //complexity - 12c + 2n^3 + 5n^2 + 7cn + 4c + 2n + 7cn + 4n^2 + 2n^3 + 2c + 2c + n = 20c + 14cn + 3n + 4n^3 + 9n^2
            public DataStructure.label generate(int [][] graph, int n) {

                //complexity - 12c
                this.ori_graph = graph;
                root_node = n;
                DataStructure.label label = new DataStructure.label();

                layers.add(new DataStructure.layer());
                DataStructure.layer first_layer = layers.get(0);
                first_layer.layer_no = 0;

                DataStructure.node node = new DataStructure.node(0,0,n);
                node.layer_no = 0;
                first_layer.nodes.add(node);

                prevlay_node.clear();
                currlay_node.add(n);

                inflated_nodes = new boolean[ori_graph.length];
                inflated_nodes[n] = true;
                inflated_node_cnt++;

                inflate_layer(1);
                for(int x=0;x<layers.size();x++)
                {
                    DataStructure.layer layer = layers.get(x);
//                System.out.println();
//                System.out.println(DataStructure.incoming_edges.size() + " " + DataStructure.outgoing_edges.size() + " " + DataStructure.internal_edges.size());
                }
                //            System.out.println("TOP DOWN RANKING");
                //complexity - 7cn + 4n^2 + 2n^3 + 2c
                rank_top_down();
//            System.out.println("BOTTOM UP RANKING");

                //complexity - 2n^3 + 5n^2 + 7cn + 4c + 2n
                rank_bottom_up();
                //complexity - c

                //complexity - n
                for(int ij=0;ij<this.layers.size();ij++)
                {
                    process_layer(this.layers.get(ij));
                }
                //complexity - c
                label.layers = this.layers;
                return label;
            }

            //complexity - 10cn^2 + 2n^5 + 5cn^3 + 2c
            public void inflate_layer(int layer_no)
            {
                //complexity - 2c
                DataStructure.layer layer = new DataStructure.layer();
                layer.layer_no = layer_no;
                DataStructure.node[] nextlay_nodes = new DataStructure.node[base_node_count];
                for(int i=0;i<base_node_count;i++){nextlay_nodes[i]=null;}

                //complexity - n(n(10c + 2n^3 + 5cn)) = 10cn^2 + 2n^5 + 5cn^3
                for(int i=0;i<layers.get(layer_no-1).nodes.size();i++)
                {
                    //complexity - n(10c + 2n^3 + 5cn)
                    for(int j=0;j<base_node_count;j++)
                    {
                        //complexity - 10c + 2n^3 + 5cn
                        if(ori_graph[layers.get(layer_no-1).nodes.get(i).ori_node_num][j]==1)
                        {
                            //check if node not already inflated
//                        System.out.println( "ONE " + layers.get(layer_no-1).nodes.get(i).ori_node_num + " " + j + " ");
                            //if(!inflated_nodes[j]) {
                            if(!arraylistcontains(currlay_node,j) && !arraylistcontains(prevlay_node,j)){
//                            System.out.println("TWO " + layers.get(layer_no-1).nodes.get(i).ori_node_num + " " + j + " ");
                                //complexity - 10c
                                DataStructure.node node;
                                if(nextlay_nodes[j]!=null)
                                {
                                    node = nextlay_nodes[j];
                                }
                                else
                                {
                                    node = new DataStructure.node(layer_no - 1, layer.nodes.size(), j);
                                    node.layer_no = layer.layer_no;
                                    layer.nodes.add(node);
                                    nextlay_nodes[j] = node;
                                    inflated_nodes[j] = true;
                                    inflated_node_cnt++;
                                }
                                DataStructure.edge edge = new DataStructure.edge(layers.get(layer_no-1).nodes.get(i).pos_row, layers.get(layer_no-1).nodes.get(i).pos_col, node.pos_row,node.pos_col,layers.get(layer_no-1).nodes.get(i), node);
                                layer.incoming_edges.add(edge);
                                layers.get(layer_no-1).outgoing_edges.add(edge);
                                layers.get(layer_no-1).nodes.get(i).out_edges.add(new DataStructure.nodeedge(node));
                                node.inc_edges.add(new DataStructure.nodeedge(layers.get(layer_no-1).nodes.get(i)));
                            }
                            else {
                                //complexity - n(2n^2 + 5c) = 2n^3 + 5cn
                                for(int k=0;k<layers.get(layer_no-1).nodes.size();k++) {
                                    //complexity - 2n^c + 5c
                                    if (layers.get(layer_no - 1).nodes.get(k).ori_node_num == j) {
//                                    System.out.println("SAME LAYER");

                                        //complexity - 2c
                                        DataStructure.edge edge = new DataStructure.edge(layers.get(layer_no - 1).nodes.get(k).pos_row, layers.get(layer_no - 1).nodes.get(k).pos_col, layers.get(layer_no - 1).nodes.get(i).pos_row, layers.get(layer_no - 1).nodes.get(i).pos_col, layers.get(layer_no - 1).nodes.get(k),layers.get(layer_no - 1).nodes.get(i));
                                        boolean repeat = false;
                                        //complexity - n^2(2c)
                                        for (int l = 0; l < layers.get(layer_no - 1).internal_edges.size(); l++) {
                                            //complexity - c
                                            if (layers.get(layer_no - 1).internal_edges.get(l).row1 == edge.row1 && layers.get(layer_no - 1).internal_edges.get(l).col1 == edge.col1) {
                                                if (layers.get(layer_no - 1).internal_edges.get(l).row2 == edge.row2 && layers.get(layer_no - 1).internal_edges.get(l).col2 == edge.col2) {
                                                    repeat = true;
                                                    break;
                                                }
                                            }
                                            //complexity - c
                                            if (layers.get(layer_no - 1).internal_edges.get(l).row1 == edge.row2 && layers.get(layer_no - 1).internal_edges.get(l).col1 == edge.col2) {
                                                if (layers.get(layer_no - 1).internal_edges.get(l).row2 == edge.row1 && layers.get(layer_no - 1).internal_edges.get(l).col2 == edge.col1) {
                                                    repeat = true;
                                                    break;
                                                }
                                            }
                                        }
                                        //complexity - 3c
                                        if (!repeat) {
//                                        System.out.println("NOT REPEAT");
                                            layers.get(layer_no - 1).internal_edges.add(edge);
                                            layers.get(layer_no - 1).nodes.get(k).int_edges.add(new DataStructure.nodeedge(layers.get(layer_no - 1).nodes.get(i)));
                                            layers.get(layer_no - 1).nodes.get(i).int_edges.add(new DataStructure.nodeedge(layers.get(layer_no - 1).nodes.get(k)));
                                        }
                                    }
                                }
                            }

                        }
                    }
                }

                prevlay_node = (ArrayList<Integer>) currlay_node.clone();
                currlay_node.clear();
                for(int i=0;i<layer.nodes.size();i++)
                {
                    currlay_node.add(layer.nodes.get(i).ori_node_num);
                }
                if(layer.nodes.size()!=0) {
                    layers.add(layer);
                    inflate_layer(layer_no + 1);
                }
//            System.out.print("COMPLETED INFLATING" + layers.size() + " ");
            }

            //complexity - 2n^3 + 5n^2 + 7cn + 4c + 2n
            public void rank_bottom_up()
            {
                //complexity - n^2 + 4c + 2n
                rank_bottom_up_bottom();
                //complexity - n(2(n^2 + 3c + 2n) + c)  = 2n^3 + 7cn + 4n^2
                for(int a = layers.size()-2;a>=0;a--)
                {   int curr_prime1=0;
                    //complexity - c
                    DataStructure.layer curr_layer = layers.get(a);

                    //complexity - n
                    ArrayList<DataStructure.node> nodes = (ArrayList<DataStructure.node>)curr_layer.nodes.clone();

                    //complexity - n^2
                    nodes.sort(new Comparator<DataStructure.node>() {
                        @Override
                        public int compare(DataStructure.node o1, DataStructure.node o2) {
                            long o1_cnt = 1, o2_cnt=1;
                            for(int aa=0;aa<o1.out_edges.size();aa++)
                            {
                                o1_cnt *= o1.out_edges.get(aa).node1.rank_bottom_up;
                            }
                            o1.curr_cnt = o1_cnt;
                            for(int aa=0;aa<o2.out_edges.size();aa++)
                            {
                                o2_cnt *= o2.out_edges.get(aa).node1.rank_bottom_up;
                            }
                            o2.curr_cnt = o2_cnt;
                            return (int)(o2_cnt-o1_cnt);
                        }
                    });

                    //complexity - 3c
                    long curr_num = nodes.get(0).curr_cnt;
                    nodes.get(0).rank_bottom_up = prime_provider.getprime(curr_prime1);

                    //complexity - n
                    for(int x =1;x<curr_layer.nodes.size();x++)
                    {
                        if(curr_num==nodes.get(x).curr_cnt)
                        {
                            nodes.get(x).rank_bottom_up = prime_provider.getprime(curr_prime1);
                        }
                        else
                        {
                            curr_num = nodes.get(x).curr_cnt;
                            nodes.get(x).rank_bottom_up = prime_provider.getprime(++curr_prime1);
                        }
                    }

                    for(int x =0;x<curr_layer.nodes.size();x++)
                    {
//                    System.out.println(nodes.get(x).rank_bottom_up + " " + nodes.get(x).curr_cnt + " " + a);
                    }

                    //complexity - n
                    for(int zx = 0;zx<nodes.size();zx++)
                    {
                        nodes.get(zx).curr_cnt = 1;
                        for(int zy=0;zy<nodes.get(zx).int_edges.size();zy++)
                        {
                            nodes.get(zx).curr_cnt *= nodes.get(zx).int_edges.get(zy).node1.rank_bottom_up;
                        }
                    }
                    ArrayList<DataStructure.node> nodes_sec = (ArrayList<DataStructure.node>)nodes.clone();
                    //complexity - n^2
                    nodes_sec.sort(new Comparator<DataStructure.node>() {
                        @Override
                        public int compare(DataStructure.node o1, DataStructure.node o2) {
                            return (int)(o2.curr_cnt-o1.curr_cnt);
                        }
                    });

                    //complexity - 3c
                    curr_num = nodes_sec.get(0).curr_cnt;
                    curr_prime1++;
                    nodes_sec.get(0).rank_bottom_up = nodes_sec.get(0).rank_bottom_up*prime_provider.getprime(curr_prime1);

                    //complexity - n
                    for(int x =1;x<curr_layer.nodes.size();x++)
                    {
                        if(curr_num==nodes_sec.get(x).curr_cnt)
                        {
                            nodes_sec.get(x).rank_bottom_up = nodes_sec.get(x).rank_bottom_up*prime_provider.getprime(curr_prime1);
                        }
                        else
                        {
                            curr_num = nodes_sec.get(x).curr_cnt;
                            nodes_sec.get(x).rank_bottom_up = nodes_sec.get(x).rank_bottom_up*prime_provider.getprime(++curr_prime1);
                        }
                    }

                    for(int zx = 0;zx<nodes_sec.size();zx++)
                    {
                        nodes_sec.get(zx).curr_cnt = 1;
                        for(int zy=0;zy<nodes_sec.get(zx).inc_edges.size();zy++)
                        {
                            nodes_sec.get(zx).curr_cnt *= nodes_sec.get(zx).inc_edges.get(zy).node1.rank_top_down;
                        }
                    }

                    nodes_sec.sort(new Comparator<DataStructure.node>() {
                        @Override
                        public int compare(DataStructure.node o1, DataStructure.node o2) {
                            return (int)(o2.curr_cnt-o1.curr_cnt);
                        }
                    });

                    curr_num = nodes_sec.get(0).curr_cnt;
                    curr_prime1++;
                    nodes_sec.get(0).rank_bottom_up = nodes_sec.get(0).rank_bottom_up* prime_provider.getprime(curr_prime1);


                    //complexity - n
                    for(int x =1;x<curr_layer.nodes.size();x++)
                    {
                        if(curr_num==nodes_sec.get(x).curr_cnt)
                        {
                            nodes_sec.get(x).rank_bottom_up = nodes_sec.get(x).rank_bottom_up*prime_provider.getprime(curr_prime1);
                        }
                        else
                        {
                            curr_num = nodes_sec.get(x).curr_cnt;
                            nodes_sec.get(x).rank_bottom_up = nodes_sec.get(x).rank_bottom_up*prime_provider.getprime(++curr_prime1);
                        }
                    }
                    for(int x =0;x<curr_layer.nodes.size();x++)
                    {
//                    System.out.println(nodes_sec.get(x).rank_bottom_up + " " + nodes_sec.get(x).curr_cnt + " " + a);
                    }
                }
            }

            //complexity - n + c + n^2 + 3c + n = n^2 + 4c + 2n
            public void rank_bottom_up_bottom()
            {
                //complexity - c
                DataStructure.layer bottom_layer = layers.get(layers.size()-1);
                //complexity - n
                ArrayList<DataStructure.node> nodes = (ArrayList<DataStructure.node>)bottom_layer.nodes.clone();

                //complexity - n^2
                nodes.sort(new Comparator<DataStructure.node>() {
                    @Override
                    public int compare(DataStructure.node o1, DataStructure.node o2) {
                        return o2.int_edges.size()-o1.int_edges.size();
                    }
                });

                //complexity - 3c
                int curr_num = nodes.get(0).int_edges.size();
                int curr_prime_index = 0;
                nodes.get(0).rank_bottom_up = prime_provider.getprime(curr_prime_index);

                //complexity - n
                for(int x =1;x<bottom_layer.nodes.size();x++)
                {
                    if(curr_num==nodes.get(x).int_edges.size())
                    {
                        nodes.get(x).rank_bottom_up = prime_provider.getprime(curr_prime_index);
                    }
                    else
                    {
                        curr_num = nodes.get(x).int_edges.size();
                        nodes.get(x).rank_bottom_up = prime_provider.getprime(++curr_prime_index);
                    }
                }

                for(int x =0;x<bottom_layer.nodes.size();x++)
                {   nodes.get(x).curr_cnt = 1;
                    for(int y=0;y<nodes.get(x).inc_edges.size();y++)
                    {
                        nodes.get(x).curr_cnt *= nodes.get(x).inc_edges.get(y).node1.rank_top_down;
                    }
                }

                nodes.sort(new Comparator<DataStructure.node>() {
                    @Override
                    public int compare(DataStructure.node o1, DataStructure.node o2) {
                        return (int)(o2.curr_cnt-o1.curr_cnt);
                    }
                });

                curr_num = (int)nodes.get(0).curr_cnt;
                nodes.get(0).rank_bottom_up *=prime_provider.getprime(++curr_prime_index);

                for(int x =1;x<bottom_layer.nodes.size();x++)
                {
                    if(curr_num==nodes.get(x).curr_cnt)
                    {
                        nodes.get(x).rank_bottom_up *= prime_provider.getprime(curr_prime_index);
                    }
                    else
                    {
                        curr_num = (int)nodes.get(x).curr_cnt;
                        nodes.get(x).rank_bottom_up *= prime_provider.getprime(++curr_prime_index);
                    }
                }

                for(int x =0;x<bottom_layer.nodes.size();x++)
                {
//                System.out.println(nodes.get(x).rank_bottom_up + " " + nodes.get(x).curr_cnt + " " + (layers.size()-1));
                }
            }

            //complexity - n(c + n + n^2 + 3c + n + n + n^2 + 3c + n) + 2c = 7cn + 4n^2 + 2n^3 + 2c
            public void rank_top_down()
            {//complexity - 2c
                rank_top_down_top();
                //complexity - n(c + n + n^2 + 3c + n + n + n^2 + 3c + n)
                for(int a = 1;a<layers.size();a++)
                {int curr_prime = 0;
                    //complexity - c
                    DataStructure.layer curr_layer = layers.get(a);
                    //complexity - n
                    ArrayList<DataStructure.node> nodes = (ArrayList<DataStructure.node>)curr_layer.nodes.clone();

                    for(int ab=0;ab<nodes.size();ab++)
                    {   nodes.get(ab).curr_cnt = 1;
                        for(int ac=0;ac<nodes.get(ab).inc_edges.size();ac++)
                        {
                            nodes.get(ab).curr_cnt *= nodes.get(ab).inc_edges.get(ac).node1.rank_top_down;
                        }
                    }

                    //complexity - n^2
                    nodes.sort(new Comparator<DataStructure.node>() {
                        @Override
                        public int compare(DataStructure.node o1, DataStructure.node o2) {
                            return (int)(o2.curr_cnt-o1.curr_cnt);
                        }
                    });

                    //complexity - 3c
                    long curr_num = nodes.get(0).curr_cnt;
                    nodes.get(0).rank_top_down = prime_provider.getprime(curr_prime);

                    //complexity - n
                    for(int x =1;x<curr_layer.nodes.size();x++)
                    {
                        if(curr_num==nodes.get(x).curr_cnt)
                        {
                            nodes.get(x).rank_top_down = prime_provider.getprime(curr_prime);
                        }
                        else
                        {
                            curr_num = nodes.get(x).curr_cnt;
                            curr_prime++;
                            nodes.get(x).rank_top_down = prime_provider.getprime(curr_prime);
                        }
                    }

                    for(int x =0;x<curr_layer.nodes.size();x++)
                    {
//                        System.out.println(nodes.get(x).rank_top_down + " " + nodes.get(x).curr_cnt + " " + a);
                    }

//                    for(int zx=0;zx<nodes.size();zx++)
//                    {
//                        nodes.get(zx).curr_cnt = nodes.get(zx).rank_top_down;
//                    }

                    //complexity - n
                    ArrayList<DataStructure.node> nodes_sec = (ArrayList<DataStructure.node>)nodes.clone();
                    //complexity - n^2
                    for(int ab=0;ab<nodes_sec.size();ab++)
                    {   nodes_sec.get(ab).curr_cnt = 1;
                        for(int ac=0;ac<nodes_sec.get(ab).int_edges.size();ac++)
                        {
                            nodes_sec.get(ab).curr_cnt *= nodes_sec.get(ab).int_edges.get(ac).node1.rank_top_down;
                        }
                    }
                    nodes_sec.sort(new Comparator<DataStructure.node>() {
                        @Override
                        public int compare(DataStructure.node o1, DataStructure.node o2) {
                            return (int)(o2.curr_cnt-o1.curr_cnt);
                        }
                    });

                    //complexity - 3c
                    curr_num = nodes_sec.get(0).curr_cnt;
                    curr_prime++;
                    nodes_sec.get(0).rank_top_down = nodes_sec.get(0).rank_top_down*prime_provider.getprime(curr_prime);

                    //complexity - n
                    for(int x =1;x<curr_layer.nodes.size();x++)
                    {
                        if(curr_num==nodes_sec.get(x).curr_cnt)
                        {
                            nodes_sec.get(x).rank_top_down = nodes_sec.get(x).rank_top_down*prime_provider.getprime(curr_prime);
                        }
                        else
                        {
                            curr_num = nodes_sec.get(x).curr_cnt;
                            nodes_sec.get(x).rank_top_down = nodes_sec.get(x).rank_top_down*prime_provider.getprime(++curr_prime);
                        }
                    }
                    for(int x =0;x<curr_layer.nodes.size();x++)
                    {
//                        System.out.println(nodes_sec.get(x).rank_top_down + " " + nodes_sec.get(x).curr_cnt + " " + a);
                    }
                }
            }

            //complexity - 2c units
            public void rank_top_down_top()
            {
                DataStructure.layer top_layer = layers.get(0);
                top_layer.nodes.get(0).rank_top_down = prime_provider.getprime(0);
            }
        }


    }*/

    //CLEAN CLASS
    //CLEAN CLASS

    //    package graphiso;
    //
    //import java.io.PrintStream;
    //import java.util.ArrayList;
    //import java.util.Comparator;
    //import java.util.Random;
    //import java.util.Scanner;
    //import graphiso.DataStructure;
    //import graphiso.DataStructure.layer;
    //import graphiso.DataStructure.node;
    //import graphiso.DataStructure.nodeedge;
    //import graphiso.DataStructure.edge;
    //import graphiso.DataStructure.label;
    //
    //import static graphiso.Helper.*;
    //
    //    /**
    //     * Created by Kuldeep on 2/15/2018.
    //     */
    //
    //    public class CleanClass {
    //        static int[][] basegraph;
    //        static PrintStream writer;
    //        static int[][] inputgraph;
    //        static int base_node_count;
    //        static int input_node_count;
    //        static int base_edge_count;
    //        static int input_edge_count;
    //        static int[] base_degreecount;
    //        static int[] input_degreecount;
    //        static String[] base_labels;
    //        static String[] input_labels;
    //        static int simicnt;
    //        static int dissimcnt;
    //        static long modulo = 1000000007;
    //        public static void main(String s[]) throws Exception
    //        {
    //            writer = System.out;
    //            prime_provider.fillprimes();
    //            Scanner scanner = new Scanner(System.in);
    //            base_node_count = scanner.nextInt();
    //            base_edge_count = 0;
    //            simicnt = 0;
    //            GraphCreator graphCreator = new GraphCreator();
    //            dissimcnt = 0;
    //            base_labels = new String[base_node_count];
    //
    //            //GRAPHS AS ADJ MATRICES
    //            basegraph = new int[base_node_count][base_node_count];
    //            basegraph = graphCreator.createGraph(base_node_count);
    //
    //            //DEGREE COUNT i.e. index 5 contains no. of node with degree 5
    //            base_degreecount = new int[base_node_count];
    //            for (int i = 0; i < base_node_count; i++) {
    //                int currdegree = 0;
    //                for (int j = 0; j < base_node_count; j++) {
    //                    //basegraph[i][j] = scanner.nextInt();
    //                    if (basegraph[i][j] == 1) {
    //                        base_edge_count++;
    //                        currdegree++;
    //                    }
    //                }
    //                base_degreecount[currdegree]++;
    //            }
    //            base_edge_count = base_edge_count / 2;
    //
    //            //input_node_count = scanner.nextInt();
    //            writer.println("HERE");
    //            int testcnt = 1;
    //            while (testcnt-- > 0) {
    //                input_node_count = base_node_count;
    //                input_labels = new String[input_node_count];
    //                input_edge_count = 0;
    //                inputgraph = new int[input_node_count][input_node_count];
    //                ArrayList permutes = permute_generator(basegraph.length, writer);
    //                int[][] graph = permute_graph(basegraph, permutes, writer);
    //                input_degreecount = new int[input_node_count];
    //                for (int i = 0; i < input_node_count; i++) {
    //                    int currdegree = 0;
    //                    for (int j = 0; j < input_node_count; j++) {
    //                        inputgraph[i][j] = graph[i][j];
    //                        //inputgraph[i][j] = scanner.nextInt();
    //                        if (inputgraph[i][j] == 1) {
    //                            input_edge_count++;
    //                            currdegree++;
    //                        }
    //                    }
    //                    input_degreecount[currdegree]++;
    //                }
    //                input_edge_count = input_edge_count / 2;
    //
    //                //generate_label generate_label = new generate_label();
    //                if (checkgraph(base_node_count, input_node_count, base_edge_count, input_edge_count, base_degreecount, input_degreecount)) {
    //                    ArrayList<DataStructure.label> base_graph_labels = new ArrayList<>();
    //                    for (int xz = 0; xz < basegraph.length; xz++) {
    //                        generate_label generate_label = new generate_label();
    //                        base_graph_labels.add(generate_label.generate(basegraph, xz));
    //                        base_labels[xz] = printlabel(base_graph_labels.get(base_graph_labels.size() - 1),writer);
    //                    }
    //                    ArrayList<DataStructure.label> input_graph_labels = new ArrayList<>();
    //                    for (int xz = 0; xz < inputgraph.length; xz++) {
    //                        generate_label generate_label = new generate_label();
    //                        input_graph_labels.add(generate_label.generate(inputgraph, xz));
    //                        input_labels[xz] = printlabel(input_graph_labels.get(input_graph_labels.size() - 1), writer);
    //                    }
    //
    //                    matchlabels(basegraph, inputgraph, base_labels, input_labels,permutes,simicnt,dissimcnt,writer);
    //                } else {
    //                    writer.println("NOT SIMILAR MAJOR");
    //                }
    //            }
    //            writer.println(simicnt + " " + dissimcnt);
    //        }
    //
    //        static class generate_label
    //        {
    //            //original matrix
    //            int[][] ori_graph;
    //            //current root for tree
    //            int root_node;
    //            //list of inflated nodes
    //            boolean inflated_nodes[];
    //            int inflated_node_cnt = 0;
    //            ArrayList<DataStructure.layer> layers = new ArrayList<>();
    //            ArrayList<Integer> prevlay_node = new ArrayList<>();
    //            ArrayList<Integer> currlay_node = new ArrayList<>();
    //
    //            public generate_label()
    //            {
    //                inflated_node_cnt = 0;
    //                layers.clear();
    //                prevlay_node.clear();
    //                currlay_node.clear();
    //            }
    //
    //            public DataStructure.label generate(int [][] graph, int n) {
    //
    //                //label for current node
    //                DataStructure.label label = new DataStructure.label();
    //                this.ori_graph = graph;
    //                root_node = n;
    //
    //                //first_layer_initialization
    //                layers.add(new DataStructure.layer());
    //                DataStructure.layer first_layer = layers.get(0);
    //                first_layer.layer_no = 0;
    //
    //                //root_node_inflated_in_first_layer
    //                DataStructure.node node = new DataStructure.node(0,0,n);
    //                node.layer_no = 0;
    //                first_layer.nodes.add(node);
    //
    //                prevlay_node.clear();
    //                currlay_node.add(n);
    //                //total inflated nodes maintain mechanism
    //                inflated_nodes = new boolean[ori_graph.length];
    //                inflated_nodes[n] = true;
    //                inflated_node_cnt++;
    //
    //
    //                //inflating DataStructure
    //                inflate_layer(1);
    //
    //
    //                for(int x=0;x<layers.size();x++)
    //                {
    //                    DataStructure.layer layer = layers.get(x);
    //                    label.layerstr = layer.incoming_edges.size() + " " + layer.outgoing_edges.size() + " " + layer.internal_edges.size();
    //                }
    //                label.layerstr += rank_top_down();
    //                // label.layerstr += rank_bottom_up();
    //                label.layers = this.layers;
    //                return label;
    //            }
    //
    //            public void inflate_layer(int layer_no)
    //            {
    //                DataStructure.layer layer = new DataStructure.layer();
    //                layer.layer_no = layer_no;
    //                DataStructure.node[] nextlay_nodes = new DataStructure.node[base_node_count];
    //                for(int i=0;i<base_node_count;i++){nextlay_nodes[i]=null;}
    //
    //                for(int i=0;i<layers.get(layer_no-1).nodes.size();i++)
    //                {
    //                    for(int j=0;j<base_node_count;j++)
    //                    {
    //                        if(ori_graph[layers.get(layer_no-1).nodes.get(i).ori_node_num][j]==1)
    //                        {
    //                            writer.println( "ONE " + layers.get(layer_no-1).nodes.get(i).ori_node_num + " " + j + " ");
    //                            //if(!inflated_nodes[j]) {
    //                            //check if node is in current DataStructure or previous DataStructure
    //                            if(!arraylistcontains(currlay_node,j) && !arraylistcontains(prevlay_node,j)){
    //                                writer.println("TWO " + layers.get(layer_no-1).nodes.get(i).ori_node_num + " " + j + " ");
    //                                DataStructure.node node;
    //                                //check if node is already inflated in next DataStructure by any previous node of current DataStructure
    //                                if(nextlay_nodes[j]!=null)
    //                                {
    //                                    node = nextlay_nodes[j];
    //                                    writer.println("nextlay_nodes");
    //                                }
    //                                else
    //                                {
    //                                    node = new DataStructure.node(layer_no - 1, layer.nodes.size(), j);
    //                                    node.layer_no = layer.layer_no;
    //                                    layer.nodes.add(node);
    //                                    nextlay_nodes[j] = node;
    //                                    inflated_nodes[j] = true;
    //                                    inflated_node_cnt++;
    //                                }
    //                                DataStructure.edge edge = new DataStructure.edge(layers.get(layer_no-1).nodes.get(i).pos_row, layers.get(layer_no-1).nodes.get(i).pos_col, node.pos_row,node.pos_col,layers.get(layer_no-1).nodes.get(i), node);
    //                                layer.incoming_edges.add(edge);
    //                                layers.get(layer_no-1).outgoing_edges.add(edge);
    //                                layers.get(layer_no-1).nodes.get(i).out_edges.add(new DataStructure.nodeedge(node));
    //                                node.inc_edges.add(new DataStructure.nodeedge(layers.get(layer_no-1).nodes.get(i)));
    //                            }
    //                            else {
    //                                for(int k=0;k<layers.get(layer_no-1).nodes.size();k++) {
    //                                    if (layers.get(layer_no - 1).nodes.get(k).ori_node_num == j) {
    //                                        writer.println("SAME LAYER");
    //
    //                                        DataStructure.edge edge = new DataStructure.edge(layers.get(layer_no - 1).nodes.get(k).pos_row, layers.get(layer_no - 1).nodes.get(k).pos_col, layers.get(layer_no - 1).nodes.get(i).pos_row, layers.get(layer_no - 1).nodes.get(i).pos_col, layers.get(layer_no - 1).nodes.get(k),layers.get(layer_no - 1).nodes.get(i));
    //                                        boolean repeat = false;
    //                                        for (int l = 0; l < layers.get(layer_no - 1).internal_edges.size(); l++) {
    //                                            if (layers.get(layer_no - 1).internal_edges.get(l).row1 == edge.row1 && layers.get(layer_no - 1).internal_edges.get(l).col1 == edge.col1) {
    //                                                if (layers.get(layer_no - 1).internal_edges.get(l).row2 == edge.row2 && layers.get(layer_no - 1).internal_edges.get(l).col2 == edge.col2) {
    //                                                    repeat = true;
    //                                                    break;
    //                                                }
    //                                            }
    //                                            if (layers.get(layer_no - 1).internal_edges.get(l).row1 == edge.row2 && layers.get(layer_no - 1).internal_edges.get(l).col1 == edge.col2) {
    //                                                if (layers.get(layer_no - 1).internal_edges.get(l).row2 == edge.row1 && layers.get(layer_no - 1).internal_edges.get(l).col2 == edge.col1) {
    //                                                    repeat = true;
    //                                                    break;
    //                                                }
    //                                            }
    //                                        }
    //                                        if (!repeat) {
    //                                            writer.println("NOT REPEAT");
    //                                            layers.get(layer_no - 1).internal_edges.add(edge);
    //                                            layers.get(layer_no - 1).nodes.get(k).int_edges.add(new DataStructure.nodeedge(layers.get(layer_no - 1).nodes.get(i)));
    //                                            layers.get(layer_no - 1).nodes.get(i).int_edges.add(new DataStructure.nodeedge(layers.get(layer_no - 1).nodes.get(k)));
    //                                        }
    //                                    }
    //                                }
    //                            }
    //
    //                        }
    //                    }
    //                }
    //
    //                prevlay_node = (ArrayList<Integer>) currlay_node.clone();
    //                currlay_node.clear();
    //                for(int i=0;i<layer.nodes.size();i++)
    //                {
    //                    currlay_node.add(layer.nodes.get(i).ori_node_num);
    //                }
    //                if(layer.nodes.size()!=0) {
    //                    layers.add(layer);
    //                    inflate_layer(layer_no + 1);
    //                }
    //                writer.print("COMPLETED INFLATING" + layers.size() + " ");
    //            }
    //
    //            public String rank_bottom_up()
    //            {   String temp = new String();
    //                temp += rank_bottom_up_bottom();
    //                for(int a = layers.size()-2;a>=0;a--)
    //                {   int curr_prime1=0;
    //                    DataStructure.layer curr_layer = layers.get(a);
    //
    //                    ArrayList<DataStructure.node> nodes = (ArrayList<DataStructure.node>)curr_layer.nodes.clone();
    //
    //                    //outcoming edges_top down
    //                    for(int lp=0;lp<nodes.size();lp++)
    //                    {
    //                        long o1_cnt = 1;
    //                        DataStructure.node o1 = nodes.get(lp);
    //                        for(int aa=0;aa<o1.out_edges.size();aa++)
    //                        {
    //                            writer.println("MULT_1*((2*3) + (4*5))" + o1_cnt + " " + (o1.out_edges.get(aa).node1.rank_top_down) + " " + prime_provider.getprime(99) + " " + (o1.out_edges.get(aa).node1.rank_bottom_up) + " " + prime_provider.getprime(97));
    //                            o1_cnt = (o1_cnt%modulo * (((o1.out_edges.get(aa).node1.rank_top_down)%modulo*(prime_provider.getprime(99))%modulo)%modulo + ((o1.out_edges.get(aa).node1.rank_bottom_up)%modulo*(prime_provider.getprime(97))%modulo)%modulo)%modulo)%modulo;
    //                        }
    //                        o1.curr_cnt = o1_cnt;
    //                    }
    //                    nodes.sort(new Comparator<DataStructure.node>() {
    //                        @Override
    //                        public int compare(DataStructure.node o1, DataStructure.node o2) {
    //                            return (int)(o2.curr_cnt-o1.curr_cnt);
    //                        }
    //                    });
    //
    //                    for(int i=0;i<nodes.size();i++)
    //                    {
    //                        writer.println("NODES SORT " + nodes.get(i).curr_cnt);
    //                    }
    //                    long curr_num = nodes.get(0).curr_cnt;
    //                    nodes.get(0).rank_bottom_up = prime_provider.getprime(curr_prime1);
    //                    for(int x =1;x<curr_layer.nodes.size();x++)
    //                    {
    //                        if(curr_num==nodes.get(x).curr_cnt) {nodes.get(x).rank_bottom_up = prime_provider.getprime(curr_prime1);}
    //                        else {curr_num = nodes.get(x).curr_cnt;nodes.get(x).rank_bottom_up = prime_provider.getprime(++curr_prime1);}}
    //
    //                    for(int x =0;x<curr_layer.nodes.size();x++) {
    //                        writer.println(nodes.get(x).rank_bottom_up + " " + nodes.get(x).curr_cnt + " " + a);
    //                    }
    //                    //internal edges_bottom up
    //                    for(int zx = 0;zx<nodes.size();zx++)
    //                    {
    //                        nodes.get(zx).curr_cnt = 1;
    //                        for(int zy=0;zy<nodes.get(zx).int_edges.size();zy++)
    //                        {
    //                            nodes.get(zx).curr_cnt = (nodes.get(zx).curr_cnt%modulo * (((nodes.get(zx).int_edges.get(zy).node1.rank_bottom_up)%modulo*(prime_provider.getprime(99))%modulo)%modulo + ((nodes.get(zx).int_edges.get(zy).node1.rank_bottom_up)%modulo*(prime_provider.getprime(97))%modulo)%modulo)%modulo)%modulo;
    //                        }
    //                    }
    //                    ArrayList<DataStructure.node> nodes_sec = (ArrayList<DataStructure.node>)nodes.clone();
    //                    nodes_sec.sort(new Comparator<DataStructure.node>() {@Override public int compare(DataStructure.node o1, DataStructure.node o2) {
    //                        return (int)(o2.curr_cnt-o1.curr_cnt);
    //                    }});
    //
    //                    curr_num = nodes_sec.get(0).curr_cnt;
    //                    curr_prime1++;
    //                    writer.println("MULT" + prime_provider.getprime(curr_prime1) + " " + nodes_sec.get(0).rank_bottom_up);
    //                    nodes_sec.get(0).rank_bottom_up = ((nodes_sec.get(0).rank_bottom_up)%modulo * (prime_provider.getprime(curr_prime1))%modulo)%modulo;
    //
    //                    for(int x =1;x<curr_layer.nodes.size();x++)
    //                    {
    //                        if(curr_num==nodes_sec.get(x).curr_cnt)
    //                        {
    //                            writer.println("MULT" + prime_provider.getprime(curr_prime1) + " " + nodes_sec.get(x).rank_bottom_up);
    //                            nodes_sec.get(x).rank_bottom_up = ((nodes_sec.get(x).rank_bottom_up)%modulo * (prime_provider.getprime(curr_prime1))%modulo)%modulo;
    //                        }
    //                        else
    //                        {
    //                            curr_num = nodes_sec.get(x).curr_cnt;
    //                            writer.println("MULT" + prime_provider.getprime(curr_prime1+1) + " " + nodes_sec.get(x).rank_bottom_up);
    //                            nodes_sec.get(x).rank_bottom_up = ((nodes_sec.get(x).rank_bottom_up)%modulo * (prime_provider.getprime(++curr_prime1))%modulo)%modulo;
    //                        }
    //                    }
    //                    for(int x =0;x<curr_layer.nodes.size();x++)
    //                    {
    //                        writer.println(nodes_sec.get(x).rank_bottom_up + " " + nodes_sec.get(x).curr_cnt + " " + a);
    //                        temp += nodes_sec.get(x).rank_bottom_up + " " + nodes_sec.get(x).curr_cnt + " " + a;
    //                    }
    //
    //                    for(int zx = 0;zx<nodes_sec.size();zx++)
    //                    {
    //                        nodes_sec.get(zx).curr_cnt = 1;
    //                        for(int zy=0;zy<nodes_sec.get(zx).inc_edges.size();zy++)
    //                        {
    //                            writer.println("MULT" + nodes_sec.get(zx).inc_edges.get(zy).node1.rank_top_down + " " + nodes_sec.get(zx).curr_cnt);
    //                            nodes_sec.get(zx).curr_cnt = (nodes_sec.get(zx).curr_cnt%modulo * nodes_sec.get(zx).inc_edges.get(zy).node1.rank_top_down%modulo)%modulo;
    //                        }
    //                    }
    //
    //                    nodes_sec.sort(new Comparator<DataStructure.node>() {
    //                        @Override
    //                        public int compare(DataStructure.node o1, DataStructure.node o2) {
    //                            return (int)(o2.curr_cnt-o1.curr_cnt);
    //                        }
    //                    });
    //
    //                    curr_num = nodes_sec.get(0).curr_cnt;
    //                    curr_prime1++;
    //                    writer.println("MULT" + prime_provider.getprime(curr_prime1) + " " + nodes_sec.get(0).rank_bottom_up);
    //                    nodes_sec.get(0).rank_bottom_up = (nodes_sec.get(0).rank_bottom_up%modulo* prime_provider.getprime(curr_prime1)%modulo);
    //
    //                    for(int x =1;x<curr_layer.nodes.size();x++)
    //                    {
    //                        if(curr_num==nodes_sec.get(x).curr_cnt)
    //                        {
    //                            writer.println("MULT" + prime_provider.getprime(curr_prime1) + " " + nodes_sec.get(x).rank_bottom_up);
    //                            nodes_sec.get(x).rank_bottom_up = (nodes_sec.get(x).rank_bottom_up%modulo * prime_provider.getprime(curr_prime1)%modulo)%modulo;
    //                        }
    //                        else
    //                        {
    //                            curr_num = nodes_sec.get(x).curr_cnt;
    //                            writer.println("MULT" + prime_provider.getprime(curr_prime1+1) + " " + nodes_sec.get(x).rank_bottom_up);
    //                            nodes_sec.get(x).rank_bottom_up = (nodes_sec.get(x).rank_bottom_up%modulo * prime_provider.getprime(++curr_prime1)%modulo)%modulo;
    //                        }
    //                    }
    //                    for(int x =0;x<curr_layer.nodes.size();x++)
    //                    {
    //                        writer.println(nodes_sec.get(x).rank_bottom_up + " " + nodes_sec.get(x).curr_cnt + " " + a);
    //                        temp += nodes_sec.get(x).rank_bottom_up + " " + nodes_sec.get(x).curr_cnt + " " + a;
    //                    }
    //                }
    //                return temp;
    //            }
    //
    //            public String rank_bottom_up_bottom()
    //            {
    //                String temp = new String();
    //                DataStructure.layer bottom_layer = layers.get(layers.size()-1);
    //                ArrayList<DataStructure.node> nodes = (ArrayList<DataStructure.node>)bottom_layer.nodes.clone();
    //
    //                for(int x =0;x<bottom_layer.nodes.size();x++)
    //                {   nodes.get(x).curr_cnt = 1;
    //                    for(int y=0;y<nodes.get(x).int_edges.size();y++)
    //                    {    writer.println("MULT" + nodes.get(x).int_edges.get(y).node1.rank_top_down + " " + nodes.get(x).curr_cnt);
    //                        nodes.get(x).curr_cnt = (nodes.get(x).curr_cnt%modulo * nodes.get(x).int_edges.get(y).node1.rank_top_down%modulo)%modulo;
    //                    }
    //                }
    //
    //                nodes.sort(new Comparator<DataStructure.node>() {
    //                    @Override
    //                    public int compare(DataStructure.node o1, DataStructure.node o2) {
    //                        return (int)(o2.curr_cnt-o1.curr_cnt);
    //                    }
    //                });
    //
    //                int curr_num = (int)nodes.get(0).curr_cnt;
    //                int curr_prime_index = 0;
    //                nodes.get(0).rank_bottom_up = prime_provider.getprime(curr_prime_index);
    //
    //                for(int x =1;x<bottom_layer.nodes.size();x++)
    //                {
    //                    if(curr_num==nodes.get(x).curr_cnt)
    //                    {
    //                        nodes.get(x).rank_bottom_up = prime_provider.getprime(curr_prime_index);
    //                    }
    //                    else
    //                    {
    //                        curr_num = (int)nodes.get(x).curr_cnt;
    //                        nodes.get(x).rank_bottom_up = prime_provider.getprime(++curr_prime_index);
    //                    }
    //                }
    //
    //                //IF SAME
    //                for(int x =0;x<bottom_layer.nodes.size();x++)
    //                {   nodes.get(x).curr_cnt = 1;
    //                    for(int y=0;y<nodes.get(x).inc_edges.size();y++)
    //                    {
    //                        writer.println("MULT" + nodes.get(x).inc_edges.get(y).node1.rank_top_down + " " + nodes.get(x).curr_cnt);
    //                        nodes.get(x).curr_cnt = (nodes.get(x).curr_cnt%modulo * nodes.get(x).inc_edges.get(y).node1.rank_top_down%modulo)%modulo;
    //                    }
    //                }
    //
    //                nodes.sort(new Comparator<DataStructure.node>() {
    //                    @Override
    //                    public int compare(DataStructure.node o1, DataStructure.node o2) {
    //                        return (int)(o2.curr_cnt-o1.curr_cnt);
    //                    }
    //                });
    //
    //                curr_num = (int)nodes.get(0).curr_cnt;
    //                nodes.get(0).rank_bottom_up *= prime_provider.getprime(++curr_prime_index);
    //
    //                for(int x =1;x<bottom_layer.nodes.size();x++)
    //                {
    //                    if(curr_num==nodes.get(x).curr_cnt)
    //                    {
    //                        writer.println("MULT" + prime_provider.getprime(curr_prime_index) + " " + nodes.get(x).rank_bottom_up);
    //                        nodes.get(x).rank_bottom_up = (nodes.get(x).rank_bottom_up%modulo * prime_provider.getprime(curr_prime_index)%modulo)%modulo;
    //                    }
    //                    else
    //                    {
    //                        curr_num = (int)nodes.get(x).curr_cnt;
    //                        writer.println("MULT" + prime_provider.getprime(curr_prime_index+1) + " " + nodes.get(x).rank_bottom_up);
    //                        nodes.get(x).rank_bottom_up = ( nodes.get(x).rank_bottom_up%modulo *prime_provider.getprime(++curr_prime_index)%modulo)%modulo;
    //                    }
    //                }
    //
    //                for(int x =0;x<bottom_layer.nodes.size();x++)
    //                {
    //                    writer.println(nodes.get(x).rank_bottom_up + " " + nodes.get(x).curr_cnt + " " + (layers.size()-1));
    //                    temp += nodes.get(x).rank_bottom_up + " " + nodes.get(x).curr_cnt + " " + (layers.size()-1);
    //                }
    //
    //                return temp;
    //
    //            }
    //
    //            public String rank_top_down()
    //            {
    //                rank_top_down_top();
    //                String temp = new String();
    //                for(int a = 1;a<layers.size();a++)
    //                {
    //                    int curr_prime = 0;
    //                    DataStructure.layer curr_layer = layers.get(a);
    //                    ArrayList<DataStructure.node> nodes = (ArrayList<DataStructure.node>)curr_layer.nodes.clone();
    //
    //                    //ASSIGNING ORDER TO NODES USING TOP_DOWN RANKS OF INC_EDGES
    //                    for(int ab=0;ab<nodes.size();ab++)
    //                    {   nodes.get(ab).curr_cnt = 1;
    //                        for(int ac=0;ac<nodes.get(ab).inc_edges.size();ac++)
    //                        {
    //                            writer.println("MULT" + nodes.get(ab).inc_edges.get(ac).node1.rank_top_down + " " + nodes.get(ab).curr_cnt);
    //                            nodes.get(ab).curr_cnt = (nodes.get(ab).curr_cnt%modulo * nodes.get(ab).inc_edges.get(ac).node1.rank_top_down%modulo)%modulo;
    //                        }
    //                    }
    //
    //                    nodes.sort(new Comparator<DataStructure.node>() {
    //                        @Override
    //                        public int compare(DataStructure.node o1, DataStructure.node o2) {
    //                            return (int)(o2.curr_cnt-o1.curr_cnt);
    //                        }
    //                    });
    //
    //                    long curr_num = nodes.get(0).curr_cnt;
    //                    nodes.get(0).rank_top_down = prime_provider.getprime(curr_prime);
    //
    //                    for(int x =1;x<curr_layer.nodes.size();x++)
    //                    {
    //                        if(curr_num==nodes.get(x).curr_cnt)
    //                        {
    //                            nodes.get(x).rank_top_down = prime_provider.getprime(curr_prime);
    //                        }
    //                        else
    //                        {
    //                            curr_num = nodes.get(x).curr_cnt;
    //                            curr_prime++;
    //                            nodes.get(x).rank_top_down = prime_provider.getprime(curr_prime);
    //                        }
    //                    }
    //
    //                    for(int x =0;x<curr_layer.nodes.size();x++)
    //                    {
    //                        writer.println(nodes.get(x).rank_top_down + " " + nodes.get(x).curr_cnt + " " + a);
    //                        temp += nodes.get(x).rank_top_down + " " + nodes.get(x).curr_cnt + " " + a;
    //                    }
    //
    //
    //                    for(int zx=0;zx<nodes.size();zx++)
    //                    {
    //                        nodes.get(zx).curr_cnt = nodes.get(zx).rank_top_down;
    //                    }
    //
    //                    ArrayList<DataStructure.node> nodes_sec = (ArrayList<DataStructure.node>)nodes.clone();
    //                    for(int ab=0;ab<nodes_sec.size();ab++)
    //                    {   nodes_sec.get(ab).curr_cnt = 1;
    //                        for(int ac=0;ac<nodes_sec.get(ab).int_edges.size();ac++)
    //                        {   writer.println("MULT" + nodes_sec.get(ab).int_edges.get(ac).node1.rank_top_down + " " + nodes_sec.get(ab).curr_cnt);
    //                            nodes_sec.get(ab).curr_cnt =(nodes_sec.get(ab).curr_cnt%modulo * nodes_sec.get(ab).int_edges.get(ac).node1.rank_top_down%modulo)%modulo;
    //                        }
    //                    }
    //                    nodes_sec.sort(new Comparator<DataStructure.node>() {
    //                        @Override
    //                        public int compare(DataStructure.node o1, DataStructure.node o2) {
    //                            return (int)(o2.curr_cnt-o1.curr_cnt);
    //                        }
    //                    });
    //
    //                    curr_num = nodes_sec.get(0).curr_cnt;
    //                    curr_prime++;
    //
    //                    writer.println("MULT" + nodes_sec.get(0).rank_top_down + " " + prime_provider.getprime(curr_prime));
    //                    nodes_sec.get(0).rank_top_down = (nodes_sec.get(0).rank_top_down%modulo * prime_provider.getprime(curr_prime)%modulo)%modulo;
    //
    //                    for(int x =1;x<curr_layer.nodes.size();x++)
    //                    {
    //                        if(curr_num==nodes_sec.get(x).curr_cnt)
    //                        {
    //                            writer.println("MULT" + nodes_sec.get(x).rank_top_down + " " + prime_provider.getprime(curr_prime));
    //                            nodes_sec.get(x).rank_top_down = (nodes_sec.get(x).rank_top_down%modulo * prime_provider.getprime(curr_prime)%modulo)%modulo;
    //                        }
    //                        else
    //                        {
    //                            curr_num = nodes_sec.get(x).curr_cnt;
    //                            writer.println("MULT" + nodes_sec.get(x).rank_top_down + " " + prime_provider.getprime(curr_prime+1));
    //                            nodes_sec.get(x).rank_top_down = (nodes_sec.get(x).rank_top_down%modulo * prime_provider.getprime(++curr_prime)%modulo)%modulo;
    //                        }
    //                    }
    //                    for(int x =0;x<curr_layer.nodes.size();x++)
    //                    {
    //                        writer.println(nodes_sec.get(x).rank_top_down + " " + nodes_sec.get(x).curr_cnt + " " + a);
    //                        temp +=nodes_sec.get(x).rank_top_down + " " + nodes_sec.get(x).curr_cnt + " " + a;
    //                    }
    //                }
    //                return temp;
    //            }
    //
    //            public void rank_top_down_top()
    //            {
    //                DataStructure.layer top_layer = layers.get(0);
    //                top_layer.nodes.get(0).rank_top_down = prime_provider.getprime(0);
    //            }
    //        }
    //
    //    }


}
