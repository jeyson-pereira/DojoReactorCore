package com.example.demo.controllers;

import com.example.demo.collections.Player;
import com.example.demo.services.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @Autowired
    private PlayerService service;

    @GetMapping()
    public Flux<Player> getPlayers(){
        return service.getAll().buffer(100).flatMap(x -> Flux.fromStream(x.parallelStream()));
    }

    @GetMapping("/age")
    public Flux<Player> getPlayersByAge(){
        return service.getPlayersByAge().buffer(100).flatMap(x -> Flux.fromStream(x.parallelStream()));
    }

    @GetMapping("/club/{name}")
    public Flux<Player> getPlayersByClub(@PathVariable String name){
        return service.getPlayerAndFilterByClub(name).buffer(100).flatMap(x -> Flux.fromStream(x.parallelStream()));
    }

    @GetMapping("/age/club/{name}")
    public Flux<Player> getPlayersByAgeAndClub(@PathVariable String name){
        return service.getPlayersByAgeAndClub(name).buffer(100).flatMap(x -> Flux.fromStream(x.parallelStream()));
    }
}
