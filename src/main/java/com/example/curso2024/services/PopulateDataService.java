package com.example.curso2024.services;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.curso2024.dto.ItemCreate;
import com.example.curso2024.dto.MemberCreate;
import com.example.curso2024.models.Item;
import com.example.curso2024.repositories.ItemsRepository;
import com.example.curso2024.repositories.MemberRepository;
import com.example.curso2024.services.items.SaveItemService;
import com.example.curso2024.services.members.SaveMemberService;

@Service
public class PopulateDataService {
    
    @Autowired ItemsRepository itemsRepository;
    @Autowired MemberRepository memberRepository;
    
    @Autowired SaveItemService saveItemService;
    @Autowired SaveMemberService saveMemberService;

    public void execute(){
        populateItems();
        populateMembers();
    }

    private static final LocalDate str2Date(String dateString){
        return LocalDate.parse(dateString);
        // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd", Locale.ENGLISH);
        // try {
        //     return formatter.parse(dateString);
        // } catch (ParseException e) {
        //     e.printStackTrace();
        // }
        // return new Date();
    }

    private void populateItems(){
        if(itemsRepository.count() == 0){
            for (ItemCreate itemCreate : items) {
                saveItemService.execute(itemCreate);
            }
        }
    }

    private void populateMembers(){
        if(memberRepository.count() == 0){
            for (MemberCreate memberCreate : members) {
                saveMemberService.execute(memberCreate);
            }
        }
    }
    
