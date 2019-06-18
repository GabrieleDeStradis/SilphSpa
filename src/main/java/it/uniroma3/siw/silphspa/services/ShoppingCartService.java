package it.uniroma3.siw.silphspa.services;



import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import it.uniroma3.siw.silphspa.model.Fotografia;

public interface ShoppingCartService {

    void aggiungiFotografia(String fotografia);

    void rimuoviFotografia(String fotografia);

    List<String> getFotografieNelCarrello();
    

    
}