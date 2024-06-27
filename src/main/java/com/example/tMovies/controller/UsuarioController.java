package com.example.tMovies.controller;

import com.example.tMovies.dto.UsuarioDto;
import com.example.tMovies.model.UsuarioModel;
import com.example.tMovies.service.UsuarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UsuarioController {

    @Autowired
    UsuarioService service;


    @PostMapping
    public ResponseEntity save(@RequestBody @Valid UsuarioDto dto){
        var usuarioModel = new UsuarioModel();
        BeanUtils.copyProperties(dto, usuarioModel);
        usuarioModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuarioModel));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioModel>> getAllUser(){
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable(value = "id")Integer id){
        Optional<UsuarioModel> optional = service.findById(id);
        if(!optional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(optional.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity update (@PathVariable(value = "id")Integer id,
                                  @RequestBody @Valid UsuarioDto dto){
        Optional<UsuarioModel> optional = service.findById(id);
        if(!optional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        var usuarioModel = new UsuarioModel();
        BeanUtils.copyProperties(dto, usuarioModel);
        usuarioModel.setId(optional.get().getId());
        usuarioModel.setRegistrationDate(optional.get().getRegistrationDate());
        return ResponseEntity.status(HttpStatus.OK).body(service.save(usuarioModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable(value = "id")Integer id){
        Optional<UsuarioModel> optional = service.findById(id);
        if(!optional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        service.delete(optional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso");
    }
}