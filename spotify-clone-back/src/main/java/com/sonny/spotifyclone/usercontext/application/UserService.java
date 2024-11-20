package com.sonny.spotifyclone.usercontext.application;

import com.sonny.spotifyclone.usercontext.ReadUserDTO;
import com.sonny.spotifyclone.usercontext.domain.User;
import com.sonny.spotifyclone.usercontext.mapper.UserMapper;
import com.sonny.spotifyclone.usercontext.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void syncWithIdp(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        User user = mapOauth2AttributesToUser(attributes);
        Optional<User> existingUser = userRepository.findOneByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            if (attributes.get("updated_at") != null) {
                Instant dbLastModifiedDate = existingUser.orElseThrow().getLastModifiedDate();
                Instant idpModifiedDate;
                if (attributes.get("updated_at") instanceof Instant) {
                    idpModifiedDate = (Instant) attributes.get("updated_at");
                }else {
                    idpModifiedDate = Instant.ofEpochSecond((Integer) attributes.get("updated_at"));
                }
                if (idpModifiedDate.isAfter(dbLastModifiedDate)) {
                    updateUser(user);
                }
            }

        }else {
            userRepository.saveAndFlush(user);
        }
    }

    public ReadUserDTO getAuthenticatedUserFromSecurityContext() {
        OAuth2User principal = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = mapOauth2AttributesToUser(principal.getAttributes());
        return userMapper.readUserDTOToUser(user);
    }

    private void updateUser(User user) {
        Optional<User> userToUpdateOpt = userRepository.findOneByEmail(user.getEmail());
        if (userToUpdateOpt.isPresent()) {
            User userToUpdate = userToUpdateOpt.get();  // we extract the user if he exists
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setImageUrl(user.getImageUrl());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setFirstName(user.getFirstName());
            userRepository.saveAndFlush(userToUpdate);
        }
    }

    private User mapOauth2AttributesToUser(Map<String, Object> oauth2Attributes) {
        User user = new User();
        String sub = String.valueOf(oauth2Attributes.get("sub"));

        String username = null;

        if (oauth2Attributes.get("preferred_username") != null) {
            username = String.valueOf(oauth2Attributes.get("preferred_username")).toLowerCase();
        }

        if (oauth2Attributes.get("given_name") != null) {
            user.setFirstName(String.valueOf(oauth2Attributes.get("given_name")));
        }else if (oauth2Attributes.get("name") != null){
            user.setFirstName(String.valueOf(oauth2Attributes.get("name")));
        }

        if (oauth2Attributes.get("family_name") != null) {
            user.setLastName(String.valueOf(oauth2Attributes.get("family_name")));
        }

        if (oauth2Attributes.get("email") != null) {
            user.setEmail((String)oauth2Attributes.get("email"));
        } else if (sub.contains("|") && username != null && username.contains("@")) {
            user.setEmail(username);
        }else user.setEmail(sub);

        if (oauth2Attributes.get("picture") != null) {
            user.setImageUrl((String)oauth2Attributes.get("picture"));
        }

        return user;
    }
}
