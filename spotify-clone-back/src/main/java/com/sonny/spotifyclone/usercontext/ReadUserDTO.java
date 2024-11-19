package com.sonny.spotifyclone.usercontext;

public record ReadUserDTO(
        String firstName,
        String lastName,
        String email,
        String imageUrl
) {
}
