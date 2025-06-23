package mpp.services;

// TODO 1: ADD ALL MODELS

import mpp.model.User;

import java.io.IOException;

public interface IServices {
    void login(String alias,IObserver client) throws IOException, InterruptedException, AppException;
    Iterable<User> findAllUser() throws IOException, InterruptedException, AppException;
//    Iterable<Configuratie> findAllConfiguratie() throws IOException, InterruptedException, AppException;
//    Iterable<Joc> findAllJoc() throws IOException, InterruptedException, AppException;
//    Joc saveJoc(Joc joc) throws AppException, IOException, InterruptedException;


    /* TODO 2: ADD METHODS TO BE IMPLEMENTED BY SERVICES
    Something login(Something something, IObserver client) throws AppException;
    Something addSomething(Something something) throws AppException;
    List<Something> getAllSomethings() throws AppException;
    Something updateSomething(Something something) throws AppException;
    Something deleteSomething(Something something) throws AppException;
    Something logout(Something something, IObserver client) throws AppException;
     */
}
