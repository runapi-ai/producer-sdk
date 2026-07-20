package producer

import (
	"context"
	"encoding/json"
	"testing"

	"github.com/runapi-ai/core-sdk/go/core"
)

type stubHTTPClient struct {
	method   string
	path     string
	body     any
	response json.RawMessage
}

func (s *stubHTTPClient) Request(_ context.Context, method, path string, opts *core.HTTPRequestOptions) (json.RawMessage, error) {
	s.method, s.path = method, path
	if opts != nil {
		s.body = opts.Body
	}
	return s.response, nil
}

func TestTextToMusicCreate(t *testing.T) {
	stub := &stubHTTPClient{response: json.RawMessage(`{"id":"task-1","status":"processing"}`)}
	client := NewClientWithHTTP(stub)
	response, err := client.TextToMusic.Create(context.Background(), TextToMusicParams{
		Model: ModelFuzz20, VocalMode: VocalModeExactLyrics,
		Prompt: "Warm acoustic pop with clear vocals", Lyrics: "[Verse] Morning light", Title: "Morning Light",
	})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "POST" || stub.path != textToMusicPath {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	body := stub.body.(map[string]any)
	if body["vocal_mode"] != "exact_lyrics" || body["lyrics"] != "[Verse] Morning light" {
		t.Fatalf("unexpected body: %v", body)
	}
	if response.ID != "task-1" {
		t.Fatalf("unexpected id: %s", response.ID)
	}
}

func TestTextToMusicGet(t *testing.T) {
	stub := &stubHTTPClient{response: json.RawMessage(`{"id":"task-1","status":"completed","audios":[{"id":"audio-1","audio_url":"https://media.runapi.ai/google-storage/producer-app-public/clips/audio.m4a","duration_seconds":78.35}]}`)}
	client := NewClientWithHTTP(stub)
	response, err := client.TextToMusic.Get(context.Background(), "task-1")
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "GET" || stub.path != textToMusicPath+"/task-1" {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	if len(response.Audios) != 1 || response.Audios[0].ID != "audio-1" {
		t.Fatalf("unexpected audios: %v", response.Audios)
	}
	if response.Audios[0].DurationSeconds != 78.35 {
		t.Fatalf("unexpected duration_seconds: %v", response.Audios[0].DurationSeconds)
	}
}
