import com.google.gson.*;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.HttpTaskServer;
import server.InstantAdapter;
import server.KVServer;
import tasks.EpicTask;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskServerTest {
    private static HttpTaskServer httpTaskServer;
    private static KVServer kvServer;
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new InstantAdapter()).create();
    private static final HttpClient client = HttpClient.newHttpClient();

    @BeforeEach
    public void start() {
        try {
            kvServer = new KVServer();
            httpTaskServer = new HttpTaskServer();
            kvServer.start();
            httpTaskServer.start();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int post(URI url, Task task, boolean isUpdate) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder() // Запрос на добавление данных
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(task)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); // Ответ сервера
        assertEquals(201, response.statusCode());
        int id = -1;
        if(!isUpdate) {
            String[] res = response.body().split(" ");
            id = Integer.parseInt(res[res.length - 1]);
            task.setId(id);
        }

        return id;
    }

    public static JsonElement get(URI url, boolean isId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder() // Запрос на получение данных
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());  // Ответ сервера
        assertEquals(200, response.statusCode());
        return isId ? JsonParser.parseString(response.body()).getAsJsonObject() :
                JsonParser.parseString(response.body()).getAsJsonArray();
    }

    public static void delete(URI url) throws IOException, InterruptedException {
        HttpRequest request = request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

    @Test
    void addTaskTest() {
        URI url = URI.create("http://localhost:8080/tasks/task/");
        Task task = new Task("Задача", "Описание задачи", Status.NEW, Instant.now(), 30);
        try {
            post(url, task, false);

            JsonArray arrayTasks = (JsonArray) get(url, false);
            assertEquals(1, arrayTasks.size());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    void addFailEmptyTaskTest() {
        URI url = URI.create("http://localhost:8080/tasks/task/");
        try {
            HttpRequest request = HttpRequest.newBuilder() // Запрос на добавление данных
                    .uri(url)
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson("")))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); // Ответ сервера
            assertEquals(400, response.statusCode());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addTasksTest() {
        URI url = URI.create("http://localhost:8080/tasks/task/");
        Task task = new Task("Задача", "Описание задачи", Status.NEW, Instant.now(), 30);
        Task task2 = new Task("Задача2", "Описание задачи2", Status.NEW,
                Instant.now().plusSeconds(2400), 30);

        try {
            post(url, task, false);
            post(url, task2, false);
            JsonArray arrayTasks = (JsonArray) get(url, false);
            assertEquals(2, arrayTasks.size());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addEpicSubTasksTest() {
        URI urlEpic = URI.create("http://localhost:8080/tasks/epic/");
        URI urlSub = URI.create("http://localhost:8080/tasks/subtask/");
        EpicTask epic = new EpicTask("Эпик", "Описание эпика", Status.NEW);
        SubTask subTask = new SubTask("Сабтаск", "Описание сабтаска", Status.NEW, Instant.now(), 30);
        epic.saveNewSubTask(subTask);

        try {
            post(urlEpic, epic, false);
            post(urlSub, subTask, false);

            JsonArray arrayTasks = (JsonArray) get(urlEpic, false);
            assertEquals(1, arrayTasks.size());
            assertEquals(1, gson.fromJson(arrayTasks.get(0), EpicTask.class).getSubTasks().size());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getTaskByIdTest() {
        URI url = URI.create("http://localhost:8080/tasks/task/");
        Instant time = Instant.parse("2023-03-23T12:00:00.00Z");
        Task task = new Task("Задача", "Описание задачи", Status.NEW, time, 20);

        try {
            int id = post(url, task, false);

            url = URI.create("http://localhost:8080/tasks/task/?id=" + id);
            JsonObject jsonObject = (JsonObject) get(url, true);
            Task responseTask = gson.fromJson(jsonObject, Task.class);
            assertEquals(task, responseTask);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getEpicByIdTest() {
        URI url = URI.create("http://localhost:8080/tasks/epic/");
        Instant time = Instant.parse("2023-03-23T12:00:00.00Z");

        EpicTask epic = new EpicTask("Эпик", "Описание эпика", Status.NEW);
        SubTask subTask = new SubTask("Сабтаск", "Описание сабтаска", Status.NEW, time, 30);
        epic.saveNewSubTask(subTask);


        try {
            int id = post(url, epic, false);

            url = URI.create("http://localhost:8080/tasks/epic/?id=" + id);
            JsonObject jsonObject = (JsonObject) get(url, true);
            EpicTask responseTask = gson.fromJson(jsonObject, EpicTask.class);
            assertEquals(epic, responseTask);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getSubtaskByIdTest() {
        URI url = URI.create("http://localhost:8080/tasks/epic/");
        Instant time = Instant.parse("2023-03-23T12:00:00.00Z");
        EpicTask epic = new EpicTask("Эпик", "Описание эпика", Status.NEW);
        SubTask subtask = new SubTask("Подзадача", "Описание подзадачи", Status.NEW, time, 20);
        epic.saveNewSubTask(subtask);


        try {
            post(url, epic, false);

            url = URI.create("http://localhost:8080/tasks/subtask/");

            int subtaskId = post(url, subtask, false);

            url = URI.create("http://localhost:8080/tasks/subtask/?id=" + subtaskId);

            JsonObject object = (JsonObject) get(url, true);

            SubTask responseTask = gson.fromJson(object, SubTask.class);
            assertEquals(subtask, responseTask);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    void getTaskEmptyTest() {
        URI url = URI.create("http://localhost:8080/tasks/task/");
        try {
            get(url, false);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateTaskTest() {
        URI url = URI.create("http://localhost:8080/tasks/task/");
        Instant time = Instant.parse("2023-03-23T12:00:00.00Z");

        Task task = new Task("Задача", "Описание задачи", Status.NEW, time, 30);

        try {
            int id = post(url, task, false);

            task.setStatus(Status.DONE);

            post(url, task, true);

            url = URI.create("http://localhost:8080/tasks/task/?id=" + id);
            JsonObject object = (JsonObject) get(url, true);

            Task responseTask = gson.fromJson(object, Task.class);
            assertEquals(task, responseTask);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteTasksTest() {
        URI url = URI.create("http://localhost:8080/tasks/task/");
        Task task = new Task("Задача", "Описание задачи", Status.NEW, Instant.now(), 20);
        Task task2 = new Task("Задача", "Описание задачи", Status.NEW, Instant.now(), 20);

        try {
            post(url, task, false);
            post(url, task2, false);
            delete(url);

            JsonArray arrayTasks = (JsonArray) get(url, false);
            assertEquals(0, arrayTasks.size());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteTaskByIdTest() {
        URI url = URI.create("http://localhost:8080/tasks/task/");
        Task task = new Task("Задача", "Описание задачи", Status.NEW, Instant.now(), 20);


        try {
            int id = post(url, task, false);

            url = URI.create("http://localhost:8080/tasks/task/?id=" + id);
            delete(url);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url)
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals("Задача с данным id не найдена", response.body());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    void deleteEpicByIdTest() {
        URI url = URI.create("http://localhost:8080/tasks/epic/");
        Instant time = Instant.parse("2023-03-23T12:00:00.00Z");
        EpicTask epic = new EpicTask("Эпик", "Описание эпика", Status.NEW);
        SubTask subtask = new SubTask("Подзадача", "Описание подзадачи", Status.NEW, time, 20);
        epic.saveNewSubTask(subtask);

        try {
            int id = post(url, epic, false);

            url = URI.create("http://localhost:8080/tasks/epic/?id=" + id);

            delete(url);

            HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals("Эпик с данным id не найден", response.body());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteSubtaskByIdTest() {
        URI url = URI.create("http://localhost:8080/tasks/epic/");

        Instant time = Instant.parse("2023-03-23T12:00:00.00Z");
        EpicTask epic = new EpicTask("Эпик", "Описание эпика", Status.NEW);
        SubTask subtask = new SubTask("Подзадача", "Описание подзадачи", Status.NEW, time, 20);
        epic.saveNewSubTask(subtask);

        try {
            post(url, epic, false);

            url = URI.create("http://localhost:8080/tasks/subtask/");

            post(url, subtask, false);

            url = URI.create("http://localhost:8080/tasks/subtask/?id=" + subtask.getId());

            delete(url);

            HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals("ПодЗадача с данным id не найдена", response.body());
        } catch (IOException |
                 InterruptedException e) {
            e.printStackTrace();
        }

    }


    @AfterEach
    void stop() {
        kvServer.stop();
        httpTaskServer.stop();
    }
}