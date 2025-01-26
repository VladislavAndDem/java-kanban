package task;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {

    @Test
    public void EpicsWithEqualIdShouldBeEqual() {
        Epic epic1 = new Epic("Поесть", "Приготовить бутерброд", TaskStatus.NEW,
                LocalDateTime.of(2025, Month.JANUARY, 1, 0, 0), Duration.ofHours(1));
        epic1.setId(4);
        Epic epic2 = new Epic("Успеть в дедлайн", "17.11.2024",
                TaskStatus.IN_PROGRESS, LocalDateTime.of(2025, Month.JANUARY, 1, 0, 0), Duration.ofHours(1));
        epic2.setId(4);
        assertEquals(epic1, epic2,
                "Ошибка! Наследники класса Task должны быть равны друг другу, если равен их id;");
    }
}