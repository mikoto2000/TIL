package devcontainer

import (
	"encoding/json"
)

type Devcontainer struct {
	Name       string
	Image      string
	Mounts     Mounts
	Features   interface{}
	RemoteUser string
}

type Mounts []Mount

type Mount struct {
	Type   string
	Source string
	Target string
}

func UnmarshalDevcontainer(data []byte) (Devcontainer, error) {
	var d Devcontainer
	err := json.Unmarshal(data, &d)
	return d, err
}
