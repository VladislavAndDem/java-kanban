package taskManager;

import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    public Node<Task> head;
    public Node<Task> tail;
    Map<Integer, Node<Task>> history = new HashMap<>();

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        Node<Task> newNode = new Node<>(task);
        if (head == null) { // если голова пустая, то есть вся история пустая
            head = newNode;//newNode становится одновременно и головой и хвостом
            tail = newNode;
        } else {
            tail.next = newNode; // хвост становится ссылкой на элемент newNode
            newNode.prev = tail; // значение ссылка на предыдущий элемент в узле newNode становиться tail
            tail = newNode;
        }
        remove(task.getId());
        history.put(task.getId(), newNode);

    }

    @Override
    public List<Task> getHistory() {
        ArrayList<Task> list = new ArrayList<>();
        Node<Task> current = head;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }

    @Override
    public void remove(int id) {
        for (Integer i : history.keySet()) {
            if (id == i) {
                removeNode(history.get(id));
            }
        }
    }

    public void removeNode(Node<Task> currentNode) {
        if (currentNode == null) {
            return;
        }
        if (currentNode == head && currentNode != tail) {
            Node<Task> nextNode = currentNode.next;
            nextNode.prev = null;
            head = nextNode;
        } else if (currentNode == tail && currentNode != head) {
            Node<Task> prevNode = currentNode.prev;
            prevNode.next = null;
            tail = prevNode;
        } else if (currentNode == head && currentNode == tail) {
            head = null;
            tail = null;
        } else {
            Node<Task> nextNode = currentNode.next;
            Node<Task> prevNode = currentNode.prev;
            prevNode.next = currentNode.next;
            nextNode.prev = currentNode.prev;
        }
    }
}