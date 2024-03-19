// This file was generated from JSON Schema using quicktype, do not modify it directly.
// To parse and unparse this JSON data, add this code to your project and do:
//
//    persons, err := UnmarshalPersons(bytes)
//    bytes, err = persons.Marshal()

package main

import "encoding/json"

type Persons []Person

func UnmarshalPersons(data []byte) (Persons, error) {
	var r Persons
	err := json.Unmarshal(data, &r)
	return r, err
}

func (r *Persons) Marshal() ([]byte, error) {
	return json.Marshal(r)
}

type Person struct {
	FirstName string `json:"first_name"`
	LastName  string `json:"last_name"`
}
