package hr.tvz.android.animetracker.repository;

import hr.tvz.android.animetracker.model.Library;
import hr.tvz.android.animetracker.model.UserAnimePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepository extends JpaRepository<Library, UserAnimePK> {

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE LIBRARY SET EPISODES_WATCHED = :EPISODES_WATCHED WHERE USER_ID = :USER_ID AND ANIME_ID = :ANIME_ID", nativeQuery = true)
    void updateEpisodesWatched(@Param("EPISODES_WATCHED") Integer count, @Param("USER_ID") Long userId, @Param("ANIME_ID") Long animeId);

    @Query(value = "SELECT * FROM LIBRARY WHERE USER_ID = :USER_ID", nativeQuery = true)
    List<Library> getUsersAnimeEpisodesWatched(@Param("USER_ID") Long userId);

    @Query(value = "SELECT COALESCE(SUM(EPISODES_WATCHED), 0) FROM LIBRARY WHERE USER_ID = :USER_ID", nativeQuery = true)
    Integer getEpisodesWatchedByUser(@Param("USER_ID") Long userId);

    @Query(value = "SELECT COUNT(ANIME_ID) FROM LIBRARY WHERE USER_ID = :USER_ID", nativeQuery = true)
    Integer getShowsWatchedByUser(@Param("USER_ID") Long userId);

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM LIBRARY WHERE USER_ID = :USER_ID AND ANIME_ID = :ANIME_ID", nativeQuery = true)
    void removeFromLibrary(@Param("USER_ID") Long userId, @Param("ANIME_ID") Long animeId);
}
