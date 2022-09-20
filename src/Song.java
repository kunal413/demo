import java.sql.Time;

public class Song {

    private int songId;
    private String songName ;
    private String genres;
    private String singerName;
    private int albumId;
    private String albumName;
    private Time songDuration;



    public Song(int songId, String songName, Time songDuration, String genres, String singerName, int albumId, String albumName) {
        this.songId = songId;
        this.songName = songName;
        this.songDuration = songDuration;
        this.genres = genres;
        this.singerName = singerName;
        this.albumId = albumId;
        this.albumName = albumName;
    }

    public Song() {

    }

    public Song(int anInt, String string, String string1, String string2, int anInt1, Time time) {
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public Time getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(Time songDuration) {
        this.songDuration = songDuration;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
}