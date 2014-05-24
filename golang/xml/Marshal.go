package main

import (
	"bytes"
	"fmt"
	"strings"
	"encoding/xml"
	"net/http"
	"io/ioutil"
	"os"
)

// リクエスト XML データ
var requestXML = `<?xml version="1.0" encoding="UTF-8"?><S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
    <S:Header/>
    <S:Body>
        <ns2:echo xmlns:ns2="http://yukari.voiceroid.ws.oyasirazu.dip.jp/">
            <message>VOICE_MESSAGE</message>
        </ns2:echo>
    </S:Body>
</S:Envelope>`

// 置換の目印
var replaceStr = "VOICE_MESSAGE"

// レスポンス XML データ
// var responseXML = `<?xml version="1.0" encoding="UTF-8"?><S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
//     <S:Body>
//         <ns2:echoResponse xmlns:ns2="http://yukari.voiceroid.ws.oyasirazu.dip.jp/">
//             <return>
//                 <type>VOICE_DATA_TYPE</type>
//                 <voice>VOICE_DATA</voice>
//             </return>
//         </ns2:echoResponse>
//     </S:Body>
// </S:Envelope>`
 
// request Object.
type request struct {
	template string
	replaceStr string
	Message string
}

// NewRequest is create new request Object.
func NewRequest(Message string) (*request) {
	req := new(request)
	req.template = requestXML
	req.replaceStr = replaceStr
	req.Message = Message
	return req
}

// GetXML is build and return request xml string.
func (r *request) GetXML() (string) {
	str := strings.Replace(r.template, r.replaceStr, r.Message, 1)
	return str
}

// VoiceData Object.
type VoiceData struct {
	Type  string `xml:"Body>echoResponse>return>type"`
	Voice []byte `xml:"Body>echoResponse>return>voice"`
}

// Yukari web service url.
// 'Yokari' is test service path.
var serviceURL = "http://voice:8080/Yokari/YukariService"

// YukariClient is Yukari web service client.
type YukariClient struct {
	serviceURL string
}

// NewYukariClient is create new YukariClient.
func NewYukariClient() (*YukariClient) {
	yc := new(YukariClient)
	yc.serviceURL = serviceURL
	return yc
}

// Get is call Yukari web service and create VoiceData(response).
func (*YukariClient) Get(message string) *VoiceData {
	// リクエストオブジェクトからリクエスト文字列を作成
	request := NewRequest(message)

	httpClient := new(http.Client)
	response, _ := httpClient.Post(serviceURL, "text/xml; charset=utf-8", bytes.NewBufferString(request.GetXML()))

	b, _ := ioutil.ReadAll(response.Body)

	// レスポンス文字列からレスポンスオブジェクトを作成
	voice := new(VoiceData)
	xml.Unmarshal([]byte(b), voice)

	return voice
}

func main() {

	client := NewYukariClient()
	response := client.Get(os.Args[1])

	fmt.Println(response)
}
