import { describe, expect, it } from 'vitest';
import { ProducerClient } from '../src';

describe('ProducerClient', () => {
  it('exposes the text-to-music resource', () => {
    expect(new ProducerClient({ apiKey: 'test-key' }).textToMusic).toBeDefined();
  });
});
