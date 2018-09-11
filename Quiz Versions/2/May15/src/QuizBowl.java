import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

class QuizBowl{
	LinkedList<Object> allQuestions=new LinkedList<Object>();  //LinkedList storing questions
	private JFileChooser fileChooser =new JFileChooser();
	Player gamer;// Creating instance gamer from class Player
    int NumReqQuestions;    // Number of questions available in text file
	int qSerialNumber=0;  // A variable used to iterate through the questions in file
	int answerdCorrectly=0;  // Variable to keep track of correctly answered questions
	int skipped=0;			
	int answerdWrongly=0;
	String helloStranger;  // Message to the user
	
    JFrame Frame = new JFrame();    // Creating new Java Frame
    GridBagConstraints xy = new GridBagConstraints();   // Using Grid Bag Constraints xy
	// first page components
	JLabel fNameLabel=new JLabel("First name :"); // JLabel First Name
	JLabel lNameLabel=new JLabel("Last name :"); // JLabel Last Name
	JLabel numOfQuesLabel=new JLabel("Number of Questions : ");
	JLabel result=new JLabel();
	
	JTextField fName=new JTextField(30); 
	JTextField lName=new JTextField(30);
	JTextField numOfQues=new JTextField(30);
	String fileLoc;
	File file ;
	JButton submit=new JButton("Start");
	JLabel FinalLabel=new JLabel();
	
	// Question components
	JLabel Question=new JLabel();   // JLabel Question will display the question from file
	JLabel gamerLabel=new JLabel();   // JLabel will display Hello and players first and last name
	JTextField answerField=new JTextField(30);   // TextFiled to input answer
	JLabel answerLabel=new JLabel("Your answer");  // JLabel Answer
	JButton answer=new JButton("Next");  // JButton Next
	JButton fileButton=new JButton("Choose your file");   // JLabel skip info
	int QuesFileNumb;
	
