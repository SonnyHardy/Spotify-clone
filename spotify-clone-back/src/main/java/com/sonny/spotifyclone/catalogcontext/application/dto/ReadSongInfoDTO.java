package com.sonny.spotifyclone.catalogcontext.application.dto;

import com.sonny.spotifyclone.catalogcontext.application.vo.SongAuthorVO;
import com.sonny.spotifyclone.catalogcontext.application.vo.SongTitleVO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ReadSongInfoDTO {

    private SongTitleVO title;

    private SongAuthorVO author;

    @NotNull
    private byte[] cover;

    @NotNull
    private String coverContentType;

    @NotNull
    private boolean isFavorite;

    @NotNull
    private UUID publicId;
}
