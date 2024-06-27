package com.example.tMovies.service;

import com.example.tMovies.model.UsuarioModel;
import com.example.tMovies.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioService {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    PasswordEncoder encoder;


    private void criptografarSenha(UsuarioModel usuario){
        String senha = usuario.getSenha();
        String senhaCripto = encoder.encode(senha);
        usuario.setSenha(senhaCripto);
    }

    public UsuarioModel autenticar(String email, String senha){
        Optional<UsuarioModel> optional = repository.findByEmail(email);
        if(!optional.isPresent()){

        }
    }

    @Transactional
    public UsuarioModel save(UsuarioModel usuarioModel) {
        validarEmail(usuarioModel.getEmail());
        criptografarSenha(usuarioModel);
        return repository.save(usuarioModel);
    }

    public List<UsuarioModel> findAll() {
        return repository.findAll();
    }

    public Optional<UsuarioModel> findById(Integer id) {
        return repository.findById(id);
    }

    @Transactional
    public void delete(UsuarioModel usuarioModel) {
        repository.delete(usuarioModel);
    }

    public void validarEmail(String email){
        boolean existe = repository.existsByEmail(email);
        if(existe){
            ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um usuário cadastrado com esse email ");
        }
    }
}
