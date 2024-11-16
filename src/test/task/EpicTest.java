package test.task;

import org.junit.jupiter.api.Test;
import task.Epic;
import task.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    public void EpicsWithEqualIdShouldBeEqual() {
        Epic epic1 = new Epic(5, "Поесть", "Приготовить бутерброд", TaskStatus.NEW);
        Epic epic2 = new Epic(5, "Успеть в дедлайн", "17.11.2024",
                TaskStatus.IN_PROGRESS);
        assertEquals(epic1, epic2,
                "Ошибка! Наследники класса Task должны быть равны друг другу, если равен их id;");
    }
}
