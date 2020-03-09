package com.IPILYON.eval_app.repository;

import com.IPILYON.eval_app.model.Commune;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CommuneRepository extends PagingAndSortingRepository<Commune, Long> {

    List<Commune> findByCodeInsee(String codeInsee);

    @Query(value="select count(*) from Communes", nativeQuery = true)
    public Long countAllById();

    @Query(value="select count(*) from Communes c where c.code_commune_INSEE is not null", nativeQuery = true)
    Long countAllByCodeInsee();

    List<Commune> findByCodePostal(String codePostal);

    @Query(value="select count(*) from Communes c where c.code_postal is not null", nativeQuery = true)
    Long countAllByCodePostal();

    Commune findByNomCommuneContainingIgnoreCase(String nomCommune);

    @Query(value="select count(*) from Communes c where c.ligne_5 like ?1", nativeQuery = true)
    Long countDistinctByLigne5(String ligne5);

    @Query(value="select c.nom_commune from Communes c where c.nom_commune like ?1% order by c.nom_commune ASC limit 0,5", nativeQuery = true)
    List<String> getNomCommuneProposition(String nomCommune);

    @Query(value="select distinct c.code_postal from Communes c where c.code_postal like ?1% order by c.code_postal ASC limit 0,5", nativeQuery = true)
    List<String> getCodePostalProposition(String codePostal);

    @Query(value="select distinct c.code_commune_INSEE from Communes c where c.code_commune_INSEE like ?1% order by c.code_commune_INSEE ASC limit 0,5", nativeQuery = true)
    List<String> getCodeInseeProposition(String codeInsee);

    List<Commune> findAll();
}
