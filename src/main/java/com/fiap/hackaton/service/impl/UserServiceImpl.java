package com.fiap.hackaton.service.impl;

import com.fiap.hackaton.domain.dto.user.UserLogin;
import com.fiap.hackaton.domain.dto.user.UserRequest;
import com.fiap.hackaton.domain.dto.user.UserResponse;
import com.fiap.hackaton.domain.entity.User;
import com.fiap.hackaton.domain.enums.Roles;
import com.fiap.hackaton.domain.exceptions.CredentialsException;
import com.fiap.hackaton.repository.UserRepository;
import com.fiap.hackaton.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public Long save(UserRequest request) {
        log.info("[UserServiceImpl] Recebendo dados para salvar um novo usuário: {}", request);
        User user = new User(request);
        this.repository.save(user);
        log.info("[UserServiceImpl] Usuário salvo com sucesso com ID: {}", user.getId());
        return user.getId();
    }

    @Override
    public User findUserEntityById(Long id) {
        log.info("[UserServiceImpl] Buscando usuário com ID: {}", id);
        return this.repository.findById(id)
                .orElseThrow(() -> {
                    log.error("[Service] Usuário com ID: {} não encontrado", id);
                    return new EntityNotFoundException("Usuário não encontrado");
                });
    }

    @Override
    public UserResponse findById(Long id) {
        log.info("[UserServiceImpl] Buscando usuário com ID: {}", id);
        User user = this.findUserEntityById(id);
        log.info("[UserServiceImpl] Usuário com ID: {} encontrado com sucesso", id);
        return new UserResponse(user);
    }

    @Override
    public Long update(Long id, UserRequest request) {
        log.info("[UserServiceImpl] Atualizando usuário com ID: {}", id);
        User user = this.findUserEntityById(id);
        user.update(request);
        this.repository.save(user);
        log.info("[UserServiceImpl] Usuário com ID: {} atualizado com sucesso", id);
        return user.getId();
    }

    @Override
    public String login(UserLogin login) {
        log.info("[UserServiceImpl] Tentativa de login para o email: {}", login.email());
        User user = this.repository.findByEmail(login.email())
                .orElseThrow(() -> {
                    log.error("[UserServiceImpl] Usuário com email: {} não encontrado", login.email());
                    return new EntityNotFoundException("Usuário não encontrado");
                });

        if (!user.getPassword().equals(login.password())) {
            log.error("[UserServiceImpl] Senha incorreta para o email: {}", login.email());
            throw new CredentialsException("Email ou senha inválida");
        }

        log.info("[UserServiceImpl] Login realizado com sucesso para o email: {}", login.email());
        return "token";
    }

    @Override
    @Transactional
    public void updateRole(Long id, Roles role) {
        log.info("[UserServiceImpl] Atualizando o papel do usuário com ID: {} para o papel: {}", id, role);
        User user = this.findUserEntityById(id);
        user.setRole(role);
        this.repository.save(user);
        log.info("[UserServiceImpl] Papel do usuário com ID: {} atualizado com sucesso para: {}", id, role);
    }
}
