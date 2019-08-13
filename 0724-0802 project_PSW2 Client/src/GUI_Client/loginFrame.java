package GUI_Client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Main.WithServer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JToggleButton;
import java.awt.BorderLayout;
import java.awt.Font;

public class loginFrame extends JFrame implements ActionListener
{

	private WithServer toServer = null;

	private JPanel contentPane;
	private JTextField idfield;
	private JButton btnLogin;
	private JButton btnJoin;
	private JLabel logininfo;

	boolean visiFlag = true;
	private JPasswordField passwordField;

	private boolean pwChk = true;
	private boolean idChk = true;
	private JPanel panel;
	private JPanel imageP;

	/**
	 * Launch the application.
	 * 
	 * @param me
	 */
	public loginFrame(WithServer me)
	{
		this.toServer = me;
		init();
		this.setVisible(visiFlag);
	}

	/**
	 * Create the frame.
	 * 
	 * @return
	 */
	public void init()
	{
		initSet();
		contentPane = new JPanel();
		setTitle("BomberMan");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 342, 462);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBounds(0, 340, 326, 84);
		contentPane.add(panel);
		panel.setLayout(null);

		idfield = new JTextField();
		idfield.setBounds(97, 10, 132, 21);
		panel.add(idfield);
		idfield.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				if (idfield.getText().contentEquals("id"))
				{
					idfield.setText("");
				}
			}
		});
		idfield.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent key)
			{
				if (idChk)
				{
					idfield.setText("");
					idChk = !idChk;
				}
			}
		});
		idfield.setColumns(10);
		idfield.setText("id");

		passwordField = new JPasswordField();
		passwordField.setBounds(97, 35, 132, 21);
		panel.add(passwordField);
		passwordField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent key)
			{
				if (key.getKeyCode() == 10)
				{
					Login();
				}
				if (pwChk)
				{
					passwordField.setText("");
					pwChk = !pwChk;
				}
			}
		});
		passwordField.setText("password");
		logininfo = new JLabel("");
		logininfo.setBounds(330, 16, 0, 0);
		panel.add(logininfo);
		logininfo.setHorizontalAlignment(SwingConstants.CENTER);

		btnJoin = new JButton("Join");
		btnJoin.setBounds(169, 57, 60, 23);
		btnJoin.addActionListener(this);
		panel.add(btnJoin);

		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("±¼¸²", Font.PLAIN, 12));
		btnLogin.setBounds(97, 57, 70, 23);
		btnLogin.addActionListener(this);
		panel.add(btnLogin);

		imageP = new JPanel()
		{
			public void paint(Graphics g)
			{
				try
				{
					Image mainImg = ImageIO.read(new File("image/MainLogin.png"));
					g.drawImage(mainImg, 0, 7, 326, 326, this);
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		};
		imageP.setBounds(0, 0, 326, 341);
		contentPane.add(imageP);
	}

	public void initSet()
	{
	}

	public void Login()
	{
		String commend = "";
		String pwd = "";
		char[] pwdCarr = passwordField.getPassword();
		for (int i = 0; i < pwdCarr.length; i++)
		{
			pwd += pwdCarr[i];
		}
		commend += "/login " + idfield.getText() + " " + pwd + " ";
		toServer.sendMsg(commend);
	}

	public boolean visibleset(String msg)
	{
		if (msg.equals("access"))
		{
			visiFlag = !visiFlag;
			this.setVisible(visiFlag);
			return true;
		} else
		{
			logininfo.setText("access denied");
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object btn = e.getSource();
		String commend = "";
		String pwd = "";
		char[] pwdCarr = passwordField.getPassword();
		for (int i = 0; i < pwdCarr.length; i++)
		{
			pwd += pwdCarr[i];
		}
		if (btnLogin.equals(btn))
		{
			commend += "/login " + idfield.getText() + " " + pwd + " /end";
		} else if (btnJoin.equals(btn))
		{
			commend += "/add " + idfield.getText() + " " + pwd + " /end";
		}
		System.out.println(commend);
		toServer.sendMsg(commend);
	}
}
