package org.thriving.coders.supermarket.util.fake;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class FakeDataGenerator {
    public static void main(String[] args) {
        Faker faker = new Faker(Locale.forLanguageTag("de"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        System.out.println("Название продукта | Производитель | Срок годности | Цена | Штрих код | Дата выпуска | Объем | Масса | Вид упаковки | Минимальная продажа | Скидка (%) | Налог");

        for (int i = 0; i < 10; i++) {
            String productName = faker.food().ingredient();
            String manufacturer = faker.company().name();
            String expirationDate = dateFormat.format(faker.date().future(365, TimeUnit.DAYS));
            double price = faker.number().randomDouble(2, 1, 10); // Генерация случайной цены от 1 до 100
            String barcode = faker.code().ean13();
            String productionDate = dateFormat.format(faker.date().past(180, TimeUnit.DAYS));
            String volume = faker.random().nextInt(1, 10) + " л";
            String weight = faker.random().nextInt(12, 1000) + " г";
            String packagingType = faker.options().option("Пластиковая", "Упаковка", "Сетка");
            int minSale = faker.number().numberBetween(3, 10);
            int discount = faker.number().numberBetween(0, 15);
            double tax = faker.number().randomDouble(2, 5, 20); // Генерация случайного налога от 5% до 20%

            System.out.println(productName + " | " + manufacturer + " | " + expirationDate + " | " +
                    price + " | " + barcode + " | " + productionDate + " | " + volume + " | " +
                    weight + " | " + packagingType + " | " + minSale + " | " + discount + " | " + tax + "%");
        }
    }
}

