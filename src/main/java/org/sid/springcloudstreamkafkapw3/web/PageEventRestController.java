package org.sid.springcloudstreamkafkapw3.web;

import org.sid.springcloudstreamkafkapw3.entities.PageEvent;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

@RestController
public class PageEventRestController {
    private StreamBridge streamBridge;//spring cloud stream
    private InteractiveQueryService interactiveQueryService;


    public PageEventRestController(StreamBridge streamBridge, InteractiveQueryService interactiveQueryService) {
        this.streamBridge = streamBridge;
        this.interactiveQueryService = interactiveQueryService;
    }

    @GetMapping("publish/{topic}/{name}")
    //publier dans un page event , name : nom de la page
    public PageEvent publish(@PathVariable String topic, @PathVariable String name){
        PageEvent pageEvent=new PageEvent();
        pageEvent.setName(name);
        pageEvent.setDate(new Date());
        pageEvent.setDuration(new Random().nextInt(1000));
        pageEvent.setUser(Math.random()>0.5?"U1":"U2");
        //envoyer un page event dans topic
        streamBridge.send(topic,pageEvent);
        return pageEvent;
    }
}
