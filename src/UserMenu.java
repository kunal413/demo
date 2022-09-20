import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class UserMenu {
    Scanner sc = null;

    MainMenu homePage = new MainMenu();
    public void ExistingUser(Connection con) throws SQLException,UnsupportedAudioFileException,LineUnavailableException,IOException,ClassNotFoundException
    {
        sc = new Scanner(System.in);
        System.out.println("Enter your UserId");
        String userId = sc.next();
        System.out.println("Enter your password");
        String password = sc.next();

        boolean flag=false;
        String checkUserName = "";
        String checkPassword = "";
        String query = "select login_id,login_password from userdetails";
        Statement stmt = con.createStatement();
        ResultSet result = stmt.executeQuery(query);
        while(result.next())
        {
            checkUserName = result.getString("login_id");
            checkPassword = result.getString("login_password");
            if(checkUserName.equalsIgnoreCase(userId) && checkPassword.equalsIgnoreCase(password))
            {
                flag=true;
                break;

            }
        }
        System.out.println();
        if(flag)
        {

            System.out.println("Valid Successfully Logged in.");
            homePage.HomePageMenus(con);
        }
        else
        {
            System.out.println("Invalid Username and Password");
        }

    }
    public void NewRegistration(Connection con) throws SQLException {
        sc = new Scanner(System.in);
        System.out.println("Enter your details below correctly");
        System.out.println("Enter your name");
        String userName = sc.next();
        System.out.println("Enter your phone number");
        String phoneNo = sc.next();
        System.out.println("Enter your email-id");
        String emailId = sc.next();
        System.out.println("Enter a strong  password");
        String password = sc.next();

        String query = "insert into userdetails(login_password,username,phonenumber,email) values(?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1,password);
        pstmt.setString(2,userName);
        pstmt.setString(3,phoneNo);
        pstmt.setString(4,emailId);

        //System.out.println("User log_in"+);
        int res = pstmt.executeUpdate();
        if(res==0)
        {
            System.out.println("Failed, give the correct details.");
        }
        else
        {
            System.out.println("Sign-Up Completed.");
            String query2="Select login_id from userdetails";
            Statement stmt5= con.createStatement();
            ResultSet rs5=stmt5.executeQuery(query2);
            int login_id=0;
            while (rs5.next()){
                login_id= rs5.getInt(1);
            }

            System.out.println("Your log_in Id"+login_id);
            System.out.println("Enjoy the JukeBox Now...");
        }

    }



    public boolean validatePassword(String s) {
        return false;
    }
}