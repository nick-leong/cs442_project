package teamm.cs442_project;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class WordClusterList{

    public class WordClusterNode{

        ArrayList<String> words;
        int score;
        int group;
        WordClusterNode next;

        public WordClusterNode(String words){
            StringTokenizer st = new StringTokenizer(words);

            this.group = Integer.parseInt(st.nextToken());

            this.words = new ArrayList<String>();
            while(st.hasMoreTokens()){
                String currWord = st.nextToken();
                this.words.add(currWord.trim());
            }
            this.score = 0;
            this.next = null;
        }

    }

    public WordClusterNode wordnodes;

    public WordClusterList(String w1, String w2, String w3, String w4){
        this.wordnodes = new WordClusterNode(w1);
        WordClusterNode node2 = new WordClusterNode(w2);
        WordClusterNode node3 = new WordClusterNode(w3);
        WordClusterNode node4 = new WordClusterNode(w4);

        this.wordnodes.next = node2;
        node2.next = node3;
        node3.next = node4;

    }
}
