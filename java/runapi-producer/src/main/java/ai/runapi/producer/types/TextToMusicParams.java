package ai.runapi.producer.types;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Parameters for text to music operations. */
public final class TextToMusicParams {
  private final String model;
  private final String vocalMode;
  private final String prompt;
  private final String lyrics;
  private final String title;
  private final String callbackUrl;

  private TextToMusicParams(Builder builder) {
    this.model = ProducerParamUtils.requireNonBlankTrim(builder.model, "model");
    this.vocalMode = ProducerParamUtils.requireNonBlank(builder.vocalMode, "vocalMode");
    this.prompt = ProducerParamUtils.requireNonBlank(builder.prompt, "prompt");
    this.lyrics = builder.lyrics;
    this.title = builder.title;
    this.callbackUrl = builder.callbackUrl;
  }

  /** Creates a new TextToMusicParams builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the RunAPI action key for this request. */
  public String action() {
    return "producer/text-to-music";
  }

  /** Converts these parameters to the JSON request body shape. */
  public Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("model", ProducerParamUtils.wireValue(model));
    raw.put("vocal_mode", ProducerParamUtils.wireValue(vocalMode));
    raw.put("prompt", ProducerParamUtils.wireValue(prompt));
    raw.put("lyrics", ProducerParamUtils.wireValue(lyrics));
    raw.put("title", ProducerParamUtils.wireValue(title));
    raw.put("callback_url", ProducerParamUtils.wireValue(callbackUrl));
    return ProducerParamUtils.compact(raw);
  }



  /** Builder for {@link TextToMusicParams}. */
  public static final class Builder {
    private String model;
    private String vocalMode;
    private String prompt;
    private String lyrics;
    private String title;
    private String callbackUrl;

    private Builder() {}

    /** Sets the model slug using a typed model value. */
    public Builder model(TextToMusicModel value) {
      this.model = java.util.Objects.requireNonNull(value, "model").value();
      return this;
    }

    /** Sets the model slug using a string value. */
    public Builder model(String value) {
      this.model = ProducerParamUtils.requireNonBlankTrim(value, "model");
      return this;
    }


    /** Sets the vocal mode. */
    public Builder vocalMode(String value) {
      this.vocalMode = ProducerParamUtils.requireNonBlank(value, "vocalMode");
      return this;
    }

    /** Sets the text prompt. */
    public Builder prompt(String value) {
      this.prompt = ProducerParamUtils.requireNonBlank(value, "prompt");
      return this;
    }

    /** Sets the lyrics. */
    public Builder lyrics(String value) {
      this.lyrics = ProducerParamUtils.requireNonBlank(value, "lyrics");
      return this;
    }

    /** Sets the title. */
    public Builder title(String value) {
      this.title = ProducerParamUtils.requireNonBlank(value, "title");
      return this;
    }

    /** Sets the webhook URL for task completion notifications. */
    public Builder callbackUrl(String value) {
      this.callbackUrl = ProducerParamUtils.requireNonBlank(value, "callbackUrl");
      return this;
    }

    /** Builds immutable text to music parameters. */
    public TextToMusicParams build() {
      return new TextToMusicParams(this);
    }
  }
}
