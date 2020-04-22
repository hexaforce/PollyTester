package io.hexaforce.util;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.AmazonPollyClientBuilder;

public class AWSClient {

	private final static Regions clientRegion = Regions.AP_NORTHEAST_1;

	private final static BasicAWSCredentials awsCreds = //
			new BasicAWSCredentials("aws_access_key_id", "aws_secret_access_key");

	public static AmazonPollyClient polly() {
		return (AmazonPollyClient) AmazonPollyClientBuilder.standard()//
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds))//
				.withClientConfiguration(new ClientConfiguration())//
				.withRegion(clientRegion)//
				.build();
	}

}
