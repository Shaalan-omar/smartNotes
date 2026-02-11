package com.example.smartNotes.service;

import com.example.smartNotes.dto.CreateUserRequest;
import com.example.smartNotes.dto.NoteResponse;
import com.example.smartNotes.dto.UserResponse;
import com.example.smartNotes.exception.UserNotFoundException;
import com.example.smartNotes.model.Note;
import com.example.smartNotes.model.User;
import com.example.smartNotes.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user
                .getId())
                .email(user
                .getEmail())
                .username(user.getUsername())
                .notes(user.getNotes().stream()
                        .map(n -> NoteResponse.builder()
                                .id(n.getId())
                                .title(n.getTitle())
                                .content(n.getContent())
                                .userId(user.getId())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
    private final UserJpaRepository userRepository;

//    public UserResponse getUserDtoById(Long id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new UserNotFoundException(id));
//
//        return UserResponse.builder()
//                .id(user.getId())
//                .username(user.getUsername())
//                .email(user.getEmail())
//                .notes(
//                        user.getNotes().stream()
//                                .map(n -> NoteResponse.builder()
//                                        .id(n.getId())
//                                        .title(n.getTitle())
//                                        .content(n.getContent())
//                                        .userId(user.getId())
//                                        .build())
//                                .collect(java.util.stream.Collectors.toList())
//                )
//                .build();
//    }

    public UserResponse createUser(CreateUserRequest dto) {
        User tempUser = new User();
        tempUser.setUsername(dto.getUsername());
        tempUser.setEmail(dto.getEmail());
        userRepository.save(tempUser);
        return toResponse(tempUser);
    }

    public UserResponse getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return toResponse(user);
    }
}
