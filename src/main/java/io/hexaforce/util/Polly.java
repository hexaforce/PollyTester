package io.hexaforce.util;

import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.model.DescribeVoicesRequest;
import com.amazonaws.services.polly.model.DescribeVoicesResult;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.Voice;

public class Polly {

	private final AmazonPollyClient polly;
	private final Voice voice;

	public Polly(int voiceTypeIndex) {

		polly = AWSClient.polly();

		// Create describe voices request.
		DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();

		// Synchronously ask Amazon Polly to describe available TTS voices.
		DescribeVoicesResult describeVoicesResult = polly.describeVoices(describeVoicesRequest);

		voice = describeVoicesResult.getVoices().get(voiceTypeIndex);

	}

	public InputStream synthesize(String text, String textType, String languageCode, OutputFormat format) throws IOException {

		SynthesizeSpeechRequest synthReq = new SynthesizeSpeechRequest()//
				.withTextType(textType)//
				.withLanguageCode(languageCode)//
				.withText(text)//
				.withVoiceId(voice.getId())//
				.withOutputFormat(format);

		SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);

		return synthRes.getAudioStream();

	}

}
