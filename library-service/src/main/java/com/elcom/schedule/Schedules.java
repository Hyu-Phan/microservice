package com.elcom.schedule;

import com.elcom.message.RequestMessage;
import com.elcom.messaging.rabbitmq.RabbitMQClient;
import com.elcom.messaging.rabbitmq.RabbitMQProperties;
import com.elcom.model.dto.BookCustom;
import com.elcom.model.dto.BookMapper;
import com.elcom.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@EnableScheduling
@Configuration
public class Schedules {
    @Autowired
    private RabbitMQClient rabbitMQClient;

    @Autowired
    private BookRepository bookRepository;

    @Scheduled(fixedDelay = 10000)
    public void syncDataToReportService() {
        List<BookCustom> reportBooks = bookRepository.findReportBooks();
        List<BookMapper> bookMappers = reportBooks.stream().map(bookCustom -> {
            return new BookMapper(bookCustom);
        }).collect(Collectors.toList());
        RequestMessage request = new RequestMessage();
        request.setRequestMethod("POST");
        request.setRequestPath(RabbitMQProperties.REPORT_RPC_URL);
        request.setUrlParam(null);
        request.setHeaderParam(null);
        Map<String, Object> books = new HashMap<>();
        books.put("books", bookMappers);
        request.setBodyParam(books);
        String msg = (String) rabbitMQClient.callRpcService(RabbitMQProperties.REPORT_RPC_EXCHANGE,
                RabbitMQProperties.REPORT_RPC_QUEUE, RabbitMQProperties.REPORT_RPC_KEY, request.toJsonString());
    }

}
