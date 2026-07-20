package ai.runapi.producer.types;

import ai.runapi.core.types.RunApiValue;

abstract class ProducerValue extends RunApiValue {
  ProducerValue(String value) {
    super(value);
  }
}
