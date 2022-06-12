package hr.tvz.android.animetracker.controller;

import hr.tvz.android.animetracker.model.Anime;
import hr.tvz.android.animetracker.model.EResponse;
import hr.tvz.android.animetracker.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/library")
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/{userId}")
    public List<Pair<Anime, Integer>> getAllAnimeByUser(@PathVariable final Long userId) {
        return libraryService.getUsersAnimeList(userId);
    }

    @GetMapping("/episodes/{userId}")
    public Integer getEpisodesWatchedCount(@PathVariable final Long userId) {
        return libraryService.getEpisodesWatchedCount(userId);
    }

    @GetMapping("/shows/{userId}")
    public Integer getShowsWatchedCount(@PathVariable final Long userId) {
        return libraryService.getShowsWatchedCount(userId);
    }

    @PostMapping("/{userId}/{animeId}")
    public EResponse updateEpisodesWatched(@PathVariable final Long userId, @PathVariable final Long animeId, @RequestBody final Integer count) {
        return libraryService.updateEpisodesWatched(userId, animeId, count);
    }

    @PostMapping("/{userId}")
    public EResponse saveToLibrary(@PathVariable final Long userId, @RequestBody final Anime anime) {
        return libraryService.saveAnimeToLibrary(userId, anime);
    }

    @DeleteMapping("{userId}/{animeId}")
    public EResponse removeFromLibrary(@PathVariable final Long userId, @PathVariable final Long animeId) {
        return libraryService.removeFromLibrary(userId, animeId);
    }
}
