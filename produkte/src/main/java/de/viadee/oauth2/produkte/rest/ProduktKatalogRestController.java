package de.viadee.oauth2.produkte.rest;

import de.viadee.oauth2.produkte.dto.ProduktDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/katalog")
public class ProduktKatalogRestController {

    @GetMapping
    public List<ProduktDTO> list() {

        final ProduktDTO tshirt = new ProduktDTO();
        tshirt.setId(77777L);
        tshirt.setFarbe("blau");
        tshirt.setGroesse("XS");
        tshirt.setName("Geheimes T-Shirt");

        final ProduktDTO schuhe = new ProduktDTO();
        schuhe.setId(788807L);
        schuhe.setFarbe("rot");
        schuhe.setGroesse("48");
        schuhe.setName("Geheime Schuhe");

        return Arrays.asList(tshirt, schuhe);
    }

}
