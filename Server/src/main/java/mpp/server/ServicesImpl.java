package mpp.server;

// TODO 1: IMPORT USED MODELS AND REPOSITORIES
import mpp.model.User;
import mpp.persistance.UserRepoInterface;
import mpp.services.AppException;
import mpp.services.IObserver;
import mpp.services.IServices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ServicesImpl implements IServices {

    // TODO 2: DEFINE/ADD REPOSITORIES
    UserRepoInterface userRepo;

    private static final Logger logger = LogManager.getLogger(ServicesImpl.class);

    private Map<String, IObserver> loggedClients;

    // TODO 3: ADD REPOSITORIES TO CONSTRUCTOR
    public ServicesImpl(UserRepoInterface userRepo) {
        this.userRepo = userRepo;
        logger.info("Initializing ServicesImpl");
        loggedClients = new ConcurrentHashMap<>();
    }

    @Override
    public void login(String alias, IObserver client) {
        loggedClients.put(alias, client);
    }

    @Override
    public Iterable<User> findAllUser() throws IOException, InterruptedException {
        return userRepo.findAll();
    }

//    @Override
//    public Iterable<Configuratie> findAllConfiguratie() throws IOException, InterruptedException, AppException {
//        return configRepo.findAll();
//    }
//
//    @Override
//    public Iterable<Joc> findAllJoc() throws IOException, InterruptedException, AppException {
//        return jocRepo.findAll();
//    }
//
//    @Override
//    public Joc saveJoc(Joc joc) throws AppException, IOException, InterruptedException {
//        Joc j = jocRepo.save(joc);
//        notifyObservers(joc);
//        return j;
//    }

    // TODO 4: DON'T FORGET TO CALL THE OBSERVERS FROM CONTROLLERS/CLIENTS WHEN NEEDED (IN THE SPECIFIC METHODS THAT NEED TO NOTIFY THE CLIENTS)

//    public synchronized User login(User user, IObserver client) throws AppException {
//        User foundUser = searchUserByName(user.getName());
//        if (foundUser != null){
//            if(loggedClients.get(user.getName()) != null)
//                throw new AppException("User already logged in.");
//            if (user.getPassword().equals(foundUser.getPassword())) {
//                loggedClients.put(user.getName(), client);
//            } else
//                throw new AppException("Authentication failed.");
//        } else
//            throw new AppException("Authentication failed.");
//        return user;
//    }
//
//    public synchronized User searchUserByName(String name) throws AppException {
//        Iterable<User> users = userRepository.findAll();
//        for (User user : users) {
//            if (user.getName().equals(name)) {
//                return user;
//            }
//        }
//        return null;
//    }
//
//    public synchronized User logout(User user, IObserver client) throws AppException {
//        IObserver localClient = loggedClients.remove(user.getName());
//        if (localClient == null)
//            throw new AppException("User " + user.getName() + " is not logged in.");
//        return user;
//    }
}