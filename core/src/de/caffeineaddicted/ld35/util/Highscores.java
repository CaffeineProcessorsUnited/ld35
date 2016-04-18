package de.caffeineaddicted.ld35.util;

import de.caffeineaddicted.ld35.CoffeeGame;
import de.caffeineaddicted.ld35.screens.CoffeeScreen;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by malte on 4/16/16.
 */
public class Highscores {

    private CoffeeGame game;
    // The name of the file where the highscores will be saved
    private final String HIGHSCORE_FILE;
    // Initialising an in and outputStream for working with the file
    ObjectOutputStream          outputStream   = null;
    ObjectInputStream           inputStream    = null;
    // An arraylist of the type "score" we will use to work with the scores
    // inside the class
    private ArrayList<Score>    scores;

    public Highscores(CoffeeGame game) {
        this(game, "scores.dat");
    }

    public Highscores(CoffeeGame game, String fileName) {
        this.game = game;
        HIGHSCORE_FILE = fileName;
        scores = new ArrayList<Score>();
    }

    public ArrayList<Score> getScores() {
        loadScoreFile();
        sort();
        return scores;
    }

    private void sort() {
        ScoreComparator comparator = new ScoreComparator();
        Collections.sort(scores, comparator);
        scores = sublist(scores, 0, Math.min(9, scores.size() - 1));
    }

    public void addScore(String name, int score) {
        scores.add(new Score(name, score));
        sort();
        updateScoreFile();
    }

    public void loadScoreFile() {
        try {
            inputStream = new ObjectInputStream(new FileInputStream(HIGHSCORE_FILE));
            scores = (ArrayList<Score>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            game.error("Highscores", "[Load] FNF Error: " + e.getMessage());
            System.out.println();
        } catch (IOException e) {
            game.error("Highscores", "[Load] IO Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            game.error("Highscores", "[Load] CNF Error: " + e.getMessage());
        } finally {
            inputStream = null;
        }
    }

    public void updateScoreFile() {
        if (scores.size() == 0) {
            return;
        }
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE));
            outputStream.writeObject(sublist(scores, 0, Math.min(9, scores.size() - 1)));
        } catch (FileNotFoundException e) {
            game.error("Highscores", "[Update] FNF Error: " + e.getMessage() + ", the program will try and make a new file");
        } catch (IOException e) {
            game.error("Highscores", "[Update] IO Error: " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
                outputStream = null;
            } catch (IOException e) {
                game.error("Highscores", "[Update] Error: " + e.getMessage());
            }
        }
    }

    public ArrayList<Score> sublist(ArrayList<Score> list, int begin, int end) {
        ArrayList<Score> result = new ArrayList<Score>();
        for (int i = begin; i <= end; i++) {
            result.add(list.get(i));
        }
        return result;
    }

    public static class Score  implements Serializable {
        private int score;
        private String name;

        public Score(String name, int score) {
            this.score = score;
            this.name = name;
        }

        public int getScore() {
            return score;
        }

        public String getName() {
            return name;
        }
    }

    public class ScoreComparator implements Comparator<Score> {
        public int compare(Score score1, Score score2) {

            int sc1 = score1.getScore();
            int sc2 = score2.getScore();

            if (sc1 > sc2) {
                return -1;
            } else if (sc1 < sc2) {
                return +1;
            } else {
                return 0;
            }
        }
    }
}