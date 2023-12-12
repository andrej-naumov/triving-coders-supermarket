package org.thriving.coders.supermarket.util.mysql.dump;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class FakeDataGenerator2 {
    public static void main(String[] args) {
        Faker faker = new Faker();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        System.out.println("Марка | Модель | Год выпуска | Цвет | VIN | Регистрационный номер | Пробег | Тип топлива | Вместимость бака | Средний расход");

        for (int i = 0; i < 10; i++) {
            String brand = faker.company().name();
            String model = faker.options().option("Мерседес", "БМВ", "Опель", "Сеат");
            int year = faker.number().numberBetween(1980, 2023);
            String color = faker.color().name();
            String vin = faker.regexify("[A-HJ-NPR-Z0-9]{17}");
            String regNumber = faker.regexify("[A-Z]{2}\\d{4}[A-Z]{2}");
            int mileage = faker.number().numberBetween(10000, 200000);
            String fuelType = faker.options().option("Бензин", "Дизель", "Электричество", "Гибрид");
            int tankCapacity = faker.number().numberBetween(40, 80);
            double avgConsumption = faker.number().randomDouble(1, 5, 15);

            System.out.println(brand + " | " + model + " | " + year + " | " +
                    color + " | " + vin + " | " + regNumber + " | " + mileage + " | " +
                    fuelType + " | " + tankCapacity + " | " + avgConsumption);
        }
    }
}

