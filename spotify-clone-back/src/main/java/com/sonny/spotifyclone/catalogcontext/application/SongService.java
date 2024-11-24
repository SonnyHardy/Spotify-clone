package com.sonny.spotifyclone.catalogcontext.application;

import com.sonny.spotifyclone.catalogcontext.application.dto.ReadSongInfoDTO;
import com.sonny.spotifyclone.catalogcontext.application.dto.SaveSongDTO;
import com.sonny.spotifyclone.catalogcontext.application.mapper.SongContentMapper;
import com.sonny.spotifyclone.catalogcontext.application.mapper.SongMapper;
import com.sonny.spotifyclone.catalogcontext.domain.Song;
import com.sonny.spotifyclone.catalogcontext.domain.SongContent;
import com.sonny.spotifyclone.catalogcontext.repository.SongContentRepository;
import com.sonny.spotifyclone.catalogcontext.repository.SongRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class SongService {

    private final SongMapper songMapper;
    private final SongRepository songRepository;
    private final SongContentRepository songContentRepository;
    private final SongContentMapper songContentMapper;

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
        /*List<Song> songs = songRepository.findAll();
        for (Song song : songs) {
            log.info(String.valueOf(song));
            log.info(String.valueOf(songMapper.songToReadSongInfoDTO(song)));
        }*/
        return songRepository.findAll()
                .stream()
                .map(songMapper::songToReadSongInfoDTO)
                .toList();
    }

}
