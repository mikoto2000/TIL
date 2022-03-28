import * as monaco from 'monaco-editor/esm/vs/editor/editor.api';
import { setDiagnosticsOptions } from 'monaco-yaml';

(function () {
    // create div to avoid needing a HtmlWebpackPlugin template
    const div = document.createElement('div');
    div.id = 'root';
    div.style = 'width:800px; height:600px; border:1px solid #ccc;';

    document.body.appendChild(div);
})();

async function init() {

  const SCHEMA_URL = './schema/Bookmarks.json';

  async function getJsonFromUri(jsonUri) {
    const response = await fetch(jsonUri);
    const json = await response.json();
    return json;
  }

  const schema = await getJsonFromUri(SCHEMA_URL);

  setDiagnosticsOptions({
    enableSchemaRequest: true,
    hover: true,
    completion: true,
    format: true,
    validate: true,
    schemas: [
      {
        uri: SCHEMA_URL,
        fileMatch: ['*'],
        schema: schema
      }
    ]
  });

  monaco.editor.create(document.getElementById('root'), {
    value: "",
    language: 'yaml',
  });
}

document.addEventListener('DOMContentLoaded', () => {
  init();
});

