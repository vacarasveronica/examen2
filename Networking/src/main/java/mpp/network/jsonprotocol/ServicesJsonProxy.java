package mpp.network.jsonprotocol;

import com.google.gson.*;
import mpp.model.User;
import mpp.services.AppException;
import mpp.services.IObserver;
import mpp.services.IServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServicesJsonProxy implements IServices {

    private String host;
    private int port;

    private IObserver client;
    private BufferedReader input;
    private BufferedWriter output;
    private Gson gsonFormatter;
    private Socket connection;
    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    private final Object lock = new Object();
    private static final Logger logger= LogManager.getLogger();

    public ServicesJsonProxy(String host, int port) {
        this.host = host;
        this.port = port;
        this.gsonFormatter = new GsonBuilder().create();
//        gsonFormatter = new GsonBuilder()
//                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
//                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
//                .create();
        qresponses = new LinkedBlockingQueue<>();
        try {
            initializeConnection();  // Inițializează conexiunea la server
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

    /* TODO 2: IMPLEMENT HANDLE REQUEST FOR NETWORKING

    public Something logout(Something something, IObserver client) throws AppException {
        Request request = JsonProtocolUtils.createLogoutRequest(something);
        sendRequest(request);
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            String err = response.getErrorMessage();
            throw new AppException(err);
        }
        return response.getSomething();
    }
     */

    public void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
    }

    private void sendRequest(Request request) throws IOException {
        logger.traceEntry("sendRequest");
        synchronized (lock) {
            String reqJson = gsonFormatter.toJson(request);
            System.out.println("Am trimis: " + gsonFormatter.toJson(request));
            output.write(reqJson);
            output.newLine();
            output.flush();
        }
    }

    private Response readResponse() throws InterruptedException {
        logger.traceEntry("readResponse");
        System.out.println("Waiting for response...");
        Response response = qresponses.take();  // Blochează până vine răspunsul
        System.out.println("Received response: " + response);
        return response;
    }

    private void initializeConnection() throws AppException {
        logger.traceEntry("initializeConnection");
        try {
            connection = new Socket(host, port);
            System.out.println("✅ Connected to server on " + host + ":" + port);
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            finished = false;
            startReader();
        } catch (IOException e) {
            throw new AppException("Error initializing connection: " + e.getMessage());
        }
    }

    private void startReader() {
        Thread readerThread = new Thread(() -> {
            while (!finished) {
                try {
                    String responseLine = input.readLine();
                    if (responseLine == null || responseLine.isEmpty()) continue;
                    Response response = gsonFormatter.fromJson(responseLine, Response.class);
                    if (isUpdate(response)) {
                        handleUpdate(response);
                    } else {
                        qresponses.put(response);
                    }
                } catch (IOException | InterruptedException e) {
                    System.out.println("Reading error: " + e.getMessage());
                }
            }
        });
        readerThread.start();
    }

    @Override
    public void login(String alias, IObserver client) throws IOException, InterruptedException, AppException {
        sendRequest(JsonProtocolUtils.createLoginRequest(alias));
        Response response = readResponse();
        if (response.getType() == ResponseType.OK) {
            this.client = client;
        } else {
            closeConnection();
            throw new AppException(response.getErrorMessage());
        }
    }

    @Override
    public Iterable<User> findAllUser() throws IOException, InterruptedException, AppException {
        sendRequest(JsonProtocolUtils.createGetAllUsersRequest());
        Response response = readResponse();
        if (response.getType() == ResponseType.ERROR) {
            throw new AppException(response.getErrorMessage());
        }
        return response.getUsers();
    }

//    @Override
//    public Iterable<Configuratie> findAllConfiguratie() throws IOException, InterruptedException, AppException {
//        sendRequest(JsonProtocolUtils.createGetAllConfiguratiiRequest());
//        Response response = readResponse();
//        if (response.getType() == ResponseType.ERROR) {
//            throw new AppException(response.getErrorMessage());
//        }
//        return response.getConfiguratii();
//    }
//
//    @Override
//    public Iterable<Joc> findAllJoc() throws IOException, InterruptedException, AppException {
//        sendRequest(JsonProtocolUtils.createGetAllJocuriRequest());
//        Response response = readResponse();
//        if (response.getType() == ResponseType.ERROR) {
//            throw new AppException(response.getErrorMessage());
//        }
//        return response.getJocuri();
//    }
//
//    @Override
//    public Joc saveJoc(Joc joc) throws AppException, IOException, InterruptedException {
//        sendRequest(JsonProtocolUtils.createSaveGameRequest(joc));
//        Response response = readResponse();
//        if (response.getType() == ResponseType.ERROR) {
//            throw new AppException(response.getErrorMessage());
//        }
//        return joc;
//    }

    /* TODO 3: ADD OBSERVER UPDATES*/
    private void handleUpdate(Response response) {
//        if (response.getType() == ResponseType.SAVE_JOC) {
//            Joc game = response.getJoc();
//            try {
//                client.gameAdded(game);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private boolean isUpdate(Response response) {
//        return response.getType() == ResponseType.SAVE_JOC;
        return false;
    }




}