package ai.runapi.producer;

import ai.runapi.core.BaseClient;
import ai.runapi.core.ClientOptions;
import ai.runapi.core.http.HttpTransport;
import java.net.URI;
import ai.runapi.producer.resources.TextToMusicResource;

/** Producer model-family Java SDK client. */
public final class ProducerClient extends BaseClient {
  private final TextToMusicResource textToMusic;

  private ProducerClient(ClientOptions options) {
    super(options);
    this.textToMusic = new TextToMusicResource(transport(), options());
  }

  /** Creates a new ProducerClient builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Text To Music operations. */
  public TextToMusicResource textToMusic() {
    return textToMusic;
  }

  /** Builder for {@link ProducerClient}. */
  public static final class Builder extends BaseClient.Builder<Builder> {
    private Builder() {}

    /** Sets the API key. If omitted, the SDK reads {@code RUNAPI_API_KEY}. */
    @Override
    public Builder apiKey(String value) {
      return super.apiKey(value);
    }

    /** Sets the RunAPI base URL. If omitted, the SDK reads {@code RUNAPI_BASE_URL}. */
    @Override
    public Builder baseUrl(String value) {
      return super.baseUrl(value);
    }

    /** Sets the RunAPI base URL from a URI. */
    @Override
    public Builder baseUrl(URI value) {
      return super.baseUrl(value);
    }

    /** Sets a custom HTTP transport. User-provided transports are not closed by SDK clients. */
    @Override
    public Builder transport(HttpTransport value) {
      return super.transport(value);
    }

    /** Builds an immutable ProducerClient. */
    @Override
    public ProducerClient build() {
      return new ProducerClient(options.build());
    }
  }
}
