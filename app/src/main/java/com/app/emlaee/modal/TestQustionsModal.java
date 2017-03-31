package com.app.emlaee.modal;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sahil on 1/12/2017.
 */

public class TestQustionsModal implements Serializable {
    String question = "";
    String quesId = "";
    String quesStatus = "";
    String quesnum = "";

    public String getQuesStatus() {
        return quesStatus;
    }

    public void setQuesStatus(String quesStatus) {
        this.quesStatus = quesStatus;
    }

    public String getQuesnum() {
        return quesnum;
    }

    public void setQuesnum(String quesnum) {
        this.quesnum = quesnum;
    }

    ArrayList<Option> options = new ArrayList<Option>();

    public TestQustionsModal(String question, String Answer,String quesId) {
        options.clear();
        this.question = question;
        this.quesId = quesId;
        try {
            JSONArray ary1 = new JSONArray(Answer);
            for (int j = 0; j < ary1.length(); j++) {
                // Log.e("", "ary1.length()" + ary1.length());

                JSONObject obj1 = ary1.getJSONObject(j);
                Log.e("obj1", "obj1" + obj1);
                Option op = new Option();
                String answer = obj1.getString("Answer");
                boolean correct = obj1.getBoolean("correct");
                String point = obj1.getString("Point");
                op.setAnswer(answer);
                op.setIsCorrect(correct);
                op.setPoints(point);
                op.setChecked(false);
                options.add(op);

            }
        } catch (Exception e) {

        }
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public class Option implements Serializable {
        String Answer = "";
        boolean IsCorrect;
        String Points = "";
        boolean isChecked;


        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean isChecked) {
            this.isChecked = isChecked;
        }

        public String getAnswer() {
            return Answer;
        }

        public void setAnswer(String answer) {
            Answer = answer;
        }

        public boolean isIsCorrect() {
            return IsCorrect;
        }

        public void setIsCorrect(boolean isCorrect) {
            IsCorrect = isCorrect;
        }

        public String getPoints() {
            return Points;
        }

        public void setPoints(String points) {
            Points = points;
        }
    }
}