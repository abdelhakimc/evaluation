package com.IPILYON.eval_app.service;

import com.IPILYON.eval_app.model.Commune;
import com.IPILYON.eval_app.repository.CommuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import java.util.List;
import java.util.Optional;

@Service
public class CommuneService {

    @Autowired
    private CommuneRepository communeRepository;

    public List<Commune> findAll(){
        return communeRepository.findAll();
    }

    public Optional<Commune> findCommuneById(Long id){
        return communeRepository.findById(id);
    }

    public List<Commune> searchCommuneByCodeInsee(String codeInsee){
        return communeRepository.findByCodeInsee(codeInsee);
    }

    public List<Commune> searchCommuneByCodePostal(String codePostal){
        return communeRepository.findByCodePostal(codePostal);
    }

    public List<Commune> searchCommuneByNom(String nomCommune){
        return communeRepository.findByNomCommuneContainingIgnoreCase(nomCommune);
    }

    public List<String> getNomCommuneProposition(String nomCommune){
        return communeRepository.getNomCommuneProposition(nomCommune);
    }

    public List<String> getCodePostalProposition(String codePostal){
        return communeRepository.getCodePostalProposition(codePostal);
    }

    public List<String> getCodeInseeProposition(String codeInsee){
        return communeRepository.getCodeInseeProposition(codeInsee);
    }

    public Long countAllCommune(){
        return communeRepository.countAllById();
    }

    public Long countAllCodeInsee(){
        return communeRepository.countAllByCodeInsee();
    }

    public Long countAllCodePostal(){
        return communeRepository.countAllByCodePostal();
    }

    public Page<Commune> findAllCommune(Integer page, Integer size, String sortDirection, String sortProperty) {
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.fromString(sortDirection),sortProperty));
        Pageable pageable = PageRequest.of(page,size,sort);
        return communeRepository.findAll(pageable);
    }

    //suppression commune
    public void deleteCommune(Long id){
        communeRepository.deleteById(id);
    }

    //tri recherche communes
    public Page<Commune> findAllCommunes(Integer page, Integer size, String sortProperty, String sortDirection) {
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.fromString(sortDirection),sortProperty));
        Pageable pageable = PageRequest.of(page,size,sort);
        return communeRepository.findAll(pageable);
    }

    //création-mis à jour commune
    public Commune createOrUpdateCommune(Commune c, boolean api) {
        List<Commune> listcommune = searchCommuneByCodeInsee(c.getCodeInsee());
        if(listcommune.size() > 0){
            // si code INSEE présent en base
            for (Commune commune:listcommune) {
                //et correspondance nécessaire avec le nom et le code postal,
                if(c.getNomCommune().equals(commune.getNomCommune()) && c.getCodePostal().equals(commune.getCodePostal())){
                    //et ligne crée ait un attribut Ligne_5 différent de ceux qui existent.
                    if(communeRepository.countDistinctByLigne5(c.getLigne5()) == 0){
                        return communeRepository.save(c);
                    } else {
                        //ligne5 existe
                        if(api){
                            throw new IllegalArgumentException("La commune existe");
                        } else {
                            //TODO
                        }
                    }
                } else {
                    //Le nom de commune ou le code postal ne correspond pas
                    if(api){
                        throw new IllegalArgumentException("Erreur le nom de communes ou le code postal ne correspond pas");
                    } else {
                        //TODO
                    }
                }
            }
        }
        return communeRepository.save(c);
    }





}
