package de.viadee.oauth2.produkte.rest;

import de.viadee.oauth2.produkte.dto.ProduktDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Collections;

@RestController
@RequestMapping("/suche")
public class ProduktsucheRestController {

    @GetMapping
    public List<ProduktDTO> suche(String farbe, String groesse) {

        final ProduktDTO tshirt = new ProduktDTO();
        tshirt.setId(12345L);
        tshirt.setFarbe("weiß");
        tshirt.setGroesse("M");
        tshirt.setName("Öffentliches T-Shirt");

        return Collections.singletonList(tshirt);
    }

}
