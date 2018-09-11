import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class filechooser {
	static JFrame frame=new JFrame();
	static File file;
	static JButton choose=new JButton("Choose");
	static JFileChooser gg=new JFileChooser();
	public static void main(String args[]){ 
		frame.add(choose);
		frame.setSize(300,300);
		frame.setVisible(true);
		choose.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int i=gg.showOpenDialog(null);
				if(i==gg.APPROVE_OPTION){
					file=gg.getSelectedFile();
					
				}
				
				
			}
			
		});
	}
}