import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;

public class WxJumpGame extends JFrame {
	private JPanel buttonListJPanel;
	private JTextArea infonationJTextArea;
	
	private JScrollPane screenShotJScrollPane;
	private JLabel screenShotJLabel;
	
	private JButton pullSCJButton;
	private JButton jumpJButton;
	
	private MyConsole adbHandler;
	private int cnt = 2;
	private boolean flagStartStop = true;
	private int x1 = 0;
	private int y1 = 0;
	private int x2 = 0;
	private int y2 = 0;
	private double distance = 0;
	
	private int scWidth = 720;
	private int scHeight = 1280;
	
	public WxJumpGame() {
		adbHandler = new MyConsole();
		measureSCSize();
		createUserInterface();
	}
	
	private void createUserInterface() {
		Container contentPane = getContentPane();
		contentPane.setLayout(null);
		
		buttonListJPanel = new JPanel();
		buttonListJPanel.setBounds(0,0,300,300);
		buttonListJPanel.setBorder(new LineBorder(Color.BLACK));
		buttonListJPanel.setLayout(null);
		contentPane.add(buttonListJPanel);
		
		pullSCJButton = new JButton("PULL SC");
		pullSCJButton.setBounds(25,25,100,50);
		pullSCJButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				pullSCJButtonActionPerformed(event);
			}
		});
		buttonListJPanel.add(pullSCJButton);
		
		jumpJButton = new JButton("JUMP");
		jumpJButton.setBounds(25,100,100,50);
		jumpJButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				updataSCJButtonActionPerformed(event);
			}
		});
		buttonListJPanel.add(jumpJButton);
		
		infonationJTextArea = new JTextArea();
		infonationJTextArea.setBounds(0,310,300,320);
		infonationJTextArea.setBorder(new LineBorder(Color.BLACK));
		infonationJTextArea.setLayout(null);
		contentPane.add(infonationJTextArea);
		
		screenShotJLabel = new JLabel();
		screenShotJLabel.setBounds(0,0,scWidth,scHeight);
		screenShotJScrollPane = new JScrollPane(screenShotJLabel);
		screenShotJScrollPane.setBounds(310,0,scWidth+30,630);
		contentPane.add(screenShotJScrollPane);
		screenShotJLabel.setIcon(new ImageIcon("pic/sc1.png"));
		screenShotJLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				screenShotJLabelMouseClicked(event);
			}
		});
		
		setTitle("Have Fun With WxJump");
		setBounds(0,0,scWidth+360,680);
		setVisible(true);
	}
	
	private void screenShotJLabelMouseClicked(MouseEvent event) {
		int x = event.getX();
		int y = event.getY();
		System.out.println(x + "	" + y);
		if (flagStartStop) {
			x1 = x;
			y1 = y;
			flagStartStop = false;
		} else {
			x2 = x;
			y2 = y;
			flagStartStop = true;
		}
		distance = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
		infonationJTextArea.setText(String.format("start:(%d,%d)\n stop:(%d,%d)\n dist:%d",x1,y1,x2,y2,(int)distance));
	}
	
	private void pullSCJButtonActionPerformed(ActionEvent event) {
		adbHandler.getSC(cnt);
		screenShotJLabel.setIcon(new ImageIcon("pic/sc" + cnt + ".png"));
		cnt ++;
	}
	
	private void updataSCJButtonActionPerformed(ActionEvent event) {
		System.out.println("jump" + cnt);
		adbHandler.jump(distance);
	}
	
	public static void main(String args[]) {
		WxJumpGame application = new WxJumpGame();
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void measureSCSize() {
		adbHandler.getSC(1);
		try{
			BufferedImage bufferedImage = ImageIO.read(new File("pic/sc1.png"));
			scWidth = bufferedImage.getWidth();
			scHeight = bufferedImage.getHeight();
			System.out.println("Your phone's screen-size is width=" + scWidth + " height=" + scHeight);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

class MyConsole {
	private Runtime runtime;
	private final String screenshotPathOfPhone = "/sdcard/DCIM/Screenshots";
	public MyConsole() {
		runtime = Runtime.getRuntime();
	}
	public void getSC(int cnt) {
		String cmd = String.format("adb shell screencap %s/sc%d.png",screenshotPathOfPhone,cnt);  
        String line = null;
        try {
			Process process = runtime.exec(cmd);
			BufferedReader  bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));  
			while ((line = bufferedReader.readLine()) != null) {  
				System.out.println(line);
			}
			cmd = String.format("adb pull %s/sc%d.png pic/sc%d.png",screenshotPathOfPhone,cnt,cnt);
			process = runtime.exec(cmd);
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));  
			while ((line = bufferedReader.readLine()) != null) {  
				System.out.println(line);
			}
        } catch (Exception e) {
            e.printStackTrace();
        }  
	}
	public void jump(double distance) {
		final double factor = 2.05;
		int pressTime = (int)(factor * distance);
		String cmd = String.format("adb shell input swipe 600 1000 600 1000 %d",pressTime);
        String line = null;
        try {
			Process process = runtime.exec(cmd);
			BufferedReader  bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((line = bufferedReader.readLine()) != null) {  
				System.out.println(line);
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
