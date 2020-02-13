package searching_unit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.ScoreDoc;


public class Main extends Application {

    public static void main(String[] args) throws IOException
    {
        launch(args);
    }

    /**
     * Outputs graph showing best matching reviewers
     * @param stage
     * @throws IOException
     */
    @Override public void start(Stage stage) throws IOException
    {

        try {
            //Query research paper
            Scanner input = new Scanner(Paths.get("search.txt"));
            String path = input.nextLine().trim();
            PDFManager p = new PDFManager();
            p.setFilePath(path);
            String essay = p.ToText();

            //Build keywords from research paper
            List<String> allWords = KeyWordBuilder.buildWordList(essay);
            List<String> uWords = Words.removeStopWords(allWords);
            List<WordPair> wordPair = KeyWordBuilder.buildWordPair(uWords,allWords);

            //Return a String query
            Scanner reader = new Scanner(Paths.get("limit.txt"));
            int limit = reader.nextInt();
            String query = "";
            for(int i = 0; i < limit; i++){
                WordPair pair = wordPair.get(i);
                query += pair.getWord() + ",";
            }

            // perform search on the given paper
            // and retrieve the top 5 result
            System.out.println("performSearch");
            SearchEngine se = new SearchEngine();

            TopDocs topDocs = se.performSearch(query, 20);
            System.out.println("Results found: " + topDocs.totalHits);
            ScoreDoc[] hits = topDocs.scoreDocs;

            HashMap<String,Float> paperScore = new HashMap<>();
            for (int i = 0; i < hits.length; i++) {
                Document doc = se.getDocument(hits[i].doc);
                //System.out.println(doc.get("author") + " (" + hits[i].score + ")");
                paperScore.put(doc.get("author"),hits[i].score);
            }

            //build bar chart
            stage.setTitle("Paper Rank Chart");
            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();
            final BarChart<String,Number> bc =
                    new BarChart<String,Number>(xAxis,yAxis);
            bc.setTitle("Review Summary");
            xAxis.setLabel("Paper Title");
            yAxis.setLabel("Paper Scores");

            HashMap<String, Float> finalScores = new HashMap<>();

            XYChart.Series series1 = new XYChart.Series();
            series1.setName("scores");
            Set<String> keys = paperScore.keySet();
            for(String key : keys){
                String name = findParent(key);
                if(finalScores.containsKey(name)){
                    float score = finalScores.get(name) + paperScore.get(key);
                    finalScores.put(name, score);
                }
                else {
                    finalScores.put(name, paperScore.get(key));
                }
            }

            Set<String> strs = finalScores.keySet();
            for(String str : strs){
                series1.getData().add(new XYChart.Data(str , finalScores.get(str)));
                System.out.println(str + " - " + finalScores.get(str));
            }


            Scene scene  = new Scene(bc,800,600);
            bc.getData().addAll(series1);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println("Exception caught.\n");
        }

    }

    /**
     * Search for folder name
     * @param fileName
     * @return
     */
    private static String findParent(final String fileName) throws IOException
    {

        List<String> paths = buildPaths();
        for(String path : paths) {
            File folder = new File(path);
            File[] files = folder.listFiles();
            for (int i = 0; i < files.length; i++) {
                String tempName = files[i].getName();
                if (tempName.equals(fileName)) {
                    return folder.getName();
                }
            }
        }
        return null;
    }

    /**
     * Build the Corpus paths
     * @return
     * @throws IOException
     */
    public static List<String> buildPaths() throws IOException
    {
        List<String> paths = new ArrayList<>();
        File file  = new File("Corpus");
        File[] files = file.listFiles();
        for(File newfile : files){
            if(newfile.isDirectory()) {
                paths.add(newfile.getAbsolutePath());
            }
        }
        return paths;
    }

}
