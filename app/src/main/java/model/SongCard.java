package model;

public class SongCard {
    private Integer songLogo;
    private String songName;

    public SongCard(Integer songLogo, String songName) {
        this.songLogo = songLogo;
        this.songName = songName;
    }

    public Integer getSongLogo() {
        return songLogo;
    }

    public String getSongName() {
        return songName;
    }
}
