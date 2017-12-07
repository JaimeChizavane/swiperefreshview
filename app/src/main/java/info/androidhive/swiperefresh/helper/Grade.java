package info.androidhive.swiperefresh.helper;

/**
 * Created by jaimechizavane on 11/11/17.
 */

public class Grade {

    String studentID;
    String teste1;
    String teste2;

    public String getTeste1() {
        return teste1;
    }

    public void setTeste1(String teste1) {
        this.teste1 = teste1;
    }

    public String getTeste2() {
        return teste2;
    }

    public void setTeste2(String teste2) {
        this.teste2 = teste2;
    }



    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public Grade(String teste1, String teste2) {
        this.teste1 = teste1;
        this.teste2 = teste2;
    }

    public Grade(){

    }

}
