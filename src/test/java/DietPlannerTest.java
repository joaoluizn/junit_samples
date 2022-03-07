import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class DietPlannerTest {


    private DietPlanner dietPlanner;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before all unit test.");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After all unit tests.");
    }

    @BeforeEach
    void setUp() {
        this.dietPlanner = new DietPlanner(20,30,50);
    }

    @AfterEach
    void tearDown() {
        System.out.println("A Unit test was finished!");
    }

    // INFO: It only makes sense to execute the test multiple times in small situations like when each test is executed in different threads
    // Or when you want to ensure the sequential execution is becoming a clean execution.
    @RepeatedTest(10)
    void shouldReturnCorrectDietPlanWhenCorrectCoder() {
        Coder coder = new Coder(1.82, 75.0, 26, Gender.MALE);
        DietPlan expected = new DietPlan(2202, 110, 73, 275);

        DietPlan actual = this.dietPlanner.calculateDiet(coder);

        assertAll(
                () -> assertEquals(expected.getCalories(), actual.getCalories()),
                () -> assertEquals(expected.getProtein(), actual.getProtein()),
                () -> assertEquals(expected.getCarbohydrate(), actual.getCarbohydrate()),
                () -> assertEquals(expected.getFat(), actual.getFat())
        );
    }
}