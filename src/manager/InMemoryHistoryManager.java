package manager;

import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private Node<Task> head;
    private Node<Task> tail;
    Map<Integer, Node<Task>> history = new HashMap<>();

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }

        Node<Task> newNode = new Node<>(task);
        remove(task.getId());
        history.put(task.getId(), newNode);
        if (head == null) { // если голова пустая, то есть вся история пустая
            head = newNode;//newNode становится одновременно и головой и хвостом
            tail = newNode;
        } else {
            tail.setNext(newNode); // хвост становится ссылкой на элемент newNode
            newNode.setPrev(tail); // значение ссылка на предыдущий элемент в узле newNode становиться tail
            tail = newNode;
        }


    }

    @Override
    public List<Task> getHistory() {
        ArrayList<Task> list = new ArrayList<>();
        Node<Task> current = head;
        while (current != null) {
            list.add(current.getData());
            current = current.getNext();
        }
        return list;
    }

    @Override
    public void remove(int id) {
        Node<Task> node = history.get(id);
        if (node != null) {
            removeNode(node);
            history.remove(id);
        }
    }

    private void removeNode(Node<Task> currentNode) {
        if (currentNode == null) {
            return;
        }
        if (currentNode == head && currentNode != tail) {
            Node<Task> nextNode = currentNode.getNext();
            nextNode.setPrev(null);
            head = nextNode;
        } else if (currentNode == tail && currentNode != head) {
            Node<Task> prevNode = currentNode.getPrev();
            prevNode.setNext(null);
            tail = prevNode;
        } else if (currentNode == head && currentNode == tail) {
            head = null;
            tail = null;
        } else {
            Node<Task> nextNode = currentNode.getNext();
            Node<Task> prevNode = currentNode.getPrev();
            prevNode.setNext(currentNode.getNext());
            nextNode.setPrev(currentNode.getPrev());
        }
    }

    class Node<T extends Task> {
        private T data;   // ссылка на данные
        private Node<T> next; // ссылка на следующий элемент
        private Node<T> prev; // ссылка на предыдущий элемент

        public Node(T data) {
            this.prev = null;
            this.next = null;
            this.data = data;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }
    }
}

