package de.caffeineaddicted.ld35.util;

import de.caffeineaddicted.ld35.CoffeeGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by malte on 4/16/16.
 */
public class Highscores {

    private CoffeeGame game;

    private ArrayList<Score> scores;

    private Preferences preferences;

    public Highscores(CoffeeGame game) {
        this.game = game;
        scores = new ArrayList<Score>();
        preferences = new Preferences(CoffeeGame.CONSTANTS.PREFERENCES_FILENAME);
        load();
    }

    private void load() {
        scores.clear();
        for (int i = 0; i < 10; i++) {
            int score = preferences.getInteger("score" + i, -1);
            String name = preferences.getString("name" + i, null);
            if (score > 0 && name != null) {
                scores.add(new Score(name, score));
            }
        }
        sort();
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    private void sort() {
        ScoreComparator comparator = new ScoreComparator();
        Collections.sort(scores, comparator);
        trim();
    }

    private void trim() {
        scores = sublist(scores, 0, Math.min(9, scores.size() - 1));
    }

    public void addScore(String name, int score) {
        scores.add(new Score(name, score));
        sort();
        trim();
        preferences.clear();
        for (int i = 0; i < Math.min(10, scores.size()); i++) {
            preferences.putInteger("score" + i, scores.get(i).getScore());
            preferences.putString("name" + i, scores.get(i).getName());
        }
        preferences.flush();
    }

    public ArrayList<Score> sublist(ArrayList<Score> list, int begin, int end) {
        ArrayList<Score> result = new ArrayList<Score>();
        for (int i = begin; i <= end; i++) {
            result.add(list.get(i));
        }
        return result;
    }

    public static class Score {
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