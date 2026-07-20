package ai.runapi.producer.resources;

import ai.runapi.core.ClientOptions;
import ai.runapi.core.RequestOptions;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.core.polling.TaskCreateResponse;
import ai.runapi.producer.types.CompletedTextToMusicResponse;
import ai.runapi.producer.types.TextToMusicParams;
import ai.runapi.producer.types.TextToMusicResponse;

/** Text To Music operations. */
public final class TextToMusicResource extends ProducerResource {
  /** API endpoint path for text to music operations. */
  public static final String ENDPOINT = "/api/v1/producer/text_to_music";

  /** Creates a resource bound to the supplied transport and client options. */
  public TextToMusicResource(HttpTransport transport, ClientOptions options) {
    super(transport, options, ENDPOINT);
  }

  /** Creates a text to music task. */
  public TaskCreateResponse create(TextToMusicParams params) {
    return create(params, RequestOptions.none());
  }

  /** Creates a text to music task with per-request options. */
  public TaskCreateResponse create(TextToMusicParams params, RequestOptions options) {
    return createTask(params.action(), params.toMap(), options);
  }

  /** Retrieves a text to music task by ID. */
  public TextToMusicResponse get(String id) {
    return get(id, RequestOptions.none());
  }

  /** Retrieves a text to music task by ID with per-request options. */
  public TextToMusicResponse get(String id, RequestOptions options) {
    return getTask(id, options, TextToMusicResponse.class);
  }

  /** Creates a text to music task and polls until it completes. */
  public CompletedTextToMusicResponse run(TextToMusicParams params) {
    return run(params, RequestOptions.none());
  }

  /** Creates a text to music task with per-request options and polls until it completes. */
  public CompletedTextToMusicResponse run(TextToMusicParams params, RequestOptions options) {
    return runTask(params.action(), params.toMap(), options, TextToMusicResponse.class, CompletedTextToMusicResponse.class);
  }
}
