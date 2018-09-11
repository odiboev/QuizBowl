
public class QuestionSA extends Question{

	public QuestionSA(String ques, int points, String correct) {
		super(ques, points, correct);
		
	}

	@Override
	public String toString() {
		return "Q\\" + Ques +" ? "+"<br>Points: " + points +"<br>" ;
	}
	
	public  String insertForm(){
		return "--------\r\n"+this.points+"\r\nSA\r\n"+this.Ques+"\r\n"+this.correct;
	}
}