    private static final List<ItemCreate> items = Arrays.asList(
        
        ItemCreate.builder().type(Item.LIBRO).title("El Camino de Los Reyes").author("Brandon Sanderson").releasedAt(str2Date("2010-08-31")).duration(1196).minimumAge(13).image("https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1433753824i/25686515.jpg").build(),
        ItemCreate.builder().type(Item.LIBRO).title("La Conjura de los Necios").author("John Kennedy Toole").releasedAt(str2Date("1980-05-01")).duration(391).minimumAge(12).image("https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1428933691i/3168524.jpg").build(),
        ItemCreate.builder().type(Item.LIBRO).title("El Libro de Atrus").author("Rand Miller").releasedAt(str2Date("1997-01-01")).duration(335).minimumAge(12).image("https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1231009753i/6063829.jpg").build(),
        ItemCreate.builder().type(Item.LIBRO).title("El Nombre del Viento").author("Patrick Rothfuss").releasedAt(str2Date("2009-05-30")).duration(872).minimumAge(16).image("https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1419003669i/6483211.jpg").build(),
        ItemCreate.builder().type(Item.LIBRO).title("Dune").author("Frank Herbert").releasedAt(str2Date("1965-06-01")).duration(658).minimumAge(13).image("https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1555447414i/44767458.jpg").build(),
        ItemCreate.builder().type(Item.JUEGO).title("Through the Ages").author("Vlaada Chvátil").releasedAt(str2Date("2015-01-01")).duration(120).minimumAge(14).image("https://cf.geekdo-images.com/fVwPntkJKgaEo0rIC0RwpA__itemrep/img/TF5TpehpgE7XvNSRzSSWjnYCbLc=/fit-in/246x300/filters:strip_icc()/pic2663291.jpg").build(),
        ItemCreate.builder().type(Item.JUEGO).title("Great Western Trail").author("Alexander Pfister").releasedAt(str2Date("2016-01-01")).duration(100).minimumAge(12).image("https://cf.geekdo-images.com/u1l0gH7sb_vnvDvoO_QHqA__itemrep/img/s3qAGoXVCK6gA8nsHDICfZ4Jc1c=/fit-in/246x300/filters:strip_icc()/pic4887376.jpg").build(),
        ItemCreate.builder().type(Item.JUEGO).title("Root").author("Cole Wehrle").releasedAt(str2Date("2018-01-01")).duration(80).minimumAge(10).image("https://cf.geekdo-images.com/JUAUWaVUzeBgzirhZNmHHw__itemrep/img/sQgkl-_hydBVvQHAMLt2Zk_3dwI=/fit-in/246x300/filters:strip_icc()/pic4254509.jpg").build(),
        ItemCreate.builder().type(Item.JUEGO).title("Agricola").author("Uwe Rosenberg").releasedAt(str2Date("2007-01-01")).duration(90).minimumAge(12).image("https://cf.geekdo-images.com/dDDo2Hexl80ucK1IlqTk-g__itemrep/img/DzC9cA0TNmWUO7WLdl4-uFHfO_k=/fit-in/246x300/filters:strip_icc()/pic831744.jpg").build(),
        ItemCreate.builder().type(Item.JUEGO).title("Azul").author("Michael Kiesling").releasedAt(str2Date("2017-01-01")).duration(45).minimumAge(8).image("https://cf.geekdo-images.com/aPSHJO0d0XOpQR5X-wJonw__itemrep/img/6oRLPDvy4zz3gOZM6e6NzIk8Seg=/fit-in/246x300/filters:strip_icc()/pic6973671.png").build(),
        ItemCreate.builder().type(Item.DISCO).title("Life Is Peachy").author("Korn").releasedAt(str2Date("1996-10-15")).duration(49).minimumAge(16).image("https://lastfm.freetls.fastly.net/i/u/500x500/41006b2356b2420e9fe2747540310d50.jpg").build(),
        ItemCreate.builder().type(Item.DISCO).title("Issues").author("Korn").releasedAt(str2Date("1999-11-01")).duration(54).minimumAge(16).image("https://lastfm.freetls.fastly.net/i/u/500x500/983f577430dbf57f96169ccda8d24115.jpg").build(),
        ItemCreate.builder().type(Item.DISCO).title("¡Viva!").author("Los Punsetes").releasedAt(str2Date("2017-03-09")).duration(38).minimumAge(16).image("https://lastfm.freetls.fastly.net/i/u/500x500/d0e1b70a129d1709294aa23a94850bcf.jpg").build(),
        ItemCreate.builder().type(Item.DISCO).title("Iowa").author("Slipknot").releasedAt(str2Date("2001-08-28")).duration(55).minimumAge(16).image("https://lastfm.freetls.fastly.net/i/u/770x0/c3161dcc6d26898bfd1574eb65cef7d5.jpg#c3161dcc6d26898bfd1574eb65cef7d5").build(),
        ItemCreate.builder().type(Item.DISCO).title("Damnation").author("Opeth").releasedAt(str2Date("2003-04-07")).duration(66).minimumAge(16).image("https://www.last.fm/es/music/Opeth/Damnation/+images/c974cc0caf0beec33bdb9f9392683d30").build()
    );

    private static final List<MemberCreate> members = Arrays.asList(
        MemberCreate.builder().username("maria.garcia").email("maria.garcia@fakemail.com").build(),
        MemberCreate.builder().username("carlos.martinez").email("carlos.martinez@fakemail.com").build(),
        MemberCreate.builder().username("laura.lopez").email("laura.lopez@fakemail.com").build(),
        MemberCreate.builder().username("javier.sanchez").email("javier.sanchez@fakemail.com").build(),
        MemberCreate.builder().username("ana.fernandez").email("ana.fernandez@fakemail.com").build(),
        MemberCreate.builder().username("david.gonzalez").email("david.gonzalez@fakemail.com").build(),
        MemberCreate.builder().username("sofia.rodriguez").email("sofia.rodriguez@fakemail.com").build(),
        MemberCreate.builder().username("pablo.perez").email("pablo.perez@fakemail.com").build(),
        MemberCreate.builder().username("isabel.diaz").email("isabel.diaz@fakemail.com").build(),
        MemberCreate.builder().username("juan.martin").email("juan.martin@fakemail.com").build()
    );

    

}
