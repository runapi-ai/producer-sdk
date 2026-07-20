# frozen_string_literal: true

module RunApi
  module Producer
    # Producer FUZZ music generation client.
    class Client < RunApi::Core::Client
      # @return [Resources::TextToMusic] Exact-lyrics and instrumental music generation.
      attr_reader :text_to_music

      def initialize(api_key: nil, **options)
        super
        @text_to_music = Resources::TextToMusic.new(http)
      end
    end
  end
end
