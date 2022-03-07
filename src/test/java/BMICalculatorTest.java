import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BMICalculatorTest {

    private final String environment = "dev";

    @Nested
    class IsDietRecommendedRelatedTests {

        @ParameterizedTest(name = "weight={0}")
        @ValueSource(doubles = {90.0, 95.0, 99.0})
        void should_Return_True_When_Diet_Recommended_Parametrized_Test(double weightValue) {
            //Given
            double height = 1.72;

            //When
            boolean recommended = BMICalculator.isDietRecommended(weightValue, height);

            //Then
            assertTrue(recommended);
        }

        @ParameterizedTest(name = "weight={0}, height={1}")
        @CsvSource(value = {"89.0, 1.72", "95.0, 1.75", "110.0, 1.78"})
        void should_Return_True_When_Diet_Recommended_Csv_Test(double weightValue, double heightValue) {
            boolean recommended = BMICalculator.isDietRecommended(weightValue, heightValue);

            assertTrue(recommended);
        }

        @ParameterizedTest(name = "weight={0}, height={1}")
        @CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
        void should_Return_True_When_Diet_Recommended_Csv_File_Test(double weightValue, double heightValue) {
            boolean recommended = BMICalculator.isDietRecommended(weightValue, heightValue);

            assertTrue(recommended);
        }

        @Test
        void shouldReturnFalseWhenDietRecommended() {
            //Given
            double weight = 50.0;
            double height = 1.92;

            //When
            boolean recommended = BMICalculator.isDietRecommended(weight, height);

            //Then
            assertFalse(recommended);
        }

        @Test
        void should_Throw_ArithmeticException_When_Height_Zero() {
            //Given
            double weight = 50.0;
            double height = 0.0;

            //Then
            assertThrows(ArithmeticException.class, () -> {
                //When
                assertTrue(BMICalculator.isDietRecommended(weight, height));

            });
        }
    }

    @Nested
    class FindCoderWithWorstBMITests {
        @Test
        void shouldReturnCoderWithWorstBMI() {
            //Given
            List<Coder> coders = new ArrayList<>();
            coders.add(new Coder(1.80, 60.0));
            coders.add(new Coder(1.82, 98.0));
            coders.add(new Coder(1.82, 64.0));

            //When
            Coder worstBMICoder = BMICalculator.findCoderWithWorstBMI(coders);

            //Then
            assertAll(
                    () -> assertEquals(1.82, worstBMICoder.getHeight()),
                    () -> assertEquals(98.0, worstBMICoder.getWeight())
            );
        }

        @Test
        void shouldReturnNullWorstBMICoderWhenEmptyList() {
            //Given
            List<Coder> coders = new ArrayList<>();

            //When
            Coder worstBMICoder = BMICalculator.findCoderWithWorstBMI(coders);

            //Then
            assertNull(worstBMICoder);
        }

        @Test
        void shouldReturnCoderWithWorstBMIin1Ms_when_list_has_10000_elements() {
            // INFO:The result of this test depends on the machine hardware where it is being executed.
            Assumptions.assumeTrue(BMICalculatorTest.this.environment.equals("prod"));
            //Given
            List<Coder> coders = new ArrayList<>();
            for (int i = 0; i < 10000 ; i++) {
                coders.add(new Coder(1.0 + i, 10.0 + 1));
            }

            //When
            Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);

            //Then
            assertTimeout(Duration.ofMillis(500), executable);
        }

    }

    @Nested
    class GetBMIScoresRelatedTests {

        @Test
        void shouldReturnCorrectBMIScoreArrayWhenCoderListNotEmpty() {
            //Given
            List<Coder> coders = new ArrayList<>();
            coders.add(new Coder(1.80, 60.0));
            coders.add(new Coder(1.82, 98.0));
            coders.add(new Coder(1.82, 64.7));
            double[] expected = {18.52, 29.59, 19.53};

            //When
            double[] bmiScores = BMICalculator.getBMIScores(coders);

            //Then
            assertArrayEquals(expected, bmiScores);
        }

    }
}