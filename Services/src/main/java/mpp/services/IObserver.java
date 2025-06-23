package mpp.services;

// TODO 1: ADD MODELS USED IN OBSERVING

import mpp.model.Joc;

public interface IObserver {
    /* TODO 2: ADD METHODS TO BE IMPLEMENTED BY OBSERVERS */
     void gameAdded(Joc j) throws InterruptedException;

}