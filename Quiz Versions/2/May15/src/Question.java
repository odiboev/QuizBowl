
public class Question {
	protected String Ques;
	protected int points;
	protected String correct;
	public Question(String ques, int points, String correct) {
		Ques = ques;
		this.points = points;
		this.correct = correct;
	}
	public int answer(String ans){
		if(ans.equals(this.correct)){
			return this.points;
		}else if(ans.equals("SKIP")){
				return 0;
		}
		else{
			return -this.points;
		}
	}
	public String getQues(){
		return this.Ques;
	}
	
	
}
