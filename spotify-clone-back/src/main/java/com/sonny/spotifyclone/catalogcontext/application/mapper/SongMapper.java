package com.sonny.spotifyclone.catalogcontext.application.mapper;

import com.sonny.spotifyclone.catalogcontext.application.dto.ReadSongInfoDTO;
import com.sonny.spotifyclone.catalogcontext.application.dto.SaveSongDTO;
import com.sonny.spotifyclone.catalogcontext.application.vo.SongAuthorVO;
import com.sonny.spotifyclone.catalogcontext.application.vo.SongTitleVO;
import com.sonny.spotifyclone.catalogcontext.domain.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SongMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publicId", ignore = true)
    Song saveSongDTOToSong(SaveSongDTO saveSongDTO);

    //@Mapping(target = "isFavorite", ignore = true)
    ReadSongInfoDTO songToReadSongInfoDTO(Song song);

    default SongTitleVO stringToSongTitleVO(String songTitle) {
        return new SongTitleVO(songTitle);
    }

    default SongAuthorVO stringToSongAuthorVO(String author) {
        return new SongAuthorVO(author);
    }

    default String SongTitleVOToString(SongTitleVO title) {
        return title.value();
    }

    default String stringToSongAuthorVO(SongAuthorVO author) {
        return author.value();
    }
}
