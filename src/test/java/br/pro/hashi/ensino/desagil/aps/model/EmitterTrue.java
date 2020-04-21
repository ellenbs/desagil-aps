package br.pro.hashi.ensino.desagil.aps.model;

import model.Emitter;

public class EmitterTrue implements Emitter {
    @Override
    public boolean read() {
        return true;
    }
}
