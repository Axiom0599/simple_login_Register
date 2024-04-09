import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Arrays;

public class loginform extends JDialog{
    private JPanel loginpane;
    private JTextField login_name;
    private JButton forgotPasswordButton;
    private JButton exitButton;
    private JButton submitButton;
    private JButton clearButton;
    private JLabel Name;
    private JLabel Password;
    private JPasswordField login_password;

    Connection con;
    PreparedStatement pst;

    public void Connect() {
        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/registrations","postgres", "0000");
        } catch (ClassNotFoundException ex){
            ex.printStackTrace();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    public loginform(JFrame parent) {
        super(parent);
        setTitle("Login to your account");
        setLocationRelativeTo(parent);
        setContentPane(loginpane);
        setMinimumSize(new Dimension(450, 474));
        Connect();

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String uname = login_name.getText();
                String pass = new String(login_password.getPassword());;

                try {
                    pst=con.prepareStatement("select * from students where student_name=? AND student_password = ?");
                    pst.setString(1, uname);
                    pst.setString(2, pass);

                    ResultSet rs;
                    rs= pst.executeQuery();

                    if(rs.next()) JOptionPane.showMessageDialog(null, "Correct user!");
                    else JOptionPane.showMessageDialog(null, "Invalid User");

                    // Close ResultSet, PreparedStatement, and Connection
                    rs.close();
                    pst.close();
                    con.close();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login_name.setText(" ");
                login_password.setText("");
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String uname = login_name.getText();

                try {
                    pst = con.prepareStatement("SELECT student_password FROM students WHERE student_name=?");
                    pst.setString(1, uname);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        String password = rs.getString("student_password");
                        JOptionPane.showMessageDialog(null, "Your password is: " + password);
                    } else {
                        JOptionPane.showMessageDialog(null, "User not found");
                    }

                    // Close ResultSet and PreparedStatement
                    rs.close();
                    pst.close();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        loginform myForm1 = new loginform(null);
    }
}