package de.tgippi.pact;

import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.AmpqTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@QuarkusTest
@Provider("TheProvider")
@PactFolder("src/test/resources/pacts")
public class ProviderPactTest {

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new AmpqTestTarget());
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void testTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @PactVerifyProvider("a message with a payload")
    public String verifyMessageForOrder() {
        return "{\"payload\": \"thepayload\"}";
    }
}
