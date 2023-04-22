package scrapper;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class EmailScrapper implements ActionListener{
	JButton scrap = new JButton("Scrap");
	JButton export = new JButton("Export");
	
	JFrame mf = new JFrame();
	JLabel outUrlL = new JLabel();
	JTextField outUrl = new JTextField();
	JLabel inL = new JLabel();
	JLabel outL = new JLabel();
	JTextArea in = new JTextArea();
	JTextArea out = new JTextArea();
	JPanel p = new JPanel();
	String lastOpr;
	EmailScrapper(){
		outUrl.setText("c:/Users/hp/Desktop/out.txt");
		outUrlL.setText("Enter the location where to extract out");
		inL.setText("Paste Data to extract from here");
		outL.setText("Extracted out put");
		in.setSize(290, 200);
		out.setSize(290, 200);
		outUrlL.setBounds(25, 25, 600, 20);
		outUrl.setBounds(25, 50, 600, 25);
		p.setLayout(new GridLayout(2,2,10,0));
		p.setBounds(25, 70, 600, 400);
		scrap.setBounds(100, 500, 100, 25);
		scrap.addActionListener(this);
		export.setBounds(400, 500, 100, 25);
		export.addActionListener(this);
		p.add(inL);
		p.add(outL);p.add(in);p.add(out);
		mf.add(outUrlL);mf.add(outUrl);
		mf.add(p);
		mf.add(scrap);mf.add(export);
		mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mf.setTitle("Email Scrapper");
	    mf.setSize(700, 600);
	    mf.setLayout(null);
	    mf.setResizable(false);
	    mf.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == scrap) {
			lastOpr = "";	
		
			String r = scrap();
			if(r.length() > 0) {
				out.setText(r);	
			}
			
		}
		if(e.getSource() == export) {
			writeToFile();
		}
		
	}
	
	public String scrap()
	{
		String result = "";
		if(in.getText().length() > 0) {
		   Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]+").matcher(in.getText());
		   
		    while (m.find()) {
		    	if(lastOpr == "") {
		    		lastOpr = m.group();
		    	}else {
		    		lastOpr = lastOpr+","+m.group();
		    	}
		    	
		    	result = result+""+ m.group()+"\r\n";	
		    
		
		    }
		}
		in.setText("");
		return result;
	}	
	
	public void writeToFile() {
		 FileWriter fw = null;
	        BufferedWriter bw = null;
	        PrintWriter pw = null;

	        try {
	            try {
	            	//outUrl.getText()
	            	//String ss = "c:/Users/hp/Desktop/EmailExtractor/out.txt";
					fw = new FileWriter(outUrl.getText(), true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            bw = new BufferedWriter(fw);
	            pw = new PrintWriter(bw);
	            String[] stored = lastOpr.split(",");
               if(stored.length > 0) {
            	  for(int i = 0; i < stored.length ; i++) {
            		  pw.println(stored[i]);  
            	  } 
               }
               lastOpr = "";
               out.setText("");
	            pw.flush();

	        } finally {
	            try {
	                pw.close();
	                bw.close();
	                fw.close();
	            } catch (IOException io) {// can't do anything }
	            }
	        }

	}

}
