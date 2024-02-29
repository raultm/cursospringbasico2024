package com.example.curso2024.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.curso2024.dto.ItemCreate;
import com.example.curso2024.models.Item;
import com.example.curso2024.repositories.ItemsRepository;
import com.example.curso2024.services.items.SaveItemService;

@Service
public class PopulateDataService {
    
    @Autowired ItemsRepository itemsRepository;

    @Autowired SaveItemService saveItemService;

    public void execute(){
        populateItems();
    }

    private static final Date str2Date(String dateString){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd", Locale.ENGLISH);
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    private static final List<ItemCreate> items = Arrays.asList(
        // https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1433753824i/25686515.jpg
        ItemCreate.builder().type(Item.LIBRO).title("El Camino de Los Reyes").author("Brandon Sanderson").releasedAt(str2Date("2010-08-31")).duration(1196).minimumAge(13).build(),
        // https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1428933691i/3168524.jpg
        ItemCreate.builder().type(Item.LIBRO).title("La Conjura de los Necios").author("John Kennedy Toole").releasedAt(str2Date("1980-05-01")).duration(391).minimumAge(12).build(),
        // https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1231009753i/6063829.jpg
        ItemCreate.builder().type(Item.LIBRO).title("El Libro de Atrus").author("Rand Miller").releasedAt(str2Date("1997-01-01")).duration(335).minimumAge(12).build(),
        // https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1419003669i/6483211.jpg
        ItemCreate.builder().type(Item.LIBRO).title("El Nombre del Viento").author("Patrick Rothfuss").releasedAt(str2Date("2009-05-30")).duration(872).minimumAge(16).build(),
        // https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1555447414i/44767458.jpg
        ItemCreate.builder().type(Item.LIBRO).title("Dune").author("Frank Herbert").releasedAt(str2Date("1965-06-01")).duration(658).minimumAge(13).build()
    );

    private void populateItems(){
        if(itemsRepository.count() == 0){
            for (ItemCreate itemCreate : items) {
                saveItemService.execute(itemCreate);
            }
        }
    }

}
