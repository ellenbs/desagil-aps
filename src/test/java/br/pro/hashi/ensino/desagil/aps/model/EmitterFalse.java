package br.pro.hashi.ensino.desagil.aps.model;

import model.Emitter;

public class EmitterFalse implements Emitter {
    @Override
    public boolean read() {
        return false;
    }
}
