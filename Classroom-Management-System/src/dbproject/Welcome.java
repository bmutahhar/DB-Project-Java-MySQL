package dbproject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Welcome extends JFrame {

    private JPanel contentPane;
    private JLabel background;
    private JLabel title;
    private JButton next;
    FromDB instanceOfDB;


    public Welcome() {

        instanceOfDB = new FromDB();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 450);
        setTitle("Classroom Management System");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        background = new JLabel(new ImageIcon("H:\\Eclipse Projects\\University-Database-System-master" +
                "\\University-Database-System-master\\classroom-2093744_960_720.jpg"));
        background.setBounds(0, 75, 700, 350);
        contentPane.add(background);

        title = new JLabel("CLASSROOM MANAGEMENT SYSTEM");
        title.setFont(new Font("Tahoma", Font.BOLD, 32));
        title.setForeground(Color.BLACK);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setBounds(0, 0, 700, 75);
        contentPane.add(title);

        next = new JButton();
        next.setText("Get Started");
        next.setFont(new Font("Tahoma", Font.BOLD, 13));
        next.setBounds(520, 365, 115, 21);
        contentPane.add(next);

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Login login = new Login();
                dispose();
                login.setVisible(true);
            }
        });

    }

    public static void main(String[] args) {
        Welcome welcome = new Welcome();
        welcome.setVisible(true);
    }

}
