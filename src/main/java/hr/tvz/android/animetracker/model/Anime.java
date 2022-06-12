package hr.tvz.android.animetracker.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Anime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String image;
    private String studio;
    private Integer episodes;
    private String genres;
    private String airedDate;
}
