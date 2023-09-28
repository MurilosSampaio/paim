import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.*;
import ij.plugin.frame.*;
import ij.*;
import ij.process.*;
import ij.gui.*;

/**
	ButterHighPass_.java	
*/
public class ButterHighPass_ extends PlugInFrame implements ActionListener {

	Panel panel;
	int previousID;
	static Frame instance;

	public ButterHighPass_() {
		super("Butter LowPass");
		if (instance!=null) {
			instance.toFront();
			return;
		}
		instance = this;
		IJ.register(ButterLowPass_.class);

		setLayout(new FlowLayout());
		panel = new Panel();
		panel.setLayout(new GridLayout(1, 4, 5, 5));
		addButton("PI/2");
		addButton("PI/4");
		addButton("PI/8");
		addButton("PI/16");
		add(panel);
		
		pack();
		GUI.center(this);
		show();
	}
	
	void addButton(String label) {
		Button b = new Button(label);
		b.addActionListener(this);
		panel.add(b);
	}

	public void actionPerformed(ActionEvent e) {
		String label = e.getActionCommand();
		if (label==null)
			return;
		new ButterRunner(label);
	}

	public void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID()==WindowEvent.WINDOW_CLOSING) {
			instance = null;	
		}
	}

}

class ButterRunner extends Thread {
	private String command;
	private ImagePlus imp;
	private ImageProcessor ip;

	ButterRunner(String command) {
		super(command);
		this.command = command;
		this.imp = imp;
		this.ip = ip;
		setPriority(Math.max(getPriority()-2, MIN_PRIORITY));
		start();
	}

	public void run() {
		try {runCommand(command);}
		catch(OutOfMemoryError e) {
			IJ.outOfMemory(command);
			if (imp!=null) imp.unlock();
		} catch(Exception e) {
			CharArrayWriter caw = new CharArrayWriter();
			PrintWriter pw = new PrintWriter(caw);
			e.printStackTrace(pw);
			IJ.write(caw.toString());
			IJ.showStatus("");
			if (imp!=null) imp.unlock();
		}
	}

	void runCommand(String command) {
		int r, i, j;
		int radius = 0;
		double f;
		ImageAccess im;
		IJ.showStatus(command + "...");
		im=new ImageAccess(256,256);
		if (command.equals("PI/16"))
			radius=16;
		if (command.equals("PI/8"))
			radius=32;
		if (command.equals("PI/4"))
			radius=64;
		if (command.equals("PI/2"))
			radius=128;
//
//		design the filter here
//
		im.show("Low Pass "+command);
	}
}
