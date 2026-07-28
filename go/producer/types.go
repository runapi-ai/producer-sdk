// Package producer provides the Producer FUZZ music generation API client.
package producer

import "github.com/runapi-ai/core-sdk/go/core"

type ProducerModel string
type VocalMode string
type TaskStatus string
type GenerationStage string

const (
	ModelFuzz20           ProducerModel = "fuzz-2.0"
	ModelFuzz20Pro        ProducerModel = "fuzz-2.0-pro"
	ModelFuzz20Raw        ProducerModel = "fuzz-2.0-raw"
	ModelFuzz11Pro        ProducerModel = "fuzz-1.1-pro"
	ModelFuzz10Pro        ProducerModel = "fuzz-1.0-pro"
	ModelFuzz10           ProducerModel = "fuzz-1.0"
	ModelFuzz11           ProducerModel = "fuzz-1.1"
	ModelFuzz08           ProducerModel = "fuzz-0.8"
	VocalModeExactLyrics  VocalMode     = "exact_lyrics"
	VocalModeInstrumental VocalMode     = "instrumental"
)

// TextToMusicParams configures exact-lyrics or instrumental music generation.
type TextToMusicParams struct {
	Model       ProducerModel `json:"model" help:"required; Producer FUZZ model slug"`
	VocalMode   VocalMode     `json:"vocal_mode" help:"required; exact_lyrics or instrumental"`
	Prompt      string        `json:"prompt" help:"required; music style and production brief, 1-200 characters"`
	Lyrics      string        `json:"lyrics,omitempty" help:"required for exact_lyrics; exact lyrics to sing; omitted for instrumental"`
	Title       string        `json:"title,omitempty" help:"optional; music title"`
	CallbackURL string        `json:"callback_url,omitempty" help:"optional; HTTPS callback URL for completion events"`
}

type AsyncTaskResponse struct {
	core.TaskBillingFacts
	ID     string     `json:"id"`
	Status TaskStatus `json:"status"`
	Error  string     `json:"error,omitempty"`
}

func (r AsyncTaskResponse) GetID() string     { return r.ID }
func (r AsyncTaskResponse) GetStatus() string { return string(r.Status) }
func (r AsyncTaskResponse) GetError() string  { return r.Error }

type Audio struct {
	ID              string        `json:"id,omitempty"`
	AudioURL        string        `json:"audio_url,omitempty"`
	ImageURL        string        `json:"image_url,omitempty"`
	ModelName       ProducerModel `json:"model_name,omitempty"`
	Title           string        `json:"title,omitempty"`
	DurationSeconds float64       `json:"duration_seconds,omitempty"`
	Lyrics          string        `json:"lyrics,omitempty"`
}

type TextToMusicResponse struct {
	AsyncTaskResponse
	Audios          []Audio         `json:"audios,omitempty"`
	GenerationStage GenerationStage `json:"generation_stage,omitempty"`
}
