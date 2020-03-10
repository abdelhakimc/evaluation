package com.IPILYON.eval_app.controller.rest;

import com.IPILYON.eval_app.model.Commune;
import com.IPILYON.eval_app.repository.CommuneRepository;
import com.IPILYON.eval_app.service.CommuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/communes")
public class RestCommuneController {

    @Autowired
    CommuneService communeService;

    //id
    @GetMapping("/{id}")
    public Commune findCommuneById(@PathVariable("id") Long id){
        Optional<Commune> c = communeService.findCommuneById(id);
        if(c.isPresent()) {
            return c.get();
        }
        throw new EntityNotFoundException("Commune introuvable.");
    }


    //code postal
    @GetMapping(params = "codePostal")
    public List<Commune> searchCommuneByCodePostal(@RequestParam("codePostal") String codePostal) {
        if (communeService.searchCommuneByCodePostal(codePostal) != null) {
            return communeService.searchCommuneByCodePostal(codePostal);
        }
        throw new EntityNotFoundException("Commune introuvable.");
    }

    //code Insee
    @GetMapping(params = "codeInsee")
    public List<Commune> searchCommuneByCodeInsee(@RequestParam("codeInsee") String codeInsee) {
        if (communeService.searchCommuneByCodeInsee(codeInsee) != null) {
            return communeService.searchCommuneByCodeInsee(codeInsee);
        }
        throw new EntityNotFoundException("Commune introuvable.");
    }


    //nom
    @GetMapping(params = "nom")
    public Commune searchCommuneByNom(@RequestParam("nom") String nom) {
        if (communeService.searchCommuneByNom(nom) != null) {
            return communeService.searchCommuneByNom(nom);
        }
        throw new EntityNotFoundException("Commune introuvable.");
    }

    //comptage commune
    @GetMapping(value = "/count/commune")
    public Long countCommune() {
        return communeService.countAllCommune();
    }

    //comptage codeInsee
    @GetMapping(value = "/count/codeInsee")
    public Long countCodeInsee() {
        return communeService.countAllCodeInsee();
    }

    //comptage code postal
    @GetMapping(value = "/count/codePostal")
    public Long countCodePostal() {
        return communeService.countAllCodePostal();
    }


    //pagination 1
    @GetMapping(params = {"page", "size", "sortDirection", "sortProperty"} )
    public Page<Commune> paginationAllCommune(
            @RequestParam("page") Integer nbpage,
            @RequestParam("size") Integer size,
            @RequestParam("sortDirection") String sortDirection,
            @RequestParam("sortProperty") String sortProperty
    ){
        return communeService.findAllCommune(nbpage, size, sortDirection, sortProperty);
    }

    //pagination 2
    @GetMapping(value = "commune/voisine/", params = {"page", "size", "sortDirection", "sortProperty"} )
    public Page<Commune> communeVoisine(
            @RequestParam("page") Integer nbpage,
            @RequestParam("size") Integer size,
            @RequestParam("sortDirection") String sortDirection,
            @RequestParam("sortProperty") String sortProperty
    ){
        return communeService.findAllCommune(nbpage, size, sortDirection, sortProperty);
    }

    //création-mis à jour commune
    @RequestMapping(value = "/createOrUpdateCommune", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void createOrUpdateCommune(@RequestParam(name = "") Commune c) {
        communeService.createOrUpdateCommune(c, true);
    }

    //suppression commune
    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommune(@PathVariable("id") Long id) {
            communeService.deleteCommune(id);
    }

    //recherche associée
    @RequestMapping(value = "/searchProposition", method = RequestMethod.GET)
    public List<String> searchProposition(
            @RequestParam(name = "NomCommune", defaultValue = "") String nomCommune,
            @RequestParam(name = "CodePostal", defaultValue = "") String codePostal,
            @RequestParam(name = "CodeInsee", defaultValue = "") String codeInsee){
        if(!nomCommune.equals("")){
            return communeService.getNomCommuneProposition(nomCommune);
        } else if(!codePostal.equals("")){
            return communeService.getCodePostalProposition(codePostal);
        } else if(!codeInsee.equals("")) {
            return communeService.getCodeInseeProposition(codeInsee);
        } else {
            return Collections.emptyList();
        }
    }
}
