package test.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import task.Task;
import task.TaskStatus;

class TaskTest {

    @Test
    public void tasksWithEqualIdShouldBeEqual() {
        Task task1 = new Task(7, "Заказать пальто", "На Ozon", TaskStatus.NEW);
        Task task2 = new Task(7, "Заказать PS-5", "На Яндекс Маркете", TaskStatus.DONE);
        assertEquals(task1, task2,
                "Ошибка! Экземпляры класса Task должны быть равны друг другу, если равен их id;");
    }
}