package graphiso;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.*;

import graphiso.DataStructure.label;
import graphiso.DataStructure.layer;

public class Helper {

    //complexity - n + 2c
    public static boolean checkgraph(int base_node_count, int input_node_count, int base_edge_count, int input_edge_count, int[] base_degreecount, int[] input_degreecount)
    {
        return matchnodecount(base_node_count, input_node_count) & matchedgecount(base_edge_count, input_edge_count) & matchdegreecount(base_node_count,base_degreecount,input_degreecount);
    }

    //MATCH TOTAL NODE COUNT
    //complexity - c
    public static boolean matchnodecount(int base_node_count, int input_node_count)
    {
        return base_node_count==input_node_count;
    }

    //MATCH TOTAL EDGE COUNT
    //complexity - c
    public static boolean matchedgecount(int base_edge_count, int input_edge_count)
    {
        return base_edge_count==input_edge_count;
    }

    //MATCH VARIETY OF DEGREE COUNTS
    //complexity - n
    public static boolean matchdegreecount(int base_node_count, int[] base_degreecount, int[] input_degreecount)
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

    //for testing
    //provides permutation of number series
    public static ArrayList<Integer> permute_generator(int n, writeHelper writer)
    {
        ArrayList<Integer> integers = new ArrayList<>();
        boolean[] taken = new boolean[n];
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
            //    System.out.print(integers.get(i) + " ");
            writer.print(integers.get(i) + " ");
        }
        //System.out.println();
        writer.println();
        return integers;
    }

    //FOR TESTING
    public static int[][] permute_graph(int[][] basegraph, ArrayList<Integer> integers, writeHelper writer)
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
                writer.print(newnewgraph[i][j]);
            }
            writer.println();
        }
        return newnewgraph;
    }

    public static String printlabel(DataStructure.label label, writeHelper writer)
    {
        writer.print(label.layers.size() + " ");
        return label.layerstr;

        //OLD CODE
        ///OLD CODE
//        //complexity - c
//        String res = label.layers.size() + " ";
//        ////writer.print(label.layers.size() + " ");
//        //complexity - 3n^4 + cn^2 + cn
//        for(int i=0;i<label.layers.size();i++)
//        {
//            DataStructure DataStructure = label.layers.get(i);
//           // //writer.print(DataStructure.nodes.size() + " " + DataStructure.incoming_edges.size() + " " + DataStructure.outgoing_edges.size() + " " + DataStructure.internal_edges.size() + " ");
//            res += DataStructure.nodes.size() + " " + DataStructure.incoming_edges.size() + " " + DataStructure.outgoing_edges.size() + " " + DataStructure.internal_edges.size() + " ";
//            //complexity - 3n^3 + cn
//            for(int j=0;j<DataStructure.nodes.size();j++)
//            {
//             //                 //writer.print(DataStructure.nodes.get(j).rank_top_down + " " + DataStructure.nodes.get(j).rank_bottom_up + " ");
//                res += DataStructure.nodes.get(j).rank_top_down + " " + DataStructure.nodes.get(j).rank_bottom_up + " ";
//             //                  //writer.print(DataStructure.nodes.get(j).inc_edges.size() + " " + DataStructure.nodes.get(j).out_edges.size() + " " + DataStructure.nodes.get(j).int_edges.size() + " ");
//                res += DataStructure.nodes.get(j).inc_edges.size() + " " + DataStructure.nodes.get(j).out_edges.size() + " " + DataStructure.nodes.get(j).int_edges.size() + " ";
//                for(int k=0;k<DataStructure.nodes.get(j).inc_edges.size();k++)
//                {
//           //                            //writer.print(DataStructure.nodes.get(j).inc_edges.get(k).node1.rank_top_down + " " + DataStructure.nodes.get(j).inc_edges.get(k).node1.rank_bottom_up + " ");
//                    res += DataStructure.nodes.get(j).inc_edges.get(k).node1.rank_top_down + " " + DataStructure.nodes.get(j).inc_edges.get(k).node1.rank_bottom_up + " ";
//                }
//                for(int k=0;k<DataStructure.nodes.get(j).out_edges.size();k++)
//                {
//         //                              //writer.print(DataStructure.nodes.get(j).out_edges.get(k).node1.rank_top_down + " " + DataStructure.nodes.get(j).out_edges.get(k).node1.rank_bottom_up + " ");
//                    res += DataStructure.nodes.get(j).out_edges.get(k).node1.rank_top_down + " " + DataStructure.nodes.get(j).out_edges.get(k).node1.rank_bottom_up + " ";
//                }
//                for(int k=0;k<DataStructure.nodes.get(j).int_edges.size();k++)
//                {
//       //             //writer.print(DataStructure.nodes.get(j).int_edges.get(k).node1.rank_top_down + " " + DataStructure.nodes.get(j).int_edges.get(k).node1.rank_bottom_up + " ");
//                    res += DataStructure.nodes.get(j).int_edges.get(k).node1.rank_top_down + " " + DataStructure.nodes.get(j).int_edges.get(k).node1.rank_bottom_up + " ";
//                }
//            }
//        }
        // //writer.println();
        //  //writer.println();
        //  //writer.println();

    }

    //complexity - n^2 + n + c
    public static boolean matchlabels(int[][] basegraph, int[][] inputgraph,String[] labels1, String[] labels2,ArrayList permutes, writeHelper writer)
    {
        boolean[] blabeltaken = new boolean[labels1.length];
        boolean[] ilabeltaken = new boolean[labels2.length];
        int matched =0;
        // boolean nodeiso = true;
//        for(int i=0;i<labels1.length;i++)
//        {
//         ////writer.println(labels1[i]);
//        }
//        for(int i=0;i<labels2.length;i++)
//        {
//            ////writer.println(labels2[i]);
//        }
//        for(int i=0;i<labels1.length;i++) {
//            for (int j = 0; j < labels1.length; j++) {
//                if (labels1[j].equals(labels1[i]) && i!=j) {
//                    writer.println("b " + i + " " + j);
//                    if(!NodeIso.checkNodeIso(basegraph,i,j))
//                    {
//                        writer.println("NOT SIMILAR NODE ISO BASE GRAPH " + i + " " + j);
//                        nodeiso = false;
//                    }
//                }
//            }
//        }
//        for(int i=0;i<labels2.length;i++) {
//            for (int j = 0; j < labels2.length; j++) {
//                if (labels2[j].equals(labels2[i]) && i!=j) {
//                    writer.println("i " + i + " " + j);
//                    if(!NodeIso.checkNodeIso(inputgraph,i,j))
//                    {
//                        writer.println("NOT SIMILAR NODE ISO INPUT GRAPH " + i + " " + j);
//                        nodeiso = false;
//                    }
//                }
//            }
//        }

//        if(!nodeiso) {
//            dissimcnt++;
//            System.out.println("NOT SIMILAR NODE ISO");
//            writer.println("NOT SIMILAR NODE ISO");
//            return true;
//        }

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
                        writer.println(i + " " + j);
                        blabeltaken[i] = true;
                        ilabeltaken[j] = true;
                        matched++;
                        break;
                    }
                }
            }
        }
