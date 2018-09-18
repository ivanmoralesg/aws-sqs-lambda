package ndr.integration.customer;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class CustomerActivationEvent {

    private static final String SQS_QUEUE_URL = "https://sqs.us-east-2.amazonaws.com/542737228538/CustomerActiveStatus";

    final AmazonSQS sqs;

    public CustomerActivationEvent() {

        super();

        sqs = AmazonSQSClientBuilder.defaultClient();

    }

    public void sendMessage(String customerCode, Integer active) {

        try {

            System.out.println("Sending a client active change to SQS");

            String messageBody = "Updated " + customerCode + " active status to " + active;

            SendMessageRequest messageRequest = new SendMessageRequest(SQS_QUEUE_URL, messageBody);

            messageRequest.addMessageAttributesEntry("customerCode",
                    new MessageAttributeValue().withDataType("String").withStringValue(customerCode));

            messageRequest.addMessageAttributesEntry("active",
                    new MessageAttributeValue().withDataType("Number").withStringValue(active.toString()));

            sqs.sendMessage(messageRequest);

            System.out.println("Message sent");

        } catch (final AmazonServiceException e) {

            String exceptionLog = String.format(
                    "Service error while sending message to queue at URL %s. Details: \n" +
                            "Message: %s\n" +
                            "HTTP Status: %s\n" +
                            "AWS Error Code: %s\n" +
                            "Error Type: %s\n" +
                            "Request ID: %s\n",
                    SQS_QUEUE_URL, e.getMessage(), e.getStatusCode(), e.getErrorCode(), e.getErrorType(), e.getRequestId());

            System.err.println(exceptionLog);

        } catch (final AmazonClientException e) {

            String exceptionLog = String.format(
                    "Client error while sending message to queue at URL %s. Details: \n" +
                            "Message: %s\n",
                    SQS_QUEUE_URL, e.getMessage());

            System.err.println(exceptionLog);

        }

    }

}
