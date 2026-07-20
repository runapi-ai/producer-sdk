package ai.runapi.producer.types;

import com.fasterxml.jackson.annotation.JsonCreator;

/** Model slug for text to music operations. */
public final class TextToMusicModel extends ProducerValue {
  /** fuzz-2.0 model slug. */
  public static final TextToMusicModel FUZZ_2_0 = new TextToMusicModel("fuzz-2.0");

  /** Creates a model value from a literal model slug. */
  @JsonCreator
  public TextToMusicModel(String value) {
    super(value);
  }
}
