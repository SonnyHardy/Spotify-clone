package com.sonny.spotifyclone.catalogcontext.repository;

import com.sonny.spotifyclone.catalogcontext.domain.Favorite;
import com.sonny.spotifyclone.catalogcontext.domain.FavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {
    List<Favorite> findALlByUserEmailAndSongPublicIdIn(String email, List<UUID> songPublicIds);
}
