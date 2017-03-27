package teamm.cs442_project;

public class QuestionNode{

    WordClusterList words;
    QuestionNode next;

    public QuestionNode(String w1, String w2, String w3, String w4){

        this.words = new WordClusterList(w1, w2, w3, w4);
        this.next = null;

    }
}
