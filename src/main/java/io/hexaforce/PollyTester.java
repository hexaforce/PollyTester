package io.hexaforce;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import com.amazonaws.services.polly.model.OutputFormat;

import io.hexaforce.util.Mp3Player;
import io.hexaforce.util.Polly;
import javazoom.jl.decoder.JavaLayerException;

public class PollyTester extends Frame {
	private static final long serialVersionUID = 1L;

	final String TEXT_SSML = "ssml";
	final String TEXT_PLAIN = "text";

	final String LANG_Japanese = "ja-JP";
	final String LANG_English = "en-US";

	public static void main(String[] args) {
		new PollyTester();
	}

	PollyTester() {

		setSize(600, 330);
		setLocationRelativeTo(null);

		T("<speak>");
		T("    音声の速さを変更します");
		T("    <prosody rate='150%'>これが声の速さを150%にした例です</prosody>");
		T("    <break time='1s'/>");
		T("    音声のトーンを増減します。");
		T("    <prosody pitch='+50%'>これが声の高さを＋50%にした例です</prosody>");
		T("    <break time='1s'/>");
		T("    音声の音量を変更します");
		T("    <prosody volume='+6dB'>これが音量を＋6デシベルにした例です</prosody>");
		T("    <break time='1s'/>");
		T("    音声を強調します");
		T("    <emphasis level='strong'>これが強調した時です</emphasis>");
		T("</speak>");

		TextArea textArea = new TextArea(SAMPLE.toString());
		Button polly = new Button("Let's Polly");
		Checkbox ssml = new Checkbox("SSML", true);
		Choice voice = new Choice();
		LANG_LIST.forEach(voice::add);
		voice.select(49);// Default:Mizuki

		Label languageLabel = new Label("Text language :", Label.LEFT);
		CheckboxGroup language = new CheckboxGroup();
		Checkbox japanese = new Checkbox("Japanese", language, true);
		Checkbox english = new Checkbox("English", language, false);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent componentEvent) {
				resize(textArea);
				resize(polly);
				resize(ssml);
				resize(voice);
				resize(japanese, english);
				resize(languageLabel);
			}
		});

		resize(textArea);
		add(textArea);

		resize(polly);
		add(polly);

		resize(ssml);
		add(ssml);

		resize(voice);
		add(voice);

		resize(japanese, english);
		add(japanese);
		add(english);

		resize(languageLabel);
		add(languageLabel);

		polly.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					speak(textArea.getText(), ssml.getState(), voice.getSelectedIndex(), "Japanese".equals(language.getSelectedCheckbox().getLabel()));
				} catch (IOException | JavaLayerException err) {
					err.printStackTrace();
				}
			}
		});

		setLayout(null);
		setVisible(true);
	}

	void resize(TextArea textArea) {
		textArea.setBounds(10, 57, getSize().width - 20, getSize().height - 110);
	}

	void resize(Button polly) {
		polly.setBounds(getSize().width - 100, getSize().height - 45, 90, 35);
	}

	void resize(Checkbox ssml) {
		ssml.setBounds(getSize().width - 170, getSize().height - 37, 100, 20);
	}

	void resize(Choice voice) {
		voice.setBounds(getSize().width - 440, getSize().height - 37, 240, 20);
	}

	void resize(Checkbox japanese, Checkbox english) {
		japanese.setBounds(getSize().width - 190, 25, 90, 30);
		english.setBounds(getSize().width - 100, 25, 100, 30);
	}

	void resize(Label languageLabel) {
		languageLabel.setBounds(getSize().width - 300, 25, 100, 30);
	}

	final StringBuffer SAMPLE = new StringBuffer();
	final String LN = System.getProperty("line.separator");

	void T(String text) {
		SAMPLE.append(text);
		SAMPLE.append(LN);
	}

	public static final List<String> LANG_LIST = Arrays.asList("Lotte(Dutch)", "Maxim(Russian)", "Salli(US English)", "Geraint(Welsh English)", "Miguel(US Spanish)", "Marlene(German)", "Giorgio(Italian)", "Ines(Portuguese)", "Zeina(Arabic)", "Zhiyu(Chinese Mandarin)", "Gwyneth(Welsh)", "Karl(Icelandic)", "Joanna(US English)", "Lucia(Castilian Spanish)", "Cristiano(Portuguese)", "Astrid(Swedish)", "Vicki(German)", "Mia(Mexican Spanish)", "Bianca(Italian)", "Vitoria(Brazilian Portuguese)", "Raveena(Indian English)", "Chantal(Canadian French)", "Amy(British English)", "Brian(British English)", "Russell(Australian English)", "Aditi(Indian English)", "Matthew(US English)", "Dora(Icelandic)", "Enrique(Castilian Spanish)", "Hans(German)", "Carmen(Romanian)", "Ivy(US English)", "Ewa(Polish)", "Maja(Polish)", "Nicole(Australian English)", "Camila(Brazilian Portuguese)", "Filiz(Turkish)", "Jacek(Polish)", "Justin(US English)", "Celine(French)", "Kendra(US English)", "Ricardo(Brazilian Portuguese)", "Mads(Danish)", "Mathieu(French)", "Lea(French)", "Naja(Danish)", "Penelope(US Spanish)", "Tatyana(Russian)", "Ruben(Dutch)", "Mizuki(Japanese)", "Takumi(Japanese)", "Conchita(Castilian Spanish)", "Carla(Italian)", "Kimberly(US English)", "Jan(Polish)", "Liv(Norwegian)", "Joey(US English)", "Lupe(US Spanish)", "Seoyeon(Korean)", "Emma(British English)");

	void speak(String text, boolean ssml, int voiceTypeIndex, boolean japan) throws IOException, JavaLayerException {
		// create the test class
		Polly polly = new Polly(voiceTypeIndex);
		// get the audio stream
		InputStream mp3 = polly.synthesize(text, ssml ? TEXT_SSML : TEXT_PLAIN, japan ? LANG_Japanese : LANG_English, OutputFormat.Mp3);
		// create an MP3 player
		Mp3Player player = new Mp3Player();
		// play it!
		player.play(mp3);
	}

}