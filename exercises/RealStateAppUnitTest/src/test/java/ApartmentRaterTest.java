import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApartmentRaterTest {

    private int ERROR_VALUE = -1;

    @ParameterizedTest(name = "area={0}, price={1}, expectedRatio={2}")
    @CsvSource(value = {"20.0, 500000, 2", "50.0, 800000, 2"})
    void should_ReturnCorrectRating_When_CorrectApartment(double area, BigDecimal price, int expectedRating) {
        Apartment apartment = new Apartment(area, price);
        int rating = ApartmentRater.rateApartment(apartment);

        Assertions.assertEquals(expectedRating, rating);
    }

    @Test
    void should_ReturnErrorValue_When_IncorrectApartment() {
        Apartment apartment = new Apartment(0.0, new BigDecimal(50000));

        int value = ApartmentRater.rateApartment(apartment);

        Assertions.assertEquals(ERROR_VALUE, value);
    }


    @Test
    void should_CalculateAverageRating_When_CorrectApartmentList() {
        List<Apartment> apartmentList = new ArrayList<>();
        Apartment firstApartment = new Apartment(20.0, new BigDecimal(500000));
        Apartment secondApartment = new Apartment(50.0, new BigDecimal(800000));
        apartmentList.add(firstApartment);
        apartmentList.add(secondApartment);

        double avgRating = ApartmentRater.calculateAverageRating(apartmentList);

        Assertions.assertEquals(2.0, avgRating);
    }

    @Test
    void should_ThrowExceptionInCalculateAverageRating_When_EmptyApartmentList() {
        List<Apartment> apartmentList = new ArrayList<>();

        Executable executable = () -> ApartmentRater.calculateAverageRating(apartmentList);

        Assertions.assertThrows(RuntimeException.class, executable);
    }
}