package hr.tvz.android.animetracker.controller;

import hr.tvz.android.animetracker.model.Anime;
import hr.tvz.android.animetracker.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/anime")
public class AnimeController {

    private final AnimeService animeService;

    @GetMapping
    public List<Anime> getAllAnime() {
        return animeService.getAllAnime();
    }
}
