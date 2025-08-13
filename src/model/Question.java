package model;

public class Question {
    public int id;
    public int quizId;
    public String questionText, optionA, optionB, optionC, optionD;
    public char correctOption;

    public Question(int id, int quizId, String questionText, String optionA, String optionB, String optionC, String optionD, char correctOption) {
        this.id = id;
        this.quizId = quizId;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOption = correctOption;
    }
}
