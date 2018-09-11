
public class QuestionTF extends Question{

	public QuestionTF(String ques, int points, String correct) {
		super(ques, points, correct);
	}
	public boolean Check(String ans){
		if(ans.equals("True")||ans.equals("False")||ans.equals("SKIP")){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public String toString() {
		return "Q\\" + Ques + "?? (Answer by True or False)<br>Points: " + points +"<br>"  ;
	}
	public  String insertForm(){
		return "--------\r\n"+this.points+"\r\nTF\r\n"+this.Ques+"\r\n"+this.correct;
	}
}
