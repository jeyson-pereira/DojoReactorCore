package com.example.demo.services;

import java.util.Objects;

import com.example.demo.collections.Player;
import com.example.demo.repositories.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository repository;

    public Flux<Player> getAll() {
        return this.repository.findAll();
    }

    /**
     * Get all players, filter out the ones that don't have an age, and then filter out the ones that
     * are younger than 35.
     *
     * @return A Flux of players who are 35 or older.
     */
    public Flux<Player> getPlayersByAge(){
        return getAll()
                .filter(player -> Objects.nonNull(player.getAge()))
                .filter(player -> player.getAge() >= 35);
    }

    /**
     * > Get all players and filter by club
     *
     * @param club The club name to filter by
     * @return A Flux of Player objects
     */
    public Flux<Player> getPlayerAndFilterByClub(String club){
        return getAll()
                .filter(player -> Objects.nonNull(player.getClub()))
                .filter(player -> player.getClub().equalsIgnoreCase(club));
    }

    /**
     * "Get all players, filter out the ones that don't have an age, filter out the ones that are
     * younger than 35, filter out the ones that don't have a club, and filter out the ones that don't
     * play for the given club."
     *
     * This is a lot of filtering. And it's not very readable
     *
     * @param club The club name
     * @return A Flux of players
     */
    public Flux<Player> getPlayersByAgeAndClub(String club){
        return getAll()
                .filter(player -> Objects.nonNull(player.getAge()))
                .filter(player -> player.getAge() >= 35)
                .filter(player -> Objects.nonNull(player.getClub()))
                .filter(player -> player.getClub().equalsIgnoreCase(club));
    }

}
