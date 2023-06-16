/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.example;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.PurgeQueueRequest;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;

import java.io.StringWriter;
import java.util.*;

public class SendReceiveMessages {

        @Value("${ship.awsServices.SQSName1}")
        private String SQSName1;

        @Value("${ship.awsServices.SQSName2}")
        private String SQSName2;

 private SqsClient getClient() {
     SqsClient sqsClient = SqsClient.builder()
             .region(Region.US_WEST_2)
             .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
             .build();

        return sqsClient;
     }
	 
    public String getXMessages() {

        List attr = new ArrayList<String>();
        attr.add("Name");

        SqsClient sqsClient = getClient();

        try {

			// Receive messages from the queue
			ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
					.queueName(SQSName2)
					.maxNumberOfMessages(10)
					.messageAttributeNames(attr)
					.build();
			List<Message> messages = sqsClient.receiveMessage(receiveRequest).messages();

			ReceiveMessageRequest receiveRequest2 = ReceiveMessageRequest.builder()
					.queueName(SQSName1)
					.maxNumberOfMessages(10)
					.messageAttributeNames(attr)
					.build();
			List<Message> messages2 = sqsClient.receiveMessage(receiveRequest2).messages();

			com.example.Message myMessage;

			List allMessages = new ArrayList<com.example.Message>();

			// Push the messages to a list
			for (Message m : messages) {

				myMessage=new com.example.Message();
				myMessage.setBody(m.body());

				Map map = m.messageAttributes();
				MessageAttributeValue val=(MessageAttributeValue)map.get("Name");
				String messageName = val.stringValue();
				myMessage.setName(val.stringValue());
				out.println("MessageName: " + messageName);

				allMessages.add(myMessage);
			}

			return convertToString(toXml(allMessages));

		} catch (SqsException e) {
			e.getStackTrace();
		}
        return "";
	}

	
}