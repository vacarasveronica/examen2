package mpp.rest;


import mpp.model.Configuratie;
import mpp.model.Joc;
import mpp.model.Pozitie;
import mpp.model.User;
import mpp.persistance.JocRepoInterface;
import mpp.persistance.UserRepoInterface;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JocService {
    private final JocRepoInterface jocRepo;

    public JocService(JocRepoInterface jocRepo) {
        this.jocRepo = jocRepo;
    }

    public List<JocDTO> getJocuri(Integer userId){
        Iterable<Joc> toate = jocRepo.findAll();
        List<JocDTO> rezultat = new ArrayList<>();
        for(Joc joc : toate){
            if(joc.getUser().getId().equals(userId)){
                rezultat.add(new JocDTO(joc.getConfiguratie(),joc.getPozPropuse(),joc.getNrPuncte(),joc.getSecunde()));
            }
        }
        return rezultat;
    }

    public Joc update(Integer id, JocDTOModificare dto) {
        Joc existenta = jocRepo.findOne(id);
        String adaugare = "(" + dto.coordonataX + "," + dto.coordonataY + ") ";
        String pozPropuse = existenta.getPozPropuse();
        existenta.setPozPropuse(pozPropuse + adaugare);
        return jocRepo.update(existenta);
    }
}
