import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;


public class MainMenu {
    Scanner sc = null;
    SongMenu so = new SongMenu();
    PlayListOp pla = new PlayListOp();
    ListenMusic ps = new ListenMusic();

    public void HomePageMenus(Connection con) throws SQLException, UnsupportedAudioFileException, LineUnavailableException, IOException, ClassNotFoundException {
        sc = new Scanner(System.in);
        System.out.println("1:View All Songs:");
        System.out.println("2:Play a Song");
        System.out.println("3:Search Songs ");
        System.out.println("4:View All Podcasts:");
        System.out.println("5:Play a Podcast");
        System.out.println("6:Search Podcasts ");
        System.out.println("7:Create Playlists By Category");
        System.out.println("8:Add Songs or Podcasts or Couple to Playlist");
        System.out.println("9 :Listen Your Playlist");
        System.out.println("10 :Exit");
        System.out.println("What you want to do? Choose any correctOption..");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                so.viewAllSongs(con);
                break;
            case 2:
                ps.songPlayFunction(con);
                break;
            case 3:
                so.SearchSongByCategory(con);
                break;
            case 4:
                ps.podcastPlayFuncation(con);
                break;
            case 5:
                pla.playListByCategory(con);
                break;
            case 6:
                pla.add(con);
                break;
            case 7:
                pla.listenPlayList(con);
                break;
            case 8:
                System.out.println("Exiting from Main Menu");
                System.exit(0);
                break;
        }
    }
}