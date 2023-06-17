import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

import javax.swing.JPanel;

abstract public class WorkSpace extends JPanel  {
		protected Data data ;
		protected Block blocks;
		private boolean compare_signal;
		private int data1;
		private int data2;
		private int data3;
		private static int panel_x=1015;
		private static int panel_y=600;
		private String op;
		protected int delay;
		WorkSpace(){}	//dummy constructor 
		WorkSpace(int number ,String[] VARIABLE_NAME,int[] VARIABLE_VALUE){
			setSize(panel_x,panel_y);
			setBackground(Color.black);
			compare_signal=false;
			data = new Data(number,VARIABLE_NAME,VARIABLE_VALUE);
			blocks = new Block();
		}
		protected void setCompare(int data1,int data2,int data3,String op){
			compare_signal=true;
			this.data1=data1;
			this.data2=data2;
			this.data3=data3;
			this.op=op;
		}
		public void paint(Graphics g) {
			super.paint(g);
			blocks.drawArray(g, null, null);
			blocks.drawBlock(g, null, null);
			if (!compare_signal) {
				blocks.drawArray(g, null, null);
				blocks.drawBlock(g, null, null);
			}
			else {
				blocks.drawCompare(g, data1,data2,data3,op);
				compare_signal=false;
			}
			
		}
		public void doOperation() {
			WriteOperation();
		}
		public void footer (String s) {
			Graphics g = getGraphics();
			g.drawString(s, panel_x/2, panel_y-50);
		}
		abstract public void WriteOperation();
	}

