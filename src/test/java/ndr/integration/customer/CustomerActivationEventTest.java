package ndr.integration.customer;

import org.junit.Test;

public class CustomerActivationEventTest {

    CustomerActivationEvent customerActivationEvent = new CustomerActivationEvent();

    @Test
    public void testInactivateCustomer() {
        customerActivationEvent.sendMessage("SHE002", 0);
    }

}