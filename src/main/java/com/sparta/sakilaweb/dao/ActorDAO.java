package com.sparta.sakilaweb.dao;


import com.sparta.sakilaweb.dto.ActorDTO;
import com.sparta.sakilaweb.entity.Actor;
import com.sparta.sakilaweb.repo.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActorDAO {

    @Autowired
    private ActorRepository actorRepo;

    public ActorDAO(ActorRepository actorRepo) {
        this.actorRepo = actorRepo;
    }

    public ActorDTO getActorById(int id){
        Optional<Actor> foundActor = actorRepo.findById(id);
        if(foundActor.isPresent())
            return convertActor(foundActor.get());
        else
            return new ActorDTO(-1, null, null, null);
    }

    //READ
    public List<ActorDTO> getAllActors(){
        List<ActorDTO> actorDTOS = new ArrayList<>();
        List<Actor> actors = actorRepo.findAll();
        for(Actor actor: actors){
            actorDTOS.add(convertActor(actor));
        }
        return actorDTOS;
    }

    public ActorDTO addNewActor(Actor actor) {
        actor.setLastUpdate(Instant.now());
        Actor savedActor = actorRepo.save(actor);
        if(savedActor != null)
            return convertActor(savedActor);
        else
            return new ActorDTO(-1, null, null, null);
    }

    public ActorDTO deleteActor(int id) {
        Optional<Actor> foundActor = actorRepo.findById(id);
        if (foundActor.isPresent()) {
            actorRepo.delete(foundActor.get());
            return convertActor(foundActor.get());
        } else
            return new ActorDTO(-1, null, null, null);
    }

    public ActorDTO update(ActorDTO actorDTO) {
        Optional<Actor> optional = actorRepo.findById(actorDTO.getId());
        Actor theActor;
        if(optional.isPresent())
            theActor = optional.get();
        else
            return new ActorDTO(-1, null, null, null);

        if(actorDTO.getFirstName()!=null)
            theActor.setFirstName(actorDTO.getFirstName());
        if(actorDTO.getLastName()!=null)
            theActor.setLastName(actorDTO.getLastName());
        actorRepo.save(theActor);
        theActor = actorRepo.findById(actorDTO.getId()).get();
        return  new ActorDTO(theActor.getId(),
                theActor.getFirstName(), theActor.getLastName(), theActor.getLastUpdate());
    }


    private ActorDTO convertActor(Actor actor) {
        return new ActorDTO(actor.getId(), actor.getFirstName(), actor.getLastName(), actor.getLastUpdate());
    }
}
