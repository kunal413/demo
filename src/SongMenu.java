import  java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SongMenu {

    Scanner sc = new Scanner(System.in);
    Song song = new Song();
    List<Song> SongsDisplayList = new ArrayList<Song>();

    public List<Song> songs1(Connection con) throws SQLException {
        String query = "select s.song_id,s.song_name,s.song_duration,s.genres,s.singer_name,s.song_path,s.album_id,a.album_name from songs s join albums a on s.album_id = a.album_id";
        Statement stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery(query);//Navigate through records in result set
        while(resultSet.next()) {
            SongsDisplayList.add(new Song(resultSet.getInt("song_id"),
                            resultSet.getString("song_name"),
                            resultSet.getTime("song_duration"),
                            resultSet.getString("genres"),
                            resultSet.getString("singer_name"),
                            resultSet.getInt("album_id"),
                            resultSet.getString("album_name")
                    )
            );
        }
        return SongsDisplayList;

    }




    //To display All Songs
    public void viewAllSongs(Connection con) throws SQLException
    {
        List<Song> listDisplaySongs = songs1(con);
        System.out.format("%30s%30s%30s%30s%30s","SongId","Song Name","Album Name","Genre","SingerName\n");
        listDisplaySongs.stream()
                .sorted(Comparator.comparing(s -> s.getSongName()))
                .collect(Collectors.toList())
                .forEach((q)->System.out.format("%30s%30s%30s%30s%30s",""+q.getSongId(),""+q.getSongName(),""+q.getAlbumName(),""+q.getGenres(),""+q.getSingerName()+"\n"));

    }


    //Search Song By Category
    public void SearchSongByCategory(Connection con) throws SQLException {
        sc = new Scanner(System.in);
        System.out.println("How do you want to search");
        System.out.println("1.Search by Artist");
        System.out.println("2.Search by Genres");
        System.out.println("3.Search by Albums");

        int choice = sc.nextInt();

        switch (choice)
        {
            case 1:
                System.out.println("Enter the Artist Name");
                String artistName = sc.next();
                List<Song> list1 = songs1(con);
                SongByArtist(list1,artistName);
                break;
            case 2:
                System.out.println("Enter the Genres Name");
                String genresName = sc.next();
                List<Song> list2 = songs1(con);
                SongByGenres(list2,genresName);
                break;
            case 3:
                System.out.println("Enter the Album Name");
                String albumName = sc.next();
                List<Song> list3 = songs1(con);
                SongByAlbum(list3,albumName);
                break;
            default: System.out.println("Enter the choice in between 1-3");
                break;

        }

    }

    public void SongByArtist(List<Song> SongsDisplayList1,String artistName1)
    {
        Stream<Song> myStream = SongsDisplayList1.stream();
        List<Song> newListSongArtists = myStream
                .filter(p ->p.getSingerName().equalsIgnoreCase(artistName1))
                .sorted(Comparator.comparing(q->q.getSingerName()))
                .collect(Collectors.toList());

        newListSongArtists.forEach((q)->System.out.format("%30s%30s%10s%30s",""+q.getSongName(),""+q.getAlbumName(),""+q.getGenres(),""+q.getSingerName()+"\n"));
    }


    public void SongByGenres(List<Song> SongsDisplayList2,String genresName1)
    {
        Stream<Song> myStream = SongsDisplayList2.stream();
        List<Song> newListOfSongGenres = myStream
                .filter(s -> s.getGenres().equalsIgnoreCase(genresName1))
                .sorted(Comparator.comparing(q->q.getGenres()))
                .collect(Collectors.toList());

        newListOfSongGenres.forEach((q)->System.out.format("%30s%30s%10s%30s",""+q.getSongName(),""+q.getAlbumName(),""+q.getGenres(),""+q.getSingerName()+"\n"));
    }

    public void SongByAlbum(List<Song> SongsDisplayList3,String albumName1)
    {
        Stream<Song> myStream = SongsDisplayList3.stream();
        List<Song> newListOfSongAlbum = myStream
                .filter(s -> s.getAlbumName().equalsIgnoreCase(albumName1))
                .sorted(Comparator.comparing(q->q.getAlbumName()))
                .collect(Collectors.toList());

        newListOfSongAlbum.forEach((q)->System.out.format("%30s%30s%10s%30s",""+q.getSongName(),""+q.getAlbumName(),""+q.getGenres(),""+q.getSingerName()+"\n"));
    }

}