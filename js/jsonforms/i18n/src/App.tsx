import { useState, useMemo } from 'react'

import { JsonForms } from '@jsonforms/react';
import {
  materialCells,
  materialRenderers,
} from '@jsonforms/material-renderers';
import { ErrorObject } from 'ajv';
import localize_en from 'ajv-i18n/localize/en';
import localize_ja from 'ajv-i18n/localize/ja';
import userSchema from './User.schema.json';
import messages from './messages.json';

import './App.css'
import { User } from './User.ts';

type SpportedLocale = 'ja' | 'en';

function App() {

  const localize = {
    'ja': localize_ja,
    'en': localize_en
  };

  const initialUser: User = {
    userName: ""
  };

  const [currentLocale, setCurrentLocale] = useState<SpportedLocale>('en');
  const [data, setData] = useState<User>(initialUser);
  const [errors, setErrors] = useState<ErrorObject[]>([]);

  const handleFormChange = (event: {data: User, errors: ErrorObject[]}) => {
    setData(event.data);
    setErrors(event.errors);
  }

  const createTranslator = (locale) => (key, defaultMessage) => {
    const localedMessage = messages[locale];
    const message = getMessage(localedMessage, key);
    return message ? message : defaultMessage;
  };

  const extractLastPropertyName = (path) =>{
    const regex = /properties\/([^\/]+)(?!.*properties\/)/;
    const match = path.match(regex);
    return match ? match[1] : null;
  }

  const createErrorTranslator = (locale) => (errorObject, translate, uischema) => {
    console.log(errorObject);
    let tmp = structuredClone(errorObject);
    localize[locale]([tmp]);
    let key = extractLastPropertyName(errorObject.schemaPath);
    return translator(key + ".label", "") + " " + tmp.message;
  };

  const getMessage = (target, key) => {

    const splitedKey = key.split('.');
    let currentTarget = target;
    for (const k of splitedKey) {
      currentTarget = currentTarget[k];
      if (!currentTarget) {
        return undefined;
      }
    }

    return currentTarget;

  };

  const translator = useMemo(() => createTranslator(currentLocale), [currentLocale]);
  const errorTranslator = useMemo(() => createErrorTranslator(currentLocale), [currentLocale]);

  return (
    <>
      <label>locale: </label>
      <select value={currentLocale} onChange={(e) => { setCurrentLocale(e.target.value) }}>
        <option value="en">en</option>
        <option value="ja">ja</option>
      </select>
      <JsonForms
        schema={userSchema}
        data={data}
        onChange={handleFormChange}
        cells={materialCells}
        renderers={materialRenderers}
        i18n={{locale: currentLocale, translate: translator, translateError: errorTranslator}}
      />

      <h1>data:</h1>
      <p>
        {JSON.stringify(data)}
      </p>

      <h1>errors:</h1>
      <p>
        {JSON.stringify(errors)}
      </p>
    </>
  )
}

export default App
