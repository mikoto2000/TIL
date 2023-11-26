import { useState } from 'react'

import { JsonForms } from '@jsonforms/react';
import {
  materialCells,
  materialRenderers,
} from '@jsonforms/material-renderers';
import { ErrorObject } from 'ajv';
import userSchema from './User.schema.json';

import './App.css'
import { User } from './User.ts';

function App() {

  const initialUser: User = {
    userName: ""
  };

  const [data, setData] = useState<User>(initialUser);
  const [errors, setErrors] = useState<ErrorObject[]>([]);

  const handleFormChange = (event: {data: User, errors: ErrorObject[]}) => {
    setData(event.data);
    setErrors(event.errors);
  }

  return (
    <>
      <JsonForms
        schema={userSchema}
        data={data}
        onChange={handleFormChange}
        cells={materialCells}
        renderers={materialRenderers}
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
