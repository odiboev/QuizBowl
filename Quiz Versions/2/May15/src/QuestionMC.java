import java.util.Arrays;

public class QuestionMC extends Question{
	private String [] choices;
	public QuestionMC(String[] choices,String ques, int points, String correct) {
		super(ques, points, correct);
		this.choices=choices;
	}
	public boolean Check(String ans){
		boolean c=false;
		for(int i=0;i<this.choices.length;i++){
			if(ans.equals(this.choices[i])||ans.equals("SKIP")){
				return true;
			}
		}

		return false;
	}
	@Override
	public String toString() {
		String cs="";
		String []abc={"• A - ", "• B - ", "• C - ", "• D - ", "• E - ", "• F - ", "• G - ", "• H - ", "• I - "};
		for(int i=0;i<choices.length;i++){
			cs=cs+abc[i]+choices[i]+"<br>";
		}
		return "Q \\" +  Ques +"?"+"<br>Points: " + points +"<br>"+cs+"</html>";
	}
	public String insertForm(){
		String cs="";
		for(String c:this.choices){
			cs=cs+c+"\r\n";
		}
		return "--------\r\n"+this.points+"\r\nMC\r\n"+this.Ques+"\r\n"+this.choices.length+"\r\n"+cs+this.correct;
	}
	
}
