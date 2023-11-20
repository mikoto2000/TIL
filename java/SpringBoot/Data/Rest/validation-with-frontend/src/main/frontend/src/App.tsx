import { useEffect, useState } from 'react'
import './App.css'

import { ItemEntityControllerApi } from './api/index';
import { ApiErrorResponse, extractApiErrorResponse } from './api/util/Error';

function App() {
  const [items, setItems] = useState("")
  const [getErrors, setGetErrors] = useState<ApiErrorResponse | null>(null)
  const [postErrors, setPostErrors] = useState<ApiErrorResponse | null>(null)

  useEffect(() => {
    (async () => {
      const client = new ItemEntityControllerApi();

      try {
        const items = await client.getCollectionResourceItemGet1();
        setItems(JSON.stringify(items.data._embedded?.items));
      } catch (error) {
        const aer = extractApiErrorResponse(error);
        setGetErrors(aer);
      }

      // try {
      //   await client.postCollectionResourceItemPost({ itemRequestBody: { } });
      // } catch (error) {
      //   const aer = extractApiErrorResponse(error);
      //   setPostErrors(aer);
      // }

      try {
        await client.patchItemResourceItemPatch({ id: "1", itemRequestBody: { orders: ["orders/1"]} });
      } catch (error) {
        const aer = extractApiErrorResponse(error);
        setPostErrors(aer);
      }
    })();
  }, []);

  return (
    <>
      <div>
        <h1>Items:</h1>
        <p>{items}</p>
        <h1>GetErrors:</h1>
        <ul>
          {getErrors?.errors.map((e) => <li>{e.targetName} : {e.message}</li>)}
        </ul>
        <h1>PostErrors:</h1>
        <ul>
          {postErrors?.errors.map((e) => <li>{e.targetName} : {e.message}</li>)}
        </ul>
      </div>
    </>
  )
}

export default App
