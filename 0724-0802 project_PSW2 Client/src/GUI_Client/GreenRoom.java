package GUI_Client;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Main.WithServer;

import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.JScrollBar;
import java.awt.ScrollPane;
import java.awt.List;

public class GreenRoom extends JFrame implements ActionListener
{
	WithServer toServer;

	private String[] textset = new String[12];

	private JPanel contentPane;

	boolean visiFlag = true;

	JTextArea chattingField;

	JPanel room0;

	JPanel P00;
	JLabel ID00;
	JLabel WIN00;

	JPanel P01;
	JLabel ID01;
	JLabel WIN01;

	JButton roombtn0;

	JPanel room1;

	JPanel P10;
	JLabel ID10;
	JLabel WIN10;

	JPanel P11;
	JLabel ID11;
	JLabel WIN11;

	JButton roombtn1;

	JPanel room2;

	JPanel P20;
	JLabel ID20;
	JLabel WIN20;

	JPanel P21;
	JLabel ID21;
	JLabel WIN21;

	JButton roombtn2;
	private JPanel panel;
	private JTextField textField;
	private ScrollPane scrollPane;
	private JButton btnNewButton;
	private JPanel panel_2;
	private JLabel label_2;
	private List Clientlist;

	JButton logout;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 * 
	 * @param toServer
	 */
	public GreenRoom(WithServer me)
	{
		this.toServer = me;
		init();
//		green();
		this.setVisible(visiFlag);
	}

