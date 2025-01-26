package manager;

public class InMemoryHistoryManagerTest {
    /*private TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();

    }

    @Test
    public void getHistoryShouldReturnListWithoutDuplicates() {
        // get History Должен возвращать Список без дубликатов
        for (int i = 0; i < 5; i++) { // добавляем 5 задач в мапу tasks
            taskManager.addNewTask(new Task("Некоторая задача", "Некоторое описание", TaskStatus.NEW, Instant.now(), 0));
        }
        // получаем таски по id
        taskManager.getTaskByID(1);
        taskManager.getTaskByID(1);//первый вызов должен удалиться
        taskManager.getTaskByID(3);
        taskManager.getTaskByID(3);//первый вызов должен удалиться
        // В истории должно быть два элемента
        List<Task> list = taskManager.getHistory();
        assertEquals(2, list.size(), "Неверное количество элементов в истории ");
    }

    @Test
    public void getHistoryShouldReturnOldTaskAfterUpdate() {
        // get History Должен Возвращать Старую Подзадачу После Обновления
        Task washFloor = new Task("Задача 1", "Описание 1", TaskStatus.NEW, Instant.now(), 0);
        taskManager.addNewTask(washFloor);
        taskManager.getTaskByID(washFloor.getId());
        Task washFlorUpdate = new Task("Задача 1.1",
                "Описание 1.1", TaskStatus.IN_PROGRESS, washFloor.getStartTime(), washFloor.getDuration());
        washFlorUpdate.setId(washFloor.getId());
        taskManager.updateTask(washFlorUpdate);
        List<Task> tasks = taskManager.getHistory();
        Task oldTask = tasks.getFirst();
        assertEquals(washFloor.getTitle(), oldTask.getTitle(), "В истории не сохранилась старая версия задачи");
        assertEquals(washFloor.getDescription(), oldTask.getDescription(),
                "В истории не сохранилась старая версия задачи");

    }

    @Test
    public void correctAddElementToLinkedListHistory() {
        // тест на корректность добавления элементов в связной список

        Task washFlor = new Task("Задача 1", "Описание 1", TaskStatus.NEW, Instant.now(), 0);
        Task task2 = new Task("title", "description", TaskStatus.NEW, Instant.now(), 0);
        taskManager.addNewTask(washFlor);
        taskManager.addNewTask(task2);

        taskManager.getTaskByID(washFlor.getId());
        taskManager.getTaskByID(task2.getId());

        assertEquals(2, taskManager.getHistory().size());
        assertEquals(washFlor, taskManager.getHistory().get(0));
    }

    @Test
    public void correctDeleteElementToLinkedListHistory() {
        // тест на корректность удаления элементов из связного списока
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

        Task task1 = new Task("title", "descroption", TaskStatus.NEW);
        task1.setId(1);
        Task task2 = new Task("title", "descroption", TaskStatus.NEW);
        task2.setId(2);

        inMemoryHistoryManager.add(task1);
        inMemoryHistoryManager.add(task2);

        inMemoryHistoryManager.remove(task1.getId());
        assertEquals(1, inMemoryHistoryManager.getHistory().size());
        assertEquals(task2, inMemoryHistoryManager.getHistory().get(0));
    }

    @Test
    public void correctOrderOfTheItemsInTheList() {
        //Тест на корректность порядка элементов в списке
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        Task task1 = new Task("title", "descroption", TaskStatus.NEW);
        task1.setId(1);
        Task task2 = new Task("title", "descroption", TaskStatus.NEW);
        task2.setId(2);
        List<Task> list = List.of(task1, task2);

        inMemoryHistoryManager.add(task1);
        inMemoryHistoryManager.add(task2);
        List<Task> list2 = inMemoryHistoryManager.getHistory();
        assertEquals(list, list2);
        assertEquals(list.get(0), list2.get(0));

    }*/
}