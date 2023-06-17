import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

final class Data {
	protected static int ARRAY[]= {20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1};
	protected static String[] VARIABLE_NAME;
	protected static int [] VARIABLE_VALUE;
	protected static int BLOCK_NUMBER;
	private static final int MAX_BLOCK_NUMBER=20;
	Data(){}
	Data(int BLOCK_SIZE,String[] VARIABLE_NAME,int[] VARIABLE_VALUE){
		if (MAX_BLOCK_NUMBER>=BLOCK_SIZE) {
			this.BLOCK_NUMBER=BLOCK_SIZE;
		}
		if (VARIABLE_NAME.length==VARIABLE_VALUE.length) {
			this .VARIABLE_NAME=VARIABLE_NAME;
			this.VARIABLE_VALUE=VARIABLE_VALUE;
		}
		
	}
}
class Block {
	private final int SIZE=40;
	private final int DISTANCE_BETWEEN_BLOCK=SIZE+10;
	private final int BLOCK_NUMBER;	//no. of block to be present
	private final int x =10;
	private final int y=280;
	private final int VIRTUAL_ADDRESS=100;
	private final int BLOCK_X=10;	//of variable x
	private final int BLOCK_Y=50;	//of varibale y
	private Font DefaultFont;
	private Map<String,String> Conditional_Operator;
	
	public Block(){
		BLOCK_NUMBER=Data.BLOCK_NUMBER;
		Conditional_Operator = new HashMap<>();
		//put operator
		Conditional_Operator.put("<", "<");
		Conditional_Operator.put(">",">");
		Conditional_Operator.put("<=","<=");
		Conditional_Operator.put(">=", ">=");
		Conditional_Operator.put("==","==");
		Conditional_Operator.put("!=", "!=");
	}
	public void delay(int msec) {
		try {
			Thread.sleep(msec);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	//draw Array in any JComponent 
	public void drawArray(Graphics g,Integer loc1,Integer loc2){
		g.drawString("MAIN ARRAY:",x,y-20);
			for (int i=0;i<BLOCK_NUMBER;i++) {
				//draw block
			while(true){
				if (loc1!=null) {
					if(loc2!=null) {
						if (loc1==i || loc2==i) g.setColor(Color.red);
						else g.setColor(Color.white);
						break;
					}
					if (loc1==i)g.setColor(Color.red);
					else g.setColor(Color.white);
					break;
				}
				else if (loc2!=null) {
					if (loc2==i) g.setColor(Color.red);
					else	g.setColor(Color.white);
					break;
				}
				else {
					g.setColor(Color.white);
					break;
				}
			}
	
				g.fillRoundRect(x+(i*DISTANCE_BETWEEN_BLOCK), y, SIZE,SIZE,10,10);	//draw array value 
				g.setColor(Color.black);
				g.drawString(new String(Data.ARRAY[i]+""),(x+(SIZE/2))+(i*(SIZE+x)),y+(SIZE/2));		//draw string
				g.setColor(Color.white);
				g.drawString(new String((VIRTUAL_ADDRESS+i)+""),(x+(SIZE/2))+(i*(SIZE+x))-10,y+(SIZE)+15);
			}
	}
	//draw any block in any JComponent 
	public void drawBlock (Graphics g, Integer loc1, Integer loc2) {
		g.drawString("Visualize Variable:", BLOCK_X, BLOCK_Y-20);
		for (int i=0;i<Data.VARIABLE_NAME.length;i++) {
			for (;;) {
				if (loc1!=null) {
					if(loc2!=null) {
						if (loc1==i || loc2==i) g.setColor(Color.red);
						else g.setColor(Color.white);
						break;
					}
					if (loc1==i)g.setColor(Color.red);
					else g.setColor(Color.white);
					break;
				}
				else if (loc2!=null) {
					if (loc2==i) g.setColor(Color.red);
					else	g.setColor(Color.white);
					break;
				}
				else {
					g.setColor(Color.white);
					break;
				}
			}
			g.fillRoundRect(BLOCK_X+(i*DISTANCE_BETWEEN_BLOCK), BLOCK_Y, SIZE,SIZE,10,10);		//draw array value 
			g.setColor(Color.black);
			g.drawString(new String(Data.VARIABLE_VALUE[i]+""),(BLOCK_X+(SIZE/2))+(i*(SIZE+x)),BLOCK_Y+(SIZE/2));	//draw string
			g.setColor(Color.white);
			g.drawString(Data.VARIABLE_NAME[i],(BLOCK_X+(SIZE/2))+(i*(SIZE+BLOCK_X))-10,BLOCK_Y+(SIZE)+15);
		}
	}
	public void drawCompare(Graphics g,int indexone,int indextwo,int data,String operator) {
		/*
		 * data =0 ARRAYONE=ARRAY ARRAYTWO=ARRAY
		 * data =1 ARRAYONE=ARRAY ARRAYTWO=VARIBALE
		 * data =2 ARRAYONE=VARIABLE ARRAYTWO=ARRAY
		 * data =3 ARRAYONE=VARIABLE ARRAYTWO=VARIABLE
		 */
		//draw signal in 1st indexone array correspondence array by data value 
		DefaultFont=g.getFont();
		if (Conditional_Operator.get(operator)!=null) {
			g.setFont(new Font("Arial",Font.BOLD,40));
			g.drawString(Conditional_Operator.get(operator), 500,200);
			g.setFont(DefaultFont);
		}
		if (data>=0 && data<=1) 
			drawArray(g,indexone,null);	//draw array by compare signal
		else 
			drawBlock(g,indexone,null);	//draw value by compare signal
		//draw signal in 2nd indexone array correspondence array by data value 
		if ((data & 1)==0) 
			if (data>=0 && data<=1)
				drawArray(g,indexone,indextwo);	//draw array by compare signal
			else
				drawArray(g,indextwo,null);
		else 
			if (data>=2 && data <=3)
				drawBlock(g,indexone,indextwo);	//draw value by compare signal
			else
				drawBlock(g,indextwo,null);
	}
}

