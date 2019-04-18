package de.fhws.fiw.pvs.restdemo;

public class Question {


    private long companyID;
    private String question;
    private String answer;
    private String dokumentPath;

    public Question(long companyID, String question, String answer, String dokumentPath) {
        this.companyID = companyID;
        this.question = question;
        this.answer = answer;
        this.dokumentPath = dokumentPath;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDokumentPath() {
        return dokumentPath;
    }

    public void setDokumentPath(String dokumentPath) {
        this.dokumentPath = dokumentPath;
    }

    public long getCompanyID() {
        return companyID;
    }

    public void setCompanyID(long companyID) {
        this.companyID = companyID;
    }
}
