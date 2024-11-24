package com.sonny.spotifyclone.catalogcontext.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "song_content")
@Data
public class SongContent implements Serializable {

    @Id
    @Column(name = "song_id")
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "song_id", referencedColumnName = "id")
    private Song song;

    @Lob
    @Column(name = "file", nullable = false)
    private byte[] file;

    @Column(name = "file_content_type")
    private String fileContentType;

}
