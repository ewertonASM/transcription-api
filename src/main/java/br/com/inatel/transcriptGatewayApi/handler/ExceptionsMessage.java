package br.com.inatel.transcriptGatewayApi.handler;

public class ExceptionsMessage {

    // Rabbit Exceptions
    public static final String RABBIT_SETUP_FAIL = "RabbitMQ cannot be configured";
    public static final String MESSAGE_SEND_FAIL = "Audio cannot be queued";

    // Translator API Exceptions
    public static final String TRANSLATION_FAIL = "The subtitle could not be translated.";

    // Transciption Exceptions
    public static final String TRANSCRIPTION_FAIL = "The video could not be translated.";

    //Files Management Exceptions
    public static final String FILE_PROCESSING_FAILED = "The file could not be processed.";



    
}
