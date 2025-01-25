package task;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {

    @Test
    public void EpicsWithEqualIdShouldBeEqual() {
        Epic epic1 = new Epic("Поесть", "Приготовить бутерброд", TaskStatus.NEW, Instant.now(), 0);
        epic1.setId(4);
        Epic epic2 = new Epic("Успеть в дедлайн", "17.11.2024",
                TaskStatus.IN_PROGRESS, Instant.now(), 0);
        epic2.setId(4);
        assertEquals(epic1, epic2,
                "Ошибка! Наследники класса Task должны быть равны друг другу, если равен их id;");
    }
}