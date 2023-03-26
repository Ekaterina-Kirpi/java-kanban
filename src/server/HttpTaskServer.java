package server;
import com.sun.net.httpserver.HttpServer;
import manager.Managers;
import manager.TaskManager;
import server.handlers.*;
import java.io.IOException;
import java.net.InetSocketAddress;


public class HttpTaskServer {
    private final HttpServer httpServer;
    private static int PORT = 8080;

    public HttpTaskServer() throws IOException, InterruptedException{
        TaskManager taskManager = Managers.getDefault();
        this.httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks/task", new TaskHandler(taskManager));
        httpServer.createContext("/tasks/subtask", new SubTaskHandler(taskManager));
        httpServer.createContext("/tasks/epic", new EpicHandler(taskManager));
        httpServer.createContext("/tasks/", new TasksHandler(taskManager));
        httpServer.createContext("/tasks/history", new HistoryHandler(taskManager));

    }

    public void start() {
        System.out.println("HTTP cервер запущен на " + PORT + " порту!");
        System.out.println("http://localhost:" + PORT + "/tasks/");
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(1);
        System.out.println("HTTP cервер остановлен на " + PORT + " порту!");

    }
}
