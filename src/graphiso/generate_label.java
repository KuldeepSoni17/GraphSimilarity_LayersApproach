package graphiso;

import java.io.PrintStream;
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

public class generate_label {


    //original matrix
    private int[][] ori_graph;
    //current root for tree
    private int root_node;
    private int base_node_count;
    //list of inflated nodes
    private boolean inflated_nodes[];
    private int inflated_node_cnt = 0;
    private label label;
    private ArrayList<layer> layers = new ArrayList<>();
    private ArrayList<Integer> prevlay_node = new ArrayList<>();
    private ArrayList<Integer> currlay_node = new ArrayList<>();
    static long modulo = 1000000007;
    writeHelper writer;

    public generate_label(int[][] base_graph, int base_node_count, int root_node, writeHelper writer){
        ori_graph = base_graph;
        this.base_node_count = base_node_count;
        this.root_node = root_node;
        inflated_node_cnt = 0;
        inflated_nodes = new boolean[ori_graph.length];
        layers.clear();
        prevlay_node.clear();
        currlay_node.clear();
        label = new label();
        this.writer = writer;
    }

    //complexity - 12c + 2n^3 + 5n^2 + 7cn + 4c + 2n + 7cn + 4n^2 + 2n^3 + 2c + 2c + n = 20c + 14cn + 3n + 4n^3 + 9n^2
    public label generate() {
        //complexity - 12c

        label.layerstr = base_node_count + " ";
        layers.add(new layer());
        layer first_layer = layers.get(0);
        first_layer.layer_no = 0;

        node node = new node(0,0,root_node);
        node.layer_no = 0;
        first_layer.nodes.add(node);

        prevlay_node.clear();
        currlay_node.add(root_node);
        inflated_nodes = new boolean[ori_graph.length];
        inflated_nodes[root_node] = true;
        inflated_node_cnt++;
        inflate_layer(1);
        label.layerstr += layers.size() + " ";
        for(int x=0;x<layers.size();x++)
        {
            layer layer = layers.get(x);
            writer.println();
            label.layerstr += layer.nodes.size() + " " + layer.incoming_edges.size() + " " + layer.outgoing_edges.size() + " " + layer.internal_edges.size() + " ";
            writer.println(layer.incoming_edges.size() + " " + layer.outgoing_edges.size() + " " + layer.internal_edges.size());
        }
        label.layerstr += "TOP_DOWN_RANKING ";
        writer.println("TOP_DOWN_RANKING");
        //complexity - 7cn + 4n^2 + 2n^3 + 2c
        label.layerstr += rank_top_down();

        label.layerstr += "BOTTOM_UP_RANKING ";
        writer.println("BOTTOM_UP_RANKING");
        //complexity - 2n^3 + 5n^2 + 7cn + 4c + 2n
        label.layerstr += rank_bottom_up();

        //complexity - c
        //complexity - n
//            for(int ij=0;ij<this.layers.size();ij++)
//            {
//                process_layer(this.layers.get(ij));
//            }
        //complexity - c
        label.layers = this.layers;

        for(int i=0;i<layers.size();i++)
        {
            writer.println(layers.get(i).toString());
        }
        writer.println("Tree construction result: " + verifyTreeConstruction(layers, ori_graph));

        return label;
    }

