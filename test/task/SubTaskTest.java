package task;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubTaskTest {

    @Test
    public void EpicsWithEqualIdShouldBeEqual() {
        SubTask subtask1 = new SubTask("Заказать пальто", "На Ozon", TaskStatus.NEW, Instant.now(),
                0, 5);
        subtask1.setId(7);
        SubTask subtask2 = new SubTask("Заказать PS-5", "На Яндекс Маркете", TaskStatus.DONE,
                Instant.now(), 0, 5);
        subtask2.setId(7);
        assertEquals(subtask1, subtask2,
                "Ошибка! Наследники класса Task должны быть равны друг другу, если равен их id;");
    }
}