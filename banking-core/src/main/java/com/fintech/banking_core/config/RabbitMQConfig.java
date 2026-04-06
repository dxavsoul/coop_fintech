package com.fintech.banking_core.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
@EnableRabbit
public class RabbitMQConfig implements RabbitListenerConfigurer {
    public static final String WELCOME_EMAIL_QUEUE = "welcomeMailQueue.queue";
    public static final String BANKING_QUEUE = "banking.queue";
    public static final String DEPOSIT_ROUTING_TRANSACTION = "deposit.transaction";
    public static final String WITHDRAWAL_ROUTING_TRANSACTION = "withdrawal.transaction";
    public static final String TRANSFER_ROUTING_TRANSACTION = "transfer.transaction";
    public static final String LOAN_ROUTING_PAYMENT = "loan.payment";

    @Bean
    public MappingJackson2MessageConverter mappingJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(mappingJackson2MessageConverter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(messageHandlerFactory());
    }
}
