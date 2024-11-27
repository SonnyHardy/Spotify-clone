package com.sonny.spotifyclone.catalogcontext.application;

import com.sonny.spotifyclone.catalogcontext.application.dto.FavoriteSongDTO;
import com.sonny.spotifyclone.catalogcontext.application.dto.ReadSongInfoDTO;
import com.sonny.spotifyclone.catalogcontext.application.dto.SaveSongDTO;
import com.sonny.spotifyclone.catalogcontext.application.dto.SongContentDTO;
import com.sonny.spotifyclone.catalogcontext.application.mapper.SongContentMapper;
import com.sonny.spotifyclone.catalogcontext.application.mapper.SongMapper;
import com.sonny.spotifyclone.catalogcontext.domain.Favorite;
import com.sonny.spotifyclone.catalogcontext.domain.FavoriteId;
import com.sonny.spotifyclone.catalogcontext.domain.Song;
import com.sonny.spotifyclone.catalogcontext.domain.SongContent;
import com.sonny.spotifyclone.catalogcontext.repository.FavoriteRepository;
import com.sonny.spotifyclone.catalogcontext.repository.SongContentRepository;
import com.sonny.spotifyclone.catalogcontext.repository.SongRepository;
import com.sonny.spotifyclone.infrastructure.service.dto.State;
import com.sonny.spotifyclone.infrastructure.service.dto.StateBuilder;
import com.sonny.spotifyclone.usercontext.ReadUserDTO;
import com.sonny.spotifyclone.usercontext.application.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class SongService {

    private final SongMapper songMapper;
    private final SongRepository songRepository;
    private final SongContentRepository songContentRepository;
    private final SongContentMapper songContentMapper;
    private final UserService userService;
    private final FavoriteRepository favoriteRepository;

    public ReadSongInfoDTO create(SaveSongDTO saveSongDTO) {
        Song song = songMapper.saveSongDTOToSong(saveSongDTO);
        Song songSaved = songRepository.save(song);

        SongContent songContent = songContentMapper.saveSongDTOToSong(saveSongDTO);
        songContent.setSong(songSaved);
        songContentRepository.save(songContent);
        return songMapper.songToReadSongInfoDTO(songSaved);
    }

    @Transactional(readOnly = true)
    public List<ReadSongInfoDTO> getAll() {
        return songRepository.findAll()
                .stream()
                .map(songMapper::songToReadSongInfoDTO)
                .toList();
    }

    public Optional<SongContentDTO> getOneByPublicId(UUID publicId) {
        Optional<SongContent> songByPublicId = songContentRepository.findOneBySongPublicId(publicId);
        return songByPublicId.map(songContentMapper::songContentToSongContentDTO);
    }

    public List<ReadSongInfoDTO> search(String searchTerm) {
        return songRepository.findByTitleOrAuthorContaining(searchTerm)
                .stream()
                .map(songMapper::songToReadSongInfoDTO)
                .collect(Collectors.toList());
    }

    public State<FavoriteSongDTO, String> addOrRemoveFromFavorite(FavoriteSongDTO favoriteSongDTO, String email) {
        StateBuilder<FavoriteSongDTO, String> builder = State.builder();
        Optional<Song> songToLikeOpt = songRepository.findOneByPublicId(favoriteSongDTO.publicId());
        if (songToLikeOpt.isEmpty()) {
            return builder.forError("Song public id doesn't exist").build();
        }

        Song songToLike = songToLikeOpt.get();
        ReadUserDTO userWhoLikedSong = userService.getByEmail(email).orElseThrow();
        if (favoriteSongDTO.favorite()) {
            Favorite favorite = new Favorite();
            favorite.setSongPublicId(songToLike.getPublicId());
            favorite.setUserEmail(userWhoLikedSong.email());
            favoriteRepository.save(favorite);

        }else {
            FavoriteId favoriteId = new FavoriteId(songToLike.getPublicId(), userWhoLikedSong.email());
            favoriteRepository.deleteById(favoriteId);
            favoriteSongDTO = new FavoriteSongDTO(false, songToLike.getPublicId());
        }

        return builder.forSuccess(favoriteSongDTO).build();
    }

    public List<ReadSongInfoDTO> fetchFavoriteSongs(String email) {
        return songRepository.findAllFavoriteByUserEmail(email)
                .stream()
                .map(songMapper::songToReadSongInfoDTO)
                .toList();
    }

}
