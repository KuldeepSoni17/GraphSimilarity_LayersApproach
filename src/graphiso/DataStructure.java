package graphiso;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

//DataStructure class
public class DataStructure {
   static class layer {
       int layer_no;
       ArrayList<edge> incoming_edges = new ArrayList<>();
       ArrayList<edge> outgoing_edges = new ArrayList<>();
       ArrayList<edge> internal_edges = new ArrayList<>();
       ArrayList<node> nodes = new ArrayList<>();
       int layerNodeCnt;
       int incEdgeCnt;
       int intEdgeCnt;
       int outEdgeCnt;

       @Override
       public String toString() {
           String str = "layer_no " + layer_no + "\n";
           str += "Nodes " + "\n";
           for(int i=0;i<nodes.size();i++){
                str+=nodes.get(i) + "\n";
           }
           str += "Incoming edges " + "\n";
           for(int i=0;i<incoming_edges.size();i++){

               str+=incoming_edges.get(i) + "\n";
           }
           str += "Outgoing edges " + "\n";
           for(int i=0;i<outgoing_edges.size();i++){
               str+=outgoing_edges.get(i) + "\n";
           }
           str += "Internal edges " + "\n";
           for(int i=0;i<internal_edges.size();i++){
               str+=internal_edges.get(i) + "\n";
           }
           return str;
       }
   }

    static class label
    {
        String layerstr = new String();
        ArrayList<layer> layers = new ArrayList<>();
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
        @Override
        public String toString() {
            return node1 + " with " + node2;
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

        node(){

        }

        @Override
        public String toString() {
            return "Row " + pos_row + " Col " + pos_col + " Ori node num " + ori_node_num;
        }
    }

    static class nodeedge{
        node node1;
        nodeedge(node node1)
        {
            this.node1 = node1;
        }

        @Override
        public String toString() {
            return node1.toString();
        }
    }

    static class tree{
       ArrayList<layer> layers;
       int nodeCnt;
       int layersCnt;
       int[][] adjMat;
    }

}
