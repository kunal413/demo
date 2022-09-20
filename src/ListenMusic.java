import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;





public class ListenMusic {
    Connection con;
    static int repeat=0,id=0;
    Clip clip;

    static MainMenu homepage =new MainMenu();

    public void songPlayFunction(Connection con) throws UnsupportedAudioFileException, IOException, LineUnavailableException, SQLException, ClassNotFoundException {

        Scanner scan=new Scanner(System.in);
        if(repeat==0)
        {
            System.out.println("enter the songid to play the song");
            id=scan.nextInt();
        }

        String query="select song_path from songs where song_id=?";
        PreparedStatement ps=con.prepareStatement(query);
        ps.setInt(1,id);

        ResultSet rs= ps.executeQuery();
        String song_path="";

        while(rs.next())
        {
            song_path=rs.getString(1);


        }




        File file=new File(song_path);
        AudioInputStream audiostream=AudioSystem.getAudioInputStream(file);
        clip= AudioSystem.getClip();
        clip.open(audiostream);
        clip.start();


        String response="";
        long duration = clip.getMicrosecondLength();
        duration = clip.getMicrosecondLength();
        long finalDuration = (duration / 1000000)*1000;      //converting into microsecond
        final Timer t = new Timer(1000, new ActionListener() {
            private long time = finalDuration ; //10 seconds, for example
            public void actionPerformed(ActionEvent e) {
                if (time >= 0) {
                    long s = ((time / 1000) % 60);   //converting into sec
                    long m = (((time / 1000) / 60) % 60); //converting into min
                    long h = ((((time / 1000) / 60) / 60) % 60);   //converting into hour
                    System.out.print("\r"+h + " hours, " + m + " minutes " + s + " seconds");//it moves the cursor to the beginning of the line
                    time -= 1000;
                }
            }
        });
        t.start();


        while (!response.equals("C"))
        {
            System.out.println("P=Play\n,S=Stop\n,R=Reset\n,N=Next song\n,L=Previous song\n,F=Forward\n,B=Backward\n,C=Close\n");
            System.out.println("enter your choice: ");
            response=scan.next();
            response=response.toUpperCase();

            try{
                switch (response)

                {
                    case("P"):
                        clip.start();
                        System.out.println("song play");

                        break;
                    case("S"): clip.stop();
                        System.out.println("song stop");
                        t.stop();
                        break;
                    case("R"):
                        t.stop();
                        clip.setMicrosecondPosition(0);
                        System.out.println("song restart");
                        break;
                    case ("N"):
                        t.stop();
                        id++;
                        repeat++;
                        clip.stop();
                        songPlayFunction(con);
                        System.out.println(" next song play");

                        break;
                    case ("L") :
                        t.stop();
                        id--;
                        repeat++;
                        clip.stop();
                        songPlayFunction(con);
                        System.out.println(" previous song play");

                        break;
                    case("F"):
                        clip.setMicrosecondPosition(clip.getMicrosecondPosition() + 10000000); // 10sec
                        break;
                    case("B"):
                        clip.setMicrosecondPosition(clip.getMicrosecondPosition() - 10000000);
                        break;
                    case("C"):
                        t.stop();
                        clip.stop();
                        System.out.println("Thanks for playing ");
                        homepage.HomePageMenus(con);
                        break;

                    default: System.out.println("not a valid response");

                }
            }catch (FileNotFoundException fe){
                fe.printStackTrace();
            }
        }
    }

    public  void podcastPlayFuncation(Connection con) throws UnsupportedAudioFileException, IOException, LineUnavailableException, SQLException, ClassNotFoundException {
//        Scanner scanner=new Scanner(System.in);
        try{
            Scanner scan=new Scanner(System.in);
            if(repeat==0)
            {
                System.out.println("enter the podcast ID. to play the podcast");
                id=scan.nextInt();
            }

            String query="select episode_path from episodes where podcast_id=?";
            PreparedStatement ps=con.prepareStatement(query);
            ps.setInt(1,id);

            ResultSet rs= ps.executeQuery();
            String episode_path="";
            while(rs.next())
            {
                episode_path=rs.getString(1);

            }


            File file=new File(episode_path);
            AudioInputStream audiostream=AudioSystem.getAudioInputStream(file);
            Clip clip=AudioSystem.getClip();
            clip.open(audiostream);
            clip.start();

            String response="";
            long duration = clip.getMicrosecondLength();
            duration = clip.getMicrosecondLength();
            long finalDuration = (duration / 1000000)*1000;      //converting into microsecond
            final Timer t = new Timer(1000, new ActionListener() {   //block lambda
                private long time = finalDuration ; //10 seconds, for example
                public void actionPerformed(ActionEvent e) {
                    if (time >= 0) {
                        long s = ((time / 1000) % 60);   //converting into sec
                        long m = (((time / 1000) / 60) % 60); //converting into min
                        long h = ((((time / 1000) / 60) / 60) % 60);   //converting into hour
                        System.out.print("\r"+h + " hours, " + m + " minutes " + s + " seconds");
                        time -= 1000;
                    }
                }
            });
            t.start();

            while (!response.equals("C"))
            {
                try{
                    System.out.println("P=Play\n,S=Stop\n,R=Reset\n,N=Next song\n,L=Previous song\n,F=Forward \n,B=Backward\n,C=Close\n");
                    System.out.println("enter your choice: ");
                    response=scan.next();
                    response=response.toUpperCase();

                    switch (response)
                    {
                        case("P"): clip.start();
                            System.out.println("podcast play");
                            break;
                        case("S"): clip.stop();
                            System.out.println("podcast stop");
                            t.stop();
                            break;
                        case("R"):
                            t.stop();
                            clip.setMicrosecondPosition(0);
                            System.out.println("podcast restart");

                            break;
                        case ("N"):
                            t.stop();
                            id++;
                            repeat++;
                            clip.stop();
                            podcastPlayFuncation(con);
                            System.out.println(" next podcast to play");

                            break;
                        case ("L") :
                            t.stop();
                            id--;
                            repeat++;
                            clip.stop();
                            podcastPlayFuncation(con);
                            System.out.println(" previous podcast to play");

                            break;
                        case("F"):
                            clip.setMicrosecondPosition(clip.getMicrosecondPosition() + 10000000); // 10sec
                            break;
                        case("B"):
                            clip.setMicrosecondPosition(clip.getMicrosecondPosition() - 10000000);//-10sec
                            break;
                        case("C"):
                            t.stop();
                            clip.close();
                            System.out.println("Thanks for playing ");
                            homepage.HomePageMenus(con);
                            break;
                        default: System.out.println("Invalid Response");

                    }
                }
                catch (FileNotFoundException fe){
                    fe.printStackTrace();
                }

            }
            System.out.println("Thanks for playing ");
        }catch(FileNotFoundException fe){
            fe.printStackTrace();
        }

    }
}