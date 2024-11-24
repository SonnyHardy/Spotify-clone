package com.sonny.spotifyclone.catalogcontext.application.dto;

import com.sonny.spotifyclone.catalogcontext.application.vo.SongAuthorVO;
import com.sonny.spotifyclone.catalogcontext.application.vo.SongTitleVO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
