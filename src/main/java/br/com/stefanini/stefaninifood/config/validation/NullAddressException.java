package br.com.stefanini.stefaninifood.config.validation;

import br.com.stefanini.stefaninifood.model.Address;

public class NullAddressException extends Exception{

    public NullAddressException(){
        super("Endereço não encontrado !");
    }
}