	public void init()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 440, 432);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		room0 = new JPanel();
		room0.setBounds(5, 5, 414, 86);
		room0.setBackground(UIManager.getColor("Button.light"));
		contentPane.add(room0);
		room0.setLayout(null);

		P00 = new JPanel();
		P00.setBounds(12, 10, 140, 67);
		room0.add(P00);
		P00.setLayout(null);

		ID00 = new JLabel(textset[0]);
		ID00.setBounds(12, 10, 57, 15);
		P00.add(ID00);

		WIN00 = new JLabel(textset[1]);
		WIN00.setBounds(71, 42, 57, 15);
		P00.add(WIN00);

		P01 = new JPanel();
		P01.setBounds(180, 10, 140, 67);
		room0.add(P01);
		P01.setLayout(null);

		ID01 = new JLabel(textset[2]);
		ID01.setBounds(12, 10, 57, 15);
		P01.add(ID01);

		WIN01 = new JLabel(textset[3]);
		WIN01.setBounds(71, 42, 57, 15);
		P01.add(WIN01);

		JLabel lblVs = new JLabel("VS");
		lblVs.setBounds(156, 62, 23, 15);
		room0.add(lblVs);

		roombtn0 = new JButton("\uCC38\uAC00");
		roombtn0.setBounds(325, 10, 97, 67);
		room0.add(roombtn0);

		room1 = new JPanel();
		room1.setBounds(5, 93, 414, 86);
		contentPane.add(room1);
		room1.setLayout(null);

		P11 = new JPanel();
		P11.setBounds(180, 10, 140, 67);
		room1.add(P11);
		P11.setLayout(null);

		ID11 = new JLabel(textset[6]);
		ID11.setBounds(12, 10, 57, 15);
		P11.add(ID11);

		WIN11 = new JLabel(textset[7]);
		WIN11.setBounds(71, 42, 57, 15);
		P11.add(WIN11);

		P10 = new JPanel();
		P10.setBounds(12, 10, 140, 67);
		room1.add(P10);
		P10.setLayout(null);

		ID10 = new JLabel(textset[4]);
		ID10.setBounds(12, 10, 57, 15);
		P10.add(ID10);

		WIN10 = new JLabel(textset[5]);
		WIN10.setBounds(71, 42, 57, 15);
		P10.add(WIN10);

		JLabel label = new JLabel("VS");
		label.setBounds(155, 62, 23, 15);
		room1.add(label);

		roombtn1 = new JButton("\uCC38\uAC00");
		roombtn1.setBounds(325, 10, 97, 67);
		room1.add(roombtn1);

		room2 = new JPanel();
		room2.setBounds(5, 181, 414, 86);
		room2.setBackground(UIManager.getColor("Button.light"));
		contentPane.add(room2);
		room2.setLayout(null);

		P21 = new JPanel();
		P21.setBounds(180, 10, 140, 67);
		room2.add(P21);
		P21.setLayout(null);

		ID21 = new JLabel(textset[10]);
		ID21.setBounds(12, 10, 57, 15);
		P21.add(ID21);

		WIN21 = new JLabel(textset[11]);
		WIN21.setBounds(71, 42, 57, 15);
		P21.add(WIN21);

		P20 = new JPanel();
		P20.setBounds(12, 10, 140, 67);
		room2.add(P20);
		P20.setLayout(null);

		ID20 = new JLabel(textset[8]);
		ID20.setBounds(12, 10, 57, 15);
		P20.add(ID20);

		WIN20 = new JLabel(textset[9]);
		WIN20.setBounds(71, 42, 57, 15);
		P20.add(WIN20);

		JLabel label_1 = new JLabel("VS");
		label_1.setBounds(156, 62, 23, 15);
		room2.add(label_1);

		roombtn2 = new JButton("\uCC38\uAC00");
		roombtn2.setBounds(325, 10, 97, 67);
		room2.add(roombtn2);

		panel = new JPanel();
		panel.setBounds(5, 269, 178, 120);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setHgap(0);
		flowLayout.setVgap(0);
		panel.add(panel_1, BorderLayout.SOUTH);

		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(10);

		btnNewButton = new JButton("\uC804\uC1A1");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String sendstr = textField.getText();
				textField.setText("");
				toServer.sendMsg("/all " + sendstr + " /end");
			}
		});
		panel_1.add(btnNewButton);

		JLabel lblNewLabel = new JLabel("\uB300\uD654\uCC3D");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel, BorderLayout.NORTH);

		chattingField = new JTextArea();
		chattingField.setLineWrap(true);
		chattingField.setEditable(false);
		chattingField.setEnabled(false);

		scrollPane = new ScrollPane();
		scrollPane.add(chattingField);
		panel.add(scrollPane, BorderLayout.CENTER);

		panel_2 = new JPanel();
		panel_2.setBounds(184, 269, 143, 120);
		contentPane.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		label_2 = new JLabel("\uC811\uC18D\uC790");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(label_2, BorderLayout.NORTH);

		Clientlist = new List();
		panel_2.add(Clientlist, BorderLayout.CENTER);

		logout = new JButton("\uB85C\uADF8\uC544\uC6C3");
		logout.setBounds(329, 366, 90, 23);
		contentPane.add(logout);

		roombtn0.addActionListener(this);
		roombtn1.addActionListener(this);
		roombtn2.addActionListener(this);
		logout.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object o = e.getSource();
		String commend = "";
		if (o.equals(roombtn0))
		{
			commend = "/roomnum " + 0 + " /end";
		} else if (o.equals(roombtn1))
		{
			commend = "/roomnum " + 1 + " /end";
		} else if (o.equals(roombtn2))
		{
			commend = "/roomnum " + 2 + " /end";
		}
		if (o.equals(logout))
		{
			commend = "/logout logout /end";
		}
		System.out.println(commend);
		toServer.sendMsg(commend);
	}

	public void setInfo(String msg)
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				StringTokenizer tokens = new StringTokenizer(msg);
				Clientlist.removeAll();
				for (int i = 0; i < textset.length; i++)
				{
					textset[i] = tokens.nextToken();
				}

				ID00.setText(textset[0]);
				WIN00.setText(textset[1]);
				ID01.setText(textset[2]);
				WIN01.setText(textset[3]);

				ID10.setText(textset[4]);
				WIN10.setText(textset[5]);
				ID11.setText(textset[6]);
				WIN11.setText(textset[7]);

				ID20.setText(textset[8]);
				WIN20.setText(textset[9]);
				ID21.setText(textset[10]);
				WIN21.setText(textset[11]);

				while (tokens.hasMoreTokens())
				{
					Clientlist.add(tokens.nextToken());
				}

			}
		}).start();
	}

	public boolean visibleset(String msg)
	{
		if (msg.equals("access"))
		{
			System.out.println("gr access");
			visiFlag = !visiFlag;
			this.setVisible(visiFlag);
			return true;
		}
		return false;
	}

	public void chat(String msg)
	{
		chattingField.append(msg + "\n");
		chattingField.setCaretPosition(chattingField.getDocument().getLength());
	}

}
