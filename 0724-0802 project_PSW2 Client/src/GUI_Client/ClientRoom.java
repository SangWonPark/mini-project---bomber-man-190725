package GUI_Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Main.WithServer;

import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JTextPane;
import java.awt.ScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientRoom extends JFrame implements ActionListener, KeyListener
{
	private WithServer toServer;

	private int roomnum = -1;

	private JPanel contentPane;

	private String waitingImgstr = "bombermanwait2";

	boolean visiFlag = true;

	JLabel ID0;
	JLabel ID1;

	JButton prepbtn;
	JButton outbtn;

	JPanel waitP;
	GameP GamePanel;
	private JTextField textField;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 * 
	 * @param me
	 */

	public ClientRoom(WithServer me, int roomidx)
	{
		this.toServer = me;
		this.roomnum = roomidx;
		
		init();
		this.setVisible(visiFlag);
	}

	public void init()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 840, 850);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		waitP = new JPanel()
		{
			public void paint(Graphics G)
			{
				try
				{
					Image waitingImg = ImageIO.read(new File("image/" + waitingImgstr + ".png"));
					G.drawImage(waitingImg, 0, 0, 760, 680, this);
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		};
		waitP.setBounds(32, 10, 760, 680);
		contentPane.add(waitP);
		GamePanel = new GameP(toServer);
//		GamePanel.setBounds(32, 10, 760, 680);
//		contentPane.add(GamePanel);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(12, 696, 800, 110);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(340, 5, 190, 100);
		panel_1.add(panel_2);
		panel_2.setLayout(null);

		ID0 = new JLabel("ID");
		ID0.setBounds(12, 10, 57, 15);
		panel_2.add(ID0);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(534, 5, 190, 100);
		panel_1.add(panel_3);
		panel_3.setLayout(null);

		ID1 = new JLabel("ID");
		ID1.setBounds(12, 10, 57, 15);
		panel_3.add(ID1);

		prepbtn = new JButton("\uC900\uBE44");
		prepbtn.setBounds(725, 5, 75, 50);
		panel_1.add(prepbtn);

		outbtn = new JButton("\uB098\uAC00\uAE30");
		outbtn.setBounds(725, 55, 75, 50);
		panel_1.add(outbtn);

		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 330, 100);
		panel_1.add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel.add(panel_4, BorderLayout.SOUTH);
		panel_4.setLayout(new BorderLayout(0, 0));

		textField = new JTextField();
		panel_4.add(textField);
		textField.setColumns(10);

		JButton chatSendBtn = new JButton("\uC804\uC1A1");
		chatSendBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				toServer.sendMsg("/roomMsg " + textField.getText() + " /end");
				textField.setText("");
			}
		});
		panel_4.add(chatSendBtn, BorderLayout.EAST);

		ScrollPane scrollPane = new ScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);

		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		scrollPane.add(textArea);

		prepbtn.addActionListener(this);
		outbtn.addActionListener(this);
		prepbtn.addKeyListener(this);
	}

	public void setInfo(String msg)
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				StringTokenizer tokens = new StringTokenizer(msg);
				int cnt = 0;
				while (cnt != roomnum * 4)
				{
					String dump = tokens.nextToken();
					cnt++;
				}
				ID0.setText(tokens.nextToken());
				tokens.nextToken();
				ID1.setText(tokens.nextToken());
			}
		}).start();
	}

	public boolean visibileset(String commend)
	{
		if (commend.equals("access"))
		{
			System.out.println("cr access");
			visiFlag = !visiFlag;
			this.setVisible(visiFlag);
			return true;

		} else if (commend.equals("access denied"))
		{

		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object o = e.getSource();
		if (o.equals(prepbtn))
		{
			toServer.sendMsg("/game prep /end");
		} else if (o.equals(outbtn))
		{
			toServer.sendMsg("/outroom outroom /end");
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int getkey = e.getKeyCode();
		String getKeyS = getkey + "";
//		System.out.println(getKeyS);
		toServer.sendMsg("/mov " + getKeyS + " /end");
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void gamEnd(String msg)
	{
		System.out.println(msg);
		contentPane.remove(GamePanel);
		if (msg.equals("You Win"))
		{
			waitingImgstr = "win";
		} else if (msg.equals("You Lose"))
		{
			waitingImgstr = "lose";
		}
		waitP.repaint();
		contentPane.add(waitP);
	}

	public void gamemsg(String msg)
	{
		System.out.println("gamemsg :"+msg);
		if (msg.equals("start"))
		{
			contentPane.remove(waitP);
//			GamePanel = new GameP(toServer);
			GamePanel.setBounds(32, 10, 760, 680);
			contentPane.add(GamePanel);
		}
	}
}
