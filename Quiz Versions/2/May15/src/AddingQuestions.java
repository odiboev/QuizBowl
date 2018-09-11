import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AddingQuestions extends JPanel{
	private File file;
	private JFileChooser fileChooser =new JFileChooser();
	private JButton chooseFile=new JButton("Select file");
	protected String fileLocation; 
	
	private String fileLoc;
	private JLabel pointsLabel=new JLabel("points");
	
	private JTextField points=new JTextField(30);
	private String [] types= {"MCQ","True/False","One word questions"};
	private String [] tf= {"True","False"};
	private JComboBox Types=new JComboBox(types);
	JLabel QuestionLabel=new JLabel("The question : ");
	JTextArea MCC=new JTextArea(2,9);
	JLabel MCCLabel=new JLabel("Choices");
	private JLabel fileLabel=new JLabel("File location");
	private JTextField Question=new JTextField(30);
	private JLabel correctLabel=new JLabel("Correct answer");
	private JTextField correct=new JTextField(30);
	private JComboBox trueOrFalse=new JComboBox(tf);
	private JButton Add=new JButton("Add a Question");
	private JLabel result=new JLabel();
	private String fileContent;
	private JButton submit=new JButton("Submit");
	private LinkedList<Object> allQuestions=new LinkedList<Object>();
	public AddingQuestions(){
		setLayout(new GridBagLayout());
		fileChooser.setFileFilter( new FileNameExtensionFilter("TEXT FILES", "txt", "text"));
		MCC.setVisible(false);
		Types.setVisible(false);
		QuestionLabel.setVisible(false);
		Question.setVisible(false);
		correct.setVisible(false);
		correctLabel.setVisible(false);
		pointsLabel.setVisible(false);
		points.setVisible(false);
		MCC.setVisible(false);
		Add.setVisible(false);
		MCCLabel.setVisible(false);
		GridBagConstraints xy=new GridBagConstraints();
		xy.fill=2;
		
		add(fileLabel,xy);
		xy.gridx=1;
		xy.gridy=0;
		xy.insets=new Insets(5,1,1,1);
		add(Types,xy);
		add(chooseFile,xy);
		xy.gridx=0;
		xy.gridy=1;
		add(QuestionLabel,xy);
		add(submit,xy);
		xy.gridx=1;
		xy.gridy=1;
		add(Question,xy);
		// true or false
		xy.fill=2;
		xy.gridx=1;
		xy.gridy=2;
		add(trueOrFalse,xy);
		// MCQ
		xy.fill=1;
		xy.gridx=0;
		xy.gridy=2;
		add(correctLabel,xy);
		xy.gridx=1;
		xy.gridy=2;
		add(correct,xy);
		xy.fill=2;
		xy.gridx=0;
		xy.gridy=5;
		add(pointsLabel,xy);
		xy.gridx=1;
		xy.gridy=5;
		add(points,xy);
		xy.gridx=0;
		xy.gridy=7;
		add(MCCLabel,xy);
		xy.gridx=1;
		xy.gridy=7;
		add(MCC,xy);
		
		xy.gridy=8;
		add(Add,xy);
		xy.gridy=9;
		add(result,xy);
		trueOrFalse.setVisible(false);
		submit.setVisible(false);
		chooseFile.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int approve=fileChooser.showOpenDialog(null);
				if(approve==fileChooser.APPROVE_OPTION){
					file=fileChooser.getSelectedFile();
					fileLoc=file.getAbsolutePath();
					submit.setVisible(true);
				}
				
			}
			
		});
		submit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					File f=new File(fileLoc);
					
					Scanner  sc =new Scanner(f);
					String []content=QuizBowl.getFileContent(f);

					for(int I=0;I<content.length;I++){
						String qContent=content[I];
						String []qInfo=qContent.split("\n");
						String qText=qInfo[2];
						String correct=qInfo[qInfo.length-1];
						int points=Integer.parseInt(qInfo[0]);
						if(QuizBowl.checkDuplicate(allQuestions,qText)){
							if(qInfo[1].equals("MC")){
								int numbOfChoices=Integer.parseInt(qInfo[3]);
								String choicesStr="";
								for(int i =4;i<numbOfChoices+4;i++){
									choicesStr=choicesStr+qInfo[i]+"\n";
								}
								allQuestions.add(new QuestionMC(choicesStr.split("\n"),qText,points,correct));
							}else if(qInfo[1].equals("TF")){
								allQuestions.add(new QuestionTF(qText,points,correct));
							}else if(qInfo[1].equals("SA")){
								allQuestions.add(new QuestionSA(qText,points,correct));
							}
							
						}
					}
					fileLocation=fileLoc;
					Types.setVisible(true);
					QuestionLabel.setVisible(true);
					Question.setVisible(true);
					correctLabel.setVisible(true);
					correct.setVisible(true);
					Add.setVisible(true);
					result.setText("");
					MCCLabel.setVisible(true);
					MCC.setVisible(true);
					points.setVisible(true);
					pointsLabel.setVisible(true);
					chooseFile.setVisible(false);
					fileLabel.setVisible(false);
					submit.setVisible(false);
			}catch(FileNotFoundException e){
				result.setText("<html><h3 style='color:red'>File not found</h3></html>");
			}catch(Exception e){
				result.setText("<html><h3 style='color:red'>The File does not follow the Questions format</h3></html>");
			}
			
		}});
		Types.addActionListener(new ActionListener (){
			
			@Override
			public void actionPerformed(ActionEvent e) {
							
				if(Types.getSelectedIndex()==0){
					trueOrFalse.setVisible(false);
					MCC.setVisible(true);
					MCCLabel.setVisible(true);
					correct.setVisible(true);
					result.setText("");
				}else if(Types.getSelectedIndex()==1){
					MCC.setVisible(false);
					trueOrFalse.setVisible(true);
					MCCLabel.setVisible(false);
					correct.setVisible(false);
					result.setText("");
				}else if(Types.getSelectedIndex()==2){
					trueOrFalse.setVisible(false);
					MCC.setVisible(false);
					MCCLabel.setVisible(false);
					correct.setVisible(true);
					result.setText("");
				}
			}
	
	});
		Add.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					FileWriter writer = new FileWriter(fileLocation);
					
					int point=Integer.parseInt(points.getText());
					if(QuizBowl.checkDuplicate(allQuestions, Question.getText())){
					if(Types.getSelectedIndex()==0){
						if(MCC.getText().split("\n").length<9||MCC.getText().split("\n").length>3){
							
							
							if(contains(MCC.getText().split("\n"),correct.getText())){
								
								allQuestions.add(new QuestionMC(MCC.getText().split("\n"),correct.getText(),point,correct.getText()));
								result.setText("<html><h3 style='color:Blue'>Question was added succussfully");
								System.out.println(allQuestions.get(allQuestions.size()-1).toString());
								
							}else{
								result.setText("<html><h3 style='color:red'>The correct answer has to be one of the choices"
										+ "</h3></html>");
							}
						}
					}else if(Types.getSelectedIndex()==1){
						if(trueOrFalse.getSelectedIndex()==0){
							allQuestions.add(new QuestionTF(Question.getText(),point,"True"));
						}else{
							allQuestions.add(new QuestionTF(Question.getText(),point,"False"));
						}
						result.setText("<html><h3 style='color:Blue'>Question was added succussfully");
					}else if(Types.getSelectedIndex()==2){
						if(correct.getText().equals("")||Question.getText().equals("")){
							result.setText("<html><h3 style='color:red'>Please fill the Question field</h3></html>");
						}else{
							allQuestions.add(new QuestionSA(Question.getText(),point,correct.getText()));
							result.setText("<html><h3 style='color:Blue'>Question was added succussfully");
						}
					}}else{
						result.setText("<html><h3 style='color:red'>Questions Already added</h3></html>");
					}
					String c="";
					for(int i=0;i<allQuestions.size();i++){
						String typo=allQuestions.get(i).getClass().getName();
						if(typo.equals("QuestionTF")){
							QuestionTF q=((QuestionTF)allQuestions.get(i));
							c=c+q.insertForm()+"\r\n";
						}else if(typo.equals("QuestionSA")){
							QuestionSA q=((QuestionSA)allQuestions.get(i));
							c=c+q.insertForm()+"\r\n";
						}else if(typo.equals("QuestionMC")){
							QuestionMC q=((QuestionMC)allQuestions.get(i));
							c=c+q.insertForm()+"\r\n";
						}
						
					}
					writer.write(c);
					writer.flush();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch(NumberFormatException e){
					result.setText("<html><h3 Style='color:red'>Points can't be in fractions or letters</h3></html>");
				}
			}
			
		});
	
	}
	public static void main(String []a){
		String [] a1={"1","2","3","4"};
		QuestionMC q=new QuestionMC(a1,"shit happens?",5,"1");
		System.out.print(q.insertForm()+12);
		JFrame f=new JFrame();
		AddingQuestions w=new AddingQuestions();
		f.add(w);
		f.setSize(600,600);
		
		f.setVisible(true);
		
	}
	public boolean checkQuestion(String question,String fileLoc){
		try{
			File a=new File(fileLoc);
			Scanner f=new Scanner(a);
			while(f.hasNextLine()){
				String q=f.nextLine();
				if(q.equals("--------")){
					f.nextLine();f.nextLine();
					if(question.equals(f.nextLine())){
						System.out.print(f.nextLine());
						return false;
						
					}
				}
			}
			return true;
		}catch (Exception e){
			System.out.println(e);
			e.printStackTrace();
			return false;
		}
	}
	public static boolean contains(String[] abcde, String e) {
		for(String x:abcde){
			if(x.equals(e)){
				return true;
			}
		}
		return false;
	}
}
