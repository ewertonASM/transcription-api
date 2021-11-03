package br.com.inatel.transcriptGatewayApi.handler;

public class ExceptionsMessage {

    // Rabbit Exceptions
    public static final String RABBIT_SETUP_FAIL = "RabbitMQ cannot be configured";
    public static final String MESSAGE_SEND_FAIL = "Audio cannot be queued";

    // Translator API Exceptions
    public static final String TRANSLATION_FAIL = "The subtitle could not be translated.";

    // Transciption Exceptions
    public static final String TRANSCRIPTION_FAIL = "The video could not be translated.";
    public static final String AUDIO_EXTRACTION_FAIL = "The video could not have the audio extracted.";

    //Files Management Exceptions
    public static final String FILE_PROCESSING_FAILED = "The file could not be processed.";
    public static final String FILE_EMPTY_ERROR = "Failed to store empty file.";
    public static final String FILE_STORAGE_OUTSIDE_ERROR = "Cannot store file outside current directory.";
    public static final String FILE_STORAGE_ERROR = "Failed to store file.";
    public static final String FILE_READ_ERROR = "Failed to read stored files";
    public static final String FILE_DELETE_ERROR = "Could not initialize storage";
    public static final String STORAGE_INITIALIZE_ERROR = "Could not initialize storage";
    // public static final String FILE_PROCESSING_FAILED = "The file could not be processed.";

    

    

    

    

}
