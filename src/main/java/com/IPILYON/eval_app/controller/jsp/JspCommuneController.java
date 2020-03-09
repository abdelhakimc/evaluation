package com.IPILYON.eval_app.controller.jsp;

import com.IPILYON.eval_app.model.Commune;
import com.IPILYON.eval_app.service.CommuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Controller
@RequestMapping(value = "/")
public class JspCommuneController {

    @Autowired
    CommuneService communeService;

    String sortDirectionOpposite;

    List<Commune> communesList;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Map<String, Object> model) {
        model.put("nbCommunes", communeService.countAllCommune());
        model.put("nbCodePostal", communeService.countAllCodePostal());
        model.put("nbCodeInsee", communeService.countAllCodeInsee());
        return "index";
    }

    @GetMapping("/communes/{id}")
    public String findCommuneById(@PathVariable("id") Long id, Map<String, Object> model){
        Optional<Commune> commune = communeService.findCommuneById(id);
        if(commune.isPresent()) {
            model.put("commune", commune.get());
            return "communes/detail";
        }
        return "erreur";
    }

    @GetMapping("/communes/create")
    public String create(Map<String, Object> model){
        Commune c = new Commune();
        model.put("commune", c);
        return "communes/detail";
    }

    @RequestMapping(value = "/communes/recherche/", method = RequestMethod.GET)
    public String searchCommunesByNom(
            @RequestParam(name = "nomCommune", defaultValue = "") String nomCommune,
            @RequestParam(name = "codePostal", defaultValue = "") String codePostal,
            @RequestParam(name = "codeInsee", defaultValue = "") String codeInsee,
            Map<String, Object> model, Pageable pageable) {
        if(!nomCommune.equals("")){
            Commune c = this.communeService.searchCommuneByNom(nomCommune);
            if(c == null){
                throw new EntityNotFoundException("Impossible de trouver la commune " + nomCommune);
            }
            model.put("commune", c);
            return "communes/detail";
        } else {

            if(!codePostal.equals("")){
                this.communesList = communeService.searchCommuneByCodePostal(codePostal);
            } else {
                this.communesList = communeService.searchCommuneByCodeInsee(codeInsee);
            }

            if(this.communesList.size() == 1){
                Commune c = this.communesList.get(0);
                model.put("commune", c);
                return "communes/detail";
            }
            Integer page = 0;
            int start =  (int) pageable.getOffset();
            int end = (start + pageable.getPageSize()) > communesList.size() ? communesList.size() : (start + pageable.getPageSize());
            Page<Commune> pageCommune = new PageImpl<Commune>(communesList.subList(start, end), pageable, communesList.size());
            model.put("communesList", pageCommune);
            model.put("size", communesList.size());
            model.put("sortDirection", "ASC");
            model.put("sortDirectionOpposite", "DESC");
            model.put("sortProperty", "codePostal");
            model.put("page", page);
            model.put("pageAffichage", page + 1);
            model.put("nextPage", page + 1);
            model.put("previousPage", page - 1);
            model.put("start", pageable.getOffset());
            model.put("end", end > pageCommune.getTotalElements() ? pageCommune.getTotalElements() : end);
            return "communes/liste";
        }
    }

    @RequestMapping(value = "/communes/save/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String save(Commune commune, Map<String, Object> model, RedirectAttributes attributes) {
        if(commune != null){
            commune = this.communeService.createOrUpdateCommune(commune, false);
        }
        model.put("commune", commune);
        attributes.addAttribute("success", "Modifications enregistrées !");
        return "communes/detail";
    }

    @RequestMapping(value = "/communes/voisine/", method = RequestMethod.GET)
    public String communeVoisine(Long id, Long rayon, Map<String, Object> model, Pageable pageable) {
        Optional<Commune> optionalCommune = communeService.findCommuneById(id);
        if(optionalCommune.isPresent()) {
            Commune c = optionalCommune.get();
            List<Commune> allCommune = communeService.findAll();
            List<Commune> communeList = new ArrayList<Commune>();
            for (Commune commune :allCommune) {
                if(commune.getLatitude() != null && commune.getLongitude() != null){
                    if( (getDistance(c.getLatitude(), c.getLongitude(), commune.getLatitude(), commune.getLongitude())) <= rayon){
                        communeList.add(commune);
                    }
                }
            }
            Integer page = 0;
            int start =  (int) pageable.getOffset();
            int end = (start + pageable.getPageSize()) > communeList.size() ? communeList.size() : (start + pageable.getPageSize());
            Page<Commune> pageCommune = new PageImpl<Commune>(communeList.subList(start, end), pageable, communeList.size());
            model.put("communeVoisine", true);
            model.put("commune", optionalCommune.get());
            model.put("communesList", pageCommune);
            model.put("size", communeList.size());
            model.put("sortDirection", "ASC");
            model.put("sortDirectionOpposite", "DESC");
            model.put("sortProperty", "codePostal");
            model.put("page", page);
            model.put("pageAffichage", page + 1);
            model.put("nextPage", page + 1);
            model.put("previousPage", page - 1);
            model.put("start", pageable.getOffset());
            model.put("end", end > pageCommune.getTotalElements() ? pageCommune.getTotalElements() : end);
            return "communes/liste";
        } else {
            throw new IllegalArgumentException("Commune inconnue");
        }

    }

    public Long getDistance(Double latitude1, Double long1, Double latitude2, Double long2){
        Double lat1 = Math.toRadians(latitude1);
        Double lng1 = Math.toRadians(long1);
        Double lat2 = Math.toRadians(latitude2);
        Double lng2 = Math.toRadians(long2);

        double dlon = lng2 - lng1;
        double dlat = lat2 - lat1;

        double a = Math.pow((Math.sin(dlat/2)),2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon/2),2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return Math.round(6371.009 * c);
    }

    @RequestMapping(value = "/communes/{id}/delete", method = RequestMethod.GET)
    public RedirectView delete(@PathVariable(name = "id") Long id, Map<String, Object> model, RedirectAttributes attributes){
        this.communeService.deleteCommune(id);
        attributes.addAttribute("success", "Suppression de la commune effectuée !");
        return new RedirectView("/");
    }


    @RequestMapping(value = "/communes", method = RequestMethod.GET)
    public String findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
            @RequestParam(value = "sortProperty", defaultValue = "nomCommune") String sortProperty,
            Map<String, Object> model){
        Page<Commune> pageCommune = communeService.findAllCommunes(page, size, sortProperty, sortDirection);
        if(sortDirection.equals("ASC")){
            this.sortDirectionOpposite = "DESC";
        } else {
            this.sortDirectionOpposite = "ASC";
        }
        model.put("communesList",pageCommune);
        model.put("size", size);
        model.put("sortDirection", sortDirection);
        model.put("sortDirectionOpposite", sortDirectionOpposite);
        model.put("sortProperty", sortProperty);
        model.put("page", page);
        model.put("pageAffichage", page + 1);
        model.put("nextPage", page + 1);
        model.put("previousPage", page - 1);
        model.put("start", (page) * size + 1);
        int end = (page + 1) * size;
        model.put("end", end > pageCommune.getTotalElements() ? pageCommune.getTotalElements() : end);
        return "communes/liste";
    }

}
