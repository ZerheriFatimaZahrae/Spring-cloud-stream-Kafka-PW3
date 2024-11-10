package org.sid.springcloudstreamkafkapw3.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.sid.springcloudstreamkafkapw3.entities.PageEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class PageEventService {
    @Bean
    //Creer un cunsumer qui va afficher le msg de page event
    //quand on return un consumer , spring cloud stream va faire un subscribe
    //vers un topic kafka
    public Consumer<PageEvent> pageEventConsumer(){
        return (pageEvent -> {
            System.out.println("******------************");
            System.out.println(pageEvent.toString());
            System.out.println("*******-----**********");
        });
    }

    @Bean
    //creer un producer  qui va produire des objets de type pageEvent
    //produit une msg dans un topic
    public Supplier<PageEvent> pageEventSupplier(){
        return ()->{
            return PageEvent.builder()
                    .name((Math.random()>0.5)?"P1":"P2")
                    .user((Math.random()>0.5)?"U1":"U2")
                    .date(new Date())
                    .duration(new Random().nextInt(1000))
                    .build();
        };
    }

    @Bean
    //faire le traitement recu par producer et les changer puis il seront afficher par un consumer ds topic R3
    public Function<PageEvent,PageEvent> pageEventFunction() {
        return (input)->{
            //ici on peut faire des transformations sur les données
            //ici on met a jour le nom du user par une transformation simple
            input.setName("Page Event ");
            input.setUser("user from stream kafka");
            return input;
        };
    }
}
