import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class PlayListOp {

    Scanner sc = new Scanner(System.in);
    String choice;



    public  void listenPlayList(Connection con) throws SQLException{
        System.out.println("1. Playlist of Song");
        System.out.println("2. playlist of Podcast");
        System.out.println("3. General playlist");
        System.out.println("4. Exit");
        // System.out.println("5. Exit");
        int acc=sc.nextInt();
        int a=0;
        while(a==0){
            switch (acc){
                case 1:
                    System.out.println("Select song which you want to play");
                    songPlaylist1(con);
                    break;
                case 2:
                    System.out.println("Select podcastId you want to play");
                    podcastPlaylist(con);
                    break;
                case 3:
                    System.out.println("1. Listen the Song");
                    System.out.println("2. Listen the Podcast");
                    System.out.println("3. Exit");
                    int val=sc.nextInt();
                    switch(val){
                        case 1:
                            System.out.format("%15s\n\n" ,"song_id");
                            String q="Select* from playlistsongs,playlistpodcasts where playlistsongs.playlist_id=playlistpodcasts.playlist_id";
                            Statement stmt= con.createStatement();
                            ResultSet rs=stmt.executeQuery(q);
                            while (rs.next()){
                                System.out.format("%15s",rs.getInt("song_id"));
                            }
                            songPlaylist1(con);
                            break;
                        case 2:
                            System.out.format("%15s\n\n" ,"podcast_id");
                            String q1="Select* from playlistsongs,playlistpodcasts where playlistsongs.playlist_id=playlistpodcasts.playlist_id";
                            Statement stmt2= con.createStatement();
                            ResultSet rs1=stmt2.executeQuery(q1);
                            while (rs1.next()){
                                System.out.format("%15s",rs1.getInt("podcast_id"));
                            }
                            podcastPlaylist(con);
                            break;
                        case 3:
                            System.exit(0);

                    }
                case 4:
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice");

            }
        }

    }

    public void songPlaylist1(Connection con){
        ListenMusic playingSongOp = new ListenMusic();
        System.out.println("Playlists are song :");


        try{
            String q ="Select* from playlists,playlistsongs where playlists.playlist_id=playlistsongs.playlist_id";
            Statement stmt = con.createStatement();
            ResultSet rs1 = stmt.executeQuery(q);
            System.out.format("%-10s %-30s %-15s %-15s ", "playlist_id", "playlist_type","login_id","Song_id");

            while (rs1.next()){
                System.out.format("%-10s",rs1.getInt("playlist_id"));
                System.out.format("%-30s",rs1.getString("playlist_type"));
                System.out.format("%-15s",rs1.getInt("login_id"));
                System.out.format("%-15s",rs1.getInt("Song_id"));
                System.out.println("\n");
            }
            //System.out.println("Song Name:"+song_Name);
            playingSongOp.songPlayFunction( con); // This method will play songs

        }
        catch (SQLException se){
            System.out.println(se);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void podcastPlaylist(Connection con){
        ListenMusic playingSongOp = new ListenMusic();

        System.out.println("Playlists  for podcast :");


        try{
            String q ="Select* from playlists,playlistpodcasts where playlists.playlist_id=playlistpodcasts.playlist_id";

            Statement stmt = con.createStatement();

            ResultSet rs1 = stmt.executeQuery(q);
            System.out.format("%-10s %-30s %-15s %-15s ", "playlist_id", "playlist_type","login_id","podcast_id");



            while (rs1.next()){
                System.out.format("%-10s",rs1.getInt("playlist_id"));
                System.out.format("%-30s",rs1.getString("playlist_type"));
                System.out.format("%-15s",rs1.getInt("login_id"));
                System.out.format("%-15s",rs1.getInt("podcast_id"));
                System.out.println("\n");
            }
            playingSongOp.podcastPlayFuncation( con); // This method will play songs

        }
        catch (SQLException se){
            System.out.println(se);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }




    public void add(Connection con) throws SQLException {
        System.out.println("\nWhat you want to add: 1.Songs   2.Podcasts  3.Couple");
        int choice = sc.nextInt();
        System.out.println("Enter your user id");
        int userId1 = sc.nextInt();
        String query1 = "select playlist_id,playlist_type,login_id from playlists";
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery(query1);

        if (choice == 1)
        {
            int pid = 0;
            System.out.println("Enter the song id you want to add");
            int songId = sc.nextInt();
            while (resultSet.next()) {
                if(userId1 == resultSet.getInt("login_id"))
                {
                    pid = resultSet.getInt("playlist_id");
                }

            }
            InsertingSongToPlaylist(con, pid, songId);
        }
        else if (choice == 2)
        {
            int pid = 0;
            System.out.println("Enter the podcast id you want to add");
            int podcastId = sc.nextInt();
            while (resultSet.next()) {
                //pid = resultSet.getInt("playlist_id");
                if(userId1 == resultSet.getInt("login_id"))
                {
                    pid = resultSet.getInt("playlist_id");
                }

            }
            InsertingPodcastToPlaylist(con, pid, podcastId);
        }
        else if (choice == 3)
        {
            int pid=0;
            while (resultSet.next())
            {
                if(userId1 == resultSet.getInt("login_id"))
                {
                    pid = resultSet.getInt("playlist_id");
                }

            }
            System.out.println("Enter the song id you want to add");
            int songId = sc.nextInt();
            InsertingSongToPlaylist(con, pid, songId);
            System.out.println("Enter the podcast id you want to add");
            int podcastId = sc.nextInt();
            InsertingPodcastToPlaylist(con, pid, podcastId);
        }
        else
        {
            System.out.println("Invalid Playlist type..!");
        }
    }

    public void InsertingSongToPlaylist(Connection con, int playlistId, int songId) throws SQLException {
        String query1 = "insert into playlistsongs(playlist_id, song_id) values (?,?)";
        PreparedStatement pstmt1 = con.prepareStatement(query1);
        pstmt1.setInt(1, playlistId);
        pstmt1.setInt(2, songId);
        int count = pstmt1.executeUpdate();
        if (count == 0) {
            System.out.println("Adding song to playlist is Failed...!");
        } else {
            System.out.println("Song has been added to playlist Successfully");
        }

    }

    public void InsertingPodcastToPlaylist(Connection con, int playlistId, int podcastId) throws SQLException {
        String query = "insert into playlistpodcasts(playlist_id,podcast_id) values (?,?)";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, playlistId);
        pstmt.setInt(2, podcastId);
        int count = pstmt.executeUpdate();
        if (count == 0) {
            System.out.println("Adding podcast to playlist is Failed...!");
        } else {
            System.out.println("Podcast has been added to playlist Successfully");
        }
    }

    public void playListByCategory(Connection con) throws SQLException {


        System.out.println("Enter your userId id");
        int userId = sc.nextInt();
        System.out.println("Select the type of playlist you want to create");
        System.out.println("Enter <Songs> to Create Playlist Of Songs");
        System.out.println("Enter <Podcasts> to Create Playlist Of Podcasts");
        System.out.println("Enter <Couple> to Create Playlist Of Songs And Podcasts");
        choice = sc.next();
        String query = "insert into playlists(playlist_type,login_id) values (?,?)";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, choice);
        pstmt.setInt(2, userId);
        int count = pstmt.executeUpdate();
        if (count == 0) {
            System.out.println("Playlist Creation Failed...!");
        } else {
            System.out.println("Playlist Created Successfully");
            add(con);
        }
    }



}