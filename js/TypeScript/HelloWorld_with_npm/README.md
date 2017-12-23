```
> npm install -g typescript
> npm init
> vim package.json
> more package.json
{
  "name": "hello_world",
  "version": "1.0.0",
  "description": "",
  "main": "HelloWorld.js",
  "scripts": {
    "build": "tsc HelloWorld.ts",
    "clean": "del HelloWorld.js",
    "test": "echo \"Error: no test specified\" && exit 1"
  },
  "author": "mikoto2000",
  "license": "MIT"
}
> npm run build
> node ./HelloWorld.js
Hello, Mikoto Ohyuki!
> npm run clean
```
