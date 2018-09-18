exports.handler = async (event) => {

    event.Records.forEach(message => {

        console.log("Received message body: " + message.body);

        let customerCode = message.messageAttributes.customerCode.stringValue;
        let active = Boolean(parseInt(message.messageAttributes.active.stringValue)); // SQS has limited data types

        console.log("Setting all of " + customerCode + " content lists to active = " + active);

        // Call to stored procedure goes here

    });

    return true; // update successful

};
