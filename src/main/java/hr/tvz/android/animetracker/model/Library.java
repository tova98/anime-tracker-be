package hr.tvz.android.animetracker.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Library {

    @EmbeddedId
    private UserAnimePK id;
    private Integer episodesWatched;
}
