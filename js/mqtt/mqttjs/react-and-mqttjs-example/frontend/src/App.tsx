import { useState, FormEvent, ChangeEvent, Fragment } from 'react'
import './App.css'
import * as MQTT from 'mqtt';
import { IPacket } from 'mqtt-packet';

function App() {
  const MQTT_USER_NAME = import.meta.env.VITE_MQTT_USER_NAME;
  const MQTT_PASSWORD = import.meta.env.VITE_MQTT_PASSWORD;

  console.log(MQTT_USER_NAME);
  console.log(MQTT_PASSWORD);

  const [mqttUrl, setMqttUrl] = useState(import.meta.env.VITE_MQTT_URL)
  // 今回のロジックだと、 isConnected 作らなくても、 client の有無だけで判断して大丈夫そう
  const [client, setClient] = useState<MQTT.MqttClient|null>(null)
  const [isConnected, setIsConnected] = useState(false)
  const [publishMessage, setPublishMessage] = useState("")
  const [receivedMessages, setReceivedMessages] = useState<Array<string>>([])
  const [addSubscribingTopic, setAddSubscribingTopic] = useState("additional/subscribe/topic")
  const [subscribingTopics, setSubscribingTopics] = useState<Set<string>>(new Set(["testtopic/#"]))
  const [publishTopic, setPublishTopic] = useState("testtopic/1")

  const KEEPALIVE_TIME = 30;

  const connect = () => {
    console.log('connect : ');
    console.log(`isConnected : ${isConnected}`);
    if (!isConnected) {
      // クライアントインスタンスの作成
      let newClient = MQTT.connect(mqttUrl, {
        username: MQTT_USER_NAME,
        password: MQTT_PASSWORD,
        keepalive: KEEPALIVE_TIME
      });
      setClient(newClient);
      console.log(`connecting ${mqttUrl}...`);

      newClient.on('connect', () => {
        console.log(`connected ${mqttUrl}`);
        setIsConnected(true);

        subscribingTopics.forEach(topic => {
          newClient.subscribe(topic);
        });
      });

      newClient.on('message', (topic: string, payload: Buffer, packet: IPacket) => {
        console.log("onmessage");
        console.log(receivedMessages);
        let newMessages = receivedMessages.concat([`${topic} : ${payload.toString()}`]);
        console.log(newMessages);
        setReceivedMessages(prevMessages => prevMessages.concat([`${topic} : ${payload.toString()}`]));
      });
    }
  };

  const disconnect = () => {
    if (client) {
      client.end();
    }
    setIsConnected(false);
  };

  const handleChangeMqttUrl = (event : ChangeEvent<HTMLInputElement>) => {
    console.log(event);
    console.log('handleChangeMqttUrl :');
    let newValue = event.currentTarget.value;
    setMqttUrl(newValue || '');
  };

  const handleSubmitConnect = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    console.log('handleSubmitConnect : ');
    console.log(event);
    if (isConnected) {
      disconnect();
    } else {
      connect();
    }
  };

  const handleChangeSendMessage = (event : ChangeEvent<HTMLTextAreaElement>) => {
    console.log(event);
    console.log('handleChangeSendMessage :');
    setPublishMessage(event.currentTarget.value || '');
  };

  const handleChangeSendTopic = (event : ChangeEvent<HTMLInputElement>) => {
    console.log('handldeChangeSendMessage :');
    console.log(event);
    let newValue = event.currentTarget.value;
    setPublishTopic(newValue || '');
  };

  const handleSubmitMessage = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    console.log('handleSubmitMessage : ');
    console.log(event);
    console.log(`publishTopic: ${publishTopic}`);
    console.log(`publishMessage: ${publishMessage}`);
    if (client) {
      client.publish(publishTopic, publishMessage)
    }
  };

  const handleChangeSubscribeTopic = (event : ChangeEvent<HTMLInputElement>) => {
    console.log('handleChangeSubscribeTopic :');
    console.log(event);
    let newValue = event.currentTarget.value;
    setAddSubscribingTopic(prevTopic => newValue || '');
  };

  const handleSubmitAddSubscribeTopic = (event : ChangeEvent<HTMLFormElement>) => {
    event.preventDefault();
    console.log('handleSubmitAddSubscribeTopic :');
    console.log(event);
    if (client) {
      client.subscribe(addSubscribingTopic);
    }
    setSubscribingTopics(prevTopics => {
      let newSet = new Set(prevTopics);
      newSet.add(addSubscribingTopic);
      return newSet;
    });
  };

  const handleDeleteSubscribeTopic = (event : React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    console.log('handleDeleteSubscribeTopic :');
    console.log(event);
    let targetTopic = event.currentTarget.value;
    if (client) {
      client.unsubscribe(targetTopic);
    }
    setSubscribingTopics(prevTopics => {
      let newSet = new Set(prevTopics);
      newSet.delete(targetTopic);
      return newSet;
    });
  };

  return (
    <div className="App">
      <h1>React MQTTJS Example</h1>
      <section>
        <h2>Connection</h2>
        <label>{
          isConnected ?
            `connected to ${mqttUrl}.`
            :
            "not connected."
        }</label>
        <form onSubmit={handleSubmitConnect}>
          <input type="text" name="mqttUrl" onChange={handleChangeMqttUrl} value={mqttUrl}></input>
          <button type="submit">{
            isConnected ?
              'disconnect'
              :
              'connect'
          }</button>
        </form>
      </section>
      <section>
        <h2>Publish</h2>
        <form onSubmit={handleSubmitMessage}>
          <div>
            <label>topic: </label><input type="text" onChange={handleChangeSendTopic} value={publishTopic}></input>
          </div>
          <div>
            <label>message: </label><textarea onChange={handleChangeSendMessage}></textarea>
          </div>
          <button type="submit">send</button>
        </form>
      </section>
      <section>
        <h2>Subscribe</h2>
        <form onSubmit={handleSubmitAddSubscribeTopic}>
          <label>add subscribe topic: </label><input type="text" onChange={handleChangeSubscribeTopic} value={addSubscribingTopic}></input>
          <button type="submit">start subscribe</button>
        </form>
        <section>
          <h2>subscribing topics</h2>
          <ul>
            {[...subscribingTopics].map((e) => <Fragment key={e}><li>{e}<button value={e} onClick={handleDeleteSubscribeTopic}>delete</button></li></Fragment>)}
          </ul>
        </section>
        <section>
          <h2>received messages:</h2>
          <ul>
            {receivedMessages.map((e,i) => <li key={i}>{e}</li>)}
          </ul>
        </section>
      </section>
    </div>
  )
}

export default App
