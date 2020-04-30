package de.tgippi.pact;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.messaging.Message;
import au.com.dius.pact.core.model.messaging.MessagePact;
import io.pactfoundation.consumer.dsl.LambdaDsl;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "TheProvider", providerType = ProviderType.ASYNCH)
public class ConsumerPactTest {
    final String thePayload = "thePayload";

    @Pact(consumer = "TheConsumer")
    public MessagePact createPact(MessagePactBuilder builder) {
        var body = LambdaDsl.newJsonBody((it) -> {
            it.stringType("payload", thePayload);
        }).build();

        return builder
                .expectsToReceive("a message with a payload")
                .withMetadata(new HashMap<>())
                .withContent(body)
                .toPact();
    }

    @Test
    void test(List<Message> messages) {
        assertEquals(1, messages.size());
        assertEquals("{\"payload\":\"thePayload\"}", messages.get(0).contentsAsString());
    }
}
