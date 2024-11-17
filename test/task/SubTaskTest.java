package task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {

    @Test
    public void EpicsWithEqualIdShouldBeEqual() {
        SubTask subtask1 = new SubTask(7, "Заказать пальто", "На Ozon", TaskStatus.NEW, 5);
        SubTask subtask2 = new SubTask(7, "Заказать PS-5", "На Яндекс Маркете", TaskStatus.DONE, 5);
        assertEquals(subtask1, subtask2,
                "Ошибка! Наследники класса Task должны быть равны друг другу, если равен их id;");
    }
}