    //complexity - 10cn^2 + 2n^5 + 5cn^3 + 2c
    public void inflate_layer(int layer_no)
    {
        //complexity - 2c
        layer layer = new layer();
        layer.layer_no = layer_no;
        node[] nextlay_nodes = new node[base_node_count];
        for(int i=0;i<base_node_count;i++){nextlay_nodes[i]=null;}

        //complexity - n(n(10c + 2n^3 + 5cn)) = 10cn^2 + 2n^5 + 5cn^3
        for(int i=0;i<layers.get(layer_no-1).nodes.size();i++)//For all nodes in previous layer
        {
            //complexity - n(10c + 2n^3 + 5cn)
            for(int j=0;j<base_node_count;j++)//For all nodes in graph
            {
                //complexity - 10c + 2n^3 + 5cn
                if(ori_graph[layers.get(layer_no-1).nodes.get(i).ori_node_num][j]==1)//If there is an edge between these nodes
                {
                    //check if node not already inflated
                    writer.println( "ONE " + layers.get(layer_no-1).nodes.get(i).ori_node_num + " " + j + " ");
                    //if(!inflated_nodes[j]) {
                    if(!arraylistcontains(currlay_node,j) && !arraylistcontains(prevlay_node,j)){
                        writer.println("TWO " + layers.get(layer_no-1).nodes.get(i).ori_node_num + " " + j + " ");
                        //complexity - 10c
                        node node;
                        if(nextlay_nodes[j]!=null)
                        {
                            node = nextlay_nodes[j];
                        }
                        else
                        {
                            node = new node(layer_no, layer.nodes.size(), j);
                            node.layer_no = layer.layer_no;
                            layer.nodes.add(node);
                            nextlay_nodes[j] = node;
                            inflated_nodes[j] = true;
                            inflated_node_cnt++;
                        }
                        edge edge = new edge(layers.get(layer_no-1).nodes.get(i).pos_row, layers.get(layer_no-1).nodes.get(i).pos_col, node.pos_row,node.pos_col,layers.get(layer_no-1).nodes.get(i), node);
                        layer.incoming_edges.add(edge);
                        layers.get(layer_no-1).outgoing_edges.add(edge);
                        layers.get(layer_no-1).nodes.get(i).out_edges.add(new nodeedge(node));
                        node.inc_edges.add(new nodeedge(layers.get(layer_no-1).nodes.get(i)));
                    }
                    else {
                        //complexity - n(2n^2 + 5c) = 2n^3 + 5cn
                        for(int k=0;k<layers.get(layer_no-1).nodes.size();k++) {
                            //complexity - 2n^c + 5c
                            if (layers.get(layer_no - 1).nodes.get(k).ori_node_num == j) {
                                writer.println("SAME LAYER");

                                //complexity - 2c
                                edge edge = new edge(layers.get(layer_no - 1).nodes.get(k).pos_row, layers.get(layer_no - 1).nodes.get(k).pos_col, layers.get(layer_no - 1).nodes.get(i).pos_row, layers.get(layer_no - 1).nodes.get(i).pos_col, layers.get(layer_no - 1).nodes.get(k),layers.get(layer_no - 1).nodes.get(i));
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
                                    writer.println("NOT REPEAT");
                                    layers.get(layer_no - 1).internal_edges.add(edge);
                                    if(layers.get(layer_no - 1).nodes.get(k).equals(layers.get(layer_no - 1).nodes.get(i)))
                                    {
                                        layers.get(layer_no - 1).nodes.get(k).int_edges.add(new nodeedge(layers.get(layer_no - 1).nodes.get(i)));
                                    }
                                    else
                                    {
                                        layers.get(layer_no - 1).nodes.get(k).int_edges.add(new nodeedge(layers.get(layer_no - 1).nodes.get(i)));
                                        layers.get(layer_no - 1).nodes.get(i).int_edges.add(new nodeedge(layers.get(layer_no - 1).nodes.get(k)));
                                    }

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
        writer.print("COMPLETED INFLATING" + layers.size() + " ");
    }

    //complexity - 2c units
    public String rank_top_down_top()
    {
        DataStructure.layer top_layer = layers.get(0);
        top_layer.nodes.get(0).rank_top_down = prime_provider.getprime(0);
        return top_layer.nodes.get(0).rank_top_down + " 0 0 ";
    }

    //complexity - n + c + n^2 + 3c + n = n^2 + 4c + 2n
    public String rank_bottom_up_bottom()
    {
        //complexity - c
        String temp = new String();
        layer bottom_layer = layers.get(layers.size()-1);
        //complexity - n
        ArrayList<node> nodes = (ArrayList<node>)bottom_layer.nodes.clone();

        for(int x =0;x<bottom_layer.nodes.size();x++)
        {   nodes.get(x).curr_cnt = 1;
            writer.println(nodes.get(x).int_edges.size() + " ");
            for(int y=0;y<nodes.get(x).int_edges.size();y++)
            {    writer.println("MULT" + nodes.get(x).int_edges.get(y).node1.rank_top_down + " " + nodes.get(x).curr_cnt  + " " + nodes.get(x).ori_node_num + " " + nodes.get(x).int_edges.get(y).node1.ori_node_num);
                nodes.get(x).curr_cnt = (nodes.get(x).curr_cnt%modulo * nodes.get(x).int_edges.get(y).node1.rank_top_down%modulo)%modulo;
            }
        }

        //complexity - n^2
        nodes.sort(new Comparator<node>() {
            @Override
            public int compare(node o1, node o2) {
                return (int)(o2.curr_cnt-o1.curr_cnt);
            }
        });

        //complexity - 3c
        int curr_num = (int)nodes.get(0).curr_cnt;
        int curr_prime_index = 0;
        nodes.get(0).rank_bottom_up = prime_provider.getprime(curr_prime_index);

        //complexity - n
        for(int x =1;x<bottom_layer.nodes.size();x++)
        {
            if(curr_num==nodes.get(x).curr_cnt)
            {
                nodes.get(x).rank_bottom_up = prime_provider.getprime(curr_prime_index);
            }
            else
            {
                curr_num = (int)nodes.get(x).curr_cnt;
                nodes.get(x).rank_bottom_up = prime_provider.getprime(++curr_prime_index);
            }
        }


        for(int x =0;x<bottom_layer.nodes.size();x++)
        {
            writer.println(nodes.get(x).rank_bottom_up + " " + nodes.get(x).curr_cnt + " " + (layers.size()-1)  + " " + nodes.get(x).ori_node_num);
            temp += nodes.get(x).rank_bottom_up + " " + nodes.get(x).curr_cnt + " " + (layers.size()-1) + " ";
        }

        //IF SAME
        for(int x =0;x<bottom_layer.nodes.size();x++)
        {   nodes.get(x).curr_cnt = 1;
            for(int y=0;y<nodes.get(x).inc_edges.size();y++)
            {
                writer.println("MULT" + nodes.get(x).inc_edges.get(y).node1.rank_top_down + " " + nodes.get(x).curr_cnt  + " " + nodes.get(x).ori_node_num + " " + nodes.get(x).inc_edges.get(y).node1.ori_node_num);
                nodes.get(x).curr_cnt = (nodes.get(x).curr_cnt%modulo * nodes.get(x).inc_edges.get(y).node1.rank_top_down%modulo)%modulo;
            }
        }

        nodes.sort(new Comparator<node>() {
            @Override
            public int compare(node o1, node o2) {
                return (int)(o2.curr_cnt-o1.curr_cnt);
            }
        });

        curr_num = (int)nodes.get(0).curr_cnt;
        nodes.get(0).rank_bottom_up *= prime_provider.getprime(++curr_prime_index);

        for(int x =1;x<bottom_layer.nodes.size();x++)
        {
            if(curr_num==nodes.get(x).curr_cnt)
            {
                writer.println("MULT" + prime_provider.getprime(curr_prime_index) + " " + nodes.get(x).rank_bottom_up + " " + nodes.get(x).ori_node_num);
                nodes.get(x).rank_bottom_up = (nodes.get(x).rank_bottom_up%modulo * prime_provider.getprime(curr_prime_index)%modulo)%modulo;
            }
            else
            {
                curr_num = (int)nodes.get(x).curr_cnt;
                writer.println("MULT" + prime_provider.getprime(curr_prime_index+1) + " " + nodes.get(x).rank_bottom_up + " " + nodes.get(x).ori_node_num);
                nodes.get(x).rank_bottom_up = ( nodes.get(x).rank_bottom_up%modulo * prime_provider.getprime(++curr_prime_index)%modulo)%modulo;
            }
        }

        for(int x =0;x<bottom_layer.nodes.size();x++)
        {
            writer.println(nodes.get(x).rank_bottom_up + " " + nodes.get(x).curr_cnt + " " + (layers.size()-1) + " " + nodes.get(x).ori_node_num);
            temp += nodes.get(x).rank_bottom_up + " " + nodes.get(x).curr_cnt + " " + (layers.size()-1) + " ";
        }

        return temp;

    }

    //complexity - 2n^3 + 5n^2 + 7cn + 4c + 2n
    public String rank_bottom_up()
    {   String temp = new String();
        //complexity - n^2 + 4c + 2n
        temp += rank_bottom_up_bottom();
        //complexity - n(2(n^2 + 3c + 2n) + c)  = 2n^3 + 7cn + 4n^2
        for(int a = layers.size()-2;a>=0;a--)
        {   int curr_prime1=0;
            //complexity - c
            layer curr_layer = layers.get(a);

            //complexity - n
            ArrayList<node> nodes = (ArrayList<node>)curr_layer.nodes.clone();

            //complexity - n^2
            //outcoming edges_top down
            for(int lp=0;lp<nodes.size();lp++)
            {
                long o1_cnt = 1;
                node o1 = nodes.get(lp);
                for(int aa=0;aa<o1.out_edges.size();aa++)
                {
                    writer.println("MULT_1*((2*3) + (4*5))" + o1_cnt + " " + (o1.out_edges.get(aa).node1.rank_top_down) + " " + prime_provider.getprime(99) + " " + (o1.out_edges.get(aa).node1.rank_bottom_up) + " " + prime_provider.getprime(97) + " " + o1.ori_node_num + " " + o1.out_edges.get(aa).node1.ori_node_num);
                    o1_cnt = (o1_cnt%modulo * (((o1.out_edges.get(aa).node1.rank_top_down)%modulo*(prime_provider.getprime(99))%modulo)%modulo + ((o1.out_edges.get(aa).node1.rank_bottom_up)%modulo*(prime_provider.getprime(97))%modulo)%modulo)%modulo)%modulo;
                }
                o1.curr_cnt = o1_cnt;
            }
            nodes.sort(new Comparator<node>() {
                @Override
                public int compare(node o1, node o2) {
                    return (int)(o2.curr_cnt-o1.curr_cnt);
                }
            });

            for(int i=0;i<nodes.size();i++)
            {
                writer.println("NODES SORT " + nodes.get(i).curr_cnt);
            }
            //complexity - 3c
            long curr_num = nodes.get(0).curr_cnt;
            nodes.get(0).rank_bottom_up = prime_provider.getprime(curr_prime1);

            //complexity - n
            for(int x =1;x<curr_layer.nodes.size();x++)
            {
                if(curr_num==nodes.get(x).curr_cnt) {nodes.get(x).rank_bottom_up = prime_provider.getprime(curr_prime1);}
                else {curr_num = nodes.get(x).curr_cnt;nodes.get(x).rank_bottom_up = prime_provider.getprime(++curr_prime1);}}

            for(int x =0;x<curr_layer.nodes.size();x++) {
                writer.println(nodes.get(x).rank_bottom_up + " " + nodes.get(x).curr_cnt + " " + a);
                temp += nodes.get(x).rank_bottom_up + " " + nodes.get(x).curr_cnt + " " + a + " ";
            }
            //complexity - n
            //internal edges_bottom up
            for(int zx = 0;zx<nodes.size();zx++)
            {
                nodes.get(zx).curr_cnt = 1;
                writer.println(nodes.get(zx).int_edges.size() + " ");
                for(int zy=0;zy<nodes.get(zx).int_edges.size();zy++)
                {
                    nodes.get(zx).curr_cnt = (nodes.get(zx).curr_cnt%modulo * (((nodes.get(zx).int_edges.get(zy).node1.rank_top_down)%modulo*(prime_provider.getprime(99))%modulo)%modulo + ((nodes.get(zx).int_edges.get(zy).node1.rank_bottom_up)%modulo*(prime_provider.getprime(97))%modulo)%modulo)%modulo)%modulo;
                    writer.println("MULT" + nodes.get(zx).curr_cnt + "*(" + nodes.get(zx).int_edges.get(zy).node1.rank_top_down + "*" + prime_provider.getprime(99) +" " + nodes.get(zx).int_edges.get(zy).node1.rank_bottom_up + "*" + prime_provider.getprime(97) + " " +  nodes.get(zx).ori_node_num + " " + nodes.get(zx).int_edges.get(zy).node1.ori_node_num);
                }
            }
            ArrayList<node> nodes_sec = (ArrayList<node>)nodes.clone();
            //complexity - n^2
            nodes_sec.sort(new Comparator<node>() {@Override public int compare(node o1, node o2) {
                return (int)(o2.curr_cnt-o1.curr_cnt);
            }});

            //complexity - 3c
            curr_num = nodes_sec.get(0).curr_cnt;
            curr_prime1++;
            writer.println("MULT" + prime_provider.getprime(curr_prime1) + " " + nodes_sec.get(0).rank_bottom_up  + " " + nodes_sec.get(0).ori_node_num);
            nodes_sec.get(0).rank_bottom_up = ((nodes_sec.get(0).rank_bottom_up)%modulo * (prime_provider.getprime(curr_prime1))%modulo)%modulo;

            //complexity - n
            for(int x =1;x<curr_layer.nodes.size();x++)
            {
                if(curr_num==nodes_sec.get(x).curr_cnt)
                {
                    writer.println("MULT" + prime_provider.getprime(curr_prime1) + " " + nodes_sec.get(x).rank_bottom_up + " " + nodes_sec.get(x).ori_node_num);
                    nodes_sec.get(x).rank_bottom_up = ((nodes_sec.get(x).rank_bottom_up)%modulo * (prime_provider.getprime(curr_prime1))%modulo)%modulo;
                }
                else
                {
                    curr_num = nodes_sec.get(x).curr_cnt;
                    writer.println("MULT" + prime_provider.getprime(curr_prime1+1) + " " + nodes_sec.get(x).rank_bottom_up + " " + nodes_sec.get(x).ori_node_num);
                    nodes_sec.get(x).rank_bottom_up = ((nodes_sec.get(x).rank_bottom_up)%modulo * (prime_provider.getprime(++curr_prime1))%modulo)%modulo;
                }
            }
            for(int x =0;x<curr_layer.nodes.size();x++)
            {
                writer.println(nodes_sec.get(x).rank_bottom_up + " " + nodes_sec.get(x).curr_cnt + " " + a + " " + nodes_sec.get(x).ori_node_num);
                temp += nodes_sec.get(x).rank_bottom_up + " " + nodes_sec.get(x).curr_cnt + " " + a + " ";
            }

            for(int zx = 0;zx<nodes_sec.size();zx++)
            {
                nodes_sec.get(zx).curr_cnt = 1;
                for(int zy=0;zy<nodes_sec.get(zx).inc_edges.size();zy++)
                {
                    writer.println("MULT" + nodes_sec.get(zx).inc_edges.get(zy).node1.rank_top_down + " " + nodes_sec.get(zx).curr_cnt  + " " + nodes_sec.get(zx).ori_node_num + " " + nodes_sec.get(zx).inc_edges.get(zy).node1.ori_node_num);
                    nodes_sec.get(zx).curr_cnt = (nodes_sec.get(zx).curr_cnt%modulo * nodes_sec.get(zx).inc_edges.get(zy).node1.rank_top_down%modulo)%modulo;
                }
            }

            nodes_sec.sort(new Comparator<node>() {
                @Override
                public int compare(node o1, node o2) {
                    return (int)(o2.curr_cnt-o1.curr_cnt);
                }
            });

            curr_num = nodes_sec.get(0).curr_cnt;
            curr_prime1++;
            writer.println("MULT" + prime_provider.getprime(curr_prime1) + " " + nodes_sec.get(0).rank_bottom_up  + " " + nodes_sec.get(0).ori_node_num);
            nodes_sec.get(0).rank_bottom_up = (nodes_sec.get(0).rank_bottom_up%modulo* prime_provider.getprime(curr_prime1)%modulo);

            //complexity - n
            for(int x =1;x<curr_layer.nodes.size();x++)
            {
                if(curr_num==nodes_sec.get(x).curr_cnt)
                {
                    writer.println("MULT" + prime_provider.getprime(curr_prime1) + " " + nodes_sec.get(x).rank_bottom_up + " " + nodes_sec.get(x).ori_node_num);
                    nodes_sec.get(x).rank_bottom_up = (nodes_sec.get(x).rank_bottom_up%modulo * prime_provider.getprime(curr_prime1)%modulo)%modulo;
                }
                else
                {
                    curr_num = nodes_sec.get(x).curr_cnt;
                    writer.println("MULT" + prime_provider.getprime(curr_prime1+1) + " " + nodes_sec.get(x).rank_bottom_up + " " + nodes_sec.get(x).ori_node_num);
                    nodes_sec.get(x).rank_bottom_up = (nodes_sec.get(x).rank_bottom_up%modulo * prime_provider.getprime(++curr_prime1)%modulo)%modulo;
                }
            }
            for(int x =0;x<curr_layer.nodes.size();x++)
            {
                writer.println(nodes_sec.get(x).rank_bottom_up + " " + nodes_sec.get(x).curr_cnt + " " + a  + " " + nodes_sec.get(x).ori_node_num);
                temp += nodes_sec.get(x).rank_bottom_up + " " + nodes_sec.get(x).curr_cnt + " " + a + " ";
            }
        }
        return temp;
    }

    //complexity - n(c + n + n^2 + 3c + n + n + n^2 + 3c + n) + 2c = 7cn + 4n^2 + 2n^3 + 2c
    public String rank_top_down()
    {//complexity - 2c
        String temp = rank_top_down_top();
        //complexity - n(c + n + n^2 + 3c + n + n + n^2 + 3c + n)
        for(int a = 1;a<layers.size();a++)
        {
            int curr_prime = 0;
            //complexity - c
            layer curr_layer = layers.get(a);
            //complexity - n
            ArrayList<node> nodes = (ArrayList<node>)curr_layer.nodes.clone();

            //ASSIGNING ORDER TO NODES USING TOP_DOWN RANKS OF INC_EDGES
            for(int ab=0;ab<nodes.size();ab++)
            {   nodes.get(ab).curr_cnt = 1;
                for(int ac=0;ac<nodes.get(ab).inc_edges.size();ac++)
                {
                    writer.println("MULT" + nodes.get(ab).inc_edges.get(ac).node1.rank_top_down + " " + nodes.get(ab).curr_cnt + " " + nodes.get(ab).ori_node_num + " " + nodes.get(ab).inc_edges.get(ac).node1.ori_node_num);
                    nodes.get(ab).curr_cnt = (nodes.get(ab).curr_cnt%modulo * nodes.get(ab).inc_edges.get(ac).node1.rank_top_down%modulo)%modulo;
                }
            }

            //complexity - n^2
            nodes.sort(new Comparator<node>() {
                @Override
                public int compare(node o1, node o2) {
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
                writer.println(nodes.get(x).rank_top_down + " " + nodes.get(x).curr_cnt + " " + a  + " " + nodes.get(x).ori_node_num);
                temp += nodes.get(x).rank_top_down + " " + nodes.get(x).curr_cnt + " " + a + " ";
            }

            for(int zx=0;zx<nodes.size();zx++)
            {
                nodes.get(zx).curr_cnt = nodes.get(zx).rank_top_down;
            }

            //complexity - n
            ArrayList<node> nodes_sec = (ArrayList<node>)nodes.clone();
            //complexity - n^2
            for(int ab=0;ab<nodes_sec.size();ab++)
            {   nodes_sec.get(ab).curr_cnt = 1;
                writer.println(nodes_sec.get(ab).int_edges.size() + " ");
                for(int ac=0;ac<nodes_sec.get(ab).int_edges.size();ac++)
                {
                    writer.println("MULT" + nodes_sec.get(ab).int_edges.get(ac).node1.rank_top_down + " " + nodes_sec.get(ab).curr_cnt  + " " + nodes_sec.get(ab).ori_node_num + " " + nodes_sec.get(ab).int_edges.get(ac).node1.ori_node_num);
                    nodes_sec.get(ab).curr_cnt =(nodes_sec.get(ab).curr_cnt%modulo * nodes_sec.get(ab).int_edges.get(ac).node1.rank_top_down%modulo)%modulo;
                }
            }
            nodes_sec.sort(new Comparator<node>() {
                @Override
                public int compare(node o1, node o2) {
                    return (int)(o2.curr_cnt-o1.curr_cnt);
                }
            });

            //complexity - 3c
            curr_num = nodes_sec.get(0).curr_cnt;
            curr_prime++;

            writer.println("MULT" + nodes_sec.get(0).rank_top_down + " " + prime_provider.getprime(curr_prime)  + " " + nodes_sec.get(0).ori_node_num);
            nodes_sec.get(0).rank_top_down = (nodes_sec.get(0).rank_top_down%modulo * prime_provider.getprime(curr_prime)%modulo)%modulo;

            //complexity - n
            for(int x =1;x<curr_layer.nodes.size();x++)
            {
                if(curr_num==nodes_sec.get(x).curr_cnt)
                {
                    writer.println("MULT" + nodes_sec.get(x).rank_top_down + " " + prime_provider.getprime(curr_prime)  + " " + nodes_sec.get(x).ori_node_num);
                    nodes_sec.get(x).rank_top_down = (nodes_sec.get(x).rank_top_down%modulo * prime_provider.getprime(curr_prime)%modulo)%modulo;
                }
                else
                {
                    curr_num = nodes_sec.get(x).curr_cnt;
                    writer.println("MULT" + nodes_sec.get(x).rank_top_down + " " + prime_provider.getprime(curr_prime+1)  + " " + nodes_sec.get(x).ori_node_num);
                    nodes_sec.get(x).rank_top_down = (nodes_sec.get(x).rank_top_down%modulo * prime_provider.getprime(++curr_prime)%modulo)%modulo;
                }
            }
            for(int x =0;x<curr_layer.nodes.size();x++)
            {
                writer.println(nodes_sec.get(x).rank_top_down + " " + nodes_sec.get(x).curr_cnt + " " + a  + " " + nodes_sec.get(x).ori_node_num);
                temp +=nodes_sec.get(x).rank_top_down + " " + nodes_sec.get(x).curr_cnt + " " + a + " ";
            }
        }
        return temp;
    }

    //OBSOLETE CODE
    //complexity - 3n^5 + n^2  + c
    //complexity - 3n^5 + n^2  + c
    /*public void process_layer(layer layer)
    {
        //complexity - c
        ArrayList<node> nodes = layer.nodes;
        //complexity - n(3n^4)
        for(int i=0;i<nodes.size();i++)
        {
            //complexity - n^2*n^2
            nodes.get(i).inc_edges.sort(new Comparator<nodeedge>() {
                @Override
                public int compare(nodeedge o1, nodeedge o2) {
                    return (int)((o1.node1.rank_top_down+ prime_provider.getprime(23))*(o1.node1.rank_bottom_up+ prime_provider.getprime(13)) - (o2.node1.rank_top_down+ prime_provider.getprime(23))*(o2.node1.rank_bottom_up+ prime_provider.getprime(13)));
                }
            });
            //complexity - n^2*n^2
            nodes.get(i).int_edges.sort(new Comparator<nodeedge>() {
                @Override
                public int compare(nodeedge o1, nodeedge o2) {
                    return (int)((o1.node1.rank_top_down+ prime_provider.getprime(23))*(o1.node1.rank_bottom_up+ prime_provider.getprime(13)) - (o2.node1.rank_top_down+ prime_provider.getprime(23))*(o2.node1.rank_bottom_up+ prime_provider.getprime(13)));
                }
            });
            //complexity - n^2*n^2
            nodes.get(i).out_edges.sort(new Comparator<nodeedge>() {
                @Override
                public int compare(nodeedge o1, nodeedge o2) {
                    return (int)((o1.node1.rank_top_down+ prime_provider.getprime(23))*(o1.node1.rank_bottom_up+ prime_provider.getprime(13)) - (o2.node1.rank_top_down+ prime_provider.getprime(23))*(o2.node1.rank_bottom_up+ prime_provider.getprime(13)));
                }
            });
        }
        //complexity - n^2
        nodes.sort(new Comparator<node>() {
            @Override
            public int compare(node o1, node o2) {
                return (int)(
                        ((o1.rank_top_down + prime_provider.getprime(23))%modulo*(o1.rank_bottom_up+ prime_provider.getprime(13))%modulo)%modulo -
                                ((o2.rank_top_down+ prime_provider.getprime(23))%modulo*(o2.rank_bottom_up+ prime_provider.getprime(13))%modulo)%modulo
                );
            }
        });
    }*/
}