//        for(int i=0;i<blabeltaken.length;i++)
//        {
//            ////writer.println(blabeltaken[i] + " ");
//            if(!blabeltaken[i]){
//                //writer.println(labels1[i]);
//            }
//        }
//        //writer.println();
//        for(int i=0;i<ilabeltaken.length;i++)
//        {
//            //writer.println(ilabeltaken[i] + " ");
//            if(!ilabeltaken[i]){
//                //writer.println(labels2[i]);
//            }
//        }
        //writer.println();
        if(matched==labels1.length)
        {
//            System.out.println("SIMILAR");
            writer.println("SIMILAR");
            return true;
        }
        else
        {
//            System.out.println("NOT SIMILAR");
            writer.println("NOT SIMILAR");
            return false;
        }
    }

    public static boolean arraylistcontains(ArrayList<Integer> integers, int chk)
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

    public static boolean similarityChecker(int[][] basegraph, int[][] inputgraph, int base_node_count, int input_node_count, int base_edge_count, int input_edge_count, int[] base_degreecount, int[] input_degreecount, ArrayList permutes, writeHelper writer){

        String[] base_labels = new String[base_node_count];
        String[] input_labels = new String[input_node_count];

        //complexity - 22cn + 15cn^2 + 3n^5 + 4n^2 + 4n^4 + 9n^3 + n + c
        if (checkgraph(base_node_count, input_node_count, base_edge_count, input_edge_count, base_degreecount, input_degreecount)) {
            ArrayList<label> base_graph_labels = new ArrayList<>();
            //complexity - 22cn + 15cn^2 + 3n^5 + 3n^2 + 4n^4 + 9n^3
            for (int xz = 0; xz < basegraph.length; xz++) {
                generate_label generate_label = new generate_label(basegraph, base_node_count,xz, writer);
                base_graph_labels.add(generate_label.generate());
                base_labels[xz] = printlabel(base_graph_labels.get(base_graph_labels.size() - 1), writer);
                TreeConstructionFromLabel.ConstructTreeFromLabel(base_labels[xz]);
            }
            writer.println("BASE_GRAPH_OVER");
            ArrayList<label> input_graph_labels = new ArrayList<>();
            //complexity - n(22c + 15cn + cn^2 + 3n^4 + 3n + 4n^3 + 9n^2) = 22cn + 15cn^2 + 3n^5 + 3n^2 + 4n^4 + 9n^3
            for (int xz = 0; xz < inputgraph.length; xz++) {
                //complexity - c
                generate_label generate_label = new generate_label(inputgraph, input_node_count, xz, writer);
                //complexity - 20c + 14cn + 3n + 4n^3 + 9n^2
                input_graph_labels.add(generate_label.generate());
                //complexity - 3n^4 + cn^2 + cn + c
                input_labels[xz] = printlabel(input_graph_labels.get(input_graph_labels.size() - 1), writer);
                TreeConstructionFromLabel.ConstructTreeFromLabel(input_labels[xz]);
            }

            //complexity - n^2 + n + c
            return matchlabels(basegraph, inputgraph, base_labels, input_labels, permutes, writer);
        }
        else {
            writer.println("NOT SIMILAR MAJOR");
            return false;
        }
    }

    public static boolean verifyTreeConstruction(ArrayList<layer> layers, int[][] graph){
        for(int i=0;i<layers.size();i++){
            for (int j=0;j<layers.get(i).internal_edges.size();j++){
                if(!(graph[layers.get(i).internal_edges.get(j).node1.ori_node_num][layers.get(i).internal_edges.get(j).node2.ori_node_num]==1 && graph[layers.get(i).internal_edges.get(j).node2.ori_node_num][layers.get(i).internal_edges.get(j).node1.ori_node_num]==1)){
                    return false;
                }
            }
            for (int j=0;j<layers.get(i).incoming_edges.size();j++){
                if(!(graph[layers.get(i).incoming_edges.get(j).node1.ori_node_num][layers.get(i).incoming_edges.get(j).node2.ori_node_num]==1 && graph[layers.get(i).incoming_edges.get(j).node2.ori_node_num][layers.get(i).incoming_edges.get(j).node1.ori_node_num]==1)){
                    return false;
                }
            }
            for (int j=0;j<layers.get(i).outgoing_edges.size();j++){
                if(!(graph[layers.get(i).outgoing_edges.get(j).node1.ori_node_num][layers.get(i).outgoing_edges.get(j).node2.ori_node_num]==1 && graph[layers.get(i).outgoing_edges.get(j).node2.ori_node_num][layers.get(i).outgoing_edges.get(j).node1.ori_node_num]==1)){
                    return false;
                }
            }
        }
        return true;
    }

    public static int[][] adjMatFromLayers(ArrayList<layer> layers){
        return null;
    }

    public static int[][] getLayeredAdjMatrix(int[][] graphMatrix, int nodeCount, int rootNode)
    {

        int[][] LAM = new int[nodeCount][nodeCount];//Layered Adj Matrix
        int[] parentLayer = new int[nodeCount] ;//Track the layer of parent node of all nodes
        int[] nodeQueue = new int[nodeCount];//Queue to process nodes one after another in order of their appearance
        int queueHead = 0;
        for(int i=0;i<nodeCount;i++)
        {
            parentLayer[i] = -1;//initialization
        }
        parentLayer[rootNode] = 0;//root node's parent is 0.
        nodeQueue[queueHead++] = rootNode;//Start with root node
        int queueLoop = 0;
        while(queueLoop < nodeCount)
        {
            int currNodeNum = nodeQueue[queueLoop];
            for(int loop=0;loop<nodeCount;loop++)//for all nodes in graph
            {
                if(graphMatrix[currNodeNum][loop] != 0)//if a node have edge with current node
                {
                    if(parentLayer[loop]==-1)//if node is encountered first time, it is child of current node.
                    {
                        nodeQueue[queueHead++] = loop;//add this loop to process next
                        parentLayer[loop] = parentLayer[currNodeNum] + 1;//parent layer is parent's parent layer + 1
                        LAM[currNodeNum][loop] = LAM[loop][currNodeNum] = ( 2 * parentLayer[loop] ) - 1;//this denotes edge's layer.Two types of edge layer, 1. incoming edge(odd), 2. internal edge(even),
                    }
                    else//node is visited early
                    {
                        if(parentLayer[currNodeNum] == parentLayer[loop])
                        {
                            LAM[currNodeNum][loop] = LAM[loop][currNodeNum] = 2 * parentLayer[loop];
                        }
                        else if(parentLayer[currNodeNum] > parentLayer[loop])
                        {
                            LAM[currNodeNum][loop] = LAM[loop][currNodeNum] = ( 2 * parentLayer[currNodeNum] ) - 1;
                            //loop is parent node. ignore.
                        }
                        else
                        {
                            //there is some error in code.
                        }
                    }
                }
            }
            queueLoop++;
        }

        for(int i=0;i<nodeCount;i++)
        {
            for(int j=0;j<nodeCount;j++)
            {
                System.out.print(LAM[i][j] + " ");
            }
            System.out.println();
        }
        return LAM;
    }

    static class writeHelper{
        public PrintStream sWriter;
        public PrintWriter wWriter;
        boolean isPrintOn;

        public void println(String s){
            if(sWriter!=null && isPrintOn)
            {
                sWriter.println(s);
            }
            if(wWriter!=null && isPrintOn){
                wWriter.println(s);
            }
        }

        public void print(String s){
            if(sWriter!=null && isPrintOn)
            {
                sWriter.print(s);
            }
            if(wWriter!=null && isPrintOn){
                wWriter.print(s);
            }
        }

        public void print(int i){
            if(sWriter!=null && isPrintOn)
            {
                sWriter.print(i);
            }
            if(wWriter!=null && isPrintOn){
                wWriter.print(i);
            }
        }

        public void println(){
            if(sWriter!=null && isPrintOn)
            {
                sWriter.println();
            }
            if(wWriter!=null && isPrintOn){
                wWriter.println();
            }
        }

      }


}