	public QuizBowl(){
		fileChooser.setFileFilter( new FileNameExtensionFilter("TEXT FILES", "txt", "text"));
		Frame.setTitle("Quiz");
		Frame.setLayout(new GridBagLayout());
		Frame.setSize(700,600);
		// Placing components into Grid Bag Layout with Constraints xy
		xy.insets = new Insets(10, 0, 0, 0);    // Top Padding
		xy.gridx=0;
		xy.gridy=1;
		Frame.add(fNameLabel,xy);
		FinalLabel.setVisible(false);
		Frame.add(FinalLabel);
		xy.gridx=1;
		xy.gridy=1;
		Frame.add(fName,xy);
		
		xy.gridx=0;
		xy.gridy=2;
		Frame.add(lNameLabel,xy);
		xy.gridx=1;
		xy.gridy=2;
		Frame.add(lName,xy);
		
		xy.gridx=0;
		xy.gridy=3;
		
		Frame.add(numOfQuesLabel,xy);
		xy.gridx=1;
		xy.gridy=3;
		Frame.add(numOfQues,xy);
		xy.gridx=1;
		xy.gridy=4;
		Frame.add(fileButton,xy);
		
		
		
		xy.gridx=1;
		xy.gridy=5;
		Frame.add(submit,xy);
		xy.gridx=1;
		xy.gridy=6;
		Frame.add(result,xy);
		 // Initially these elements are not displayed.
		submit.setVisible(false);
		Question.setVisible(false);
		answerField.setVisible(false);
		answer.setVisible(false);
		answerLabel.setVisible(false);
		gamerLabel.setVisible(false);
		xy.gridx=0;
		xy.gridy=0;
		Frame.add(gamerLabel,xy);
		xy.gridx=1;
		xy.gridy=2;
		Frame.add(Question,xy);
		xy.gridx=0;
		xy.gridy=4;
		Frame.add(answerLabel,xy);
		xy.gridx=1;
		xy.gridy=4;
		Frame.add(answerField,xy);
		xy.gridx=0;
		xy.gridy=5;
		Frame.add(answer,xy);
		 
		fileButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
					file=fileChooser.getSelectedFile();
					fileLoc=file.getAbsolutePath();
					submit.setVisible(true);
				}
				
			}
			
		});
		// submit button ("Start Button") ActionListener
		submit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try{
					File f=file;  // New File instance to get text from file
					Scanner  sc =new Scanner(f);  // Scanner to scan through the imported file
					gamer=new Player(fName.getText(),lName.getText());  // Calling Player Class
					String []content=getFileContent(f);      // Storing contents of imported file as an array
					
					
							
					try{
						 // Splitting the contents of text file into elements by splitting them at line breaks
						for(int I=0;I<content.length;I++){
							String qContent=content[I];
							String []qInfo=qContent.split("\n");
							String qText=qInfo[2];
							String correct=qInfo[qInfo.length-1];
							int points=Integer.parseInt(qInfo[0]);
							// Extracting points from first line of text file and storing as integer points through parsing method
							if(checkDuplicate(allQuestions,qText)){   // CheckDuplicate method is called to check duplicate questions
								if(qInfo[1].equals("MC")){
									int numbOfChoices=Integer.parseInt(qInfo[3]);  // Extracting number of choices from 3rd line of question text
									String choicesStr="";
									for(int i =4;i<numbOfChoices+4;i++){
										choicesStr=choicesStr+qInfo[i]+"\n";
									}
									allQuestions.add(new QuestionMC(choicesStr.split("\n"),qText,points,correct));  // Calling QuestionMC Class
								}else if(qInfo[1].equals("TF")){
									allQuestions.add(new QuestionTF(qText,points,correct));  // Calling QuestionTF Class
								}else if(qInfo[1].equals("SA")){
									allQuestions.add(new QuestionSA(qText,points,correct));  // Calling QuestionSA Class
								}
							}
						}
						NumReqQuestions=Integer.parseInt(numOfQues.getText());
						QuesFileNumb=allQuestions.size();
						if(NumReqQuestions>QuesFileNumb){
							 // ERROR MESSAGE IN HTML FORMAT
							result.setText("<html><h3 Style='color:red'>There only "+QuesFileNumb+" Questions</h3></html>");
						}else if(NumReqQuestions<1){
							 // ERROR MESSAGE IN HTML FORMAT
							result.setText("<html><h3 Style='color:red'>Number of question can't be less than 1</h3></html>");
						}
						else{
							 // Turning off visibility for elements from first page
						fName.setVisible(false);
						lName.setVisible(false);
						fNameLabel.setVisible(false);
						lNameLabel.setVisible(false);
						
						fileButton.setVisible(false);
						numOfQues.setVisible(false);
						numOfQuesLabel.setVisible(false);
						submit.setVisible(false);
						
						Question.setText("<html><h3 style='color:blue'>"+allQuestions.get(0).toString()+"</h3></html>");
						 // Turning on visibility for quiz iterface (Question, Answers, User Answer, Button, Error Message, Player Info, and labels)
						Question.setVisible(true);
						answerField.setVisible(true);
						answer.setVisible(true);
						answerLabel.setVisible(true);
						gamerLabel.setVisible(true);
						result.setText("");
						helloStranger="<html><h3 style='font-family: 'Times New Roman', Times, serif'>Hello "
								+gamer.getFirstName()+" "+gamer.getLastName();
						gamerLabel.setText(helloStranger);
						}
						}
						catch (NumberFormatException e){
							result.setText("<html><h3 style='color:red'>Write the number of question in numbers without fractions</h3></html>"); // ERROR MESSAGE IN HTML FORMAT
						}catch (Exception e){
						result.setText("<html><h3 style='color:red'>The file does not have the question format</h3></html>");  // ERROR MESSAGE IN HTML FORMAT
						//e.printStackTrace();
					}
					//aa
				}catch(FileNotFoundException e){
					result.setText("<html><h3 style='color:red'>File not found</h3></html>");;  // ERROR MESSAGE IN HTML FORMAT
				}
				
				System.out.println(allQuestions.size());
				
			}
			
		});
		 // answer button ("Next" Button) action listener
		answer.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Object a=allQuestions.get(qSerialNumber);       // Assigning a number to each question from the text
				if(a.getClass().getName().equals("QuestionMC")){
					if(((QuestionMC) a).Check(answerField.getText())){      // Calling QuestionMC and Checking if user input answer is correct
						gamer.setPoints(gamer.getPoints()+((QuestionMC) a).answer(answerField.getText()));
						qSerialNumber++;	 // Iterating through questions in text file
						result.setText("");
						cal(((QuestionMC) a).answer(answerField.getText()));
					}else{
						result.setText("<html><h3 style='color:red'>You have to choose one of the choices</h3></html>");   // ERROR MESSAGE IN HTML FORMAT
					}
				}else if(a.getClass().getName().equals("QuestionTF")){
					if(((QuestionTF) a).Check(answerField.getText())){
						gamer.setPoints(gamer.getPoints()+((QuestionTF) a).answer(answerField.getText()));   // Calling QuestionTF Class
						qSerialNumber++;
						cal(((QuestionTF) a).answer(answerField.getText()));    // Iterating through questions in text file
						result.setText("");
					}else{
						result.setText("<html><h3 style='color:red'>You answer by (True or False)</h3></html>");
					}
				}else if(a.getClass().getName().equals("QuestionSA")){
					gamer.setPoints(gamer.getPoints()+((QuestionSA) a).answer(answerField.getText()));
					qSerialNumber++;
					result.setText("");
					cal(((QuestionSA) a).answer(answerField.getText()));
				}
				 // Once the Quiz has Ended
				if(qSerialNumber==NumReqQuestions||qSerialNumber>=QuesFileNumb){
					//gamer.setPoints(gamer.getPoints()+((Question) a).answer(answerField.getText()));
					System.out.println(gamer.getPoints());
					if(gamer.getPoints()<0){
						gamer.setPoints(0);
					}
					gamerLabel.setText(helloStranger+"<br>Points : "+gamer.getPoints());
					qSerialNumber++;
					Question.setVisible(false);
					answer.setVisible(false);
					answerField.setVisible(false);
					answerLabel.setVisible(false);
					gamerLabel.setVisible(false);
					FinalLabel.setText("<html><h3>"+helloStranger+"<br>Your points : "+gamer.getPoints()+
							"<br>Number of correct answers : "+answerdCorrectly+"<br>Number of wrong answers : "
							+answerdWrongly+"<br> Skipped Questions : "+skipped+"<br><hr><br>Total : "+NumReqQuestions+
							"<br>"+"OverAll : "+(100*answerdCorrectly/NumReqQuestions)+"%");  // Calculating the percentage of corrct answers
					FinalLabel.setVisible(true);
				}else{
					Question.setText("<html><h3 style='color:blue'>"+allQuestions.get(qSerialNumber).toString()+"</h3></html>");
					if(gamer.getPoints()<0){
						gamer.setPoints(0);
					}
					gamerLabel.setText(helloStranger+"<br>Points : "+gamer.getPoints());
				}
				answerField.setText("");
			}
			
		});
		
		Frame.setVisible(true);
		
	}
	public static void main (String [] arg){
		 try {
			 // JTatoo library is used for Look and Feel of the program
	            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
	        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
	            System.out.println("Error: " + ex);
	        }
		 // Calling QuizBowl
		new QuizBowl();
	}
	
	public static String[] getFileContent(File f ) throws FileNotFoundException{  // Method to get file contents
		
			String con="";
			Scanner sc=new Scanner(f);
			sc.nextLine();
			while(sc.hasNextLine()){ // While the text file has next line it is imported into app 
				con=con+sc.nextLine()+"\n";
			}
			return con.split("\n--------\n");   // Splitting the question segments by 8 minuses
		
	
	}
	public static boolean checkDuplicate(LinkedList<Object> allQuestions2,String Q){  // Method to check for Duplicate questions
		for(int i=0;i<allQuestions2.size();i++){
			if(((Question) allQuestions2.get(i)).getQues().equals(Q)){
				return false;
			}
		}
		return true;
	}
	public void cal(int p){
		if(p>0){
			answerdCorrectly++;
		}else if(p<0){
			answerdWrongly++;
		}else if(p==0){
			skipped++;
		}
	}
}