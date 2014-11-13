package yukari

import (
	"bytes"
	"encoding/base64"
	"encoding/xml"
	"io/ioutil"
	"net/http"
	"strings"
)

// リクエスト XML データ
const requestXML = `<?xml version="1.0" encoding="UTF-8"?><S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
    <S:Header/>
    <S:Body>
        <ns2:echo xmlns:ns2="http://yukari.voiceroid.ws.oyasirazu.dip.jp/">
            <message>VOICE_MESSAGE</message>
        </ns2:echo>
    </S:Body>
</S:Envelope>`

// 置換の目印
const replaceStr = "VOICE_MESSAGE"

// レスポンス XML データ
// const responseXML = `<?xml version="1.0" encoding="UTF-8"?><S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
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
	template   string
	replaceStr string
	message    string
}

// NewRequest is create new request Object.
func NewRequest(message string) *request {
	req := new(request)
	req.template = requestXML
	req.replaceStr = replaceStr
	req.message = message
	return req
}

// BuildXML is build and request xml string.
func (r *request) BuildXML() string {
	str := strings.Replace(r.template, r.replaceStr, r.message, 1)
	return str
}

// VoiceData Object.
type VoiceData struct {
	Type  string `xml:"Body>echoResponse>return>type"`
	Voice []byte `xml:"Body>echoResponse>return>voice"`
}

// Yukari web service url.
// 'Yokari' is test service path.
const defaultServiceURL = "http://voice:8080/Yokari/YukariService"

// YukariClient is Yukari web service client.
type YukariClient struct {
	serviceURL string
}

// NewYukariClient is create new YukariClient.
func NewYukariClient() *YukariClient {
	yc := new(YukariClient)
	yc.serviceURL = defaultServiceURL
	return yc
}

// NewYukariClientWithServiceURL is create new YukariClient.
func NewYukariClientWithServiceURL(serviceURL string) *YukariClient {
	yc := new(YukariClient)
	yc.serviceURL = serviceURL
	return yc
}

// Get is call Yukari web service and create VoiceData(response).
func (yc *YukariClient) Get(message string) *VoiceData {
	// リクエスト文字列を基に、リクエストオブジェクトを作成
	request := NewRequest(message)

	httpClient := new(http.Client)
	response, _ := httpClient.Post(yc.serviceURL, "text/xml; charset=utf-8", bytes.NewBufferString(request.BuildXML()))

	b, _ := ioutil.ReadAll(response.Body)

	// レスポンス文字列からレスポンスオブジェクトを作成
	voice := new(VoiceData)
	xml.Unmarshal([]byte(b), voice)

	// base64 デコード
	voice.Voice, _ = base64.StdEncoding.DecodeString(string(voice.Voice))

	return voice
}
