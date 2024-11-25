package com.sonny.spotifyclone.catalogcontext.application;

import com.sonny.spotifyclone.catalogcontext.application.dto.ReadSongInfoDTO;
import com.sonny.spotifyclone.catalogcontext.application.dto.SaveSongDTO;
import com.sonny.spotifyclone.catalogcontext.application.dto.SongContentDTO;
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

}
