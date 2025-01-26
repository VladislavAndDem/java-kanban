package task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {

    @Test
    public void tasksWithEqualIdShouldBeEqual() {
        Task task1 = new Task("Заказать пальто", "На Ozon");
        task1.setId(5);
        Task task2 = new Task("Заказать PS-5", "На Яндекс Маркете");
        task2.setId(5);
        assertEquals(task1, task2,
                "Ошибка! Экземпляры класса Task должны быть равны друг другу, если равен их id;");
    }
}