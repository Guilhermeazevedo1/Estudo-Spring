package com.mballen.demo_park_api.web.controller;

import com.mballen.demo_park_api.web.dto.UsuarioCreateDto;
import com.mballen.demo_park_api.entity.Usuario;
import com.mballen.demo_park_api.service.UsuarioService;
import com.mballen.demo_park_api.web.dto.UsuarioResponseDto;
import com.mballen.demo_park_api.web.dto.UsuarioSenhaDto;
import com.mballen.demo_park_api.web.dto.mapper.UsuarioMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto createDto){
        Usuario user = service.salvar(UsuarioMapper.toUsuario(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> buscarPorId(@PathVariable Long id){
        Usuario user = service.buscarPorId(id);
        return ResponseEntity.ok().body(UsuarioMapper.toDto(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@Valid @PathVariable Long id, @RequestBody UsuarioSenhaDto dto){
        Usuario user = service.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<UsuarioResponseDto>> getAll(){
        List<Usuario> users = service.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }
}
