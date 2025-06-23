package mpp.network.jsonprotocol;


import com.google.gson.*;
import mpp.model.User;
import mpp.services.IObserver;
import mpp.services.IServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientJsonWorker implements Runnable, IObserver {

    private IServices server;
    private Socket connection;

    private BufferedReader input;
    private BufferedWriter output;
    private Gson gsonFormatter;
    private volatile boolean connected;
    private static final Logger logger= LogManager.getLogger();

    public ClientJsonWorker(IServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        this.gsonFormatter = new GsonBuilder().create();
//        gsonFormatter = new GsonBuilder()
//                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
//                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
//                .create();
        try {
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (connected) {
            try {
                String requestLine = input.readLine();
                Request request = gsonFormatter.fromJson(requestLine, Request.class);
                Response response = handleRequest(request);
                if (response != null) {
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO 2: ADD OBSERVER METHODS IMPLEMENTATIONS

    private static Response okResponse = JsonProtocolUtils.createOkResponse();

    private Response handleRequest(Request request) {
        logger.traceEntry("handeling request");
        switch (request.getType()) {
            case LOGIN:
                return handleLogin(request);
            case GET_ALL_USERS:
                return handleGetAllUsers();
//            case GET_ALL_CONFIGURATII:
//                return handleGetAllConfiguratii();
//            case GET_ALL_JOCURI:
//                return handleGetAllJocuri();
//            case SAVE_JOC:
//                return handleSaveJoc(request);
            default:
                return JsonProtocolUtils.createErrorResponse("Unknown request type: " + request.getType());
        }
    }

    private void sendResponse(Response response) throws IOException {
        logger.traceEntry("sending response");
        String json = gsonFormatter.toJson(response);
        System.out.println("Sending response: " + json);
        synchronized (output) {
            output.write(json);
            output.newLine();
            output.flush();
        }
    }

    private Response handleLogin(Request request) {
        logger.traceEntry("handling login request");
        try {
            String alias = request.getAlias();
            System.out.println("Login received for user: " + alias);
            server.login(alias, this);
            return okResponse;
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse(e.getMessage());
        }
    }

    private Response handleGetAllUsers() {
        logger.traceEntry("handling getAllUsers request");
        try {
            System.out.println("SERVER: Handling GET_ALL_Users");
            Iterable<User> users;
            synchronized (server) {
                users = server.findAllUser();
            }
            return JsonProtocolUtils.createGetAllUsersResponse(users);
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse(e.getMessage());
        }
    }
}
