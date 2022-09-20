import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class JukeboxImpl {

    public static void main(String[] args) throws SQLException, UnsupportedAudioFileException, LineUnavailableException, IOException, ClassNotFoundException {
        Connection con = null;
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/SAANAG","root","Abhi9718@");
        }catch (SQLException se)
        {
            se.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        for(;;)
        {
            System.out.println("\nWELCOME TO Jukebox ->>> The Next Music");
            System.out.println("1.Exit");
            System.out.println("Enter your choice");
            int choice = sc.nextInt();
            try{
                switch (choice)
                {
                    case 1:
                        System.out.println("Exiting from main menu!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice");
                }
            }catch (InputMismatchException ip){
                ip.printStackTrace();
            }
        }
    }
}