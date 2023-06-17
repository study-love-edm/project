import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;

class Main_Frame extends JFrame {
	private WorkSpace workspace = null;
	Main_Frame(){
		
		CodeWriter workspace = new CodeWriter();
		setSize(1025,637);
		setVisible(true);
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(workspace);
		workspace.doOperation();
		
	}
}
public class Animation {
	public static void main (String[] args) {
		new Main_Frame();
	}
}
