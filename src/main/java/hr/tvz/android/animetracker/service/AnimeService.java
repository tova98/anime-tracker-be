package hr.tvz.android.animetracker.service;

import hr.tvz.android.animetracker.model.Anime;
import hr.tvz.android.animetracker.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    public List<Anime> getAllAnime() {
        return animeRepository.findAllByOrderByTitle();
    }
}
