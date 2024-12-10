package taskManager;

import task.Task;

public class Node<T extends Task> {
    public T data;   // ссылка на данные
    public Node<T> next; // ссылка на следующий элемент
    public Node<T> prev; // ссылка на предыдущий элемент

    public Node(T data) {
        this.prev = null;
        this.next = null;
        this.data = data;
    }
}
