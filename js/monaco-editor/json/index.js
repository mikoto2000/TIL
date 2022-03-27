import * as monaco from 'monaco-editor/esm/vs/editor/editor.api';

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

  const schemaUri = monaco.Uri.parse(SCHEMA_URL);
  const schema = await getJsonFromUri(SCHEMA_URL);

  monaco.languages.json.jsonDefaults.setDiagnosticsOptions({
    validate: true,
    schemas: [
      {
        uri: schemaUri,
        fileMatch: ['*'],
        schema: schema
      }
    ]
  });

  monaco.editor.create(document.getElementById('root'), {
    value: "",
    language: 'json',
  });
}

document.addEventListener('DOMContentLoaded', () => {
  init();
});
