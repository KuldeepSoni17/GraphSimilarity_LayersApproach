package graphiso;//CREATED TO MATCH SIMI_DISSSIMI
// TODO: FIRST TOP DOWN THEN BOTTOM UP AND INCLUDE TOP DOWN WHILE DOING BOTTOM UP
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Kuldeep on 2/15/2018.
 */
public class TestClass_3 {
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
    static long modulo = 1000000007;
    public static void main(String s[]) throws Exception
    {
        String filename = "result";
        long glbsimi=0;
        long glbdis=0;
        for(int loop=31;loop<=100;loop++) {
            System.out.println(loop);
            long lclsimi = 0;
            long lcldis = 0;
            for (int loop2 = 0; loop2 < 10000; loop2++) {
                System.out.println(loop2);
                filename = "new_disi_result_" + loop + "_" + loop2 + new Date().getTime() +".txt";
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
                        //        basegraph[i][j] = scanner.nextInt();
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
                input_node_count = base_node_count;
                input_labels = new String[input_node_count];
                input_edge_count = 0;
                inputgraph = new int[input_node_count][input_node_count];
                inputgraph = graphCreator.createGraph(input_node_count);
                input_degreecount = new int[input_node_count];
                for (int i = 0; i < input_node_count; i++) {
                    int currdegree = 0;
                    for (int j = 0; j < input_node_count; j++) {
                        writer.print(inputgraph[i][j] + " ");
                        //inputgraph[i][j] = scanner.nextInt();
                        if (inputgraph[i][j] == 1) {
                            input_edge_count++;
                            currdegree++; }
                        }
                        writer.println();
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
                        matchlabels(base_labels, input_labels);
                    } else {
                        writer.println("NOT SIMILAR MAJOR");
                    }
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
    }

    public static void printit(String s)
    {
        ////writer.println(s);
    }

    //FOR TESTING
    public static int[][] permute_graph(int[][] basegraph, ArrayList<Integer> integers)
    {
        int[][] newgraph  = new int[basegraph.length][basegraph.length];
        for(int i=0;i<basegraph.length;i++)
        {
            for(int j=0;j<basegraph.length;j++)
            {
                newgraph[i][j] = basegraph[integers.get(i)][j];
            }
        }
        int[][] newnewgraph = new int[basegraph.length][basegraph.length];
        for(int i=0;i<basegraph.length;i++)
        {
            for(int j=0;j<basegraph.length;j++)
            {
                newnewgraph[i][j] = newgraph[i][integers.get(j)];
            }
        }
        for(int i=0;i<basegraph.length;i++)
        {
            for(int j=0;j<basegraph.length;j++)
            {
                ////writer.print(newnewgraph[i][j]);
            }
            ////writer.println();
        }
        return newnewgraph;
    }

    //complexity - n^2 + n + c
    public static boolean matchlabels(String[] labels1, String[] labels2)
    {
        boolean blabeltaken[] = new boolean[labels1.length];
        boolean ilabeltaken[] = new boolean[labels2.length];
        int matched =0;
        for(int i=0;i<labels1.length;i++)
        {
            ////writer.println(labels1[i]);
        }
        for(int i=0;i<labels2.length;i++)
        {
            ////writer.println(labels2[i]);
        }
        for(int i=0;i<labels1.length;i++) {
            for (int j = 0; j < labels1.length; j++) {
                if (labels1[j].equals(labels1[i])) {
                    ////writer.println("b " + i + " " + j);
                }
            }
        }
        for(int i=0;i<labels2.length;i++) {
            for (int j = 0; j < labels2.length; j++) {
                if (labels2[j].equals(labels2[i])) {
                    ////writer.println("i " + i + " " + j);
                }
            }
        }
        //complexity - n
        for(int i=0;i<labels1.length;i++)
        {
            blabeltaken[i] = false;
            ilabeltaken[i] = false;
        }
        //complexity - n^2
        for(int i=0;i<labels1.length;i++)
        {
            for(int j=0;j<labels2.length;j++)
            {
                if(!ilabeltaken[j])
                {
                    if(labels2[j].equals(labels1[i]))
                    {
                        ////writer.println(i + " " + j);
                        blabeltaken[i] = true;
                        ilabeltaken[j] = true;
                        matched++;
                        break;
                    }
                }
            }
        }
        for(int i=0;i<blabeltaken.length;i++)
        {
            ////writer.println(blabeltaken[i] + " ");
            if(!blabeltaken[i]){
                //writer.println(labels1[i]);
            }
        }
        //writer.println();
        for(int i=0;i<ilabeltaken.length;i++)
        {
            //writer.println(ilabeltaken[i] + " ");
            if(!ilabeltaken[i]){
                //writer.println(labels2[i]);
            }
        }
        //writer.println();
        if(matched==labels1.length)
        {   simicnt++;
            writer.println("SIMILAR");
        }
        else
        {
            dissimcnt++;
            writer.println("NOT SIMILAR");
        }
        return  true;
    }

    //complexity - 3n^4 + cn^2 + cn + c
    public static String printlabel(label label)
    {
        //complexity - c
        String res = label.layers.size() + " ";
        ////writer.print(label.layers.size() + " ");
        //complexity - 3n^4 + cn^2 + cn
        for(int i=0;i<label.layers.size();i++)
        {
            layer layer = label.layers.get(i);
            // //writer.print(layer.nodes.size() + " " + layer.incoming_edges.size() + " " + layer.outgoing_edges.size() + " " + layer.internal_edges.size() + " ");
            res += layer.nodes.size() + " " + layer.incoming_edges.size() + " " + layer.outgoing_edges.size() + " " + layer.internal_edges.size() + " ";
            //complexity - 3n^3 + cn
            for(int j=0;j<layer.nodes.size();j++)
            {
                //                 //writer.print(layer.nodes.get(j).rank_top_down + " " + layer.nodes.get(j).rank_bottom_up + " ");
                res += layer.nodes.get(j).rank_top_down + " " + layer.nodes.get(j).rank_bottom_up + " ";
                //                  //writer.print(layer.nodes.get(j).inc_edges.size() + " " + layer.nodes.get(j).out_edges.size() + " " + layer.nodes.get(j).int_edges.size() + " ");
                res += layer.nodes.get(j).inc_edges.size() + " " + layer.nodes.get(j).out_edges.size() + " " + layer.nodes.get(j).int_edges.size() + " ";
                for(int k=0;k<layer.nodes.get(j).inc_edges.size();k++)
                {
                    //                            //writer.print(layer.nodes.get(j).inc_edges.get(k).node1.rank_top_down + " " + layer.nodes.get(j).inc_edges.get(k).node1.rank_bottom_up + " ");
                    res += layer.nodes.get(j).inc_edges.get(k).node1.rank_top_down + " " + layer.nodes.get(j).inc_edges.get(k).node1.rank_bottom_up + " ";
                }
                for(int k=0;k<layer.nodes.get(j).out_edges.size();k++)
                {
                    //                              //writer.print(layer.nodes.get(j).out_edges.get(k).node1.rank_top_down + " " + layer.nodes.get(j).out_edges.get(k).node1.rank_bottom_up + " ");
                    res += layer.nodes.get(j).out_edges.get(k).node1.rank_top_down + " " + layer.nodes.get(j).out_edges.get(k).node1.rank_bottom_up + " ";
                }
                for(int k=0;k<layer.nodes.get(j).int_edges.size();k++)
                {
                    //             //writer.print(layer.nodes.get(j).int_edges.get(k).node1.rank_top_down + " " + layer.nodes.get(j).int_edges.get(k).node1.rank_bottom_up + " ");
                    res += layer.nodes.get(j).int_edges.get(k).node1.rank_top_down + " " + layer.nodes.get(j).int_edges.get(k).node1.rank_bottom_up + " ";
                }
            }
        }
        // //writer.println();
        //  //writer.println();
        //  //writer.println();
        return label.layerstr;
    }

    //MAIN FUNCTION FOR CHECKING ISOMORPHISM
    //complexity - n + 2c
    public static boolean checkgraph()
    {
        //match_node
//        boolean result = false;
        return matchnodecount() & matchedgecount() & matchdegreecount();
//        if(matchnodecount() & matchedgecount())
//           return true;
//        else
//            return false;
    }

    //MATCH TOTAL NODE COUNT
    //complexity - n
    public static boolean matchnodecount()
    {
        return base_node_count==input_node_count;
    }

    //MATCH TOTAL EDGE COUNT
    //complexity - c
    public static boolean matchedgecount()
    {
        return base_edge_count==input_edge_count;
    }

    //MATCH VARIETY OF DEGREE COUNTS
    //complexity - n
    public static boolean matchdegreecount()
    {
        boolean result = true;
        for(int i=0;i<base_node_count;i++)
        {
            if(base_degreecount[i]!=input_degreecount[i])
            {
                result = false;
                break;
            }
        }
        return result;
    }

    static class label
    {
        String layerstr = new String();
        ArrayList<layer> layers = new ArrayList<>();

    }


    static class generate_label
    {
        //original matrix
        int[][] ori_graph;
        //current root for tree
        int root_node;
        //list of inflated nodes
        boolean inflated_nodes[];
        int inflated_node_cnt = 0;
        ArrayList<layer> layers = new ArrayList<>();

        public generate_label()
        {}

        //complexity - 12c + 2n^3 + 5n^2 + 7cn + 4c + 2n + 7cn + 4n^2 + 2n^3 + 2c + 2c + n = 20c + 14cn + 3n + 4n^3 + 9n^2
        public label generate(int [][] graph, int n) {

            label label = new label();
            //complexity - 12c
            this.ori_graph = graph;
            root_node = n;

            layers.add(new layer());
            layer first_layer = layers.get(0);
            first_layer.layer_no = 0;

            node node = new node(0,0,n);
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
                layer layer = layers.get(x);
                //writer.println();
                label.layerstr = layer.incoming_edges.size() + " " + layer.outgoing_edges.size() + " " + layer.internal_edges.size();
                //writer.println(layer.incoming_edges.size() + " " + layer.outgoing_edges.size() + " " + layer.internal_edges.size());
            }
            label.layerstr += "TOP DOWN RANKING";
            //writer.println("TOP DOWN RANKING");
            //complexity - 7cn + 4n^2 + 2n^3 + 2c
            label.layerstr += rank_top_down();

            label.layerstr += "BOTTOM UP RANKING";
            //writer.println("BOTTOM UP RANKING");
            //complexity - 2n^3 + 5n^2 + 7cn + 4c + 2n
            label.layerstr += rank_bottom_up();

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

        //complexity - 3n^5 + n^2  + c
        public void process_layer(layer layer)
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
                    return (int)((o1.rank_top_down+ prime_provider.getprime(23))*(o1.rank_bottom_up+ prime_provider.getprime(13)) - (o2.rank_top_down+ prime_provider.getprime(23))*(o2.rank_bottom_up+ prime_provider.getprime(13)));
                }
            });
        }
        ArrayList<Integer> prevlay_node = new ArrayList<>();
        ArrayList<Integer> currlay_node = new ArrayList<>();
        //complexity - 10cn^2 + 2n^5 + 5cn^3 + 2c
        public void inflate_layer(int layer_no)
        {
            //complexity - 2c
            layer layer = new layer();
            layer.layer_no = layer_no;
            node[] nextlay_nodes = new node[base_node_count];
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
                        //writer.println( "ONE " + layers.get(layer_no-1).nodes.get(i).ori_node_num + " " + j + " ");
                        //if(!inflated_nodes[j]) {
                        if(!arraylistcontains(currlay_node,j) && !arraylistcontains(prevlay_node,j)){
                            //writer.println("TWO " + layers.get(layer_no-1).nodes.get(i).ori_node_num + " " + j + " ");
                            //complexity - 10c
                            node node;
                            if(nextlay_nodes[j]!=null)
                            {
                                node = nextlay_nodes[j];
                            }
                            else
                            {
                                node = new node(layer_no - 1, layer.nodes.size(), j);
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
                                    //writer.println("SAME LAYER");

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
                                        //writer.println("NOT REPEAT");
                                        layers.get(layer_no - 1).internal_edges.add(edge);
                                        layers.get(layer_no - 1).nodes.get(k).int_edges.add(new nodeedge(layers.get(layer_no - 1).nodes.get(i)));
                                        layers.get(layer_no - 1).nodes.get(i).int_edges.add(new nodeedge(layers.get(layer_no - 1).nodes.get(k)));
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
            //writer.print("COMPLETED INFLATING" + layers.size() + " ");
        }

        public boolean arraylistcontains(ArrayList<Integer> integers, int chk)
        {
            for(int i=0;i<integers.size();i++)
            {
                if(integers.get(i)==chk)
                {
                    return true;
                }
            }
            return false;
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
                nodes.sort(new Comparator<node>() {
                    @Override
                    public int compare(node o1, node o2) {
                        long o1_cnt = 1, o2_cnt=1;
                        for(int aa=0;aa<o1.out_edges.size();aa++)
                        {
                            //writer.println("MULT_1*((2*3) + (4*5))" + o1_cnt + " " + (o1.out_edges.get(aa).node1.rank_top_down) + " " + prime_provider.getprime(99) + " " + (o1.out_edges.get(aa).node1.rank_bottom_up) + " " + prime_provider.getprime(97));
                            o1_cnt = (o1_cnt%modulo * (((o1.out_edges.get(aa).node1.rank_top_down)%modulo*(prime_provider.getprime(99))%modulo)%modulo + ((o1.out_edges.get(aa).node1.rank_bottom_up)%modulo*(prime_provider.getprime(97))%modulo)%modulo)%modulo)%modulo;
                        }
                        o1.curr_cnt = o1_cnt;
                        for(int aa=0;aa<o2.out_edges.size();aa++)
                        {
                            //writer.println("MULT_1*((2*3) + (4*5))" + o2_cnt + " " + (o2.out_edges.get(aa).node1.rank_top_down) + " " + prime_provider.getprime(99) + " " + (o2.out_edges.get(aa).node1.rank_bottom_up) + " " + prime_provider.getprime(97));
                            o2_cnt = (o2_cnt%modulo * (((o2.out_edges.get(aa).node1.rank_top_down)%modulo*(prime_provider.getprime(99))%modulo)%modulo + ((o2.out_edges.get(aa).node1.rank_bottom_up)%modulo*(prime_provider.getprime(97))%modulo)%modulo)%modulo)%modulo;
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
                    if(curr_num==nodes.get(x).curr_cnt) {nodes.get(x).rank_bottom_up = prime_provider.getprime(curr_prime1);}
                    else {curr_num = nodes.get(x).curr_cnt;nodes.get(x).rank_bottom_up = prime_provider.getprime(++curr_prime1);}}

                for(int x =0;x<curr_layer.nodes.size();x++) {
                    //writer.println(nodes.get(x).rank_bottom_up + " " + nodes.get(x).curr_cnt + " " + a);
                }
                //complexity - n
                //internal edges_bottom up
                for(int zx = 0;zx<nodes.size();zx++)
                {
                    nodes.get(zx).curr_cnt = 1;
                    for(int zy=0;zy<nodes.get(zx).int_edges.size();zy++)
                    {
                        nodes.get(zx).curr_cnt = (nodes.get(zx).curr_cnt%modulo * (((nodes.get(zx).int_edges.get(zy).node1.rank_top_down)%modulo*(prime_provider.getprime(99))%modulo)%modulo + ((nodes.get(zx).int_edges.get(zy).node1.rank_bottom_up)%modulo*(prime_provider.getprime(97))%modulo)%modulo)%modulo)%modulo;
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
                //writer.println("MULT" + prime_provider.getprime(curr_prime1) + " " + nodes_sec.get(0).rank_bottom_up);
                nodes_sec.get(0).rank_bottom_up = ((nodes_sec.get(0).rank_bottom_up)%modulo * (prime_provider.getprime(curr_prime1))%modulo)%modulo;

                //complexity - n
                for(int x =1;x<curr_layer.nodes.size();x++)
                {
                    if(curr_num==nodes_sec.get(x).curr_cnt)
                    {
                        //writer.println("MULT" + prime_provider.getprime(curr_prime1) + " " + nodes_sec.get(x).rank_bottom_up);
                        nodes_sec.get(x).rank_bottom_up = ((nodes_sec.get(x).rank_bottom_up)%modulo * (prime_provider.getprime(curr_prime1))%modulo)%modulo;
                    }
                    else
                    {
                        curr_num = nodes_sec.get(x).curr_cnt;
                        //writer.println("MULT" + prime_provider.getprime(curr_prime1+1) + " " + nodes_sec.get(x).rank_bottom_up);
                        nodes_sec.get(x).rank_bottom_up = ((nodes_sec.get(x).rank_bottom_up)%modulo * (prime_provider.getprime(++curr_prime1))%modulo)%modulo;
                    }
                }
                for(int x =0;x<curr_layer.nodes.size();x++)
                {
                    //writer.println(nodes_sec.get(x).rank_bottom_up + " " + nodes_sec.get(x).curr_cnt + " " + a);
                    temp += nodes_sec.get(x).rank_bottom_up + " " + nodes_sec.get(x).curr_cnt + " " + a;
                }

                for(int zx = 0;zx<nodes_sec.size();zx++)
                {
                    nodes_sec.get(zx).curr_cnt = 1;
                    for(int zy=0;zy<nodes_sec.get(zx).inc_edges.size();zy++)
                    {
                        //writer.println("MULT" + nodes_sec.get(zx).inc_edges.get(zy).node1.rank_top_down + " " + nodes_sec.get(zx).curr_cnt);
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
                //writer.println("MULT" + prime_provider.getprime(curr_prime1) + " " + nodes_sec.get(0).rank_bottom_up);
                nodes_sec.get(0).rank_bottom_up = (nodes_sec.get(0).rank_bottom_up%modulo* prime_provider.getprime(curr_prime1)%modulo);

                //complexity - n
                for(int x =1;x<curr_layer.nodes.size();x++)
                {
                    if(curr_num==nodes_sec.get(x).curr_cnt)
                    {
                        //writer.println("MULT" + prime_provider.getprime(curr_prime1) + " " + nodes_sec.get(x).rank_bottom_up);
                        nodes_sec.get(x).rank_bottom_up = (nodes_sec.get(x).rank_bottom_up%modulo * prime_provider.getprime(curr_prime1)%modulo)%modulo;
                    }
                    else
                    {
                        curr_num = nodes_sec.get(x).curr_cnt;
                        //writer.println("MULT" + prime_provider.getprime(curr_prime1+1) + " " + nodes_sec.get(x).rank_bottom_up);
                        nodes_sec.get(x).rank_bottom_up = (nodes_sec.get(x).rank_bottom_up%modulo * prime_provider.getprime(++curr_prime1)%modulo)%modulo;
                    }
                }
                for(int x =0;x<curr_layer.nodes.size();x++)
                {
                    //writer.println(nodes_sec.get(x).rank_bottom_up + " " + nodes_sec.get(x).curr_cnt + " " + a);
                    temp += nodes_sec.get(x).rank_bottom_up + " " + nodes_sec.get(x).curr_cnt + " " + a;
                }
            }
            return temp;
        }

        //complexity - n + c + n^2 + 3c + n = n^2 + 4c + 2n
        public String rank_bottom_up_bottom()
        {
            String temp = new String();
            //complexity - c
            layer bottom_layer = layers.get(layers.size()-1);
            //complexity - n
            ArrayList<node> nodes = (ArrayList<node>)bottom_layer.nodes.clone();

            for(int x =0;x<bottom_layer.nodes.size();x++)
            {   nodes.get(x).curr_cnt = 1;
                for(int y=0;y<nodes.get(x).int_edges.size();y++)
                {    //writer.println("MULT" + nodes.get(x).int_edges.get(y).node1.rank_top_down + " " + nodes.get(x).curr_cnt);
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

            //IF SAME
            for(int x =0;x<bottom_layer.nodes.size();x++)
            {   nodes.get(x).curr_cnt = 1;
                for(int y=0;y<nodes.get(x).inc_edges.size();y++)
                {
                    //writer.println("MULT" + nodes.get(x).inc_edges.get(y).node1.rank_top_down + " " + nodes.get(x).curr_cnt);
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
                    //writer.println("MULT" + prime_provider.getprime(curr_prime_index) + " " + nodes.get(x).rank_bottom_up);
                    nodes.get(x).rank_bottom_up = (nodes.get(x).rank_bottom_up%modulo * prime_provider.getprime(curr_prime_index)%modulo)%modulo;
                }
                else
                {
                    curr_num = (int)nodes.get(x).curr_cnt;
                    //writer.println("MULT" + prime_provider.getprime(curr_prime_index+1) + " " + nodes.get(x).rank_bottom_up);
                    nodes.get(x).rank_bottom_up = ( nodes.get(x).rank_bottom_up%modulo *prime_provider.getprime(++curr_prime_index)%modulo)%modulo;
                }
            }

            for(int x =0;x<bottom_layer.nodes.size();x++)
            {
                //writer.println(nodes.get(x).rank_bottom_up + " " + nodes.get(x).curr_cnt + " " + (layers.size()-1));
                temp += nodes.get(x).rank_bottom_up + " " + nodes.get(x).curr_cnt + " " + (layers.size()-1);
            }

            return temp;

        }

        //complexity - n(c + n + n^2 + 3c + n + n + n^2 + 3c + n) + 2c = 7cn + 4n^2 + 2n^3 + 2c
        public String rank_top_down()
        {//complexity - 2c
            rank_top_down_top();
            String temp = new String();
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
                        //writer.println("MULT" + nodes.get(ab).inc_edges.get(ac).node1.rank_top_down + " " + nodes.get(ab).curr_cnt);
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
                    //writer.println(nodes.get(x).rank_top_down + " " + nodes.get(x).curr_cnt + " " + a);
                    temp += nodes.get(x).rank_top_down + " " + nodes.get(x).curr_cnt + " " + a;
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
                    for(int ac=0;ac<nodes_sec.get(ab).int_edges.size();ac++)
                    {   //writer.println("MULT" + nodes_sec.get(ab).int_edges.get(ac).node1.rank_top_down + " " + nodes_sec.get(ab).curr_cnt);
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

                //writer.println("MULT" + nodes_sec.get(0).rank_top_down + " " + prime_provider.getprime(curr_prime));
                nodes_sec.get(0).rank_top_down = (nodes_sec.get(0).rank_top_down%modulo * prime_provider.getprime(curr_prime)%modulo)%modulo;

                //complexity - n
                for(int x =1;x<curr_layer.nodes.size();x++)
                {
                    if(curr_num==nodes_sec.get(x).curr_cnt)
                    {
                        //writer.println("MULT" + nodes_sec.get(x).rank_top_down + " " + prime_provider.getprime(curr_prime));
                        nodes_sec.get(x).rank_top_down = (nodes_sec.get(x).rank_top_down%modulo * prime_provider.getprime(curr_prime)%modulo)%modulo;
                    }
                    else
                    {
                        curr_num = nodes_sec.get(x).curr_cnt;
                        //writer.println("MULT" + nodes_sec.get(x).rank_top_down + " " + prime_provider.getprime(curr_prime+1));
                        nodes_sec.get(x).rank_top_down = (nodes_sec.get(x).rank_top_down%modulo * prime_provider.getprime(++curr_prime)%modulo)%modulo;
                    }
                }
                for(int x =0;x<curr_layer.nodes.size();x++)
                {
                    //writer.println(nodes_sec.get(x).rank_top_down + " " + nodes_sec.get(x).curr_cnt + " " + a);
                    temp +=nodes_sec.get(x).rank_top_down + " " + nodes_sec.get(x).curr_cnt + " " + a;
                }
            }
            return temp;
        }

        //complexity - 2c units
        public void rank_top_down_top()
        {
            layer top_layer = layers.get(0);
            top_layer.nodes.get(0).rank_top_down = prime_provider.getprime(0);
        }
    }

    //node class
    static class node
    {
        int pos_row;
        int pos_col;
        int ori_node_num;
        long rank_bottom_up;
        long rank_top_down;
        long rank_bottom_up_td;
        long rank_top_down_bu;
        int layer_no;
        long curr_cnt;
        ArrayList<nodeedge> int_edges = new ArrayList<>();
        ArrayList<nodeedge> inc_edges = new ArrayList<>();
        ArrayList<nodeedge> out_edges = new ArrayList<>();

        node(int pos_row, int pos_col, int ori_node_num)
        {
            this.pos_col = pos_col;
            this.pos_row = pos_row;
            this.ori_node_num = ori_node_num;
        }
    }

    //edge class
    static class edge{
        int row1;
        int row2;
        int col1;
        int col2;
        node node1;
        node node2;
        edge(int row1, int col1, int row2, int col2, node node1, node node2)
        {
            this.row1 = row1;
            this.col1 = col1;
            this.row2 = row2;
            this.col2 = col2;
            this.node1 = node1;
            this.node2 = node2;

        }
    }

    static class nodeedge{
        node node1;
        nodeedge(node node1)
        {
            this.node1 = node1;
        }
    }

    //layer class
    static class layer{
        int layer_no;
        ArrayList<edge> incoming_edges = new ArrayList<>();
        ArrayList<edge> outgoing_edges = new ArrayList<>();
        ArrayList<edge> internal_edges = new ArrayList<>();
        ArrayList<node> nodes = new ArrayList<>();
    }

    //provides prime
    //function : fillprimes() - complexiety - 1000
    //function : getprime() - complexiety - 1
    static class prime_provider
    {
        static ArrayList<Integer> Primes = new ArrayList<Integer>();
        public static void fillprimes()
        {
            Primes.add(2);
            Primes.add(3);
            Primes.add(5);
            Primes.add(7);
            Primes.add(11);
            Primes.add(13);
            Primes.add(17);
            Primes.add(19);
            Primes.add(23);
            Primes.add(29);
            Primes.add(31);
            Primes.add(37);
            Primes.add(41);
            Primes.add(43);
            Primes.add(47);
            Primes.add(53);
            Primes.add(59);
            Primes.add(61);
            Primes.add(67);
            Primes.add(71);
            Primes.add(73);
            Primes.add(79);
            Primes.add(83);
            Primes.add(89);
            Primes.add(97);
            Primes.add(101);
            Primes.add(103);
            Primes.add(107);
            Primes.add(109);
            Primes.add(113);
            Primes.add(127);
            Primes.add(131);
            Primes.add(137);
            Primes.add(139);
            Primes.add(149);
            Primes.add(151);
            Primes.add(157);
            Primes.add(163);
            Primes.add(167);
            Primes.add(173);
            Primes.add(179);
            Primes.add(181);
            Primes.add(191);
            Primes.add(193);
            Primes.add(197);
            Primes.add(199);
            Primes.add(211);
            Primes.add(223);
            Primes.add(227);
            Primes.add(229);
            Primes.add(233);
            Primes.add(239);
            Primes.add(241);
            Primes.add(251);
            Primes.add(257);
            Primes.add(263);
            Primes.add(269);
            Primes.add(271);
            Primes.add(277);
            Primes.add(281);
            Primes.add(283);
            Primes.add(293);
            Primes.add(307);
            Primes.add(311);
            Primes.add(313);
            Primes.add(317);
            Primes.add(331);
            Primes.add(337);
            Primes.add(347);
            Primes.add(349);
            Primes.add(353);
            Primes.add(359);
            Primes.add(367);
            Primes.add(373);
            Primes.add(379);
            Primes.add(383);
            Primes.add(389);
            Primes.add(397);
            Primes.add(401);
            Primes.add(409);
            Primes.add(419);
            Primes.add(421);
            Primes.add(431);
            Primes.add(433);
            Primes.add(439);
            Primes.add(443);
            Primes.add(449);
            Primes.add(457);
            Primes.add(461);
            Primes.add(463);
            Primes.add(467);
            Primes.add(479);
            Primes.add(487);
            Primes.add(491);
            Primes.add(499);
            Primes.add(503);
            Primes.add(509);
            Primes.add(521);
            Primes.add(523);
            Primes.add(541);
            Primes.add(547);
            Primes.add(557);
            Primes.add(563);
            Primes.add(569);
            Primes.add(571);
            Primes.add(577);
            Primes.add(587);
            Primes.add(593);
            Primes.add(599);
            Primes.add(601);
            Primes.add(607);
            Primes.add(613);
            Primes.add(617);
            Primes.add(619);
            Primes.add(631);
            Primes.add(641);
            Primes.add(643);
            Primes.add(647);
            Primes.add(653);
            Primes.add(659);
            Primes.add(661);
            Primes.add(673);
            Primes.add(677);
            Primes.add(683);
            Primes.add(691);
            Primes.add(701);
            Primes.add(709);
            Primes.add(719);
            Primes.add(727);
            Primes.add(733);
            Primes.add(739);
            Primes.add(743);
            Primes.add(751);
            Primes.add(757);
            Primes.add(761);
            Primes.add(769);
            Primes.add(773);
            Primes.add(787);
            Primes.add(797);
            Primes.add(809);
            Primes.add(811);
            Primes.add(821);
            Primes.add(823);
            Primes.add(827);
            Primes.add(829);
            Primes.add(839);
            Primes.add(853);
            Primes.add(857);
            Primes.add(859);
            Primes.add(863);
            Primes.add(877);
            Primes.add(881);
            Primes.add(883);
            Primes.add(887);
            Primes.add(907);
            Primes.add(911);
            Primes.add(919);
            Primes.add(929);
            Primes.add(937);
            Primes.add(941);
            Primes.add(947);
            Primes.add(953);
            Primes.add(967);
            Primes.add(971);
            Primes.add(977);
            Primes.add(983);
            Primes.add(991);
            Primes.add(997);
            Primes.add(1009);
            Primes.add(1013);
            Primes.add(1019);
            Primes.add(1021);
            Primes.add(1031);
            Primes.add(1033);
            Primes.add(1039);
            Primes.add(1049);
            Primes.add(1051);
            Primes.add(1061);
            Primes.add(1063);
            Primes.add(1069);
            Primes.add(1087);
            Primes.add(1091);
            Primes.add(1093);
            Primes.add(1097);
            Primes.add(1103);
            Primes.add(1109);
            Primes.add(1117);
            Primes.add(1123);
            Primes.add(1129);
            Primes.add(1151);
            Primes.add(1153);
            Primes.add(1163);
            Primes.add(1171);
            Primes.add(1181);
            Primes.add(1187);
            Primes.add(1193);
            Primes.add(1201);
            Primes.add(1213);
            Primes.add(1217);
            Primes.add(1223);
            Primes.add(1229);
            Primes.add(1231);
            Primes.add(1237);
            Primes.add(1249);
            Primes.add(1259);
            Primes.add(1277);
            Primes.add(1279);
            Primes.add(1283);
            Primes.add(1289);
            Primes.add(1291);
            Primes.add(1297);
            Primes.add(1301);
            Primes.add(1303);
            Primes.add(1307);
            Primes.add(1319);
            Primes.add(1321);
            Primes.add(1327);
            Primes.add(1361);
            Primes.add(1367);
            Primes.add(1373);
            Primes.add(1381);
            Primes.add(1399);
            Primes.add(1409);
            Primes.add(1423);
            Primes.add(1427);
            Primes.add(1429);
            Primes.add(1433);
            Primes.add(1439);
            Primes.add(1447);
            Primes.add(1451);
            Primes.add(1453);
            Primes.add(1459);
            Primes.add(1471);
            Primes.add(1481);
            Primes.add(1483);
            Primes.add(1487);
            Primes.add(1489);
            Primes.add(1493);
            Primes.add(1499);
            Primes.add(1511);
            Primes.add(1523);
            Primes.add(1531);
            Primes.add(1543);
            Primes.add(1549);
            Primes.add(1553);
            Primes.add(1559);
            Primes.add(1567);
            Primes.add(1571);
            Primes.add(1579);
            Primes.add(1583);
            Primes.add(1597);
            Primes.add(1601);
            Primes.add(1607);
            Primes.add(1609);
            Primes.add(1613);
            Primes.add(1619);
            Primes.add(1621);
            Primes.add(1627);
            Primes.add(1637);
            Primes.add(1657);
            Primes.add(1663);
            Primes.add(1667);
            Primes.add(1669);
            Primes.add(1693);
            Primes.add(1697);
            Primes.add(1699);
            Primes.add(1709);
            Primes.add(1721);
            Primes.add(1723);
            Primes.add(1733);
            Primes.add(1741);
            Primes.add(1747);
            Primes.add(1753);
            Primes.add(1759);
            Primes.add(1777);
            Primes.add(1783);
            Primes.add(1787);
            Primes.add(1789);
            Primes.add(1801);
            Primes.add(1811);
            Primes.add(1823);
            Primes.add(1831);
            Primes.add(1847);
            Primes.add(1861);
            Primes.add(1867);
            Primes.add(1871);
            Primes.add(1873);
            Primes.add(1877);
            Primes.add(1879);
            Primes.add(1889);
            Primes.add(1901);
            Primes.add(1907);
            Primes.add(1913);
            Primes.add(1931);
            Primes.add(1933);
            Primes.add(1949);
            Primes.add(1951);
            Primes.add(1973);
            Primes.add(1979);
            Primes.add(1987);
            Primes.add(1993);
            Primes.add(1997);
            Primes.add(1999);
            Primes.add(2003);
            Primes.add(2011);
            Primes.add(2017);
            Primes.add(2027);
            Primes.add(2029);
            Primes.add(2039);
            Primes.add(2053);
            Primes.add(2063);
            Primes.add(2069);
            Primes.add(2081);
            Primes.add(2083);
            Primes.add(2087);
            Primes.add(2089);
            Primes.add(2099);
            Primes.add(2111);
            Primes.add(2113);
            Primes.add(2129);
            Primes.add(2131);
            Primes.add(2137);
            Primes.add(2141);
            Primes.add(2143);
            Primes.add(2153);
            Primes.add(2161);
            Primes.add(2179);
            Primes.add(2203);
            Primes.add(2207);
            Primes.add(2213);
            Primes.add(2221);
            Primes.add(2237);
            Primes.add(2239);
            Primes.add(2243);
            Primes.add(2251);
            Primes.add(2267);
            Primes.add(2269);
            Primes.add(2273);
            Primes.add(2281);
            Primes.add(2287);
            Primes.add(2293);
            Primes.add(2297);
            Primes.add(2309);
            Primes.add(2311);
            Primes.add(2333);
            Primes.add(2339);
            Primes.add(2341);
            Primes.add(2347);
            Primes.add(2351);
            Primes.add(2357);
            Primes.add(2371);
            Primes.add(2377);
            Primes.add(2381);
            Primes.add(2383);
            Primes.add(2389);
            Primes.add(2393);
            Primes.add(2399);
            Primes.add(2411);
            Primes.add(2417);
            Primes.add(2423);
            Primes.add(2437);
            Primes.add(2441);
            Primes.add(2447);
            Primes.add(2459);
            Primes.add(2467);
            Primes.add(2473);
            Primes.add(2477);
            Primes.add(2503);
            Primes.add(2521);
            Primes.add(2531);
            Primes.add(2539);
            Primes.add(2543);
            Primes.add(2549);
            Primes.add(2551);
            Primes.add(2557);
            Primes.add(2579);
            Primes.add(2591);
            Primes.add(2593);
            Primes.add(2609);
            Primes.add(2617);
            Primes.add(2621);
            Primes.add(2633);
            Primes.add(2647);
            Primes.add(2657);
            Primes.add(2659);
            Primes.add(2663);
            Primes.add(2671);
            Primes.add(2677);
            Primes.add(2683);
            Primes.add(2687);
            Primes.add(2689);
            Primes.add(2693);
            Primes.add(2699);
            Primes.add(2707);
            Primes.add(2711);
            Primes.add(2713);
            Primes.add(2719);
            Primes.add(2729);
            Primes.add(2731);
            Primes.add(2741);
            Primes.add(2749);
            Primes.add(2753);
            Primes.add(2767);
            Primes.add(2777);
            Primes.add(2789);
            Primes.add(2791);
            Primes.add(2797);
            Primes.add(2801);
            Primes.add(2803);
            Primes.add(2819);
            Primes.add(2833);
            Primes.add(2837);
            Primes.add(2843);
            Primes.add(2851);
            Primes.add(2857);
            Primes.add(2861);
            Primes.add(2879);
            Primes.add(2887);
            Primes.add(2897);
            Primes.add(2903);
            Primes.add(2909);
            Primes.add(2917);
            Primes.add(2927);
            Primes.add(2939);
            Primes.add(2953);
            Primes.add(2957);
            Primes.add(2963);
            Primes.add(2969);
            Primes.add(2971);
            Primes.add(2999);
            Primes.add(3001);
            Primes.add(3011);
            Primes.add(3019);
            Primes.add(3023);
            Primes.add(3037);
            Primes.add(3041);
            Primes.add(3049);
            Primes.add(3061);
            Primes.add(3067);
            Primes.add(3079);
            Primes.add(3083);
            Primes.add(3089);
            Primes.add(3109);
            Primes.add(3119);
            Primes.add(3121);
            Primes.add(3137);
            Primes.add(3163);
            Primes.add(3167);
            Primes.add(3169);
            Primes.add(3181);
            Primes.add(3187);
            Primes.add(3191);
            Primes.add(3203);
            Primes.add(3209);
            Primes.add(3217);
            Primes.add(3221);
            Primes.add(3229);
            Primes.add(3251);
            Primes.add(3253);
            Primes.add(3257);
            Primes.add(3259);
            Primes.add(3271);
            Primes.add(3299);
            Primes.add(3301);
            Primes.add(3307);
            Primes.add(3313);
            Primes.add(3319);
            Primes.add(3323);
            Primes.add(3329);
            Primes.add(3331);
            Primes.add(3343);
            Primes.add(3347);
            Primes.add(3359);
            Primes.add(3361);
            Primes.add(3371);
            Primes.add(3373);
            Primes.add(3389);
            Primes.add(3391);
            Primes.add(3407);
            Primes.add(3413);
            Primes.add(3433);
            Primes.add(3449);
            Primes.add(3457);
            Primes.add(3461);
            Primes.add(3463);
            Primes.add(3467);
            Primes.add(3469);
            Primes.add(3491);
            Primes.add(3499);
            Primes.add(3511);
            Primes.add(3517);
            Primes.add(3527);
            Primes.add(3529);
            Primes.add(3533);
            Primes.add(3539);
            Primes.add(3541);
            Primes.add(3547);
            Primes.add(3557);
            Primes.add(3559);
            Primes.add(3571);
            Primes.add(3581);
            Primes.add(3583);
            Primes.add(3593);
            Primes.add(3607);
            Primes.add(3613);
            Primes.add(3617);
            Primes.add(3623);
            Primes.add(3631);
            Primes.add(3637);
            Primes.add(3643);
            Primes.add(3659);
            Primes.add(3671);
            Primes.add(3673);
            Primes.add(3677);
            Primes.add(3691);
            Primes.add(3697);
            Primes.add(3701);
            Primes.add(3709);
            Primes.add(3719);
            Primes.add(3727);
            Primes.add(3733);
            Primes.add(3739);
            Primes.add(3761);
            Primes.add(3767);
            Primes.add(3769);
            Primes.add(3779);
            Primes.add(3793);
            Primes.add(3797);
            Primes.add(3803);
            Primes.add(3821);
            Primes.add(3823);
            Primes.add(3833);
            Primes.add(3847);
            Primes.add(3851);
            Primes.add(3853);
            Primes.add(3863);
            Primes.add(3877);
            Primes.add(3881);
            Primes.add(3889);
            Primes.add(3907);
            Primes.add(3911);
            Primes.add(3917);
            Primes.add(3919);
            Primes.add(3923);
            Primes.add(3929);
            Primes.add(3931);
            Primes.add(3943);
            Primes.add(3947);
            Primes.add(3967);
            Primes.add(3989);
            Primes.add(4001);
            Primes.add(4003);
            Primes.add(4007);
            Primes.add(4013);
            Primes.add(4019);
            Primes.add(4021);
            Primes.add(4027);
            Primes.add(4049);
            Primes.add(4051);
            Primes.add(4057);
            Primes.add(4073);
            Primes.add(4079);
            Primes.add(4091);
            Primes.add(4093);
            Primes.add(4099);
            Primes.add(4111);
            Primes.add(4127);
            Primes.add(4129);
            Primes.add(4133);
            Primes.add(4139);
            Primes.add(4153);
            Primes.add(4157);
            Primes.add(4159);
            Primes.add(4177);
            Primes.add(4201);
            Primes.add(4211);
            Primes.add(4217);
            Primes.add(4219);
            Primes.add(4229);
            Primes.add(4231);
            Primes.add(4241);
            Primes.add(4243);
            Primes.add(4253);
            Primes.add(4259);
            Primes.add(4261);
            Primes.add(4271);
            Primes.add(4273);
            Primes.add(4283);
            Primes.add(4289);
            Primes.add(4297);
            Primes.add(4327);
            Primes.add(4337);
            Primes.add(4339);
            Primes.add(4349);
            Primes.add(4357);
            Primes.add(4363);
            Primes.add(4373);
            Primes.add(4391);
            Primes.add(4397);
            Primes.add(4409);
            Primes.add(4421);
            Primes.add(4423);
            Primes.add(4441);
            Primes.add(4447);
            Primes.add(4451);
            Primes.add(4457);
            Primes.add(4463);
            Primes.add(4481);
            Primes.add(4483);
            Primes.add(4493);
            Primes.add(4507);
            Primes.add(4513);
            Primes.add(4517);
            Primes.add(4519);
            Primes.add(4523);
            Primes.add(4547);
            Primes.add(4549);
            Primes.add(4561);
            Primes.add(4567);
            Primes.add(4583);
            Primes.add(4591);
            Primes.add(4597);
            Primes.add(4603);
            Primes.add(4621);
            Primes.add(4637);
            Primes.add(4639);
            Primes.add(4643);
            Primes.add(4649);
            Primes.add(4651);
            Primes.add(4657);
            Primes.add(4663);
            Primes.add(4673);
            Primes.add(4679);
            Primes.add(4691);
            Primes.add(4703);
            Primes.add(4721);
            Primes.add(4723);
            Primes.add(4729);
            Primes.add(4733);
            Primes.add(4751);
            Primes.add(4759);
            Primes.add(4783);
            Primes.add(4787);
            Primes.add(4789);
            Primes.add(4793);
            Primes.add(4799);
            Primes.add(4801);
            Primes.add(4813);
            Primes.add(4817);
            Primes.add(4831);
            Primes.add(4861);
            Primes.add(4871);
            Primes.add(4877);
            Primes.add(4889);
            Primes.add(4903);
            Primes.add(4909);
            Primes.add(4919);
            Primes.add(4931);
            Primes.add(4933);
            Primes.add(4937);
            Primes.add(4943);
            Primes.add(4951);
            Primes.add(4957);
            Primes.add(4967);
            Primes.add(4969);
            Primes.add(4973);
            Primes.add(4987);
            Primes.add(4993);
            Primes.add(4999);
            Primes.add(5003);
            Primes.add(5009);
            Primes.add(5011);
            Primes.add(5021);
            Primes.add(5023);
            Primes.add(5039);
            Primes.add(5051);
            Primes.add(5059);
            Primes.add(5077);
            Primes.add(5081);
            Primes.add(5087);
            Primes.add(5099);
            Primes.add(5101);
            Primes.add(5107);
            Primes.add(5113);
            Primes.add(5119);
            Primes.add(5147);
            Primes.add(5153);
            Primes.add(5167);
            Primes.add(5171);
            Primes.add(5179);
            Primes.add(5189);
            Primes.add(5197);
            Primes.add(5209);
            Primes.add(5227);
            Primes.add(5231);
            Primes.add(5233);
            Primes.add(5237);
            Primes.add(5261);
            Primes.add(5273);
            Primes.add(5279);
            Primes.add(5281);
            Primes.add(5297);
            Primes.add(5303);
            Primes.add(5309);
            Primes.add(5323);
            Primes.add(5333);
            Primes.add(5347);
            Primes.add(5351);
            Primes.add(5381);
            Primes.add(5387);
            Primes.add(5393);
            Primes.add(5399);
            Primes.add(5407);
            Primes.add(5413);
            Primes.add(5417);
            Primes.add(5419);
            Primes.add(5431);
            Primes.add(5437);
            Primes.add(5441);
            Primes.add(5443);
            Primes.add(5449);
            Primes.add(5471);
            Primes.add(5477);
            Primes.add(5479);
            Primes.add(5483);
            Primes.add(5501);
            Primes.add(5503);
            Primes.add(5507);
            Primes.add(5519);
            Primes.add(5521);
            Primes.add(5527);
            Primes.add(5531);
            Primes.add(5557);
            Primes.add(5563);
            Primes.add(5569);
            Primes.add(5573);
            Primes.add(5581);
            Primes.add(5591);
            Primes.add(5623);
            Primes.add(5639);
            Primes.add(5641);
            Primes.add(5647);
            Primes.add(5651);
            Primes.add(5653);
            Primes.add(5657);
            Primes.add(5659);
            Primes.add(5669);
            Primes.add(5683);
            Primes.add(5689);
            Primes.add(5693);
            Primes.add(5701);
            Primes.add(5711);
            Primes.add(5717);
            Primes.add(5737);
            Primes.add(5741);
            Primes.add(5743);
            Primes.add(5749);
            Primes.add(5779);
            Primes.add(5783);
            Primes.add(5791);
            Primes.add(5801);
            Primes.add(5807);
            Primes.add(5813);
            Primes.add(5821);
            Primes.add(5827);
            Primes.add(5839);
            Primes.add(5843);
            Primes.add(5849);
            Primes.add(5851);
            Primes.add(5857);
            Primes.add(5861);
            Primes.add(5867);
            Primes.add(5869);
            Primes.add(5879);
            Primes.add(5881);
            Primes.add(5897);
            Primes.add(5903);
            Primes.add(5923);
            Primes.add(5927);
            Primes.add(5939);
            Primes.add(5953);
            Primes.add(5981);
            Primes.add(5987);
            Primes.add(6007);
            Primes.add(6011);
            Primes.add(6029);
            Primes.add(6037);
            Primes.add(6043);
            Primes.add(6047);
            Primes.add(6053);
            Primes.add(6067);
            Primes.add(6073);
            Primes.add(6079);
            Primes.add(6089);
            Primes.add(6091);
            Primes.add(6101);
            Primes.add(6113);
            Primes.add(6121);
            Primes.add(6131);
            Primes.add(6133);
            Primes.add(6143);
            Primes.add(6151);
            Primes.add(6163);
            Primes.add(6173);
            Primes.add(6197);
            Primes.add(6199);
            Primes.add(6203);
            Primes.add(6211);
            Primes.add(6217);
            Primes.add(6221);
            Primes.add(6229);
            Primes.add(6247);
            Primes.add(6257);
            Primes.add(6263);
            Primes.add(6269);
            Primes.add(6271);
            Primes.add(6277);
            Primes.add(6287);
            Primes.add(6299);
            Primes.add(6301);
            Primes.add(6311);
            Primes.add(6317);
            Primes.add(6323);
            Primes.add(6329);
            Primes.add(6337);
            Primes.add(6343);
            Primes.add(6353);
            Primes.add(6359);
            Primes.add(6361);
            Primes.add(6367);
            Primes.add(6373);
            Primes.add(6379);
            Primes.add(6389);
            Primes.add(6397);
            Primes.add(6421);
            Primes.add(6427);
            Primes.add(6449);
            Primes.add(6451);
            Primes.add(6469);
            Primes.add(6473);
            Primes.add(6481);
            Primes.add(6491);
            Primes.add(6521);
            Primes.add(6529);
            Primes.add(6547);
            Primes.add(6551);
            Primes.add(6553);
            Primes.add(6563);
            Primes.add(6569);
            Primes.add(6571);
            Primes.add(6577);
            Primes.add(6581);
            Primes.add(6599);
            Primes.add(6607);
            Primes.add(6619);
            Primes.add(6637);
            Primes.add(6653);
            Primes.add(6659);
            Primes.add(6661);
            Primes.add(6673);
            Primes.add(6679);
            Primes.add(6689);
            Primes.add(6691);
            Primes.add(6701);
            Primes.add(6703);
            Primes.add(6709);
            Primes.add(6719);
            Primes.add(6733);
            Primes.add(6737);
            Primes.add(6761);
            Primes.add(6763);
            Primes.add(6779);
            Primes.add(6781);
            Primes.add(6791);
            Primes.add(6793);
            Primes.add(6803);
            Primes.add(6823);
            Primes.add(6827);
            Primes.add(6829);
            Primes.add(6833);
            Primes.add(6841);
            Primes.add(6857);
            Primes.add(6863);
            Primes.add(6869);
            Primes.add(6871);
            Primes.add(6883);
            Primes.add(6899);
            Primes.add(6907);
            Primes.add(6911);
            Primes.add(6917);
            Primes.add(6947);
            Primes.add(6949);
            Primes.add(6959);
            Primes.add(6961);
            Primes.add(6967);
            Primes.add(6971);
            Primes.add(6977);
            Primes.add(6983);
            Primes.add(6991);
            Primes.add(6997);
            Primes.add(7001);
            Primes.add(7013);
            Primes.add(7019);
            Primes.add(7027);
            Primes.add(7039);
            Primes.add(7043);
            Primes.add(7057);
            Primes.add(7069);
            Primes.add(7079);
            Primes.add(7103);
            Primes.add(7109);
            Primes.add(7121);
            Primes.add(7127);
            Primes.add(7129);
            Primes.add(7151);
            Primes.add(7159);
            Primes.add(7177);
            Primes.add(7187);
            Primes.add(7193);
            Primes.add(7207);
            Primes.add(7211);
            Primes.add(7213);
            Primes.add(7219);
            Primes.add(7229);
            Primes.add(7237);
            Primes.add(7243);
            Primes.add(7247);
            Primes.add(7253);
            Primes.add(7283);
            Primes.add(7297);
            Primes.add(7307);
            Primes.add(7309);
            Primes.add(7321);
            Primes.add(7331);
            Primes.add(7333);
            Primes.add(7349);
            Primes.add(7351);
            Primes.add(7369);
            Primes.add(7393);
            Primes.add(7411);
            Primes.add(7417);
            Primes.add(7433);
            Primes.add(7451);
            Primes.add(7457);
            Primes.add(7459);
            Primes.add(7477);
            Primes.add(7481);
            Primes.add(7487);
            Primes.add(7489);
            Primes.add(7499);
            Primes.add(7507);
            Primes.add(7517);
            Primes.add(7523);
            Primes.add(7529);
            Primes.add(7537);
            Primes.add(7541);
            Primes.add(7547);
            Primes.add(7549);
            Primes.add(7559);
            Primes.add(7561);
            Primes.add(7573);
            Primes.add(7577);
            Primes.add(7583);
            Primes.add(7589);
            Primes.add(7591);
            Primes.add(7603);
            Primes.add(7607);
            Primes.add(7621);
            Primes.add(7639);
            Primes.add(7643);
            Primes.add(7649);
            Primes.add(7669);
            Primes.add(7673);
            Primes.add(7681);
            Primes.add(7687);
            Primes.add(7691);
            Primes.add(7699);
            Primes.add(7703);
            Primes.add(7717);
            Primes.add(7723);
            Primes.add(7727);
            Primes.add(7741);
            Primes.add(7753);
            Primes.add(7757);
            Primes.add(7759);
            Primes.add(7789);
            Primes.add(7793);
            Primes.add(7817);
            Primes.add(7823);
            Primes.add(7829);
            Primes.add(7841);
            Primes.add(7853);
            Primes.add(7867);
            Primes.add(7873);
            Primes.add(7877);
            Primes.add(7879);
            Primes.add(7883);
            Primes.add(7901);
            Primes.add(7907);
            Primes.add(7919);
        }

        static public int getprime(int index)
        {
            return Primes.get(index);
        }
    }

    //for testing
    //provides permutation of number series
    public static ArrayList<Integer> permute_generator(int n)
    {
        ArrayList<Integer> integers = new ArrayList<>();
        boolean taken[] = new boolean[n];
        Random random = new Random();
        for(int i=0;i<n;i++)
        {
            int a = random.nextInt(n);
            if(!taken[a])
            {
                integers.add(a);
                taken[a] = true;
            }
            else
            {i--;}
        }
//        integers.clear();

        writer.println();
        for(int i=0;i<n;i++)
        {
            //          integers.add(new Scanner(System.in).nextInt());
            writer.print(integers.get(i) + " ");
        }
        writer.println();
        return integers;
    }
}
