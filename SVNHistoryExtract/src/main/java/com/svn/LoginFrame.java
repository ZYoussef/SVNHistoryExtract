package artifact;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JSeparator;

public class LoginFrame
    extends JFrame implements ActionListener
{

    private JFrame frame;

    private JTextField login;

    private JTextField release;
    
    private JList svnHistory;
    private JList dataChosen;
    JTextArea emailText = new JTextArea();
    SvnManager svnMng;

    /**
     * Return frame.
     * @return frame
     */
    public JFrame getFrame()
    {
        return frame;
    }

    /**
     * Set frame.
     * @param frame frame
     */
    public void setFrame(JFrame frame)
    {
        this.frame = frame;
    }

    /**
     * Return login.
     * @return login
     */
    public String getLogin()
    {
        return login.getText();
    }

    /**
     * Set login.
     * @param login login
     */
    public void setLogin(String login)
    {
        this.login.setText(login);
    }

    /**
     * Return release.
     * @return release
     */
    public String getRelease()
    {
        return release.getText();
    }

    /**
     * Set release.
     * @param release release
     */
    public void setRelease(String release)
    {
        this.release.setText(release);
    }

    /**
     * Return name.
     * @return name
     */
    @Override
    public String getName()
    {
        return name.getText();
    }

    /**
     * Set name.
     * @param name name
     */
    @Override
    public void setName(String name)
    {
        this.name.setText(name);
    }

    /**
     * Return password.
     * @return password
     */
    public String getPassword()
    {
        return password.getText();
    }

    /**
     * Set password.
     * @param password password
     */
    public void setPassword(JPasswordField password)
    {
        this.password = password;
    }

    /**
     * Return from.
     * @return from
     */
    public JTextField getFrom()
    {
        return from;
    }

    /**
     * Set from.
     * @param from from
     */
    public void setFrom(JTextField from)
    {
        this.from = from;
    }

    /**
     * Return to.
     * @return to
     */
    public JTextField getTo()
    {
        return to;
    }

    /**
     * Set to.
     * @param to to
     */
    public void setTo(JTextField to)
    {
        this.to = to;
    }
    

    private JTextField name;

    private JPasswordField password;

    private JTextField from;

    private JTextField to;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    LoginFrame window = new LoginFrame();
                    window.frame.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public LoginFrame()
    {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
        frame = new JFrame();
        frame.setBounds(100, 100, 1530, 489);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        login = new JTextField();
        login.setToolTipText("SVN login");
        login.setBounds(158, 34, 155, 20);
        frame.getContentPane().add(login);
        login.setColumns(10);

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setBounds(35, 37, 83, 14);
        frame.getContentPane().add(loginLabel);

        JLabel releaseLabel = new JLabel("Release version");
        releaseLabel.setBounds(35, 210, 111, 14);
        frame.getContentPane().add(releaseLabel);

        release = new JTextField();
        release.setToolTipText("Ex : 7.7.2.48");
        release.setBounds(158, 207, 155, 20);
        frame.getContentPane().add(release);
        release.setColumns(10);

        JButton btnClear = new JButton("Clear");

        btnClear.setBounds(94, 332, 89, 23);
        frame.getContentPane().add(btnClear);

        JLabel versionEpeLabel = new JLabel("Version EPE");
        versionEpeLabel.setBounds(35, 160, 111, 14);
        frame.getContentPane().add(versionEpeLabel);

        final JComboBox<String> versionEpeComboBox = new JComboBox<String>();
        versionEpeComboBox.addItem("Select");
        versionEpeComboBox.addItem("3.0.5a");
        versionEpeComboBox.addItem("3.0.5b");
        versionEpeComboBox.addItem("3.10a.1");
        versionEpeComboBox.addItem("3.10b cons");
        versionEpeComboBox.addItem("3.11");
        versionEpeComboBox.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
            }
        });
        versionEpeComboBox.setBounds(158, 160, 155, 20);
        frame.getContentPane().add(versionEpeComboBox);

        JButton btnSubmit = new JButton("Submit"); 	

        btnSubmit.setBackground(Color.WHITE);
        btnSubmit.setForeground(Color.DARK_GRAY);
        btnSubmit.setBounds(224, 332, 89, 23);
        frame.getContentPane().add(btnSubmit);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(35, 80, 83, 14);
        frame.getContentPane().add(passwordLabel);

        JLabel nameLabel = new JLabel("Sender's name");
        nameLabel.setBounds(35, 117, 111, 14);
        frame.getContentPane().add(nameLabel);

        name = new JTextField();
        name.setColumns(10);
        name.setBounds(158, 114, 155, 20);
        frame.getContentPane().add(name);

        password = new JPasswordField();
        password.setToolTipText("SVN password");
        password.setBounds(158, 77, 155, 20);
        frame.getContentPane().add(password);

        JLabel dateFromLabel = new JLabel("(Date) From");
        dateFromLabel.setBounds(35, 241, 80, 14);
        frame.getContentPane().add(dateFromLabel);

        from = new JTextField();
        from.setToolTipText("dd/mm/yy");
        from.setColumns(10);
        from.setBounds(158, 238, 155, 20);
        frame.getContentPane().add(from);

        JLabel dateToLabel = new JLabel("(Date) To");
        dateToLabel.setBounds(35, 277, 80, 14);
        frame.getContentPane().add(dateToLabel);

        to = new JTextField();
        to.setToolTipText("dd/mm/yy");
        to.setColumns(10);
        to.setBounds(158, 274, 155, 20);
        frame.getContentPane().add(to);
        
        JLabel lblSvnHistory = new JLabel("SVN history");
        lblSvnHistory.setFont(new Font("MS Reference Sans Serif", Font.ITALIC, 16));
        lblSvnHistory.setBounds(522, 13, 167, 20);
        frame.getContentPane().add(lblSvnHistory);
        
        final DefaultListModel sv = new DefaultListModel();
        final DefaultListModel d = new DefaultListModel();
        dataChosen = new JList();
        dataChosen.setModel(d);
        
        JButton generate = new JButton(">>");
        generate.setFont(new Font("Tahoma", Font.PLAIN, 25));
        generate.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// recup de données selectionnées 
        		//List<String> str = svnHistory.getSelectedValuesList();
        		generateEmail();
        		/*for(String s : str)
        		{
        			System.out.println(str.toString());
        			emailText.setText(str.toString());
        		}*/
        		
        	}
        });
        generate.setForeground(Color.WHITE);
        generate.setBackground(Color.RED);
        generate.setBounds(782, 134, 89, 40);
        frame.getContentPane().add(generate);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(373, 34, 396, 395);
        frame.getContentPane().add(scrollPane);
        svnHistory = new JList();
        scrollPane.setViewportView(svnHistory);
        svnHistory.setModel(sv);
        
        JScrollPane scrollPaneForEmailText = new JScrollPane();
        scrollPaneForEmailText.setBounds(883, 34, 593, 395);
        frame.getContentPane().add(scrollPaneForEmailText);
        
        
        scrollPaneForEmailText.setViewportView(emailText);
        
        JLabel lblDeliveryEmail = new JLabel("Delivery email");
        lblDeliveryEmail.setFont(new Font("MS Reference Sans Serif", Font.ITALIC, 16));
        lblDeliveryEmail.setBounds(1138, 13, 167, 20);
        frame.getContentPane().add(lblDeliveryEmail);
        

        
        
      
        btnSubmit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                if (login.getText().isEmpty() || (password.getText().isEmpty()) || (release.getText().isEmpty())
                    || (from.getText().isEmpty()) || (versionEpeComboBox.getSelectedItem().equals("Select")))
                {
                    JOptionPane.showMessageDialog(null, "Data Missing", "Warning", JOptionPane.WARNING_MESSAGE);
                }
                else
                {
                    // appel SVN et recup de données : Ajout des données à la JList
                	svnMng = new SvnManager();
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
                    sv.addElement("Commit 2 ");
                    sv.addElement("Commit 3 ");
                    sv.addElement("Commit 1 ");
  
                }
            }
        });

        btnClear.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                release.setText(null);
                password.setText(null);
                login.setText(null);
                name.setText(null);
                release.setText(null);
                from.setText(null);
                to.setText(null);
                versionEpeComboBox.setSelectedItem("Select");

            }
        });

    }
    public void generateEmail()
    {
    	SvnManager svnMng = new SvnManager();
    	emailText.append("Goodbye Cruel World\n");
		emailText.append(svnMng.getResult());
    }
   

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
