package graphiso;

import java.util.ArrayList;
import graphiso.DataStructure.layer;
import graphiso.DataStructure.tree;
import graphiso.DataStructure.node;

public class TreeConstructionFromLabel {
    static tree resultTree;
    static String[] strData;
    static tree ConstructTreeFromLabel(String label){
        resultTree = new tree();
        if(label==null)
        {
            return null;
        }
        strData = label.split(" ");
        System.out.println("label \n o label:- " + label);

         int curInd = 0;

        resultTree.nodeCnt = Integer.parseInt(strData[curInd++]);
        resultTree.layers = new ArrayList<>();
        InitializeAdjMat(resultTree.nodeCnt);
        resultTree.layersCnt = Integer.parseInt(strData[curInd++]);

        for(int i=0;i<resultTree.layersCnt;i++)
        {
            layer layerObj = new layer();
            layerObj.layer_no = i;
            layerObj.layerNodeCnt = Integer.parseInt(strData[curInd++]);
            layerObj.incEdgeCnt = Integer.parseInt(strData[curInd++]);
            layerObj.outEdgeCnt = Integer.parseInt(strData[curInd++]);
            layerObj.intEdgeCnt = Integer.parseInt(strData[curInd++]);
            resultTree.layers.add(layerObj);
        }
        //TOP DOWN RANKING STARTS
        curInd++;//TOP_DOWN_RANKING String



        //First layer
        node nodeObj0 = new node();
        nodeObj0.rank_top_down = Integer.parseInt(strData[curInd++]);
        curInd++;//Curr count - always 0 for first entry
        nodeObj0.layer_no = Integer.parseInt(strData[curInd++]);
        resultTree.layers.get(0).nodes.add(nodeObj0);

        for(int i=1;i<resultTree.layersCnt;i++){
            for(int j=0;j<resultTree.layers.get(i).layerNodeCnt;j++){
                curInd++;curInd++;curInd++;
            }
            for(int j=0;j<resultTree.layers.get(i).layerNodeCnt;j++){

                node nodeObj = new node();

                //AFTER INTERNAL EDGES
                nodeObj.rank_top_down = Integer.parseInt(strData[curInd++]);
                curInd++;
                nodeObj.layer_no = Integer.parseInt(strData[curInd++]);

                resultTree.layers.get(i).nodes.add(nodeObj);
            }
        }

        //BOTTOM UP RANKING STARTS
        curInd++;

        for(int i=1;i<=resultTree.layersCnt;i++)
        {
            for(int j=0;j<resultTree.layers.get(resultTree.layersCnt - i).layerNodeCnt;j++)
            {

                node nodeObj = resultTree.layers.get(resultTree.layersCnt - i).nodes.get(j);

                if(i!=1) {
                //AFTER OUTGOING EDGES
                nodeObj.rank_bottom_up = Integer.parseInt(strData[curInd++]);
                factorizer(nodeObj.rank_bottom_up);
                curInd++;
                nodeObj.layer_no = Integer.parseInt(strData[curInd++]);
                }

                //AFTER INTERNAL EDGES
                nodeObj.rank_bottom_up = Integer.parseInt(strData[curInd++]);
                curInd++;
                nodeObj.layer_no = Integer.parseInt(strData[curInd++]);

                //AFTER INCOMING EDGES
                nodeObj.rank_bottom_up = Integer.parseInt(strData[curInd++]);
                curInd++;
                nodeObj.layer_no = Integer.parseInt(strData[curInd++]);

                //resultTree.layers.get(resultTree.layersCnt - i).nodes.add(nodeObj);
            }
        }

        processLayersTopDown(resultTree);

        return resultTree;
    }

    static void processLayersTopDown(tree resultTree){

        for(int nodeNo=resultTree.layersCnt-1;nodeNo>=0;nodeNo--)
        {

        }
    }

    static ArrayList<Integer> factorizer(long num){
        ArrayList<Integer> factors = new ArrayList<Integer>();
        int i=0;
        while(num!=1){
            int currPrime = prime_provider.getprime(i);
            if((num%currPrime) == 0){
                num = num/currPrime;
                factors.add(currPrime);
            }
            i++;
        }
        System.out.println(factors);
        return factors;
    }

    static void InitializeAdjMat(int num)
    {
        resultTree.adjMat = new int[num][num];
        for(int i=0;i<num;i++){
            for(int j=0;j<num;j++){
                if(i<j){
                    resultTree.adjMat[i][j] = 1;
                }
                else{
                    resultTree.adjMat[i][j] = 0;
                }
            }
        }
    }
}
