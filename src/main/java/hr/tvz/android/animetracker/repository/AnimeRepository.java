package hr.tvz.android.animetracker.repository;

import hr.tvz.android.animetracker.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {

    List<Anime> findAllByOrderByTitle();

    @Query(value = "SELECT ANIME.* FROM ANIME INNER JOIN LIBRARY ON LIBRARY.ANIME_ID = ANIME.ID WHERE LIBRARY.USER_ID = :USER_ID", nativeQuery = true)
    List<Anime> getAllAnimeByUserId(@Param("USER_ID") Long userId);
}
