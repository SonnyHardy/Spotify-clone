package com.sonny.spotifyclone.catalogcontext.application.dto;

import com.sonny.spotifyclone.catalogcontext.application.vo.SongAuthorVO;
import com.sonny.spotifyclone.catalogcontext.application.vo.SongTitleVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record SaveSongDTO(
        @Valid SongTitleVO songTitleVO,
        @Valid SongAuthorVO songAuthorVO,
        @NotNull byte[] cover,
        @NotNull String coverContentType,
        @NotNull byte[] file,
        @NotNull String fileContentType
        ) {
}