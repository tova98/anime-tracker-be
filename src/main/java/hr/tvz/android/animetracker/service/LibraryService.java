package hr.tvz.android.animetracker.service;

import hr.tvz.android.animetracker.model.*;
import hr.tvz.android.animetracker.repository.AnimeRepository;
import hr.tvz.android.animetracker.repository.LibraryRepository;
import hr.tvz.android.animetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final UserRepository userRepository;
    private final AnimeRepository animeRepository;

    public List<Pair<Anime, Integer>> getUsersAnimeList(final Long userId) {
        final List<Pair<Anime, Integer>> returnList = new ArrayList<>();
        final List<Anime> animeList = animeRepository.getAllAnimeByUserId(userId);
        final List<Library> libraryList = libraryRepository.getUsersAnimeEpisodesWatched(userId);

        final Map<Long, Anime> animeMap = new HashMap<>();
        animeList.forEach(anime -> animeMap.put(anime.getId(), anime));

        libraryList.forEach(library -> returnList.add(Pair.of(animeMap.get(library.getId().getAnime().getId()), library.getEpisodesWatched())));
        return returnList;
    }

    public Integer getEpisodesWatchedCount(final Long userId) {
        return libraryRepository.getEpisodesWatchedByUser(userId);
    }

    public Integer getShowsWatchedCount(final Long userId) {
        return libraryRepository.getShowsWatchedByUser(userId);
    }

    public EResponse saveAnimeToLibrary(final Long userId, final Anime anime) {
        final Library library = new Library();
        final UserAnimePK userAnimePK = new UserAnimePK();

        final Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            return EResponse.ERROR;
        }

        userAnimePK.setUser(user.get());
        userAnimePK.setAnime(anime);

        if(libraryRepository.findById(userAnimePK).isPresent()) {
            return EResponse.ERROR;
        }

        library.setId(userAnimePK);
        library.setEpisodesWatched(0);
        libraryRepository.save(library);
        return EResponse.SUCCESS;
    }

    @Transactional
    public EResponse updateEpisodesWatched(final Long userId, final Long animeId, final Integer count) {
        libraryRepository.updateEpisodesWatched(count, userId, animeId);
        return EResponse.SUCCESS;
    }

    @Transactional
    public EResponse removeFromLibrary(final Long userId, final Long animeId) {
        libraryRepository.removeFromLibrary(userId, animeId);
        return EResponse.SUCCESS;
    }
}
