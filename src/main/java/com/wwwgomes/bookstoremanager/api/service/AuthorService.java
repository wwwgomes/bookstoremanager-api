package com.wwwgomes.bookstoremanager.api.service;

import com.wwwgomes.bookstoremanager.api.dto.AuthorDTO;
import com.wwwgomes.bookstoremanager.api.mapper.AuthorMapper;
import com.wwwgomes.bookstoremanager.domain.repository.AuthorRepository;
import com.wwwgomes.bookstoremanager.exception.AuthorAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final static AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorDTO create(AuthorDTO authorDTO) {
        verifyIfExists(authorDTO.getName());

        var authorToCreate = authorMapper.toModel(authorDTO);
        var createdAuthor = authorRepository.save(authorToCreate);

        return authorMapper.toDTO(createdAuthor);
    }

    private void verifyIfExists(String authorName) {
        authorRepository.findByName(authorName)
                .ifPresent(author -> { throw new AuthorAlreadyExistsException(author.getName()); });
    }
}
