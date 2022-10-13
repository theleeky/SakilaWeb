package com.sparta.sakilaweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.sakilaweb.dao.ActorDAO;
import com.sparta.sakilaweb.dto.ActorDTO;
import com.sparta.sakilaweb.entity.Actor;
import com.sparta.sakilaweb.repo.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("castList")
public class SakilaWebController {
    @Autowired
    private ActorRepository repo;
    private final ActorDAO actorDAO;


    public SakilaWebController(ActorDAO actorDAO) {
        this.actorDAO = actorDAO;
    }

//    @GetMapping("/sakila/testname")
//    public String testName(String nameParam, Model model){
//        model.addAttribute("nameAtribute", nameParam+" is awesome");
//        return "displayName";
//    }

    @GetMapping("/sakila/actor")
    public String findActor(int id, Model model){
        Actor result = repo.findById(id).get();
        model.addAttribute("actor", result);
        return "displayActor";
    }

    @GetMapping("/sakila")
    public String findAllActors(Model model){
        List<ActorDTO> allActors = actorDAO.getAllActors();
        model.addAttribute("allActors", allActors);
        return "index";
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
        System.out.println(actor.getFirstName());
        ActorDTO savedActor = actorDAO.addNewActor(actor);
        model.addAttribute("actor", savedActor);
        return "displayActor";
    }

    @GetMapping("sakila/actor/delete/{id}")
    public String deleteActor(@PathVariable int id, Model model){
        ActorDTO actor = actorDAO.getActorById(id);
        model.addAttribute("actor", actor);
        if (id > 200)
            return "actorToDelete";
        else
            return "redirect:/sakila";

    }

    @PostMapping("sakila/actor/delete")
    public String actorDeleted(@ModelAttribute Actor actor, Model model){
        System.out.println(actor.getId());
        ActorDTO deletedActor = actorDAO.deleteActor(actor.getId());
        model.addAttribute("actor", deletedActor);
        return "actorDeleted";
    }

    @GetMapping("/sakila/actor/edit/{id}")
    public String editActor(@PathVariable int id, Model model){
        ActorDTO actor = actorDAO.getActorById(id);
        model.addAttribute("actor", actor);
        return "editActor";
    }

    @PostMapping("/sakila/actor/edit")
    public String actorEdited(@ModelAttribute Actor actor, Model model){
        ActorDTO editedActor = actorDAO.update(actor);
        model.addAttribute("actor",actor);
        return "actorEdited";
    }

    @GetMapping("/sakila/cast/add/{id}")
    public String addCastMember(@PathVariable int id,
                                Model model,
                                @ModelAttribute("castList") List<ActorDTO> castList){
        ActorDTO actorToAdd = actorDAO.getActorById(id);
        if (!castList.contains(actorToAdd))
            castList.add(actorToAdd);
        System.out.println(castList);
        return "displayCastList";
    }

    @ModelAttribute("castList")
    public List<ActorDTO> castList(){
        System.out.println("Initializing the cast list...");
        return new ArrayList<>();
    }


//    @PostMapping("sakila/actor/form/{id}")
//    public String updateActor(@ModelAttribute Actor actor, @PathVariable int id, Model model){
//        Actor actorToUpdate = repo.findById(id).get();
//    }
//    @GetMapping("/login")
//    public String login(){
//        return "login";
//    }

//    @GetMapping("/accessDenied")
//    public String accessDenied(){
//        return "accessDenied";
//    }
}
