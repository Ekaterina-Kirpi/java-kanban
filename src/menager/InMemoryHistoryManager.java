package menager;

import org.w3c.dom.Node;
import tasks.Task;

import java.awt.*;
import java.util.*;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final CustomLinkedList historyTasks = new CustomLinkedList();

    @Override
    public void addTask(Task task) {
        if (task != null) {
            remove(task.getId());
            historyTasks.linkLast(task);
        }
    }


    @Override
    public void remove(int id) {
        historyTasks.removeNode(id);
        //removeNode
    }

    @Override
    public List<Task> getHistory() {
        return historyTasks.getTasks();
    }
}

final class CustomLinkedList {
    private Node head;
    private Node tail;
    private HashMap<Integer, Node> mapNode = new HashMap<>();

    public CustomLinkedList() {
    }//нужны ли аргументы?

    public void linkLast(Task element) {
        final Node oldTail = tail;
        final Node newNode = new Node(null, oldTail, element);
        tail = newNode;
        mapNode.put(element.getId(), newNode); // добавлять тут или в addTask?
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
    }

    public List<Task> getTasks() {
        List<Task> result = new ArrayList<>();
        Node currentNode = head;
        while (currentNode != null) {
            result.add(currentNode.element);
            currentNode = currentNode.next;
        }
        return result;
    }


    public void removeNode(Node node) {
        if (node != null) {
            final Node next = node.next;
            final Node prev = node.prev;
            node.element = null;
            if (head == node && tail == node) {
                head = null;
                tail = null;
            } else if (head == node) {
                head = next; // head == head.next
                head.prev = null; // head.next.prev
            } else if (tail == node) {
                tail = prev;
                tail.next = null;
            } else {
                prev.next = next;
                next.prev = prev;
            }
        }
    }

    public void removeNode(int id) {
        if (mapNode.containsKey(id)) {
            removeNode(mapNode.get(id));
        }
    }


    class Node {
        public Node next;
        public Node prev;
        public Task element;

        public Node(Node next, Node prev, Task element) {
            this.next = next;
            this.prev = prev;
            this.element = element;
        }
    }


}







