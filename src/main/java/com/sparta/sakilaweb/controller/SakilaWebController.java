package com.sparta.sakilaweb.controller;

import com.sparta.sakilaweb.entity.Actor;
import com.sparta.sakilaweb.repo.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Instant;
import java.util.List;

@Controller
public class SakilaWebController {
    @Autowired
    private ActorRepository repo;

    @GetMapping("/sakila/testname")
    public String testName(String nameParam, Model model){
        model.addAttribute("nameAtribute", nameParam+" is awesome");
        return "displayName";
    }

    @GetMapping("/sakila/actor")
    public String findActor(int id, Model model){
        Actor result = repo.findById(id).get();
        model.addAttribute("actor", result);
        return "displayActor";
    }

    @GetMapping("sakila/actor/all")
    public String findAllActors(Model model){
        List<Actor> allActors = repo.findAll();
        model.addAttribute("allActors", allActors);
        return "displayAllActor";
    }

    @GetMapping("sakila/actor/id/{id}")
    public String findActorById(@PathVariable int id, Model model){
        Actor actor = repo.findById(id).get();
        model.addAttribute("actor", actor);
        return "displayActor";
    }

    @GetMapping("sakila/actor/form")
    public String actorForm(Model model) {
        Actor actor = new Actor();
        model.addAttribute("actor", actor);
        return "actorForm";
    }

    @PostMapping("sakila/actor/form")
    public String createActor(@ModelAttribute Actor actor, Model model){
        actor.setLastUpdate(Instant.now());
        repo.save(actor);
        Actor newActor = repo.findById(actor.getId()).get();
        model.addAttribute("actor", newActor);
        return "displayActor";
    }

    @PostMapping("sakila/actor/form/{id}")
    public String updateActor(@ModelAttribute Actor actor, @PathVariable int id, Model model){
        Actor actorToUpdate = repo.findById(id).get();

    }

}
